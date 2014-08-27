package com.vgmoose.movement2;

import java.net.URISyntaxException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class Main extends Activity {

	public static boolean debug = false;
	static float zoomifier = (float) 1;
	GamePanel drawView;
	int viewWidth, viewHeight;
	static Sync syncMaster;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// all spritesheet names go here
		String[] images = {"fat_boy.png", "fat_girl.png", "santa.png", 
				"santa_girl.png", "reindeer_boy.png", "evil_boy.png", "evil_girl.png"};
		
		// Give these image paths to Player 
		Player.setImages(this, images);
		
		drawView = new GamePanel(this);
		
		RelativeLayout layout2 = (RelativeLayout)findViewById(R.id.fullscreen_content);
		layout2.addView(drawView);
		
		// setup sync object
		try {
			syncMaster = new Sync(Main.this);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		menu.clear();
		
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, 0, 0, "Debug Mode Toggle");
		menu.add(0, 1, 0, "Zoom in");
		menu.add(0, 2, 0, "Zoom out");
		menu.add(0, 3, 0, syncMaster.connected? "Disconnect" : "Connect");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == 0) {
			debug = !debug;
			drawView.invalidate();
		} else if (id == 1){
			zoomifier += .5;
			drawView.setScaleX(zoomifier);
			drawView.setScaleY(zoomifier);
		} else if (id == 2){
			zoomifier -= .5;
			drawView.setScaleX(zoomifier);
			drawView.setScaleY(zoomifier);
		} else if (id == 3){
			try
			{
				syncMaster.connect(Main.this);
			}
			catch (Exception e)
			{
				Popup.alert(Main.this, "Connection Issue", "Unknown issue: " + e.getMessage());
			}
		}
		return super.onOptionsItemSelected(item);
	}


}
