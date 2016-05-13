import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bumpers
{
	int x,y;
	static BufferedImage bumper, litBumper;
	
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
	}
	
	public BufferedImage getImage()
	{
		return bumper;
	}
	
}
