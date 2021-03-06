package com.vgmoose.movement2;
import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.util.Log;
import android.view.KeyEvent;

public class Player 
{
	private static Bitmap[] images;
	private int color, direction, frame;
	public int x,y; // this is public information
	public static Bitmap bg;
	public static Bitmap crosshair;
	private int speed = 4;
	int destX, destY;
	private Sack sack;
	private boolean moving = false;
	int id = -1;

	// frame is the column of the image (step)
	// direction is 0=down, 1=up, 2=left, 3=right

	private Bitmap sprite;

	public Player(int type, int x, int y) 
	{
		// the size mod 3 will determine what color this link is
		this.color = type;

		// set the coordinates too
		this.x = x;
		this.y = y;

		// update and initialize the image
		updateImage();
	}

	public void updateImage()
	{
		// this is to control how link moves a little bit
		int thisFrame = frame/5;

		// set the appropriate image for the current direction and frame
		sprite = Bitmap.createBitmap(images[color], thisFrame*32, direction*32, 32, 32);
	}

	public void draw(Canvas g, Paint p)
	{
		// draw bounding box in debug mode
		if (Main.debug)
		{
			g.drawRect(x, y, 32+x, 32+y, p);
			//			g.drawString(""+this.x+","+this.y, x-5, y-6);
		}

		// draw the sprite on the given Graphics object
		g.drawBitmap(sprite,this.x,this.y, p);
	}

	static void setImages(Context ctx, String[] im) 
	{
		// initialize and set the length of the Image array 
		images = new Bitmap[im.length];

		// create the background
		try {
			bg =  BitmapFactory.decodeStream(ctx.getAssets().open("grass.png"));
			crosshair = BitmapFactory.decodeStream(ctx.getAssets().open("crosshair.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Create a new Image object for each path passed
		for (int i=0; i<im.length; i++)
		{
			try {
				images[i] = BitmapFactory.decodeStream(ctx.getAssets().open("overworld/"+im[i]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void move(int x, int y, GamePanel gp) 
	{
		x *= this.speed;
		y *= this.speed;

		// advance the frame
		frame = (frame+1)%10;

		if (x > 0)
			direction = 3;
		if (x < 0)
			direction = 2;
		if (y > 0)
			direction = 0;
		if (y < 0)
			direction = 1;

		// move in the direction of the code
		if (gp.checkCollisions(this, this.x + x, this.y))
			this.x += x;

		if (gp.checkCollisions(this, this.x, this.y + y))
			this.y += y;

		// update the image since we've moved
		updateImage();
	}

	public void move(GamePanel gp, double dx, double dy)
	{

		// advance the frame
		frame = (frame+1)%15;

		dx = (dx < 0) ? Math.floor(dx/4) : Math.ceil(dx/4);
		dy = (dy < 0) ? Math.floor(dy/4) : Math.ceil(dy/4);

		dx *= 4;
		dy *= 4;

		// we have arrived, or we stopped moving (hit a wall)
		if (dx == 0 && dy == 0)
		{
			moving = false;
			if (gp.getActivePlayer() == this)
				sendMovementEvent(gp.ctx.syncMaster);
			updateImage();
			return;
		}

		//		Log.v("movements", "" + dx + " " + dy);

		if (Math.abs(dx) > Math.abs(dy))
		{
			if (dx > 0)
				direction = 2;
			else
				direction = 1;
		}
		else
		{
			if (dy > 0)
				direction = 0;
			else
				direction = 3;
		}

		if (gp.checkCollisions(this, (int) (this.x + dx), this.y))
			x += dx;

		if (gp.checkCollisions(this, this.x, (int) (this.y + dy)))
			y += dy;


		updateImage();
	}

	public void moveToDest(GamePanel gp) 
	{

		int tx = destX - 16, ty = destY - 16, lx = x, ly = y;

		//difference vector
		int zx = tx - lx;
		int zy = ty - ly;

		//how much we need to shorten the distance vector
		double a = 4/Math.sqrt(zx*zx + zy*zy);

		//how much to move link
		double dx = a*zx;
		double dy = a*zy;

		if (Double.isNaN(dx)) dx = 0;
		if (Double.isNaN(dy)) dy = 0;

		move(gp, dx, dy);
	}

	void setSack(Sack s)
	{
		this.sack = s;
	}

	Sack getSack()
	{
		return this.sack;
	}

	void setDest(int destX, int destY)
	{
		this.destX = destX;
		this.destY = destY;
		moving = true;
	}

	public int getType()
	{
		return color;
	}

	public boolean isMoving() 
	{
		return moving;
	}

	public void setId(int value) {
		id = value;
	}

	private void sendGenericEvent(Sync syncer, int value)
	{
		if (!syncer.connected)
			return;
		
		JSONObject payload = new JSONObject();

		try {

			if (value == 2)
			{
				payload.put("dest_x", destX);
				payload.put("dest_y", destY);
			}

//			if (value == 1)
//			{
				payload.put("kind", color);
//			}

			payload.put("x", x);
			payload.put("y", y);
		} catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		syncer.socket.emit("get_move", payload);
	}
	
	void sendMovementEvent(Sync syncer)
	{
		sendGenericEvent(syncer, 3);
	}

	void sendDestinationEvent(Sync syncer)
	{
		sendGenericEvent(syncer, 2);
	}

	void sendInitialEvent(Sync syncer)
	{
		sendGenericEvent(syncer, 1);
	}

}
