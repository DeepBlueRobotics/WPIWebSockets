package org.carlmontrobotics.wpiws.connection;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * Sets up Websockets and forwards their calls to {@link ConnectionProcessor}
 */
public final class WSConnection {

    /**
     * Connects to a WebSocket server on the given URI
     * @param uri the URI to which to connect
     * @return a running WebSocketClient connected to the given server
     */
    public static RunningObject<WebSocketClient> connect(URI uri, boolean autoReconnect) {
        return new RunningObject<>(new WSClientImpl(uri, autoReconnect));
    }

    /**
     * Connects to a the WPI HALSim server using either its default location or the environment variables: HALSIMWS_HOST, HALSIMWS_PORT, and HALSIMWS_URI
     * @return a running WebSocketClient connected to the WPI HALSim server
     */
    public static RunningObject<WebSocketClient> connectHALSim(boolean autoReconnect) throws URISyntaxException {
        Map<String, String> env = System.getenv();
        String host = env.containsKey("HALSIMWS_HOST") ? env.get("HALSIMWS_HOST") : "localhost";
        String port = env.containsKey("HALSIMWS_PORT") ? env.get("HALSIMWS_PORT") : "3300";
        String resource = env.containsKey("HALSIMWS_URI") ? env.get("HALSIMWS_URI") : "/wpilibws";
        URI uri = new URI("ws://" + host + ":" + port + resource);
        RunningObject<WebSocketClient> client = connect(uri, autoReconnect);
        client.object.setConnectionLostTimeout(0);
        return client;
    }

    /**
     * Starts a WebSocketServer on the given address
     * @param addr the address on which to start the server
     * @return a running WebSocketServer on the given address
     */
    public static RunningObject<WebSocketServer> startServer(InetSocketAddress addr) {
        return new RunningObject<>(new WSServerImpl(addr));
    }

    /**
     * Starts a server on either the default location of the WPI HALSim or a generated address based on the environment variables: HALSIMWS_HOST and HALSIMWS_PORT
     * @return a running WebSocketClient connected to the WPI HALSim server
     */
    public static RunningObject<WebSocketServer> startHALSimServer() {
        Map<String, String> env = System.getenv();
        int port;
        if(env.containsKey("HALSIMWS_PORT")) {
            try {
                port = Integer.parseInt(env.get("HALSIMWS_PORT"));
            } catch(NumberFormatException e) {
                System.err.println("Invalid port in HALSIMWS_PORT defaulting to 3300");
                port = 3300;
            }
        } else {
            port = 3300;
        }
        RunningObject<WebSocketServer> server = startServer(new InetSocketAddress(port));
        server.object.setConnectionLostTimeout(0);
        return server;
    }

    private WSConnection() {}

    /**
     * A WebSocketClient which forwards its method calls to {@link ConnectionProcessor}
     */
    private static class WSClientImpl extends WebSocketClient {

        public boolean autoReconnect;
        public int retryTimeout;

        public WSClientImpl(URI serverURI, boolean autoReconnect) {
            super(serverURI);
            this.autoReconnect = autoReconnect;
            retryTimeout = 1;
        }

        @Override
        public void onOpen(ServerHandshake handshake) {
            retryTimeout = 0;
            ConnectionProcessor.onOpen(this);
        }

        @Override
        public void onMessage(String message) {
            ConnectionProcessor.onMessage(this, message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            ConnectionProcessor.onClose(this, code, reason, remote);
            if(autoReconnect && remote) {
                System.out.println("Reconnecting...");
                RunningObject.start(this::tryReconnect);
            }
        }

        @Override
        public void onError(Exception e) {
            if(e instanceof ConnectException) {
                //Connection Refused
                System.err.println("Error: Connection Refused! Trying again in " + retryTimeout + " second(s)");
                int waitSecs = retryTimeout++;
                if(retryTimeout > 5) {
                    retryTimeout = 5;
                }
                RunningObject.start(() -> tryReconnect(waitSecs*1000));
            } else {
                ConnectionProcessor.onError(this, e);
            }
        }

        public void tryReconnect() {
            tryReconnect(0);
        }

        public void tryReconnect(long delay) {
            try {
                Thread.sleep(delay);
                reconnectBlocking();
            } catch(InterruptedException e) {
                System.err.println("Interrupted while reconnecting!");
            }
        }

    }

    /**
     * A WebSocketServer which forwards its method calls to {@link ConnectionProcessor}
     */
    private static class WSServerImpl extends WebSocketServer {

        public WSServerImpl(InetSocketAddress addr) {
            super(addr);
        }

        @Override
        public void onOpen(WebSocket socket, ClientHandshake handshake) {
            ConnectionProcessor.onOpen(socket);
        }

        @Override
        public void onClose(WebSocket socket, int code, String reason, boolean remote) {
            ConnectionProcessor.onClose(socket, code, reason, remote);
        }

        @Override
        public void onMessage(WebSocket socket, String message) {
            ConnectionProcessor.onMessage(socket, message);
        }

        @Override
        public void onError(WebSocket socket, Exception e) {
            ConnectionProcessor.onError(socket, e);
        }

        @Override
        public void onStart() {
            System.out.println("Starting Server on: " + getAddress().toString());
        }

    }

}
