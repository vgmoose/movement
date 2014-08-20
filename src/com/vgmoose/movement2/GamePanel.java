package com.vgmoose.movement2;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;


@SuppressLint("NewApi")
public class GamePanel extends View implements View.OnTouchListener
{
	// An array list is an object wrapper for an array, allows dynamic resizing
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player activePlayer;
	private int keyCode = -1;
	private Timer t;
	Paint p;
	Context ctx;
	float magicScale;
	Typeface face;


	int bfontOff = 300;
	int joyx, joyy;

	float hudIniX, hudIniY, hudCurX, hudCurY;

	boolean mousedown, placementmode;

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


		face = Typeface.createFromAsset(ctx.getAssets(), "LCD_Solid.ttf");

		// this is a kind of weird thing, it's needed to not have a
		// strange delay when you hold down a key
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				updateTimer();
			}
		}, 0, 40);


		//		Display display = ((Activity) ctx).getWindowManager().getDefaultDisplay();
		//		Point size = new Point();
		//		display.getSize(size);
		//		int width = size.x;
		//		int height = size.y;
	}

	// this method is called when someone calls repaint()
	public void onDraw(Canvas g)
	{
		// this call clears the canvas (it's like clrScreen)
		//		super.paintComponent(g);
		super.onDraw(g);		

		updateScreenSizeIfNecessary();

		p.setColor(Color.rgb(250, 250, 178));

		if (!Main.debug)
		{			
			g.drawRect(0, 0, getWidth(), getHeight(), p);

			p.setColor(Color.BLACK);


			// draw the background
			g.drawBitmap(Player.bg, getWidth()/2-250, getHeight()/2-250, p);
			//			Paint p = new Paint();
			//			
			//			// draw rectangle in the top left

			//			g.drawRect(getWidth()/(magicScale*2), getHeight()/(magicScale*2), getWidth()-getWidth()/(magicScale*2), getHeight()-getHeight()/(magicScale*2), p);

			// draw text strings 
			//			g.drawText("Click to create a new player at the mouse position", 3, 13, p);
			//			g.drawText("Click an existing player to mark them active", 3, 27, p);
			//			g.drawText("Use the arrow keys to move the active player", 3, 41, p);
			//			g.drawText("Right click to toggle debug information", 3, 55, p);
		}

		// draw each player in the arraylist
		// (they are passed the Graphics object, and then tell it how to draw them)
		for (Player p : players)
		{
			p.draw(g, this.p);

			if (p.isMoving())
				g.drawBitmap(Player.crosshair, p.destX-16, p.destY-16, this.p);
		}

		//		drawControls(g);
		drawText(g);
	}

	public void drawText(Canvas g)
	{
		p.setColor(Color.argb(140, 0, 102, 230));
		g.drawRect(0, (float) (.65*getHeight()), getWidth(), getHeight(), p);
		p.setColor(Color.WHITE);
		p.setTextSize(20);
		p.setTypeface(face);
		g.drawText("This is a bitmap font test.", bfontOff, (float) (.67*getHeight()), p);
	}

	private void updateScreenSizeIfNecessary() 
	{
		float thisMagicScale = (float) (Math.min(getWidth(),  getHeight()) / 360.0);

		if (thisMagicScale != magicScale)
		{
			Log.v("he magic", ""+magicScale + " " + + getHeight() + " " + getWidth());

			magicScale = thisMagicScale;
			setScaleY(magicScale);
			setScaleX(magicScale);

			Log.v("he magic", ""+magicScale + " " + + getHeight() + " " + getWidth());

			((Main)ctx).zoomifier = magicScale;

			//			Log.v("aaa", ""+getWidth() + " " + getHeight() + " " + magicScale);

			// create some default players
			for (int x=0; x<7; x++)
			{
				activePlayer = new Player(players.size(), (int)(getWidth()/2/4)*4-32*(x-3), (int)(getHeight()/2/4)*4-32*(x-3));
				players.add(activePlayer);
			}
		}

	}

	//	public void drawControls(Canvas g)
	//	{
	//
	//		int distance = 2*(int)Math.sqrt((hudCurX-hudIniX)*(hudCurX-hudIniX) + (hudCurY-hudIniY)*(hudCurY-hudIniY));
	//
	//		int bx = (int) hudCurX;
	//		int by = (int) hudCurY;
	//
	//		if (distance < 40)
	//			return;
	//
	//		if (distance>100)
	//			distance = 100;		
	//
	//		//		if (distance>100)
	//		//		{
	//		//			double theta = Math.atan2(hudCurY-hudCurY, hudIniX-hudIniY);
	//		//			bx = (int) (hudIniX + 50 * Math.cos(theta));
	//		//			by = (int) (hudIniY + 50 * Math.sin(theta));
	//		//
	//		//		}
	//
	//
	//		//		((Graphics2D) g).setStroke(new BasicStroke(3));
	//
	//		p.setColor(Color.argb(100,130,150,50));
	//		//		g.drawCircle(hudIniX, hudIniY, distance, p); // outer
	//		g.drawCircle(hudIniX, hudIniY, 25, p); // initial
	//		//		g.setColor(Color.black);
	//		g.drawCircle(bx, by, 10, p); // head
	//
	//		//		((Graphics2D) g).setStroke(new BasicStroke(7));
	//
	//		g.drawLine(bx, by, hudIniX, hudIniY, p); // line
	//		//		g.setColor(Color.darkGray);
	//
	//		joyx = (int) ((bx - hudIniX) / 50.0);
	//		joyy = (int) ((by - hudIniY) / 50.0);
	//
	//		//		Log.v("aaa", ""+joyx +  " " + joyy);
	//
	//	}

	public boolean onTouch(View v, MotionEvent e) 
	{
		// toggle debug mode when right mouse button is pressed
		//		if (SwingUtilities.isRightMouseButton(e))
		//		{
		//			Movement.debug = !Movement.debug;
		//			return;
		//		}

		if (e.getAction() == MotionEvent.ACTION_DOWN)
		{

			mousedown = false;


			// get the coordinates of the click
			int x = Math.round(e.getX()/4) * 4;
			int y = Math.round(e.getY()/4) * 4;

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
			// if no one was found at the current click, draw the crosshair and set the destination

			//			if (!Main.debug)
			//			{
			activePlayer.setDest(x, y);
			//			}
			//			else
			//			{
			//				// Create a new player and give the mouse coordinates
			//				// as well as how many players currently exist (size of players arraylist)
			//				Player p = new Player(players.size(), x-16, y-16);
			//
			//				// add this player to the arraylist
			//				players.add(p);
			//				// make this player active
			//				activePlayer = p;
			//			}
			//
			//			// redraw the canvas
			invalidate();
			//

		}
		//		else if (e.getAction() == MotionEvent.ACTION_DOWN)
		//		{
		//			hudIniX = e.getX();
		//			hudIniY = e.getY();
		//			hudCurX = hudIniX;
		//			hudCurY = hudIniY;
		//			placementmode = true;
		//			mousedown = true;
		//		}
		//		else if (e.getAction() == MotionEvent.ACTION_MOVE)
		//		{			
		//			int distance = 2*(int)Math.sqrt((e.getX()-hudIniX)*(e.getX()-hudIniX) + ( e.getY()-hudIniY)*(e.getY()-hudIniY));
		//
		//			if (distance > 40)
		//			{
		//				hudCurX =  e.getX();
		//				hudCurY = e.getY();
		//				placementmode = false;
		//			}
		//		}

		return true;

	}

	//	public void move(int a)
	//	{
	//		switch (a)
	//		{
	//		case 1:
	//			activePlayer.move(-1, -1, this);
	//			break;
	//		case 2:
	//			activePlayer.move(0, -1, this);
	//			break;
	//		case 3:
	//			activePlayer.move(1, -1, this);
	//			break;
	//		case 4:
	//			activePlayer.move(1, 0, this);
	//			break;
	//		case 5:
	//			activePlayer.move(1, 1, this);
	//			break;
	//		case 6:
	//			activePlayer.move(0, 1, this);
	//			break;
	//		case 7:
	//			activePlayer.move(-1, 1, this);
	//			break;
	//		case 0:
	//		case 8:
	//			activePlayer.move(-1, 0, this);
	//			break;
	//		}
	//	}

	//	public void keyPressed(KeyEvent e) 
	//	{
	//		// if a key is down, don't repeat the key press event
	//		if (keyCode != -1)
	//			return;
	//
	//		// get keycode from event
	//		keyCode = e.getKeyCode();
	//
	//		// send that keycode to the player to move
	//		if (activePlayer != null)
	//			activePlayer.move(keyCode, this);
	//
	//		// repaint the canvas
	//		invalidate();
	//	}

	public void updateTimer()
	{
		//		// move if a key is being held
		//		if (mousedown)
		//			if (activePlayer != null)
		//				if (hudCurX != hudIniX && hudCurY != hudIniY)
		//					move(getDirectionValue());

		bfontOff -= 1;

		if (bfontOff < -300)
			bfontOff = 600;

		for (Player p : players)
			if (p.isMoving())
				p.moveToDest(this);

		// repaint the canvas
		//		if (mousedown)
		//		{
		((Activity) ctx).runOnUiThread(new Runnable(){ public void run() {
			invalidate();
		}});
		//		}
	}

	public void keyReleased(KeyEvent arg0) 
	{
		// set keycode to -1 to indicate no key is being held down
		keyCode = -1;
	}

	public int getDirectionValue()
	{
		return (int)((((Math.atan2(hudCurY-hudIniY, hudCurX-hudIniX))*(180/Math.PI))+180+22.5)/45);
	}

	public String getDirectionStatus() 
	{
		String s;
		int a = getDirectionValue();

		switch (a)
		{
		case 1:
			s = "Up-Left";
			break;
		case 2:
			s = "Up";
			break;
		case 3:
			s = "Up-Right";
			break;
		case 4:
			s = "Right";
			break;
		case 5:
			s = "Down-Right";
			break;
		case 6:
			s = "Down";
			break;
		case 7:
			s = "Down-Left";
			break;
		case 0:
		case 8:
			s = "Left";
			break;


		default:
			s = "";

		}

		return s;
	}

	public boolean checkCollisions(Player currentPlayer, int x, int y) 
	{
		// this runs a simple rectangle boundary check on all the players and the given coordinates
		for (Player p : players)
			if (p != currentPlayer)
				if (p.x < x+32 && p.x+32 > x && y < p.y+32 && y+32 > p.y) 
					return false;

		return true;
	}


}
