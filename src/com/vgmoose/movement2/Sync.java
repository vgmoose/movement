package com.vgmoose.movement2;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Sync 
{
	Socket s;
	
	public void connect(Main c)
	{
		try {
			s = new Socket("http://192.168.1.121", 3001);
		} catch (UnknownHostException e) {
			
		} catch (IOException e) {
			
		}
	}
}
