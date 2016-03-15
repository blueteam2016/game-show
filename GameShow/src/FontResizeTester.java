import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FontResizeTester {

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private Font f;
	double ratioW;
	double ratioL;
	
	public FontResizeTester(){
		
		frame = new JFrame("This is not a test");
		//frame.setSize(200,60);
		panel = new JPanel();
		panel.setLayout(new GridLayout());
		
		label = new JLabel("Honey, I shrunk the font!");
		
        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
            	
            	System.out.println("hi");
            	
            	Font labelFont = label.getFont();
            	String labelText = label.getText();
            	int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
            	int componentWidth = label.getWidth();
            	double widthRatio = (double)componentWidth / (double)stringWidth;
            	int newFontSize = (int)(labelFont.getSize() * widthRatio);
            	int componentHeight = label.getHeight();
            	int fontSizeToUse = Math.min(newFontSize, componentHeight);
            	label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
             
               
               
            }
        });
		
		
		panel.add(label);
		frame.setContentPane(panel);
		
		frame.pack();
		frame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	private static void runGUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		new FontResizeTester();
	}
	
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				runGUI();
			}
		});
	}
}
