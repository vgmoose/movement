package com.vgmoose.movement2;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Looper;
import android.util.Log;

public class Popup 
{
	public static void alert(final Main ctx, final String title, final String message)
	{
		ctx.runOnUiThread(new Runnable() {
		    public void run() {
		    	new AlertDialog.Builder(ctx)
		        .setTitle(title)
		        .setMessage(message)
		        .show();
				Log.v(title, message);
		    }
		});
		
	}
	
	public static void alert(Main ctx, String title)
	{
		alert(ctx, title, "");
	}
}
