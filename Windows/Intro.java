package Windows;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class Intro extends JPanel implements ActionListener {

	JButton button1, button2;
	JLabel title, message;
	private String[] modeStrings = { "Mode", "Casual", "Studio" };
	private JComboBox modeList = new JComboBox(modeStrings);
	int mode = 0;


	/**
	 * Initializes the introduction panel to start off the project.
	 */
	public Intro() {
		JPanel p = new JPanel();		
		setBackground(Color.blue);

		title = new JLabel("EZEditor");
		title.setFont(new Font("Dialogue-Bold", Font.BOLD, 50));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setAlignmentY(Component.CENTER_ALIGNMENT);
		p.add(title);
		
		message = new JLabel("Welcome!");
		message.setFont(new Font("Serif", Font.PLAIN, 20));
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		message.setAlignmentY(Component.CENTER_ALIGNMENT);
		//message.setLocation(message.getX(), message.getY()+ 200);		// This doesn't work
		p.add(message);
		
		p.setBackground(Color.GREEN);

		p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
		p.add(Box.createVerticalStrut(300));
		
		p.add(modeList);
		
		button1 = new JButton("Start the Program"); 		
		button1.addActionListener(this);
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		button1.setEnabled(false);
		p.add(button1);

		button2 = new JButton("Credits");
		button2.addActionListener(this);
		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.add(button2);

		add(p);
	}

	/**
	 * This method allows you to get the button that was pressed.
	 * @return The integer representing the button that was pressed
	 */
	public int getAction(){
		if (button1.getModel().isPressed()){
			return 1;
		}
		else if(button2.getModel().isPressed()){
			button2.setEnabled(false);
			return 2;
		}
		return 0;
	}
	/**
	 * 
	 * @return the mode currently set by the user
	 * @return 0 for undecided, 1 for casual mode, 2 for studio mode
	 */
	public int getMode(){
		String mode = (String)modeList.getSelectedItem();
		if (mode.equals("Mode")){
			return 0;
		}
		else if (mode.equals("Casual")){
			return 1;
		}
		else if (mode.equals("Studio")){
			return 2;
		}
		return 0;
	}
	/**
	 * Method changes the condition of the open Program button
	 * @param state The condition of the Button to open the program, false means disabled and true means enabled
	 */
	public void open(boolean state){
		if (state)
			button1.setEnabled(true);
		else
			button1.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}