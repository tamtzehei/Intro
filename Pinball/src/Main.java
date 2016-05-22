import java.awt.Color;
import java.awt.Frame;

public class Main 
{
	public static void main(String[] args)
	{
		Frame frm = new Board();
		frm.setBackground(Color.white);
		frm.setSize(600, 1000);
		frm.setVisible(true);
		//while(true)
			frm.repaint();
		
	}
}
