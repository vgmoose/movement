
public class Enemy {

	int speed;
	int row;
	String type;
	int xCord;
	int yCord;
	
	public Enemy(int speed, int row, String type, int xCord, int yCord)
	{
		this.speed = speed;
		this.row = row;
		this.type = type;
		this.xCord = xCord;
		this.yCord = yCord;
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
	
	public void setXCord(int x)
	{
		this.xCord = x;
	}
	
	public int getXCord()
	{
		return xCord;
	}
	
	public void setYCord(int y)
	{
		this.yCord = y;
	}
	
	public int getYCord()
	{
		return yCord;
	}
	
}
