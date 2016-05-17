import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pinball

{
	int x,y;
	int dx = 0,dy = 0;
	static BufferedImage pinball;
	
	public Pinball(int x, int y)
	{
		try
		{
			pinball = ImageIO.read(getClass().getResourceAsStream("pinball.jpg"));
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
	}
	public void move()
	{
		x += dx;
		y += dy;
		
		dy += 1;
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
	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,50,50);
	}
	public BufferedImage getImage()
	{
		return pinball;
	}
}
