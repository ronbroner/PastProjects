package Windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Tools.BasicTools;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;


public class ImageView extends PApplet {
	private final JFileChooser fc = new JFileChooser();
	private ArrayList<PImage> photos = new ArrayList<PImage>();
	private float scale = 1;
	private int xCor, yCor,xCor2, yCor2, mx, my, mx2, my2;
	private int w2, h2;
	private JTabbedPane tp = new JTabbedPane();
	private ToolBar[] tools = new ToolBar[3];
	private JFrame window3 = new JFrame();
	private boolean palletOpen = false;
	private Pallet p = new Pallet();
	private int picIndex;
	private int color = 0;
	private int xR, yR;	
	private int maxHeight = 10000, minHeight = 100;
	private int command = 1;
	private boolean secondPic = false;
	private boolean firstPicSelected = true;
	private boolean secondPicSelected = false;
	private int tintSet = 0;
	private boolean tinted = false;
	PImage topImage = new PImage();
	private int mode;
		/**
		 * Constructs the ImageView object with the initial image and the companion toolbar window. 
		 * Mode 1 means the user is in casual mode and can make unlimited undo calls, while mode
		 * 2 means that the user is in professional mode and can only make 5 undo calls, to save memory for photos
		 */
	public ImageView(int mode){
		this.mode = mode;
		tools[0] = new Edit();
		tools[1] = new Insert();
		tools[2] = new OtherTools();
		tp.setFocusable(true);
		makeToolBar();
		JFrame window2 = new JFrame();
		window2.setSize(200, 500);
		window2.setLocation(500, getY());
		window2.setMinimumSize(new Dimension(100,100));
		window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window2.add(tp);
		window2.setVisible(true);
		window3.setSize(200,200);
		window3.setLocation(500, getY()+20+window2.getHeight());
		window3.setResizable(false);
		window3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		picIndex = 0;
		xR = 0;
		yR = 0;
		w2 = 50;
		h2 = 50;

		//palletOpen = false;
	}
	/**
	 * Sets up the panel.
	 */
	public void setup() {
		size(500, 500);
		chooseImage();
	}
	
