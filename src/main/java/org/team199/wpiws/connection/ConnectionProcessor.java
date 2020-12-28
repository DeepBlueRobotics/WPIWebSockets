package org.team199.wpiws.connection;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.java_websocket.WebSocket;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonKey;

/**
 * Manages Websocket Connections
 */
public final class ConnectionProcessor {
    
    private static final CopyOnWriteArrayList<WebSocket> sockets = new CopyOnWriteArrayList<>();
    private static Consumer<Runnable> threadExecutor = Runnable::run;
    private static Object threadExecutorLock = new Object();
    
    /**
     * Allows custom handling of message processing. This allows for syncronous execution of callbacks with another thread.
     * @param executor A Consumer which will be fed Runnable calls which forward messages to the rest of the code
     */
    public static void setThreadExecutor(Consumer<Runnable> executor) {
        synchronized(threadExecutorLock) {
            threadExecutor = executor;
        }
    }

    /**
     * Called when a WebSocket is opened
     * @param socket the WebSocket which has been opened
     */
    public static void onOpen(WebSocket socket) {
        System.out.println(getSocketInfo(socket) + " connected");
        sockets.add(socket);
    }

    /**
     * Called when a WebSocket is closed
     * @param socket the WebSocket which has been closed
     */
    public static void onClose(WebSocket socket, int code, String reason, boolean remote) {
        System.out.println(getSocketInfo(socket) + " disconnected by " + (remote ? "remote" : "local") + " host (" + code + "): " + reason);
        sockets.remove(socket);
    }

    public static final Function<Object, String> STRING_CAST = obj -> (String)obj;

    enum MessageKeys implements JsonKey {
        DEVICE,
        TYPE,
        DATA;

        @Override
        public String getKey() {
            return this.name().toLowerCase();
        }

        @Override
        public Object getValue() {
            return new Exception("Message is missing required key: " + getKey());
        }
    }

    /**
     * Called when a WebSocket recieves a message
     * @param socket the WebSocket which recieve the message
     * @param message the message
     */
    public static void onMessage(WebSocket socket, String message) {
        try {
            JsonObject jsonMessage = (JsonObject)Jsoner.deserialize(message);
            String device = jsonMessage.getString(MessageKeys.DEVICE);
            String type = jsonMessage.getString(MessageKeys.TYPE);
            Map<String, String> dataObject = jsonMessage.getMap(MessageKeys.DATA);
            List<WSValue> data = dataObject.entrySet().stream()
                .map(entry -> new WSValue(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
            threadExecutor.accept(() -> MessageProcessor.process(device, type, data));
        } catch(Exception e) {
            System.err.println("Invalid Message from: " + getSocketInfo(socket));
            e.printStackTrace(System.err);
        }
    }

    /**
     * Called when an error occurs while processing data for a Websocket
     * @param socket the WebSocket on which the error occured
     * @param e the Exception which was thrown
     */
    public static void onError(WebSocket socket, Exception e) {
        System.err.println("Error on: " + (socket == null ? "<null>" : getSocketInfo(socket)));
        e.printStackTrace(System.err);
    }
    
    /**
     * Brodcasts a message to all connected WebSockets
     * @param device the device name of the device which sent the message
     * @param type the device type of the device which sent the message
     * @param data the value of that device which have been modified
     */
    public static void brodcastMessage(Object device, String type, WSValue data) {
        brodcastMessage(device, type, Arrays.asList(new WSValue[] {data}));
    }
    
    /**
     * Brodcasts a message to all connected WebSockets
     * @param device the device name of the device which sent the message
     * @param type the device type of the device which sent the message
     * @param data the values of that device which have been modified
     */
    @SuppressWarnings("all")
    public static void brodcastMessage(Object device, String type, List<WSValue> data) {
        JsonObject message = new JsonObject();
        message.put(MessageKeys.DEVICE, device);
        message.put(MessageKeys.TYPE, type);
        JsonObject messageData = new JsonObject();
        data.forEach(value -> messageData.put(value.getKey(), value.getValue()));
        message.put(MessageKeys.DATA, messageData);
        String messageJson = message.toJson();
        brodcastMessage(messageJson);
    }
    
    /**
     * Brodcasts a message to all connected WebSockets
     * @param message the message to send
     */
    public static void brodcastMessage(String message) {
        sockets.stream().filter(WebSocket::isOpen).forEach(socket -> socket.send(message));
    }
    
    /**
     * Retrieves info about the specifed WebSocket to display to the user
     * @param socket the WebSocket
     */
    public static String getSocketInfo(WebSocket socket) {
        return "local: " + (socket.getLocalSocketAddress() == null ? "<unknown>" : socket.getLocalSocketAddress().toString()) + " remote: " +
            (socket.getRemoteSocketAddress() == null ? "<disconnected>" : socket.getRemoteSocketAddress().toString());
    }
    
    private ConnectionProcessor() {}
}
