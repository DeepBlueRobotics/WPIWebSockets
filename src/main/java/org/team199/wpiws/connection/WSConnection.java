package org.team199.wpiws.connection;

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
    public static RunningObject<WebSocketClient> connect(URI uri) {
        return new RunningObject<>(new WSClientImpl(uri));
    }
    
    /**
     * Connects to a the WPI HALSim server using either its default location or the environment variables: HALSIMWS_HOST, HALSIMWS_PORT, and HALSIMWS_URI 
     * @return a running WebSocketClient connected to the WPI HALSim server
     */
    public static RunningObject<WebSocketClient> connectHALSim() throws URISyntaxException {
        Map<String, String> env = System.getenv();
        String host = env.containsKey("HALSIMWS_HOST") ? env.get("HALSIMWS_HOST") : "localhost";
        String port = env.containsKey("HALSIMWS_PORT") ? env.get("HALSIMWS_PORT") : "8080";
        String resource = env.containsKey("HALSIMWS_URI") ? env.get("HALSIMWS_URI") : "/wpilibws";
        URI uri = new URI("ws://" + host + ":" + port + resource);
        RunningObject<WebSocketClient> client = connect(uri);
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
                System.err.println("Invalid port in HALSIMWS_PORT defaulting to 8080");
                port = 8080;
            }
        } else {
            port = 8080;
        }
        RunningObject<WebSocketServer> server = startServer(new InetSocketAddress(port));
        server.object.setConnectionLostTimeout(0);
        return server;
    }
    
    private WSConnection() {}
    
    /**
     * A WebsocketClient which forwards its method calls to {@link ConnectionProcessor}
     */
    private static class WSClientImpl extends WebSocketClient {

        public WSClientImpl(URI serverURI) {
            super(serverURI);
        }
        
        @Override
        public void onOpen(ServerHandshake handshake) {
            ConnectionProcessor.onOpen(this);
        }

        @Override
        public void onMessage(String message) {
            ConnectionProcessor.onMessage(this, message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            ConnectionProcessor.onClose(this, code, reason, remote);
        }

        @Override
        public void onError(Exception e) {
            ConnectionProcessor.onError(this, e);
        }
        
    }
    
    /**
     * A WebsocketServer which forwards its method calls to {@link ConnectionProcessor}
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
