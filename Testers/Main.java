package Testers;

import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Windows.ImageView;
import Windows.Intro;


public class Main {
	
	private static int start = 0;
	private static int mode = 0;
	
	public static void main(String[] args){
		if (start == 0)
			{
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				JFrame open = new JFrame("EZ editor");
				open.setSize(500, 500);
				open.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Intro intro = new Intro();
				open.add(intro);
				open.setVisible(true);
				while (start != 1)
				{
					start = intro.getAction();
					mode = intro.getMode();
					if (mode!=0){
						intro.open(true);
					}
					if (mode==0){
						intro.open(false);
					}
					if (start==2){
						JOptionPane.showMessageDialog(intro, "By Ron Broner, Cole Brower, and Theodore Tefera"); 
					}
					System.out.println();
				}
				open.dispose();
			}
		
		
		if (start == 1){
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			JFrame w = new JFrame("EZ editor");
			w.setSize(500, 500);
			w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ImageView panel = new ImageView(mode);
			panel.init();
			w.add(panel);
			w.setVisible(true);
	}
}
}
