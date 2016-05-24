import java.awt.Rectangle;


public class RedBumper 
{
	int x, y;
	public RedBumper(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,50,50);
	}
}
