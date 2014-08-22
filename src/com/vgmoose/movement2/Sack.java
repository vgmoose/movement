package com.vgmoose.movement2;
import java.util.ArrayList;

import android.util.Log;


public class Sack {
	
	int maxWeight;
	ArrayList<Present> presents;
	ArrayList<Present> presentsDropped;
	int currentWeight;
	boolean weightBased;
	
	public Sack(int maxWeight, int currentWeight, boolean weightBased) 
	{
		this.maxWeight = maxWeight;
		this.presents = new ArrayList<Present>();
		this.presentsDropped = new ArrayList<Present>();
		this.currentWeight = currentWeight;
		this.weightBased = weightBased;
		// TODO: Weight isn't the only way a sack can fill, they can also fill based on quantity
		// 		 perhaps introduce a boolean to determine if the Sack is weight-based or quantity-based
	}
	
	public void setMaxWeight(int weight)
	{
		maxWeight = weight;
	}
	
	public int getMaxWeight()
	{
		return maxWeight;
	}
		
	public ArrayList<Present> getPresents()
	{
		return presents;
	}
	
	public void setCurrentWeight(int weight)
	{
		currentWeight = weight;
	}
	
	public int getCurrentWeight()
	{
		return currentWeight;
	}
	
	public void setWeightBased(boolean weight)
	{
		weightBased = weight;
	}
	
	public boolean getWeightBased()
	{
		return weightBased;
	}
	
	public void addPresent(Present present)
	{
		if(weightBased && currentWeight + present.getWeight() > maxWeight)
			return;
		else if(!weightBased && presents.size() <= maxWeight)
			return;
		
		presents.add(present);
		currentWeight += present.getWeight();
	}
	
	public ArrayList<Present> losePresents(int numberLost)
	{
		int dropped;
		//clear the old dropped presents
		presentsDropped.clear();
		for(int i=0; i<numberLost; i++)
		{
			dropped = (int)(Math.random()*presents.size());
			presentsDropped.add(presents.get(dropped));
			presents.remove(dropped);
		}
		
		return presentsDropped;
	}
	
	public void clearSack()
	{
		while(!presents.isEmpty())
			presents.remove(0);
	}
	
}
