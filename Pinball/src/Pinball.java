import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pinball

{
	int x,y;

	int dx = 0,dy = 1;
	static BufferedImage pinball;
	
	public Pinball(int x, int y)
	{
		try
		{
			pinball = ImageIO.read(getClass().getResourceAsStream("pinball.png"));
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
		if(dy < 5)
			dy += 1;
		if(dx > 5)
			dx--;
		else if(dx < -5)
			dx++;
		
	}
	/**
	 * 
	 * @param vertical 
	 * 				- if it hit a vertical wall
	 * @param horizontal
	 * 				- if it hit a horizontal wall
	 */
	public void changeDirection(boolean vertical, boolean horizontal, boolean corner)
	{
		if(corner)
		{
			int temp = dy;
			dy = dx;
			dx = temp;
		}
		if(vertical)
			dx = -dx;
		if(horizontal)
			dy = -dy;
		
			
		
		
	}
	public void bumperHit()
	{
		
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
	public int getDx()
	{
		return dx;
	}
	public void setDx(int dx)
	{
		this.dx = dx;
	}
	public int getDy()
	{
		return dy;
	}
	public void setDy(int dy)
	{
		this.dy = dy;
	}
	public Rectangle getRectangle()
	{
		return new Rectangle(x,y,30,30);
	}
	public BufferedImage getImage()
	{
		return pinball;
	}
}
