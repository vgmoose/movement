package com.vgmoose.movement2;
import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.Image;
import android.view.KeyEvent;

public class Player 
{
	private static Bitmap[] images;
	private int color, direction, frame;
	public int x,y; // this is public information
	public static Bitmap bg;
	private int speed = 4;
	
	// frame is the column of the image (step)
	// direction is 0=down, 1=up, 2=left, 3=right
	
	private Bitmap sprite;
	
	public Player(int size, int x, int y) 
	{
		// the size mod 3 will determine what color this link is
		this.color = size % 3;
		
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
		// draw the sprite on the given Graphics object
		g.drawBitmap(sprite,this.x,this.y, p);
		
		// draw bounding box in debug mode
		if (Main.debug)
		{
			g.drawRect(x, y, 32+x, 32+y, p);
//			g.drawString(""+this.x+","+this.y, x-5, y-6);
		}
	}

	static void setImages(Context ctx, String[] im) 
	{
		// initialize and set the length of the Image array 
		images = new Bitmap[im.length];
		
		// create the background
		try {
			bg =  BitmapFactory.decodeStream(ctx.getAssets().open("grass.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Create a new Image object for each path passed
		for (int i=0; i<im.length; i++)
		{
			try {
				images[i] = BitmapFactory.decodeStream(ctx.getAssets().open(""+im[i]));
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
		if (gp.checkCollisions(this.x + x, this.y))
			this.x += x;
		
		if (gp.checkCollisions(this.x, this.y + y))
			this.y += y;
		
		// update the image since we've moved
		updateImage();
	}

}
