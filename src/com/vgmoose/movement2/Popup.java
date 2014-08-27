package com.vgmoose.movement2;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
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
				Toast t = new Toast(m);
				t.setText(string);
				t.setDuration(Toast.LENGTH_SHORT);
				t.show();
			}
		});
	}
}
