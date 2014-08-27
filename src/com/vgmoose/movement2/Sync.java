package com.vgmoose.movement2;

import java.io.IOException;
import java.net.UnknownHostException;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.EngineIOException;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.IO.Options;
import com.github.nkzawa.socketio.client.Socket;

public class Sync 
{
	Socket socket;
	boolean connected;
	
	public void alertErrors(Main m, Object... args)
	{
			for (Object o : args)
			{
				Popup.alert(m, o.getClass().toString(), o.toString());
			}
	}
	public void connect(final Main m) throws Exception
	{
		Options opts = new IO.Options();
		opts.reconnection = false;
		
		socket = IO.socket("http://192.168.1.121:3001", opts);
		
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

		  @Override
		  public void call(Object... args) {
			Popup.toast(m, "Connected Successfully");
		    socket.emit("foo", "hi");
		    socket.disconnect();
		  }

		}).on("event", new Emitter.Listener() {

		  @Override
		  public void call(Object... args) {}

		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

		  @Override
		  public void call(Object... args) {}

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
