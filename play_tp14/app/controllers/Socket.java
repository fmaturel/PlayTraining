package controllers;

import java.util.UUID;

import models.MessageRoom;
import play.mvc.Controller;
import play.mvc.WebSocket;

import com.fasterxml.jackson.databind.JsonNode;

public class Socket extends Controller {

    /**
     * Handle the chat websocket.
     */
    public static WebSocket<JsonNode> connect() {
        return new WebSocket<JsonNode>() {

            // Called when the Websocket Handshake is done.
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {

                // Join the chat room.
                try {
                    MessageRoom.join(UUID.randomUUID().toString(), in, out);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }
}