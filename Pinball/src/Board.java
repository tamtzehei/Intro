import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Board extends JFrame
{
	ArrayList<Bumpers> bumpers = new ArrayList<Bumpers>();
	Flippers left, right;
	static BufferedImage leftDown, leftUp, rightDown, rightUp;
	
	public int board[][] = 
	{
			{1,1,1,1,1,1,1,1,1,1,1,1},
			{1,6,3,3,3,3,3,3,3,3,7,1},
			{1,3,3,4,3,4,3,4,3,3,3,1},
			{1,3,3,3,3,3,3,3,3,1,3,1},
			{1,3,4,3,4,3,4,3,3,1,3,1},
			{1,3,3,3,3,3,3,3,3,1,3,1},
			{1,3,3,4,3,4,3,4,3,1,3,1},
			{1,3,3,3,3,3,3,3,3,1,3,1},
			{1,3,4,3,4,3,4,3,3,1,3,1},
			{1,3,3,3,3,3,3,3,3,1,3,1},
			{1,3,3,3,3,3,3,3,3,1,3,1},
			{1,2,3,3,3,3,3,3,3,1,3,1},
			{1,1,3,3,3,3,3,3,3,1,3,1},
			{1,1,3,9,3,3,3,9,3,1,3,1},
			{1,1,3,9,3,3,3,9,3,1,3,1},
			{1,1,3,3,3,3,3,3,3,1,3,1},
			{1,1,2,3,3,3,3,3,11,1,3,1},
			{1,1,1,5,3,3,3,10,1,1,3,1},
			{1,1,1,2,3,3,3,11,1,1,3,1},
			{1,1,1,1,2,3,11,1,1,1,8,1},
	};
	public Board()
	{
		initialize();
		try {
			leftDown = ImageIO.read(getClass().getResourceAsStream("triangleleftdown.png"));
			leftUp = ImageIO.read(getClass().getResourceAsStream("triangleleftup.png"));
			rightDown = ImageIO.read(getClass().getResourceAsStream("trianglerightdown.png"));
			rightUp = ImageIO.read(getClass().getResourceAsStream("trianglerightup.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void initialize()
	{
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 12; j++)
			{
				if(board[i][j] == 4)
					bumpers.add(new Bumpers(j * 50, i * 50));
			}
		}
	}
	public void paint(Graphics g)
	{
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 12; j++)
			{
				if(board[i][j] == 1)
				{
					g.setColor(Color.YELLOW);
					g.fillRect(j * 50, i * 50, 50, 50);
				}
				else if(board[i][j] == 2)
				{
					g.drawImage(leftUp, j * 50, i * 50, 50, 50, null);
				}
				else if(board[i][j] == 3)
				{
					
				}
				else if(board[i][j] == 4)
				{
					for(Bumpers b : bumpers)
					{
						g.drawImage(b.getImage(), j * 50, i * 50, 50, 50, null);
					}
				}
				else if(board[i][j] == 5)
				{
					left = new Flippers(false, j * 50, i * 50);
				}
				else if(board[i][j] == 6)
				{
					g.drawImage(leftDown, j * 50, i * 50, 50, 50, null);
				}
				else if(board[i][j] == 7)
				{
					g.drawImage(rightDown, j * 50, i * 50, 50, 50, null);
				}
				else if(board[i][j] == 8)
				{
					
				}
				else if(board[i][j] == 9)
				{
					
				}
				else if(board[i][j] == 10)
				{
					right = new Flippers(true, i * 50, j * 50);
				}
				else if(board[i][j] == 11)
				{
					g.drawImage(rightUp, j * 50, i * 50, 50, 50, null);
				}
			}
		}
	}
	
}
