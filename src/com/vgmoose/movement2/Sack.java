package com.vgmoose.movement2;

public class Sack {
	
	int maxWeight;
	PresentList presents;
	int currentWeight;
	boolean weightBased;
	
	public Sack(int maxWeight, boolean weightBased)
	{
		this.maxWeight = maxWeight;
		this.presents = new PresentList();
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
		
	public PresentList getPresents()
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
	
	public boolean addPresent(Present present)
	{
		if(weightBased && currentWeight + present.getWeight() > maxWeight)
			return false;
		else if(!weightBased && presents.size() >= maxWeight)
			return false;
		else
		{
			presents.add(present);
			currentWeight += present.getWeight();
			return true;
		}
	}
	
	public PresentList losePresents(int numberLost)
	{
		int dropped;
		//clear the old dropped presents
		PresentList presentsDropped = new PresentList();
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
