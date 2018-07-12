package Windows;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import processing.core.PImage;

public class ToolBar extends JPanel {
	protected JButton[] ex = new JButton[10];
	protected int type;
	/**
	 * Constructs the most basic toolbar object with a grid format of 5x2
	 */
	public ToolBar(){
		setLayout(new GridLayout(5,2));
	}

	@Override
	public void paintComponent(Graphics g){
		if (type==1||type==2){

			for (int i = 0; i<ex.length; i++){
				if (ex[i].getModel().isPressed()){
					for (int j=0;j<ex.length;j++){
						if (j != i)
							ex[j].setEnabled(true);
						else
							ex[j].setEnabled(false);
					}
				}
			}
		repaint();
		}
		

	}
	/**
	 * Method used to check for the level of Posterization entered by the user
	 * @pre value must be between 2 and 255
	 */
	public int getPostValue(){
		return 255;
	}
	/**
	 * Method used to check if the apply button for filters is selected
	 */
	public boolean isPressed(){
		return false;
	}
	
	
	/**
	 * Used check which of the buttons in the toolbar is being pressed
	 * @return the value of the specific button pressed by the user
	 * @post buttons 7-10 are always enabled, regardless of the users press
	 */
	public int getAction(){
		for (int i=0; i<ex.length; i++){
			if (!ex[i].getModel().isEnabled()){
				ex[3].setEnabled(true);
				ex[6].setEnabled(true);
				ex[7].setEnabled(true);
				ex[8].setEnabled(true);
				ex[9].setEnabled(true);
				return i+1;
			}
		}
		return 0;
	}
	/**
	 * Method used to check for the specific type of merge type, based on the selection of the user
	 */
	public int getAction(int x){
		return 0;
	}

	/**
	 * Method gets the color of tint selected by the user
	 * @return int value representing an RGB value
	 */
	public int getTintColor(){
		return 0;
	}
	/**
	 * Method used to check if the setHSL button for filters is selected
	 */
	public boolean isSet(){
		return false;
	}
	/**
	 * Method used to check for the type of change made by the user in the JTextFields
	 * 
	 */
	public int getType(){
		return 0;
	}
	/**
	 * returns the level of brightness entered by the user 
	 *
	 */
	public int getBrightness(){
		return 0;
	}
	/**
	 * returns the level of tint(transparency) entered by the user
	 */
	public int getTintLevel(){
		return 0;
	}
	
}
