import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Flippers
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
				rightFlipperDown = ImageIO.read(getClass().getResourceAsStream("rightdown.png"));
				rightFlipperUp = ImageIO.read(getClass().getResourceAsStream("rightup.png"));
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
	
	public boolean isLeftPosition()
	{
		return leftPosition;
	}

	public boolean isRightPosition()
	{
		return rightPosition;
	}
	/**
	 * 
	 * @param upDown
	 * 			- true for up and false for down
	 * @param leftRight
	 * 			- true for left and false for right
	 * @return
	 */
	public BufferedImage getImage(boolean upDown, boolean leftRight)
	{
		if(leftRight && upDown)
			return leftFlipperUp;
		else if(upDown)
			return rightFlipperUp;
		else if(leftRight)
			return leftFlipperDown;
		else
			return rightFlipperDown;
	}

	public Rectangle getBounds(boolean upDown, boolean leftRight)
	{
		if(upDown && leftRight)
			return new Rectangle(200, 800, 50, 35);
		else if(upDown)
			return new Rectangle(300, 800, 50, 35);
		else if(leftRight)
			return new Rectangle(315, 750, 35, 50);
		else
			return new Rectangle(200, 750, 35, 50);
		
	}
}
