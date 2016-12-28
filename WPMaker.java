import java.io.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//import java.utils.Math;
public class WPMaker{

    private BufferedImage image;
    private BufferedImage newImage;
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

    public void loadFile(String url){
	try{
	    image = ImageIO.read(new File(url));
	    width = image.getWidth();
	    height = image.getHeight();
	}catch(IOException e){
	}
    }

    public void loadPixelData(){
	pixelArray = new Color[height][width];
	for (int r=0; r<height; r++){
	    for (int c=0; c<width; c++){
		pixelArray[r][c] = getPixelData(image,c,r);
	    }
	}
    }

    public Color getPixelData(BufferedImage image, int x, int y){
	int[] rgba = new int[4];
	int clr = image.getRGB(x,y);
	rgba[3] = Math.floorMod((clr & 0xff000000) >> 24, 256);
	rgba[0] = (clr & 0x00ff0000) >> 16;
	rgba[1] = (clr & 0x0000ff00) >> 8;
	rgba[2] = (clr & 0x000000ff);
	return new Color(rgba[0],rgba[1],rgba[2],rgba[3]);
    }

    public void updatePixelData(){
	for (int r=0; r<height; r++){
	    for (int c=0; c<width; c++){
		image.setRGB(c,r,pixelArray[r][c].getRGB());
	    }
	}
    }
    
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
    
    public void saveFile(String url){
	try{
	    String extension = url.substring(url.lastIndexOf('.')+1,url.length());
	    ImageIO.write(image, extension, new File("retest.png"));
	}catch(IOException e){
	}
    }
    
    public static void main(String[] args){
	WPMaker test = new WPMaker();
	test.loadFile(args[0]);
	test.loadPixelData();
	test.updatePixelData();
	test.saveFile(args[1]);
    }
}
