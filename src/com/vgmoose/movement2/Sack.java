package com.vgmoose.movement2;
import java.util.ArrayList;


public class Sack {
	
	int maxWeight;
	ArrayList<Present> presents;
	int currentWeight;
	
	public Sack(int maxWeight, int currentWeight) 
	{
		this.maxWeight = maxWeight;
		this.presents = new ArrayList<Present>();
		this.currentWeight = currentWeight;
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
	
	public void addPresent(Present present)
	{
		// TODO: check if it's full before adding... If full return false, else true
		presents.add(present);
	}
	
	public ArrayList<Present> losePresents(ArrayList<Present> presents, int numberLost)
	{
		for(int i=0; i<numberLost; i++)
			presents.remove((int)(Math.random()*presents.size()));
		
		return presents;
	}
	
	// TODO: clear() method is missing...
}
