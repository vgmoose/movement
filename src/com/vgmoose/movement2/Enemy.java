package com.vgmoose.movement2;

public class Enemy {

	int speed;
	int row;
	String type;
	int xCord;
	int yCord;
	
	public Enemy(int speed, int row, String type)
	{
		this.speed = speed;
		this.row = row;
		this.type = type;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setX(int x)
	{
		this.xCord = x;
	}
	
	public int getX()
	{
		return xCord;
	}
	
	public void setY(int y)
	{
		this.yCord = y;
	}
	
	public int getY()
	{
		return yCord;
	}

	public void collide(Player p)
	{
		int numberLost = (int)(Math.random()*p.getSack().getPresents().size())+1;
		p.getSack().losePresents(numberLost);
	}
	
}
