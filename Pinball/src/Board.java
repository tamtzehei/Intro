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

public class Board extends JFrame implements KeyListener
{
	ArrayList<Bumpers> bumpers = new ArrayList<Bumpers>();
	ArrayList<Wall> walls = new ArrayList<Wall>();
	ArrayList<WallBlock> wallBlocks = new ArrayList<WallBlock>();
	ArrayList<RedBumper> redBumpers = new ArrayList<RedBumper>();
	Flippers left, right;
	static BufferedImage leftDown, leftUp, rightDown, rightUp, compressed, released, spring;
	Pinball ball;
	int score, ballsLeft;

	
	public int board[][] = 
	{
			{8,8,13,13,13,13,13,13,13,13,8,8},
    		{8,6,3,3,3,3,3,3,3,3,7,8},			
        	{1,3,3,4,3,4,3,4,3,3,3,1},
		    {1,3,3,3,3,3,3,3,3,1,3,1},
			{1,3,4,3,4,3,4,3,3,1,3,1},
	    	{1,3,3,3,3,3,3,3,3,1,3,1},
		    {1,3,3,4,3,4,3,4,3,1,3,1},
			{1,3,3,3,3,3,3,3,3,1,3,1},
			{1,3,4,3,4,3,4,3,3,1,3,1},
			{1,3,3,3,3,3,3,3,3,1,3,1},
			{8,2,3,3,3,3,3,3,3,1,3,1},
			{8,1,3,3,3,3,3,3,3,1,3,1},
			{8,1,3,9,3,3,3,9,3,1,3,1},
			{8,1,3,9,3,3,3,9,3,1,3,1},
			{8,1,3,3,3,3,3,3,3,1,3,1},
			{8,8,2,3,3,3,3,3,11,1,3,1},
			{8,8,8,2,3,3,3,11,8,1,3,1},
			{8,8,8,1,5,3,10,1,8,1,3,1},
			{8,8,8,8,2,3,11,8,8,1,12,1},
			{8,8,8,8,1,3,1,8,8,8,1,8},
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
		initialize();
		
		ballsLeft = 3;
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
					wallBlocks.add(new WallBlock(j * 50, i * 50, false));
				else if(board[i][j] == 6)
					walls.add(new Wall(j * 50, (i + 1) * 50, (j + 1) * 50, i * 50));
				else if(board[i][j] == 7)
					walls.add(new Wall(j * 50, i * 50, (j + 1) * 50, (i + 1) * 50));
				else if(board[i][j] == 2)
					walls.add(new Wall(j * 50, i * 50, (j + 1) * 50, (i + 1) * 50));
				else if(board[i][j] == 11)
					walls.add(new Wall(j * 50, (i + 1) * 50, (j + 1) * 50, i * 50));
				else if(board[i][j] == 13)
					wallBlocks.add(new WallBlock(j * 50, i * 50, true));
				else if(board[i][j] == 9)
					redBumpers.add(new RedBumper(j * 50, i * 50));
				spring = compressed;
				left = new Flippers(false, 5 * 50, 11 * 50);
				right = new Flippers(true, 7 * 50, 11 * 50);
				ball = new Pinball(500, 875);
			}
		}
	}
	
	public void paint(Graphics g)
	{
		updatePinball();
		if(ballsLeft > 0)
			paintGame(g);
		else
			paintGameOver(g);
	}
	
	public void paintGame(Graphics g)
	{
		g.clearRect(0, 0, 600, 1000);
		
		
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 12; j++)
			{
				if(board[i][j] == 8)
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
				else if(board[i][j] == 12)
				{
					if(spring.equals(compressed))
						g.drawImage(spring, j * 50, i * 50 , 50, 50, null);
					else
						g.drawImage(spring, j * 50, (i - 1) * 50, 50, 100, null);
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
			g.setColor(Color.GREEN);
			g.drawRect(w.getX(), w.getY(), 50, 50);
			g.setColor(Color.YELLOW);
			g.fillRect(w.getX(), w.getY(), 50, 50);
		}
		for(Wall w : walls)
		{
			g.setColor(Color.GREEN);
			g.drawLine((int)w.getX1(), (int)w.getY1(), (int)w.getX2(), (int)w.getY2());
		}
		g.drawImage(ball.getImage(), ball.getX(), ball.getY(), 30, 30, null);
	//	g.drawLine((int)left.getX1(true, true), (int)left.getY1(true, true), (int)left.getX2(true, true), (int)left.getY2(true, true));
	}
	public void paintGameOver(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawString("Game Over", 250, 500);
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
	public void updatePinball()
	{
		Rectangle ballRect = ball.getRectangle();
		if((ballRect.intersects(left.getBounds(true, true)) && left.isLeftPosition()) || ((ballRect.intersects(right.getBounds(false, true)) && right.isRightPosition())))
		{
			ball.changeDirection(false, false, true);
			ball.dy -= 15;
			System.out.println("Done");
		}
		else if(ballRect.intersects(left.getBounds(false, true)) || ballRect.intersects(right.getBounds(false, false)))
		{
			ball.changeDirection(false, false, true);
			
		}
		for(Bumpers b : bumpers)
		{
			Rectangle bumpRect = b.getRectangle();
			if(bumpRect.intersects(ballRect))
			{
				b.image = b.litBumper;
				score += 100;
				
				if(ball.getY() < 100 || (ball.getY() > 150 && ball.getY() < 200) || (ball.getY() > 250 && ball.getY() < 300) || (ball.getY() > 350 && ball.getY() < 400) || ball.getY() > 450) 
					ball.dy = -ball.dy;
				else
					ball.dx = -ball.dx;
				break;
			}			
		}
		for(Wall w : walls)
		{
			if(ballRect.intersectsLine(w.getX1(), w.getY1(), w.getX2(), w.getY2()))
			{
				int temp = ball.getDy();
				ball.setDy(ball.getDx());
				ball.setDx(temp);
				
			}
		}
		for(WallBlock w : wallBlocks)
		{
			Rectangle wallRect = w.getRectangle();
			
			if(ballRect.intersects(wallRect) && w.orientation)
			{
				ball.setDy(-ball.getDy());
				break;
			}
			else if(ballRect.intersects(wallRect) && !w.orientation)
			{
				ball.setDx(-ball.getDx());
				break;
			}
		}
		for(RedBumper r : redBumpers)
		{
			if(ballRect.intersects(r.getRectangle()))
			{
				ball.setDy(-(ball.getDy() + 10));
				ball.setDx(-(ball.getDx() + 10));
			}
		}
		Rectangle springRect = new Rectangle(500, 900, 50, 50); 
		if(ballRect.intersects(springRect) && spring.equals(released))
		{
			ball.setDy(-41);
		}
		else if(ballRect.intersects(springRect))
			ball.setDy(0);
		if(ball.getX() < 420)
		{
			wallBlocks.add(new WallBlock(450, 50, false));
			wallBlocks.add(new WallBlock(450, 100, false));
		}
		if(ball.getY() > 1000)
		{
			ballsLeft--;
			ball.setX(500);
			ball.setY(875);
		}
		ball.move();
		
	}
}
