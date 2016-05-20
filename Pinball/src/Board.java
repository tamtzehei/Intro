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
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Board extends JFrame implements ActionListener, KeyListener
{
	ArrayList<Bumpers> bumpers = new ArrayList<Bumpers>();
	ArrayList<Wall> walls = new ArrayList<Wall>();
	ArrayList<WallBlock> wallBlocks = new ArrayList<WallBlock>();
	Flippers left, right;
	static BufferedImage leftDown, leftUp, rightDown, rightUp, compressed, released, spring;
	Pinball ball;
	int score, ballsLeft;
	boolean gameOver = true;
	
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
		ball = new Pinball(200,600);
		left = new Flippers(false, 5 * 50, 11 * 50);
		right = new Flippers(true, 7 * 50, 11 * 50);
		spring = compressed;
	}
	public void initialize()
	{
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 12; j++)
			{
				if(board[i][j] == 4)
					bumpers.add(new Bumpers(j * 50, i * 50));
				else if(board[i][j] == 1)
					wallBlocks.add(new WallBlock(j * 50, i * 50));
				else if(board[i][j] == 6)
					walls.add(new Wall(j * 50, (i + 1) * 50, (j + 1) * 50, i * 50));
				else if(board[i][j] == 7)
					walls.add(new Wall(j * 50, i * 50, (j + 1) * 50, (i + 1) * 50));
				else if(board[i][j] == 2)
					walls.add(new Wall(j * 50, i * 50, (j + 1) * 50, (i + 1) * 50));
				else if(board[i][j] == 11)
					walls.add(new Wall(j * 50, (i + 1) * 50, (j + 1) * 50, i * 50));
			}
		}
	}

	
	public void paint(Graphics g)
	{
		g.clearRect(0, 0, 600, 1000);
	
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
				
				else if(board[i][j] == 5)
				{
					if(left.leftPosition)
					{
						g.drawImage(left.getImage(true, true), j * 50, (i - 1) * 50, 50, 50, null);
						g.clearRect(j * 50, i * 50, 50, 50);
					}
					else
					{
						g.drawImage(left.getImage(false, true), j * 50, i * 50, 50, 50, null);
						g.clearRect(j * 50, (i - 1) * 50, 50, 50);
					}
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
					if(spring.equals(compressed))
						g.drawImage(spring, j * 50, i * 50 , 50, 50, null);
					else
						g.drawImage(spring, j * 50, i * 50, 50, 100, null);
				}
				else if(board[i][j] == 9)
				{
					g.setColor(Color.red);
					g.fillRect(j * 50, i * 50, 50, 50);
				}
				else if(board[i][j] == 10)
				{
					if(right.rightPosition)
					{
						g.drawImage(right.getImage(true, false), j * 50, (i - 1) * 50, 50, 50, null);
						g.clearRect(j * 50, i * 50, 50, 50);
					}
					else
					{
						g.drawImage(right.getImage(false, false), j * 50, i * 50, 50, 50, null);
						g.clearRect(j * 50, (i - 1) * 50, 50, 50);
					}
				}
				else if(board[i][j] == 11)
				{
					g.drawImage(rightUp, j * 50, i * 50, 50, 50, null);
				}

			}
		}

		for(Bumpers b : bumpers)
		{
			g.drawImage(b.getImage(), b.x, b.y, 50, 50, null);
			b.image = b.bumper;
			g.setColor(Color.GREEN);
			g.drawRect(b.x, b.y, 50, 50);
		}
		for(WallBlock w : wallBlocks)
		{
			g.drawRect(w.getX(), w.getY(), 50, 50);
		}
		for(Wall w : walls)
		{
			g.drawLine((int)w.getX1(), (int)w.getY1(), (int)w.getX2(), (int)w.getY2());
		}
		g.drawLine((int)left.getX1(true, true), (int)left.getY1(true, true), (int)left.getX2(true, true), (int)left.getY2(true, true));
		g.drawImage(ball.getImage(), ball.getX(), ball.getY(), 30, 30, null);
		g.drawRect(ball.getX(), ball.getY(), 30, 30);
		
	}
	public void actionPerformed(ActionEvent e)
	{
		
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	

	public void keyPressed(KeyEvent e)
	{
		updatePinball();
		repaint();
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
	public void updatePinball()
	{
		Rectangle ballRect = ball.getRectangle();
		for(Bumpers b : bumpers)
		{
			Rectangle bumpRect = b.getRectangle();
			if(bumpRect.intersects(ballRect))
			{
				b.image = b.litBumper;
				score += 100;
				boolean vertical = false, horizontal = false;
				if(ball.getX() - b.x < 5)
					horizontal = true;
				if(ball.getY() - b.y < 5)
					vertical = true;
				ball.changeDirection(vertical, horizontal, false);
				break;
			}			
		}
		for(Wall w : walls)
		{
			if(ballRect.intersectsLine(w.getX1(), w.getY1(), w.getX2(), w.getY2()))
			{
				ball.changeDirection(false, false, true);
				break;
			}
		}
		for(WallBlock w : wallBlocks)
		{
			Rectangle wallRect = w.getRectangle();
			if(ballRect.intersects(wallRect))
			{
				boolean vertical = false, horizontal = false;
				if(ball.getX() - w.getX() < 5)
					horizontal = true;
				if(ball.getY() - w.getY() < 5)
					vertical = true;
				ball.changeDirection(vertical, horizontal, false);
				break;
			}
		}
		if(ballRect.intersectsLine(left.getX1(true, true), left.getY1(true, true), left.getX2(true, true), left.getY2(true, true)) || ballRect.intersectsLine(right.getX1(true, false), right.getY1(true, false), right.getX2(true, false), right.getY2(true, false)))
		{
			ball.changeDirection(false, false, true);
			ball.dy += 20;
		}
		else
		{
			ball.changeDirection(false, false, true);
		}
		ball.move();
	}
}
