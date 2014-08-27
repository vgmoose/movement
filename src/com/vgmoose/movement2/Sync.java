package com.vgmoose.movement2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.IO.Options;
import com.github.nkzawa.socketio.client.Socket;

public class Sync 
{
	Socket socket;
	boolean connected = false;
	
	public void alertErrors(Main m, Object... args)
	{
			for (Object o : args)
			{
				Popup.alert(m, o.getClass().toString(), o.toString());
			}
	}
	public void connect(final Main m) throws Exception
	{
		if (connected)
			socket.disconnect();
		
		Options opts = new IO.Options();
		opts.reconnection = false;
		
		socket = IO.socket("http://octo.vgmoose.com:3005", opts);
		
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

		  @Override
		  public void call(Object... args) {
			Popup.toast(m, "Connected Successfully");
			
			JSONObject playerInfo = new JSONObject();
			try {
				playerInfo.put("kind", m.drawView.getActivePlayer().getType());
				playerInfo.put("x", m.drawView.getActivePlayer().x);
				playerInfo.put("y", m.drawView.getActivePlayer().y);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    socket.emit("add_player", playerInfo);
		    connected = true;
		  }

		}).on("id_assign", new Emitter.Listener() {

		  @Override
		  public void call(Object... args) {
			  try {
				m.drawView.getActivePlayer().setId((Integer) ((JSONObject) args[0]).get("id"), m.drawView);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }

		}).on("get_move", new Emitter.Listener() {

			  @Override
			  public void call(Object... args) {
				  try {
					JSONObject move = ((JSONObject) args[0]);
					
					int id = (Integer) move.get("id");
					
					// if there's a kind attached with this event, this is a new player
					if (move.has("kind"))
					{
						int kind = (Integer) move.get("kind");
						m.drawView.addPlayer(id, kind);
					}
					
					int x = (Integer) move.get("x");
					int y = (Integer) move.get("y");
					
					m.drawView.moveById(id, x, y);
					
					// if there's a destination included with this event
					if (move.has("dest_x") && move.has("dest_y"))
					{
						int destX = (Integer) move.get("dest_x");
						int destY = (Integer) move.get("dest_y");
						
						m.drawView.setDestById(id, x, y);
					}
										
				} catch (JSONException e) {
					e.printStackTrace();
				}
			  }

			}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

		  @Override
		  public void call(Object... args) {
			  m.drawView.dropPlayers();
			  connected = false;
			  Popup.alert(m, "Disconnected!");
		  }

		}).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				alertErrors(m, args);
			}
		}).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				alertErrors(m, args);
			}
		});

		socket.connect();
	}
}
