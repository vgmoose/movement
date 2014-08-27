package com.vgmoose.movement2;

import java.net.URISyntaxException;

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

	public Sync(final Main m) throws URISyntaxException
	{
		socket = IO.socket("http://octo.vgmoose.com:3005");

		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				Popup.toast(m, "Connected Successfully");
				connected = true;
				m.drawView.getActivePlayer().sendInitialEvent(m.syncMaster);
			}

		}).on("id_assign", new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				try {
					m.drawView.getActivePlayer().setId((Integer) ((JSONObject) args[0]).get("id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).on("drop_player", new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				try {
					m.drawView.removePlayerById((Integer) ((JSONObject) args[0]).get("id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).on("recv_move", new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				try {
					JSONObject move = ((JSONObject) args[0]);

					int id = (Integer) move.get("id");

					// check if the id exists
					if (!m.drawView.doesIdExist(id))
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

						m.drawView.setDestById(id, destX, destY);
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
				Popup.toast(m, "Disconnected!");
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
	}

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
		{
			socket.disconnect();
			return;
		}

		socket.connect();
	}
}
