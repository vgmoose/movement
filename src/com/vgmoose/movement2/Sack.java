import java.util.ArrayList;


public class Sack {
	
	int maxWeight;
	ArrayList<Present> presents;
	int currentWeight;
	
	public Sack(int maxWeight, ArrayList<Present> presents, int currentWeight) 
	{
		this.maxWeight = maxWeight;
		this.presents = presents;
		this.currentWeight = currentWeight;
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
		presents.add(present);
	}
	
	public ArrayList<Present> losePresents(ArrayList<Present> presents, int numberLost)
	{
		for(int i=0; i<numberLost; i++)
			presents.remove((int)(Math.random()*presents.size()));
		
		return presents;
	}
}
