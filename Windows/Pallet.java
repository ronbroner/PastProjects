package Windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Scrollbar;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import processing.core.PApplet;
import processing.core.PImage;


public class Pallet extends JPanel implements MouseListener{
	JLabel j;
	PImage image;
	private int width = 200;
	private int height = 150;
	private JTextField size;
	private int mouseX, mouseY;
	
	/**
	 * Constructs a default Pallet with an Image to select stroke color from and a Text field to input stroke size
	 */
	public Pallet(){
		mouseX = 0;
		mouseY = 0;
		j = new JLabel(new ImageIcon("ImageIcons/colors.png"));
		j.setMinimumSize(new Dimension(width, height));
		j.setPreferredSize(new Dimension(width, height));
		j.setMaximumSize(new Dimension(width, height));
		size = new JTextField("Stroke Size");
		add(j);
		add(size);
	}
	
	/**
	 * Method gives the size of the inputed stroke size
	 * @pre input must be an integer value greater than -1
	 * @return The size of the stroke entered by the user in the text field
	 */
	public int getStrokeSize(){
		
		String input = size.getText();
		int result = 1;
		try{
				result = Integer.parseInt(input);
		}
		catch (NumberFormatException e){
			
		}
		return result;
	}
	/**
	 * Method gives the user the color of a specific pixel
	 * @return the color of the pixel clicked on by the user
	 */
	public int getPixelColor(){
		int Color = -1;
		
		Icon icon = j.getIcon();
		Image i = iconToImage(icon);
		BufferedImage bi = new BufferedImage(icon.getIconWidth(),
		                icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
		
		Graphics g = bi.createGraphics();
	    g.drawImage(i, 0, 0, null);
	    g.dispose();
		
		if (mouseX !=0 && mouseY!=0){
			Color = bi.getRGB(mouseX+50, mouseY);
		}
		return Color;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 * @author RealHowTo
	 * @date April 29 2011
	 * 
	 */
	private static Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon)icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge = 
              GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }
}
