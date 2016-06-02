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
	int score, ballsLeft, speed, count;
	boolean first;
	
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
			{9,2,3,3,3,3,3,3,3,1,3,1},
			{9,1,3,3,3,3,3,3,3,1,3,1},
			{9,1,3,3,3,3,3,3,3,1,3,1},
			{9,1,3,3,3,3,3,3,3,1,3,1},
			{9,1,3,3,3,3,3,3,3,1,3,1},
			{8,9,2,3,3,3,3,3,11,1,3,1},
			{8,9,9,2,3,3,3,11,9,1,3,1},
			{8,8,9,1,5,3,10,1,8,1,3,1},
			{8,8,9,1,2,3,11,1,8,1,12,1},
			{8,8,8,9,1,3,1,9,8,8,1,8},
	};
	
	public Board()
	{
		addKeyListener(this);

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
					walls.add(new Wall((j + 1) * 50, i * 50, j * 50, (i + 1) * 50));
				else if(board[i][j] == 13)
					wallBlocks.add(new WallBlock(j * 50, i * 50, true));
				else if(board[i][j] == 9)
					redBumpers.add(new RedBumper(j * 50, i * 50));		
			}
		}
		spring = compressed;
		left = new Flippers(false, 5 * 50, 11 * 50);
		right = new Flippers(true, 7 * 50, 11 * 50);
		ball = new Pinball(500, 875);
		ballsLeft = 3;
		first = true;
		score = 0;
		
		double rand = Math.random();
		if(rand < .2)
			speed = 41;
		else if(rand <.4)
			speed = 42;
		else if(rand < .6)
			speed = 43;
		else if(rand < .8)
			speed = 44;
		else
			speed = 45;
		
		System.out.println(wallBlocks.size());
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
		g.clearRect(0, 0, 800, 1000);
		g.setColor(Color.BLACK);
		g.drawString("N for next ball", 650, 100);
		g.drawString("R for restart", 650, 150);
		g.drawString("Balls Left: " + ballsLeft, 650, 200);
		g.drawString("Score: " + ((Integer)score).toString(), 650, 500);	
		
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
					g.setColor(Color.yellow);
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
		}
		for(WallBlock w : wallBlocks)
		{
			g.setColor(Color.YELLOW);
			g.fillRect(w.getX(), w.getY(), 50, 50);
		}
		g.drawImage(ball.getImage(), ball.getX(), ball.getY(), 30, 30, null);
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
		else if(e.getKeyCode() == KeyEvent.VK_N)
		{
			ballsLeft--;
			ball.setX(500);
			ball.setY(875);
			ball.setDx(0);
			ball.setDy(0);
			wallBlocks.remove(wallBlocks.size() - 1);
			wallBlocks.remove(wallBlocks.size() - 1);
			first = true;
			
			double rand = Math.random();
			if(rand < .2)
				speed = 41;
			else if(rand <.4)
				speed = 42;
			else if(rand < .6)
				speed = 43;
			else if(rand < .8)
				speed = 44;
			else
				speed = 45;
		}
		else if(e.getKeyCode() == KeyEvent.VK_R)
		{
			spring = compressed;
			ballsLeft = 3;
			first = true;
			score = 0;
			ball.setX(500);
			ball.setY(875);
			ball.setDx(0);
			ball.setDy(0);
			
			double rand = Math.random();
			if(rand < .2)
				speed = 41;
			else if(rand <.4)
				speed = 42;
			else if(rand < .6)
				speed = 43;
			else if(rand < .8)
				speed = 44;
			else
				speed = 45;
		}
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
			ball.dy -= 30;
		}
		
		else if(ballRect.intersects(left.getBounds(false, true)) || ballRect.intersects(right.getBounds(false, false)))
		{
			ball.changeDirection(false, false, true);
			
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
				ball.setDy(-1 * (ball.getDy() + 5));
				ball.setDx(-1 * (ball.getDx() + 5));
				
			}
		}
		Rectangle springRect = new Rectangle(500, 900, 50, 50); 
		if(ballRect.intersects(springRect) && spring.equals(released))
		{
			
			ball.setDy(-speed);
		}
		else if(ballRect.intersects(springRect))
			ball.setDy(0);
		if(ball.getX() < 420 && first)
		{
			wallBlocks.add(new WallBlock(450, 50, false));
			wallBlocks.add(new WallBlock(450, 100, false));
			first = false;
		}
		if(ball.getY() > 1000)
		{
			ballsLeft--;
			ball.setX(500);
			ball.setY(875);
			ball.setDx(0);
			ball.setDy(0);
			wallBlocks.remove(wallBlocks.size() - 1);
			wallBlocks.remove(wallBlocks.size() - 1);
			first = true;
			
			double rand = Math.random();
			if(rand < .2)
				speed = 41;
			else if(rand <.4)
				speed = 42;
			else if(rand < .6)
				speed = 43;
			else if(rand < .8)
				speed = 44;
			else
				speed = 45;
		}
		
		ball.move();
		
	}
}
