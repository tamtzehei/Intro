import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Flippers implements KeyListener
{
	boolean leftPosition, rightPosition; // false for down  true for up

	public Flippers()
	{
		leftPosition = false;
		rightPosition = false;
	}
	
	public boolean isLeftPosition()
	{
		return leftPosition;
	}

	public boolean isRightPosition()
	{
		return rightPosition;
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			leftPosition = true;
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightPosition = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
			
	}

}
