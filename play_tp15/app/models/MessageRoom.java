package models;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.WebSocket;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * A chat room is an Actor.
 */
public class MessageRoom extends UntypedActor {

    // Default room.
    public static ActorRef ROOM = Akka.system().actorOf(Props.create(MessageRoom.class));

    /**
     * Join the default room.
     */
    public static void join(final String username, WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out)
            throws Exception {

        // Send the Join message to the room
        String result = (String) Await.result(ask(ROOM, new Join(username, out), 1000), Duration.create(1, SECONDS));

        if ("OK".equals(result)) {

            // For each event received on the socket,
            in.onMessage(new Callback<JsonNode>() {
                public void invoke(JsonNode event) {

                    // Send a Talk message to the room.
                    ROOM.tell(new Talk(username, event.get("text").asText()), null);

                }
            });

            // When the socket is closed.
            in.onClose(new Callback0() {
                public void invoke() {

                    // Send a Quit message to the room.
                    ROOM.tell(new Quit(username), null);

                }
            });
        } else {
            // Cannot connect, create a Json error.
            ObjectNode error = Json.newObject();
            error.put("error", result);

            // Send the error to the socket.
            out.write(error);
        }
    }

    // Members of this room.
    Map<String, WebSocket.Out<JsonNode>> members = new HashMap<String, WebSocket.Out<JsonNode>>();

    public void onReceive(Object message) throws Exception {

        if (message instanceof Join) {

            // Received a Join message
            Join join = (Join) message;

            // Check if this username is free.
            if (members.containsKey(join.username)) {
                getSender().tell("This username is already used", getSelf());
            } else {
                members.put(join.username, join.channel);
                getSender().tell("OK", getSelf());
            }

        } else if (message instanceof Talk) {

            // Received a Talk message
            Talk talk = (Talk) message;

            notifyAllButMe(talk.username, talk.text);

        } else if (message instanceof Quit) {

            // Received a Quit message
            Quit quit = (Quit) message;

            members.remove(quit.username);
        } else {
            unhandled(message);
        }

    }

    // Send a Json event to all members
    public void notifyAllButMe(String me, String text) {
        for (String username : members.keySet()) {
            if (username.equals(me)) {
                continue;
            }
            ObjectNode event = Json.newObject();
            event.put("message", text);
            members.get(username).write(event);
        }
    }

    // -- Messages

    public static class Join {

        final String username;
        final WebSocket.Out<JsonNode> channel;

        public Join(String username, WebSocket.Out<JsonNode> channel) {
            this.username = username;
            this.channel = channel;
        }

    }

    public static class Talk {

        final String username;
        final String text;

        public Talk(String username, String text) {
            this.username = username;
            this.text = text;
        }

    }

    public static class Quit {

        final String username;

        public Quit(String username) {
            this.username = username;
        }

    }

}