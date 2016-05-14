import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Board extends JFrame implements ActionListener, KeyListener
{
	ArrayList<Bumpers> bumpers = new ArrayList<Bumpers>();
	Flippers left, right;
	static BufferedImage leftDown, leftUp, rightDown, rightUp, compressed, released, spring;
	Pinball ball;
	int score;
	
	public int board[][] = 
	{
		//	{1,1,1,1,1,1,1,1,1,1,1,1},
		//	{1,6,3,3,3,3,3,3,3,3,7,1},
		//	{1,3,3,4,3,4,3,4,3,3,3,1},
		//	{1,3,3,3,3,3,3,3,3,1,3,1},
		//	{1,3,4,3,4,3,4,3,3,1,3,1},
		//	{1,3,3,3,3,3,3,3,3,1,3,1},
		//	{1,3,3,4,3,4,3,4,3,1,3,1},
			{1,3,3,3,3,3,3,3,3,1,3,1},
			{1,3,4,3,4,3,4,3,3,1,3,1},
			{1,3,3,3,3,3,3,3,3,1,3,1},
			{1,2,3,3,3,3,3,3,3,1,3,1},
			{1,1,3,3,3,3,3,3,3,1,3,1},
			{1,1,3,9,3,3,3,9,3,1,3,1},
			{1,1,3,9,3,3,3,9,3,1,3,1},
			{1,1,3,3,3,3,3,3,3,1,3,1},
			{1,1,2,3,3,3,3,3,11,1,3,1},
			{1,1,1,2,3,3,3,11,1,1,3,1},
			{1,1,1,1,5,3,10,1,1,1,3,1},
			{1,1,1,1,2,3,11,1,1,1,8,1},
			{1,1,1,1,1,3,1,1,1,1,1,1},
	};
	public Board()
	{
		addKeyListener(this);
		initialize();
		try {
			leftDown = ImageIO.read(getClass().getResourceAsStream("triangleleftdown.png"));
			leftUp = ImageIO.read(getClass().getResourceAsStream("triangleleftup.png"));
			rightDown = ImageIO.read(getClass().getResourceAsStream("trianglerightdown.png"));
			rightUp = ImageIO.read(getClass().getResourceAsStream("trianglerightup.png"));
			compressed = ImageIO.read(getClass().getResourceAsStream("compressedspring.jpg"));
			released = ImageIO.read(getClass().getResourceAsStream("releasedspring.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ball = new Pinball(300, 400);
		left = new Flippers(false, 5 * 50, 11 * 50);
		right = new Flippers(true, 7 * 50, 11 * 50);
		spring = compressed;
	}
	public void initialize()
	{
		for(int i = 0; i < 13; i++)
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
		g.drawImage(ball.getImage(), ball.getX(), ball.getY(), 50, 50, null);
		for(int i = 0; i < 13; i++)
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
				else if(board[i][j] == 4)
				{
					for(Bumpers b : bumpers)
					{
						g.drawImage(b.getImage(), j * 50, i * 50, 50, 50, null);
					}
				}
				else if(board[i][j] == 5)
				{
					g.drawImage(left.getImage(left.isLeftPosition(), true), j * 50, i * 50, 60, 60, null);
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
					g.drawImage(spring, j * 50, i * 50 , 50, 50, null);
				}
				else if(board[i][j] == 9)
				{
					g.setColor(Color.red);
					g.fillRect(j * 50, i * 50, 50, 50);
				}
				else if(board[i][j] == 10)
				{
					g.drawImage(right.getImage(right.isRightPosition(), false), j * 50, i * 50, 60, 60, null);
				}
				else if(board[i][j] == 11)
				{
					g.drawImage(rightUp, j * 50, i * 50, 50, 50, null);
				}
				
			}
		}
	}
	public void actionPerformed(ActionEvent e)
	{
	
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			left.leftPosition = true;
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			right.rightPosition = true;
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			spring = released;

		repaint();
	}

	public void keyReleased(KeyEvent e)
	{
		left.leftPosition = false;
		right.rightPosition = false;
		spring = compressed;
		repaint();
	}
	public void checkBumperCollisions()
	{
		Rectangle ballRect = ball.getRectangle();
		for(Bumpers b : bumpers)
		{
			Rectangle bumpRect = b.getRectangle();
			if(bumpRect.intersects(ballRect))
			{
				b.image = b.litBumper;
				score += 100;
			}
		}
	}
}
