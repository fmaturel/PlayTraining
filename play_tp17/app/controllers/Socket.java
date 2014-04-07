package controllers;

import java.util.UUID;

import models.MessageRoom;
import play.Logger;
import play.mvc.Controller;
import play.mvc.WebSocket;

import com.fasterxml.jackson.databind.JsonNode;

public class Socket extends Controller {

    /**
     * Action non standard dédiée aux WebSockets
     */
    public static WebSocket<JsonNode> connect() {
        // On peut récupérer des objets de session ici si besoin!
        return new WebSocket<JsonNode>() {
            // Appelé quand le "Handshake" est réalisé lors de la première requête HTTP.
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {

                // Joindre la pièce dédiée aux messages.
                try {
                    MessageRoom.join(UUID.randomUUID().toString(), in, out);
                } catch (Exception ex) {
                    Logger.error("Error in WebSocket onReady", ex);
                }
            }
        };
    }
}