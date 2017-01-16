import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WPGUI extends JFrame implements ActionListener{
    private Container pane;
    private JTextField width,height,input,output,level,red,green,blue,caption;
    private JButton create;
    private JLabel labelWidth,labelHeight,labelInput,labelOutput,status,labelLevel,labelRed,labelGreen,labelBlue,labelCaption;
    private JCheckBox isNegative;
    
    public WPGUI(){
	this.setTitle("Wallpaper Maker");
	this.setSize(400,400);
	this.setLocation(50,50);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	pane = this.getContentPane();
	pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

	labelWidth = new JLabel("Width:");
	
	width = new JTextField(10);
	width.setHorizontalAlignment(JTextField.CENTER);

	labelHeight = new JLabel("Height:");
	
	height = new JTextField(10);
	height.setHorizontalAlignment(JTextField.CENTER);

	labelInput = new JLabel("Input Filename:");
	
	input = new JTextField(10);
	input.setHorizontalAlignment(JTextField.CENTER);

	labelOutput = new JLabel("Output Filename:");
	
	output = new JTextField(10);
	output.setHorizontalAlignment(JTextField.CENTER);

	labelLevel = new JLabel("Level (float between 0 and 1)");

	level = new JTextField(10);
	level.setHorizontalAlignment(JTextField.CENTER);
	
	isNegative = new JCheckBox("isNegative");

	labelRed = new JLabel("R");
	red = new JTextField(10);
	labelGreen=new JLabel("G");
	green = new JTextField(10);
	labelBlue= new JLabel("B");
	blue = new JTextField(10);
	
	labelCaption = new JLabel("Caption:");
	caption = new JTextField(10);

	create = new JButton("create");
	create.setActionCommand("wpmaker");
	create.addActionListener(this);

	status = new JLabel(" ");
	
	pane.add(labelWidth);
	pane.add(width);
	pane.add(labelHeight);
	pane.add(height);
	pane.add(labelInput);
	pane.add(input);
	pane.add(labelOutput);
	pane.add(output);
	pane.add(labelLevel);
	pane.add(level);
	pane.add(isNegative);
	pane.add(labelRed);
	pane.add(red);
	pane.add(labelGreen);
	pane.add(green);
	pane.add(labelBlue);
	pane.add(blue);
	pane.add(labelCaption);
	pane.add(caption);
	pane.add(create);
	pane.add(status);
    }

    public void actionPerformed(ActionEvent e){
	String action = e.getActionCommand();
	if (action.equals("wpmaker")){
	    WPMaker test = new WPMaker();
	    //Loads the image at url
	    test.loadFile(input.getText());
	    //Loads the pixel color array
	    test.loadPixelData();
	    //Applies the transformation to the image
	    test.transform(Float.parseFloat(level.getText()),isNegative.isSelected());    
	    //Saves the stored image
	    test.saveFile(output.getText(),
			  Integer.parseInt(width.getText()),
			  Integer.parseInt(height.getText()),
			  new Color(Integer.parseInt(red.getText()),
				    Integer.parseInt(green.getText()),
				    Integer.parseInt(blue.getText())),
			  caption.getText());
	    status.setText(output.getText()+" created");
	}
    }

    public static void main(String[]args){
	WPGUI w = new WPGUI();
	w.setVisible(true);
    }
}
