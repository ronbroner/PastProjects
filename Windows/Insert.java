package Windows;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Insert extends ToolBar{
	/**
	 * Constructs the Insert panel of the toolbar using the constructor of the parent class, ToolBar
	 */
	public Insert(){
		super();
		type = 2;
		for (int s = 0;s<ex.length;s++){
			ex[s] = new JButton();
		}
		setIcons();
		for (int i = 0; i<ex.length; i++)
		{
			add(ex[i]);
		}
	}
	
	/**
	 * Converts image url's from ImageIcon Folder to an Array of imageIcons
	 * @return array of ImageIcons 
	 */
	private ImageIcon[] setIcons(){
		ImageIcon[] icons = new ImageIcon[10];
		icons[0] = new ImageIcon("ImageIcons/overImage.png");
		icons[1] = new ImageIcon("ImageIcons/shapes.png");
		icons[2] = new ImageIcon("ImageIcons/zoomin.png");
		icons[3] = new ImageIcon("ImageIcons/zoomout.png");
		icons[4] = new ImageIcon("ImageIcons/sample.png");
		icons[5] = new ImageIcon("ImageIcons/newPage.jpeg");
		icons[6] = new ImageIcon("ImageIcons/left.png");
		icons[7] = new ImageIcon("ImageIcons/right.png");
		icons[8] = new ImageIcon("ImageIcons/save.png");
		icons[9] = new ImageIcon("ImageIcons/upload.png");
		for (int i=0;i<icons.length;i++){
			Image img = icons[i].getImage() ;  
			Image newimg = img.getScaledInstance( 70,70,  java.awt.Image.SCALE_SMOOTH ) ;  
			icons[i] = new ImageIcon( newimg );
			ex[i].setIcon(icons[i]);
		}
		return icons;
	}
	
	
	/**
	 * Used check which of the buttons in the toolbar is being pressed
	 * @return the value of the specific button pressed by the user
	 * @post buttons 7-10 are always enabled, regardless of the users press
	 */
	public int getAction(){
		for (int i=0; i<ex.length; i++){
			if (!ex[i].getModel().isEnabled()){
				ex[0].setEnabled(true);
				ex[2].setEnabled(true);
				ex[3].setEnabled(true);
				ex[5].setEnabled(true);
				ex[6].setEnabled(true);
				ex[7].setEnabled(true);
				ex[8].setEnabled(true);
				ex[9].setEnabled(true);
				return i+11;
			}
		}
		return 0;
	}
}
