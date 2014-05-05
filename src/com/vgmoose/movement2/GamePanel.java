package com.vgmoose.movement2;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;


public class GamePanel extends View implements View.OnTouchListener
{
	// An array list is an object wrapper for an array, allows dynamic resizing
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player activePlayer;
	private int keyCode = -1;
	private Timer t;
	Paint p;
	Context ctx;

	public GamePanel(Context ctx)
	{
		super(ctx);
		this.ctx = ctx;
		// set the size of this canvas
//		super.setPreferredSize(new Dimension(500, 500));

		// set the mouse and key listener of this panel to this panel
//		super.addMouseListener(this);
//		super.addKeyListener(this);

		// give this panel focus so keystrokes apply to it
//		super.setFocusable(true);
//		super.requestFocusInWindow();

		// create a timer to update the player every x seconds
		t = new Timer();
		p = new Paint();
		p.setColor(Color.BLACK);
		
		setOnTouchListener(this);

		// this is a kind of weird thing, it's needed to not have a
		// strange delay when you hold down a key
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				updateTimer();
			}
		}, 0, 40);
	}

	// this method is called when someone calls repaint()
	public void onDraw(Canvas g)
	{
		// this call clears the canvas (it's like clrScreen)
//		super.paintComponent(g);

		if (!Main.debug)
		{
			// draw the background
			g.drawBitmap(Player.bg, 0, 0, p);
//			Paint p = new Paint();
//			
//			// draw rectangle in the top left
//			p.setColor(Color.rgb(250, 250, 178));
//			g.drawRect(0, 0, 330, 61, p);
//			p.setColor(Color.BLACK);

			// draw text strings 
			g.drawText("Click to create a new player at the mouse position", 3, 13, p);
			g.drawText("Click an existing player to mark them active", 3, 27, p);
			g.drawText("Use the arrow keys to move the active player", 3, 41, p);
			g.drawText("Right click to toggle debug information", 3, 55, p);
		}

		// draw each player in the arraylist
		// (they are passed the Graphics object, and then tell it how to draw them)
		for (Player p : players)
			p.draw(g);
	}

	public boolean onTouch(View v, MotionEvent e) 
	{
		// toggle debug mode when right mouse button is pressed
//		if (SwingUtilities.isRightMouseButton(e))
//		{
//			Movement.debug = !Movement.debug;
//			return;
//		}
		
		if (e.getAction() == MotionEvent.ACTION_UP)
		{
		
		// get the coordinates of the click
		int x = (int) (e.getX()/4*4);
		int y = (int) (e.getY()/4*4);

		// for each player, does the cursor lie in their coordinates?
		for (Player p : players)
		{
			// if so, make them the active player and return
			if (p.x < x+16 && p.x+32 > x-16 && y-16 < p.y+32 && y+16 > p.y)
			{
				activePlayer = p;
				return true;
			}
		}

		// if no one was found at the current click, make a new one

		// Create a new player and give the mouse coordinates
		// as well as how many players currently exist (size of players arraylist)
		Player p = new Player(players.size(), x-16, y-16);

		// add this player to the arraylist
		players.add(p);

		// redraw the canvas
		invalidate();

		// make this player active
		activePlayer = p;
		}
		else if (e.getAction() == MotionEvent.ACTION_DOWN)
		{
			int x = (int) (e.getX()/4*4);
			int y = (int) (e.getY()/4*4);
			
			
		}
		
		return true;

	}

	public void keyPressed(KeyEvent e) 
	{
		// if a key is down, don't repeat the key press event
		if (keyCode != -1)
			return;

		// get keycode from event
		keyCode = e.getKeyCode();

		// send that keycode to the player to move
		if (activePlayer != null)
			activePlayer.move(keyCode, this);

		// repaint the canvas
		invalidate();
	}

	public void updateTimer()
	{
		// move if a key is being held
		if (keyCode > 0)
			if (activePlayer != null)
				activePlayer.move(keyCode, this);

		// repaint the canvas

		((Activity) ctx).runOnUiThread(new Runnable(){ public void run() {
			invalidate();
		}});
	}

	public void keyReleased(KeyEvent arg0) 
	{
		// set keycode to -1 to indicate no key is being held down
		keyCode = -1;
	}

	public boolean checkCollisions(int x, int y) 
	{
		// this runs a simple rectangle boundary check on all the players and the given coordinates
		for (Player p : players)
			if (p != activePlayer)
				if (p.x < x+32 && p.x+32 > x && y < p.y+32 && y+32 > p.y) 
					return false;

		return true;
	}


}
