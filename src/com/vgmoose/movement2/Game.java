import java.util.ArrayList;


public class Game<Bitmap> {
	
	Bitmap icon;
	
	public Bitmap getIcon(){
		
		return icon;
	}
	
	public static void main(String[] args) 
	{
		Game game = new Game();
		
		ArrayList<Present> gifts = new ArrayList<Present>();
		Sack sack = new Sack(5,gifts,0);
		
		Present present1 = new Present(1, 0, game.icon, null);
		Present present2 = new Present(2, 0, game.icon, null);
		Present present3 = new Present(3, 0, game.icon, null);
		Present present4 = new Present(4, 0, game.icon, null);
		Present present5 = new Present(5, 0, game.icon, null);
		
		sack.addPresent(present1);
		sack.addPresent(present2);
		sack.addPresent(present3);
		sack.addPresent(present4);
		sack.addPresent(present5);

		sack.losePresents(sack.getPresents(), 2);
		
		for(int i=0; i<sack.getPresents().size(); i++)
		{
			System.out.println();
			System.out.println(sack.getPresents().get(i).getWeight());
		}
	}
	
}
