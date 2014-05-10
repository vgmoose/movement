package com.vgmoose.movement2;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.os.Build;

public class Main extends ActionBarActivity {

	public static boolean debug = false;
	float zoomifier = (float) 1;
	GamePanel drawView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		String[] images = {"link_sprites.png","link_red.png","blue_links.png"};
		
		// Give these image paths to Player 
		Player.setImages(this, images);
		
		drawView = new GamePanel(this);
		
		RelativeLayout layout2 = (RelativeLayout)findViewById(R.id.fullscreen_content);
		layout2.addView(drawView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, 0, 0, "Debug Mode Toggle");
		menu.add(0, 1, 0, "Zoom in");
		menu.add(0, 2, 0, "Zoom out");

		return true;
	}

	@SuppressLint("NewApi")
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
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
