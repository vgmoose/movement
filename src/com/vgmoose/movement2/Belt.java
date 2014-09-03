package com.vgmoose.movement2;

public class Belt {

	//beltIncoming - set speed, set present, remove present

	Present[] slots = new Present[10];
	
	//make array of belts to all make random presents
	
	public void makePresent()
	{
		
	}
	
	public void grabPresent(int index, Player p)
	{
		if(slots[index] == null)
			return;
		else if(p.getSack().addPresent(slots[index]) == true)
			slots[index] = null;
	}
	
	public void removePresent(int index)
	{
		slots[index] = null;
	}
	
	public void setSpeed()
	{
		
	}
}
