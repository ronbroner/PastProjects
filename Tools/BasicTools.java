package Tools;

import java.awt.Color;
import Windows.ImageView;
import processing.core.PImage;


public class BasicTools {
	
	private static final float SCALE_FACTOR = .05f;
	
	private static Color negative(Color c){
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}
	
	/**
	 * This image makes the image have opposite RGB values to put the photo in a negative state
	 * @param image The image that we are going to make a new negative PImage
	 * @return A new PImage with opposite RGB values.
	 */
	public static PImage negative(PImage image){
		PImage copy = new PImage(image.width, image.height);
		for(int x = 0; x < image.width; x++){
			for(int y = 0; y < image.height; y++){
				Color c = new Color(image.get(x, y));
				copy.set(x, y, negative(c).getRGB());
			}
		}
		return copy;
	}
	

	/**
	 * This method crops the picture to remove unnecessary pixels
	 * @param x The x coordinate of the top left corner of the crop.
	 * @param y The y coordinate of the top left corner of the crop.
	 * @param width The width of the crop.
	 * @param height The height of the crop.
	 * @param image The image that you are cropping from.
	 * @return A new PImage that contains the crop.
	 */
	public static PImage crop(int x, int y, int width, int height, PImage image){
		return image.get(x, y, width, height);
	}
	
	/**
	 * This method erases a portion of the image.
	 * @param x The x coordinate of the erase.
	 * @param y The y coordinate of the erase.
	 * @param width The width of the erase.
	 * @param height The height of the erase.
	 * @param density The density of the eraser.
	 * @param image The image we will erase a portion of.
	 * @return A new image that contains the erase.
	 */
	public static PImage erase(int x, int y, int width, int height, int density, PImage image){
		return draw(x, y, width, height, Color.WHITE.getRGB(), density, image);
	}
	
	/**
	 * This method zooms in on the image.
	 * @param image The PImage that you are zooming in on.
	 */
	public static void zoomIn(ImageView image){
		image.incScale(SCALE_FACTOR);
	}
	
	/**
	 * This method zooms out of the image.
	 * @param image The image that you are zooming out of.
	 */
	public static void zoomOut(ImageView image){
		image.incScale(-SCALE_FACTOR);
	}
	
	
	/**
	 * This method allows you to draw
	 * @param x The center x coordinate of what you are drawing.
	 * @param y The center y coordinate of what you are drawing.
	 * @param width The width of the draw.
	 * @param height The height of the draw.
	 * @param color The color of what you are drawing.
	 * @param density The density of the draw.
	 * @param image The image that you are drawing on.
	 * @return a new PImage that contains the desired draw updates.
	 */
	public static PImage draw(int x, int y, int width, int height, int color,int density, PImage image){
		PImage copy = image.get(0, 0, image.width, image.height);
		
		for(int i = x; i < x + width; i++){
			for(int j = y; j < y + height; j++){
					int startX = 0, startY = 0, endX = 0, endY = 0;
					if (x>density-1)
						startX = x-density;
					else if (x>density/2-1)
						startX = x-density/2;
					else
						startX = x;
					if (y>density-1)
						startY = y-density;
					else if (y>density/2-1)
						startY = y-density/2;
					else
						startY = y;
					if (x<image.width-1-density){
						endX = x+1+density;
					}
					else if  (x<image.width-1-density/2){
						endX = x+1+density/2;
					}
					else
						endX = x+1;
					if (y<image.height-1-density){
						endY = y+1+density;
					}
					else if (y<image.height-1-density/2){
						endY = y+1+density/2;
					}
					else
						endY = y+1;
					for (int t=startX;t<endX;t++){
						for (int u=startY;u<endY;u++){
							copy.set(t, u, color);
						}
					}
			}
		}
		return copy;
	}
	
	/**
	 * This method rotates the image to the left.
	 * @param image A PImage that represents the image you are rotating
	 * @return a new PImage that is rotated to the left.
	 */
	public static PImage leftRotate(PImage image){
		PImage copy = new PImage(image.height, image.width);
		for(int x = 0; x < image.width; x++){
			for(int y = 0; y < image.height; y++){
				copy.set(y, x, image.get(image.width - 1 - x, y));
			}
		}
		return copy;
	}
	
