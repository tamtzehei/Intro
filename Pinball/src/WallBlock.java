import java.awt.Rectangle;


public class WallBlock
{
	int x, y;
	public WallBlock(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,50,50);
	}
}
