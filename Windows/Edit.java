package Windows;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Edit extends ToolBar{
	/**
	 * Constructs the Edit panel of the toolbar using the constructor of the parent class, ToolBar
	 */
	public Edit(){
		super();
		type = 1;
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
		icons[0] = new ImageIcon("ImageIcons/pointer.png");
		icons[1] = new ImageIcon("ImageIcons/eraser.png");
		icons[2] = new ImageIcon("ImageIcons/crop.png");
		icons[3] = new ImageIcon("ImageIcons/Help.png");
		icons[4] = new ImageIcon("ImageIcons/brush.jpeg");
		icons[5] = new ImageIcon("ImageIcons/swirl.png");
		icons[6] = new ImageIcon("ImageIcons/re.png");
		icons[7] = new ImageIcon("ImageIcons/un.png");
		icons[8] = new ImageIcon("ImageIcons/mirror.png");
		icons[9] = new ImageIcon("ImageIcons/colorFlip.jpeg");
		for (int i=0;i<icons.length;i++){
			Image img = icons[i].getImage() ;  
			Image newimg = img.getScaledInstance( 70,70,  java.awt.Image.SCALE_SMOOTH ) ;  
			icons[i] = new ImageIcon( newimg );
			ex[i].setIcon(icons[i]);
		}
		return icons;
	}
}