	/**
	 * This method rotates the image to the right
	 * @param image A PImage that represents the image you are rotating
	 * @return a new PImage that is rotated to the right.
	 */
	public static PImage rightRotate(PImage image){
		PImage copy = new PImage(image.height, image.width);
		for(int x = 0; x < image.width; x++){
			for(int y = 0; y < image.height; y++){
				copy.set(y, x, image.get(x,  image.height - 1 - y));
			}
		}
		return copy;
	}
	
	/**
	 * This method mirrors the image Horizontally.
	 * @param image The image that is going to be mirrored
	 * @return A new PImage that is mirrored.
	 */
	public static PImage mirror(PImage image){
		PImage copy = image.get(0, 0, image.width, image.height);
		for(int y = 0; y < image.height; y++){
			for(int x = 0; x < image.width; x++){
				copy.set(x, y, image.get((image.width - 1) - x, y));
			}
		}
		return copy;
	}
	
	/**
	 * This method averages the color of all the pixels in the region specified, and draws a rectangle of that color in that rectangle
	 * @param x The x coordinate of the blend.
	 * @param y The y coordinate of the blend.
	 * @param w The width of the blend.
	 * @param h The height of the blend.
	 * @param image The image containing the blend.
	 * @return A new image containing the blend.
	 */
	public static PImage blend( int x, int y, int w, int h,PImage image){
		PImage copy = image.get(0, 0, image.width, image.height);
		double r = 0;
		double g = 0;
		double b = 0;
		double counter = 0;
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				Color c = new Color(image.get(x+i, y+j));
				r = r + c.getRed();
				g= g + c.getGreen();
				b = b + c.getBlue();
				counter++;
			}
		}
		int red = (int)(r/counter);
		int green  = (int)(g/counter);
		int blue = (int)(b/counter);
		Color c = new Color(red,green,blue);
		int color = c.getRGB();
		for (int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				copy.set(x+i,y+j,(int)color);
			}
		}
		return copy;
	}
	/**
	 * This method applies a filter to a specific images from the PConstants filter list
	 * @param type The type of filter to be used on the image (See PConstants for referece)
	 * @param image The image to which the filter will be applied to 
	 * @return Image with the specified applied to it
	 */
	public static PImage filter(int type, PImage image){

		PImage copy = image.get(0, 0, image.width, image.height);
		if (type ==15){
			copy.filter(type,10);
		}
		else
			copy.filter(type);
		return copy;
	}
	
	/**
	 * The method draws a rectangle.
	 * @param x The x coordinate of the top left corner of the rectangle.
	 * @param y The y coordinate of the top left corner of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @param Color The color of the rectangle.
	 * @param image The image that is getting the rectangle drawn for.
	 * @return A new image containing the image drawn.
	 */
	public static PImage rect(int x, int y, int width, int height, int Color, PImage image){
		
		PImage copy = image.get(0, 0, image.width, image.height);

		for(int i = x; i < x + width; i++){
			for(int j = y; j < y + height; j++){
				copy.set(i, j, Color);
				
			}
		}
		
		return copy;
	}
	
	/**
	 * This method merges two images together.
	 * @param type The type of merge.
	 * @param im1 The first image.
	 * @param im2 The second image.
	 * @param x1 The first images x coordinate of the merge.
	 * @param y1 The first images y coordinate of the merge.
	 * @param x2 The second images x coordinate of the merge.
	 * @param y2 The second images y coordinate of the merge.
	 * @return A new PImage containing both images merged together.
	 */
	public static PImage merge(int type, PImage im1, PImage im2, int x1, int y1, int x2, int y2){
		PImage copy = im1.get(0, 0, im1.width, im1.height);
		copy.blend(im2, x1, y1, im1.width, im1.height,x2, y2, im2.width, im2.height, type);
		return copy;
	}
	
}