	/**
	 * Increments the scale to either zoom in or out the image.
	 * @param scale The amount you are adding onto the scale.
	 */
	public void incScale(float scale){
		if(scale > 0 && this.scale * photos.get(picIndex - 1).height < maxHeight){
			this.scale += scale;
		} else if(scale < 0 && this.scale * photos.get(picIndex - 1).height > minHeight){
			this.scale += scale;
		}
	}
	/**
	 * Lets user choose image from local database
	 * @pre image from data base must be a jpg file
	 * @post increments the index of photos, picIndex by 1 to allow room for new photo
	 */
	public void chooseImage(){
		//int max = photos.size();
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			photos.add(new PImage());
			File file = fc.getSelectedFile();
			photos.set(picIndex,loadImage(file.getAbsolutePath()));
		} else if (photos.size()<1){
			photos.add(new PImage());
			photos.set(picIndex,loadImage("ImageIcons/BlankPage.png"));
		}
		picIndex++;
	}
	
	/**
	 * Saves the most current image to a destination of the users choice
	 * @post adds a new photo of type .jpg to the Users desktop
	 */
	public void saveImage(){
		    if (tinted == true){
		    	System.out.println("hi");
		    	int tintColor = tools[2].getTintColor();
				int tintLevel = (int)(((1-0.01*tools[2].getTintLevel())) * 255); // from 0-100 to 0-255
				Color c = new Color(tintColor);
				tint(c.getRed(),c.getGreen(),c.getBlue(), tintLevel);
		    }
		    PImage newImage = photos.get(picIndex-1);
		    int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				newImage.save(file.getAbsolutePath());
		}
	}
	
	/**
	 * Checks for whichever button is pressed in the toolbar class, and performs a specific task based on the button
	 * @post modifies the index of photos, picIndex to either make room for the modified image, or to undo a certain move
	 * 
	 */
	public void draw() {	
		background(100);	
		if (photos.size()>0){
			int panelNum = tp.getSelectedIndex();
			if (panelNum<0)
				panelNum = 0;

			if (panelNum == 2){
				int action = tools[panelNum].getAction();
				int mergeAction = tools[panelNum].getAction(1);
				if (tools[panelNum].isPressed()){
					removeFrom(picIndex);
					if (secondPic == true){
						photos.add(BasicTools.merge(mergeAction, photos.get(picIndex-1), topImage, xCor, yCor, xCor2, yCor2));
						picIndex++;
					}
					photos.add(BasicTools.filter(action,photos.get(picIndex-1)));
					picIndex++;
				}
				if (secondPic){
					tools[panelNum].enable();
				}
				
				if (tools[panelNum].isSet()){
					tintSet = tools[panelNum].getType();

				}
			}
			else if (panelNum ==0 ||panelNum ==1){
				command = tools[panelNum].getAction(); 
				if (command == 1){
					if(mousePressed && mx>0 && my>0 && mx< photos.get(picIndex-1).width * scale && my< photos.get(picIndex-1).height * scale){ 
						xCor = mouseX-mx;
						yCor = mouseY-my;
						firstPicSelected = true;
						secondPicSelected = false;
					 	}
					if (secondPic==true&&(mousePressed && mx2>0 && my2>0 && mx2< w2 && my2< h2 )){
						firstPicSelected = false; 
						secondPicSelected = true;
						xCor2 = mouseX-mx2;
						yCor2 = mouseY-my2;
					}
				}else if(command == 13){
				if (firstPicSelected == true)
					BasicTools.zoomIn(this);
				else if (secondPicSelected == true){
					w2 = w2 +10;
					h2 = h2 + 10; 
				}
				}else if (command ==4){
					JOptionPane.showMessageDialog(this, "Tips: \n1) select image you want to edit using drag tool \n2)maximum of 10 images backlog, edit carefuly \n"); // PUT INSTRUCTIONS HERE
				}
			
				else if(command == 5 ){
					if (palletOpen == false){
						window3.setVisible(true);
						palletOpen = true;
						window3.addMouseListener(p);
						window3.add(p);
					}
					int tColor = p.getPixelColor();
					if (tColor !=-1){
						color = tColor;
					}
					int density = p.getStrokeSize();
					if (this.mousePressed){
						if (picIndex==photos.size()+1){
							picIndex--;
						}
						photos.add(BasicTools.draw((int)((this.mouseX - xCor) / scale), (int)((this.mouseY - yCor) / scale), 1, 1, color,density, photos.get(picIndex-1)));
						picIndex++;
					}
					removeFrom(picIndex);
				}
				else {
					window3.setVisible(false);
					palletOpen = false;
				}
				if(command ==  7){
					photos.add(BasicTools.leftRotate(photos.get(picIndex - 1)));
					picIndex++;
					removeFrom(picIndex);
				} else if(command ==  8){
					photos.add(BasicTools.rightRotate(photos.get(picIndex - 1)));
					picIndex++;
					removeFrom(picIndex);
				}else if(command == 9){
					photos.add(BasicTools.mirror(photos.get(picIndex - 1)));
					picIndex++;
					removeFrom(picIndex);
				} else if(command == 10){
					photos.add(BasicTools.negative(photos.get(picIndex - 1)));
					picIndex++;
					removeFrom(picIndex);
				}
				else if (command ==11){
				
					int returnVal = fc.showOpenDialog(this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						topImage = loadImage(file.getAbsolutePath());
						secondPic = true;
					} 
				}
				else if(command == 14){
					if (firstPicSelected == true)
						BasicTools.zoomOut(this);
					else if (secondPicSelected == true){
						w2 = w2-10;
						h2 = h2-10;
					}
				}
				else if (command == 15){
					PImage p = photos.get(picIndex-1);
					if (mousePressed)
						color = p.get((int)((this.mouseX - xCor) / scale), (int)((this.mouseY - yCor) / scale));
				}
				else if (command == 16){
					photos.add(loadImage("ImageIcons/BlankPage.png"));
					picIndex++;
					removeFrom(picIndex);
				}
				else if (command == 17){
					if(picIndex>1){
						picIndex--;
					}
				}
				else if (command == 18){
					if (picIndex<photos.size()){
						picIndex++;
					}
				}
				else if (command == 19){
					saveImage();
				}
				else if (command == 20){
					chooseImage();
				}
			}
			if (mode == 1){
				if (photos.size()>200){
					photos.remove(0);
				}
			}
			else if(mode == 2){
				if (photos.size()>5){
					photos.remove(0);
				}
			}
			if (picIndex==photos.size()+1){
				picIndex--;
			}
			int tintColor = tools[2].getTintColor();
			int tintLevel = (int)(((1-0.01*tools[2].getTintLevel())) * 255);// from 0-100 to 0-255
			if (tintSet == 0){
				noTint();
			}
			if (tintSet == 1){
			    tinted = true;
				if (tintColor!=-1){
					Color c = new Color(tintColor);
					tint(c.getRed(),c.getGreen(),c.getBlue(), tintLevel);
				}
			}
			else if (tintSet == 2){
				tinted = true;
				int brightness = (int)((0.01*tools[2].getBrightness())*255);
				tint(brightness, tintLevel);
			}
			else if (tintSet == 3){
				tinted = true;
				tint(255, tintLevel);
			}
			image(photos.get(picIndex-1), xCor, yCor, photos.get(picIndex-1).width * scale, photos.get(picIndex-1).height * scale);
			if (secondPic == true){
				image(topImage, xCor2,yCor2, w2,h2);
			}
			fill(color);
			noTint();
			this.rect(0,0,30,30);
		}
	}
	
	private void removeFrom(int index){
		while (index<photos.size()){
			photos.remove(index);
		}
	}
	
	private void makeToolBar(){
		JComponent panel1 = makeTextPanel("Something Went Wrong",1);
		tp.addTab("Edit 1", null, panel1,
		                  null);
		JComponent panel2 = makeTextPanel("Something Went Wrong",2);
		tp.addTab("Edit 2", null, panel2,
		                  null);

		JComponent panel3 = makeTextPanel("Something Whent Wrong",3);
		tp.addTab("Filters", null, panel3,
		                  null);

		
	}
	
	
	public void mouseDragged(){
		super.mouseDragged();
		if(tools[tp.getSelectedIndex()].getAction() == 5){
			int tColor = p.getPixelColor();
			if (tColor !=-1){
				color = tColor;
			}
			int density = p.getStrokeSize();
			if (this.mousePressed){
				if (picIndex==photos.size()+1){
					picIndex--;
				}
				photos.add(BasicTools.draw((int)((this.mouseX - xCor) / scale), (int)((this.mouseY - yCor) / scale), 1, 1, color,density, photos.get(picIndex-1)));
				picIndex++;
				removeFrom(picIndex);
			}
		}
		else {
			window3.setVisible(false);
			palletOpen = false;
		}
		if (tools[tp.getSelectedIndex()].getAction() == 2){
			photos.add(BasicTools.erase((int)((this.mouseX - xCor) / scale), (int)((this.mouseY - yCor) / scale), 1, 1, 2, photos.get(picIndex-1)));
			picIndex++;
			removeFrom(picIndex);
		}
	}
	
	public void mousePressed(){
			mx = mouseX-xCor;
			my = mouseY-yCor;
			mx2 = mouseX-xCor2;
			my2 = mouseY-yCor2;
	}
	
	
	
	public void mouseReleased(MouseEvent e){
		super.mouseReleased(e);
		xR = mouseX-xCor;
		yR = mouseY-yCor;
		int width =  (int)((xR - mx) / scale);
		int height = (int)((yR - my) / scale);
		if (width>0&&height>0){
			if(tools[tp.getSelectedIndex()].getAction() == 3){
				photos.add(BasicTools.crop((int)(mx / scale), (int)(my / scale), (int)((xR - mx) / scale), (int)((yR - my) / scale), photos.get(picIndex - 1)));
				picIndex++;
				removeFrom(picIndex);
			}
			
			else if (tools[tp.getSelectedIndex()].getAction() == 6){
				photos.add(BasicTools.blend((int)(mx / scale), (int)(my / scale), (int)((xR - mx) / scale), (int)((yR - my) / scale), photos.get(picIndex - 1)));
				picIndex++;
				removeFrom(picIndex);
			}
			else if(tools[tp.getSelectedIndex()].getAction() == 12){
				photos.add(BasicTools.rect((int)(mx / scale), (int)(my / scale), (int)((xR - mx) / scale), (int)((yR - my) / scale), color, photos.get(picIndex - 1)));
				picIndex++;
				removeFrom(picIndex);
			}
		}
	}
	
	public void mouseWheel(MouseEvent event) {
		  float e = event.getCount();
		  if (firstPicSelected){
		  	if (e>0){
		  		BasicTools.zoomIn(this);
		  	}
		  	else{
		  		BasicTools.zoomOut(this);
		  		}
		  }
		  else if (secondPicSelected){
			  if (e>0){
				  h2+=5;
				  w2+=5;
			  }
			  else if (e<0){
				  h2-=5;
				  w2-=5;
			  }
		  }
		}	
	
	private JComponent makeTextPanel(String text, int panelNum) {
		JPanel panel = new JPanel(false);
		if (panelNum == 1){
			 tools[0].setFocusable(true);
			 panel.add(tools[0]);
		}
		else if (panelNum == 2){
			tools[1].setFocusable(true);
			 panel.add(tools[1]);
		}
		else if (panelNum == 3){
			tools[2].setFocusable(true);
			 panel.add(tools[2]);
		}
	    return panel;
	}
}
