package models;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.mvc.WebSocket;

public class Server {

    static {
        new Server(MessageRoom.ROOM);
    }

    private Server(ActorRef messageRoom) {

        // Create a Fake socket out for the robot that log events to the console.
        WebSocket.Out<JsonNode> serverChannel = new WebSocket.Out<JsonNode>() {

            public void write(JsonNode frame) {
                Logger.info(Json.stringify(frame));
            }

            public void close() {
            }
        };

        // Join the room
        messageRoom.tell(new MessageRoom.Join("Server", serverChannel), null);
    }

    public static void talk(String message) {
        MessageRoom.ROOM.tell(new MessageRoom.Talk("Server", message), null);
    }

}
