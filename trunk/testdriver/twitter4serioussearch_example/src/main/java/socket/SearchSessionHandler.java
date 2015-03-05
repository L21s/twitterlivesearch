package socket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

@ApplicationScoped
public class SearchSessionHandler {
	private final Set<Session> sessions = new HashSet<>();
    
	private final Set<String> tweets = new HashSet<>();
	
	public void addSession(Session session) {
        sessions.add(session);
        for (String tweet : tweets) {
            JsonObject addMessage = createAddMessage(tweet);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
    
    public void addTweet(String t) {
    	tweets.add(t);
    	JsonObject addMessage = createAddMessage(t);
        sendToAllConnectedSessions(addMessage);
    }
    
    public String getTweetById(int id) {
    	return null;
    }
    
    private JsonObject createAddMessage(String tweet) {
        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("name", tweet)
                .build();
        return addMessage;
    }
    
    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }
    
    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(SearchSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
