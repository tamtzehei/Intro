import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Flippers implements KeyListener
{
	int x,y;
	boolean leftPosition, rightPosition; // false for down  true for up
	static BufferedImage leftFlipperDown, leftFlipperUp, rightFlipperDown, rightFlipperUp;

	public Flippers(boolean position, int x, int y)	// false for left   true for right
	{
		if(position)
		{
			rightPosition = false;
			try {
				rightFlipperDown = ImageIO.read(getClass().getResourceAsStream("rightdown.jpg"));
				rightFlipperUp = ImageIO.read(getClass().getResourceAsStream("rightup.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
			leftPosition = false;
			try {
				leftFlipperDown = ImageIO.read(getClass().getResourceAsStream("leftdown.png"));
				leftFlipperUp = ImageIO.read(getClass().getResourceAsStream("leftup.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g)
	{
		if(leftPosition)
			g.drawImage(leftFlipperUp, x, y, 50, 50, null);
		else
			g.drawImage(leftFlipperDown, x, y, 50, 50, null);
		
		if(rightPosition)
			g.drawImage(rightFlipperUp, x, y, 50, 50, null);
		else
			g.drawImage(rightFlipperDown, x, y, 50, 50, null);
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}


	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			leftPosition = true;
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightPosition = true;
		
		
	}

	public void keyReleased(KeyEvent e)
	{
			
	}

}
