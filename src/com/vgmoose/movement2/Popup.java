package com.vgmoose.movement2;

import android.app.AlertDialog;
import android.widget.Toast;

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
			}
		});

	}

	public static void alert(Main ctx, String title)
	{
		alert(ctx, title, "");
	}

	public static void toast(final Main m, final String string) 
	{
		m.runOnUiThread(new Runnable() {
			public void run() {
		Toast.makeText(m, string, Toast.LENGTH_SHORT).show();
			}
			}
		);
	}
}
