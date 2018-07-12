package Windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import processing.core.PApplet;
import processing.core.PImage;

public class OtherTools extends ToolBar {

	private JTextField[] HSL = new JTextField[3];
	private JLabel[] titles = new JLabel[3];
	private JButton setHSL = new JButton("Set");
	private JButton resetHSL = new JButton("Reset ");
	private JButton apply = new JButton("Apply     ");
	private JButton[] Fillers = new JButton[5];
	private JLabel Filter = new JLabel("                Filter");
	private JLabel Merge = new JLabel("                 Merge");
	private String[] filterStrings = { "Add Filter", "Threshold", "Gray", "Posterize", "Blur", "Erode", "Dilate" };
	private String[] mergeStrings = { "Merge images", "Add", "Subtract", "Darkest", "Lightest" };
	private JComboBox filterList = new JComboBox(filterStrings);
	private JComboBox mergeList = new JComboBox(mergeStrings);
	private BufferedImage pic;
	private int postVal = 255;
	private JFrame tintWindow = new JFrame();
	private boolean palletOpen;
	private Pallet p = new Pallet();
	
	/**
	 * Constructs the OtherTools panel of the toolbar using the constructor of the parent class, ToolBar
	 */
	public OtherTools(){
		mergeList.setEnabled(false);
		filterList.setSelectedIndex(4);
		filterList.addActionListener(null);
		filterList.setSelectedItem("Add Filter");
		setLayout(new GridLayout(9,2));
		HSL[0] = new JTextField("0");
		HSL[1] = new JTextField("255,255,255");
		HSL[2] = new JTextField("100");
		titles[0] = new JLabel("                Transparency");
		titles[1] = new JLabel("                Tint");
		titles[2] = new JLabel("                Brightness");
		for( int i=0;i<HSL.length;i++){
			Fillers[i] = new JButton();
			Fillers[i].setVisible(false);
			add(titles[i]);
			add(HSL[i]);
		}
		tintWindow.setSize(200,200);
		tintWindow.setLocation(500, getY()+500);
		tintWindow.setResizable(false);
		tintWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		HSL[1].addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
				if (palletOpen == false){
					tintWindow.setVisible(true);
					palletOpen = true;
					tintWindow.addMouseListener(p);
					tintWindow.add(p);
				}
            }

            @Override
            public void focusLost(FocusEvent e) {
            	//palletOpen = false;
            }
        });
		
		Fillers[3] = new JButton();
		Fillers[3].setVisible(false);	
		Fillers[4] = new JButton();
		Fillers[4].setVisible(false);
		add(setHSL);
		add(resetHSL);
		type = 3;
		add(Fillers[0]);
		add(Fillers[1]);
		add(Filter);
		//add(Fillers[1]);
		add(filterList);
		add(Fillers[2]);
		add(Fillers[3]);
		add(Merge);
		add(mergeList);
		add(Fillers[4]);
		add(apply);
		
	}
	
	/**
	 * Method used to check for specific type of filter, as selected by the user
	 * @post If the reset button clicked, the tintwindow is set to false, the pallet is closed, and all the JTextFiled return to their initial, constructor values
	 * @post
	 */
	public int getAction(){
		if (resetHSL.getModel().isPressed()){
			tintWindow.setVisible(false);
			palletOpen = false;
			tintWindow.setVisible(false);
			HSL[0].setText("0");
			HSL[1].setText("255,255,255");
			HSL[2].setText("100");
		}
		String temp = (String)filterList.getSelectedItem();
		if (temp.equals("Threshold")){
			return 16;
		}
		else if(temp.equals("Gray")){
			return 12;
		}
		else if(temp.equals("Posterize")){
			return 15;
		}
		else if(temp.equals("Blur")){
			return 11;
		}
		else if(temp.equals("Erode")){
			return 17;
		}
		else if(temp.equals("Dilate")){
			return 18;
		}
		return 0;
	}
	/**
	 * Method used to set the mergeList JComboBox to true
	 */
	public void enable(){
		mergeList.setEnabled(true);
	}
	/**
	 * Method used to check for the specific type of merge type, based on the selection of the user
	 */
	public int getAction(int x){
		String merge = (String)mergeList.getSelectedItem();
		if (merge.equals("Add")){
			return 2;
		}
		else if (merge.equals("Subtract")){
			return 4;
		}
		else if (merge.equals("Darkest")){
			return 16;
		}
		else if (merge.equals("Lightest")){
			return 8;
		}
		return 0;
	}
	/**
	 * Method used to check if the apply button for filters is selected
	 */
	public boolean isPressed(){
		if (apply.getModel().isPressed()){
			return true;
		}
		return false;
	}
	/**
	 * Method used to check if the setHSL button for filters is selected
	 */
	public boolean isSet(){
		if (setHSL.getModel().isPressed()){
			return true;
		}
		return false;
	}
	/**
	 * Method used to check for the type of change made by the user in the JTextFields
	 * @return 1 for tint change, 2 for brightness change, 3 for transparency change
	 */
	public int getType(){
		if (!HSL[1].getText().equals("255,255,255")){
			return 1;
		}
		else if (!HSL[2].getText().equals("100")){
			return 2;
		}
		else if (!HSL[0].getText().equals("0")){
			return 3;
		}
		return 0;
	}
	/**
	 * Method used to check for the level of Posterization entered by the user
	 * @pre value must be between 2 and 255
	 */
	public int getPostValue(){
		String inputValue = JOptionPane.showInputDialog("Level of Posterization (2-255)");
		if (inputValue!=null&&inputValue.length()>0){
			postVal = Integer.parseInt(inputValue);
		}
		return postVal;
	}
	/**
	 * Method gets the color of tint selected by the user
	 * @return int value representing an RGB value
	 */
	public int getTintColor(){
		if (palletOpen){
			Color c = new Color(p.getPixelColor());
			HSL[1].setText(c.getRed()+","+c.getGreen()+","+c.getBlue());
			return p.getPixelColor();
		}
		return -1;
	}
	/**
	 * returns the level of brightness entered by the user between 0 and 100
	 * @pre input must be a number between 0 and 100
	 */
	public int getBrightness(){
		String inputValue = HSL[2].getText();
		int result;
		try{
			result = Integer.parseInt(inputValue);
			if (result>100){
				return 100;
			}
			else if (result<0){
				return 0;
			}
		}
		catch (NumberFormatException e){
			result = 100;
		}
		return result;
	}
	/**
	 * returns the level of tint(transparency) entered by the user between 0 and 100
	 * @pre input must be a number between 0 and 100
	 */
	public int getTintLevel(){
		String inputValue = HSL[0].getText();
		int result;
		try{
			result = Integer.parseInt(inputValue);
			if (result>100){
				return 100;
			}
			else if (result<0){
				return 0;
			}
		}
		catch (NumberFormatException e){
			result = 0;
		}
		return result;
	}






	


	
	

}
