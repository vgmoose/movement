package com.vgmoose.movement2;

import android.graphics.Bitmap;

public class Present {

	int weight;
	int value;
	Bitmap icon;
	String type;
	
	public Present(int weight, int value, Bitmap icon, String type)
	{
		this.weight = weight;
		this.value = value;
		this.icon = icon;
		this.type = type;
	}
	
	public void setWeight(int weight)
	{
		this.weight = weight;
	}
	
	public int getWeight()
	{
		return weight;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setIcon(Bitmap icon)
	{
		this.icon = icon;
	}
	
	public Bitmap getIcon()
	{
		return icon;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return type;
	}
}
