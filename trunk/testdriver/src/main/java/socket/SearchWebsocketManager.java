package socket;

import java.io.StringReader;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import twitter.MyTweetListener;
import twitter.TwitterMaschine;

@ServerEndpoint("/tweet")
public class SearchWebsocketManager {
	@Inject
	private SearchSessionHandler handler;
	
	@Inject
	private TwitterMaschine twitter;
	
	@OnOpen
	public void open(Session session) {
		handler.addSession(session);
	}

	@OnClose
	public void close(Session session) {
		handler.removeSession(session);
	}

	@OnError
	public void onError(Throwable error) {
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject jsonMessage = reader.readObject();
		
		if("add".equals(jsonMessage.getString("action"))) {
			twitter.getTwitter().registerQuery(jsonMessage.getString("name"), session.getId(), new MyTweetListener(session));
		}
	}
}
