import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FontResizeTester {

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	double ratio;
	
	public FontResizeTester(){
		
		frame = new JFrame("This is not a test");
		frame.setSize(300,300);
		panel = new JPanel();
		
		label = new JLabel("Honey, I shrunk the font!");
		
		//ratio = (double) label.getSize2D()/300;
		
		frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
               System.out.println("hi");
            }
        });
		
		
		panel.add(label);
		frame.setContentPane(panel);
		
		frame.pack();
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
