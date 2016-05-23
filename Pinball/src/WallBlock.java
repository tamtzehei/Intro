import java.awt.Rectangle;


public class WallBlock
{
	int x, y;
	boolean orientation, corner;
	/**
	 * 
	 * @param x
	 * @param y
	 * @param orientation
	 * 				- true for horizontal, false for vertical
	 * @param corner
	 * 				- true for corner
	 */
	public WallBlock(int x, int y, boolean orientation, boolean corner)
	{
		this.x = x;
		this.y = y;
		this.orientation = orientation; 
		this.corner = corner;
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
