import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bumpers
{
	int x,y;
	BufferedImage bumper, litBumper, image;
	
	public Bumpers(int x, int y)
	{
		this.x = x;
		this.y = y;
		try {
			bumper = ImageIO.read(getClass().getResourceAsStream("imgres.png"));
			litBumper = ImageIO.read(getClass().getResourceAsStream("litbumper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		image = bumper;
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	public Rectangle getRectangle()
	{
		return new Rectangle(x, y, 50, 50);
	}
}
