import java.awt.Rectangle;

public class Pinball
{
	int x,y;
	int dx,dy;
	
	public Pinball()
	{

	}
	public void move()
	{
		x += dx;
		y += dy;
		
		dy -= 1;
	}
	/**
	 * 
	 * @param vertical 
	 * 				- if it hit a vertical wall
	 * @param horizontal
	 * 				- if it hit a horizontal wall or corner
	 */
	public void changeDirection(boolean vertical, boolean horizontal)
	{
		dy = dx;
		dx = dy;
		if(vertical)
			dx = -dx;
		else if(horizontal)
			dy = -dy;
		
	}
	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		this.y = y;
	}
}
