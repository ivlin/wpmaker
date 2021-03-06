import java.io.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class WPMaker{

    private BufferedImage image;
    private Color[][] pixelArray;
    private int height, width;
    
    public WPMaker() {
	this(null,null);
    }

    public WPMaker(String url, String text) {
	if (url != null){
	    loadFile(url);
	}
    }

    /*
      Stores the image at the url
     */
    public void loadFile(String url){
	try{
	    image = ImageIO.read(new File(url));
	    width = image.getWidth();
	    height = image.getHeight();
	}catch(IOException e){
	}
    }

    /*
      Loads data from bufferedimage into the pixel color array
     */
    public void loadPixelData(){
	pixelArray = new Color[height][width];
	for (int r=0; r<height; r++){
	    for (int c=0; c<width; c++){
		pixelArray[r][c] = getPixelData(image,c,r);
	    }
	}
    }

    /*
      returns the color of the pixel at (x,y) on image
     */
    public Color getPixelData(BufferedImage image, int x, int y){
	int[] rgba = new int[4];
	int clr = image.getRGB(x,y);
	rgba[3] = 255;//Math.floorMod((clr & 0xff000000) >> 24, 256);
	rgba[0] = (clr & 0x00ff0000) >> 16;
	rgba[1] = (clr & 0x0000ff00) >> 8;
	rgba[2] = (clr & 0x000000ff);
	return new Color(rgba[0],rgba[1],rgba[2],rgba[3]);
    }

    /*
      Sets a bufferedimage to the stored pixel array
     */
    public void updatePixelData(BufferedImage image){
	for (int r=0; r<height; r++){
	    for (int c=0; c<width; c++){
		image.setRGB(c,r,pixelArray[r][c].getRGB());
	    }
	}
    }
    
    /*
      Prints out the array of pixel colors
     */
    public void printPixelData(){
	System.out.println("[");
	for (int r=0; r<height; r++){
	    for (int c=0; c<width; c++){
		System.out.printf(pixelArray[r][c].toString());
	    }
	    System.out.println("\n");
	}
	System.out.println("]");
    }

    /*
      Some transformation on the array of pixels
      if isNegative is true: brights are turned transparent, darks become dark
      if isNegative is false: brights are turned black, darks become transparent
     */
    public void transform(float contrast, boolean isNegative){
	Color cur;
	for (int r = 0; r<height; r++){
	    for (int c=0; c<width; c++){
		cur = pixelArray[r][c];
		if ((cur.getRed()+cur.getGreen()+cur.getBlue())/3 >= 255*contrast){
		    if (isNegative){
			pixelArray[r][c] = new Color(0,0,0,255);
		    }
		    else{
			pixelArray[r][c] = new Color(255,255,255,0);
		    }
		}
		else{
		    if (isNegative){
			pixelArray[r][c] = new Color(255,255,255,0);
		    }
		    else{
			pixelArray[r][c] = new Color(0,0,0,255);
		    }
		}
	    }
	}
    }
    
    /*
      Creates an solid color-colored background of size width and height
      Image is stamped onto the background
    */
    public BufferedImage loadBackground(int width, int height, Color color, BufferedImage image){
	BufferedImage bg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	for (int r = 0; r<height; r++){
	    for (int c=0; c<width; c++){
		bg.setRGB(c,r,color.getRGB());
	    }
	}
	Graphics g = bg.createGraphics();
	g.drawImage(image,(width-image.getWidth())/2,(height-image.getHeight())/2,null);
	return bg;
    }

    /*
      Saves a bufferedimage as url
     */    
    public void saveFile(String url, int wpwidth, int wpheight, Color c, String caption){
	try{
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    updatePixelData(newImage);
	    BufferedImage bg = loadBackground(wpwidth,wpheight,c,newImage);
	    caption(bg,caption,"sans-serif",64);
	    String extension = url.substring(url.lastIndexOf('.')+1,url.length());
	    ImageIO.write(bg, extension, new File(url));
	}catch(IOException e){
	}
    }

    public void caption(BufferedImage bg, String caption, String fontFamily, int fontSize){
	Graphics2D g = bg.createGraphics();
	g.setColor(new Color(0,0,0));
	Font captionFont = new Font(fontFamily, Font.BOLD, fontSize);
	FontMetrics metrics = g.getFontMetrics(captionFont);
	g.setFont(captionFont);	
	g.drawString(caption, 
		     (bg.getWidth()-metrics.stringWidth(caption))/2, 
		     (bg.getHeight()+image.getHeight())/2+metrics.getHeight());
    }

    /*
    public static void main(String[] args){
	WPMaker test = new WPMaker();
	//Loads the image at url
	test.loadFile(args[0]);
	//Loads the pixel color array
	test.loadPixelData();
	//Applies the transformation to the image
	test.transform((float)0.5,false);
	//Saves the stored image
	test.saveFile("out.png",2000,1200,new Color(240,220,100),"Hello, \n World");
    }
    */
}
