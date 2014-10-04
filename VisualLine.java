package visualLine;

import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class VisualLine extends JFrame {
	static JFrame frame;

	int height = 0;

	public VisualLine() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setTitle("VisualLine");

		JMenuBar menubar = frame.getJMenuBar();
		int mbh = (menubar != null ? menubar.getSize().height : 0);
		Insets insets = frame.getInsets();
		height += insets.top + mbh;
		Panel.setTopInset(height);
	}

	public static void main(String[] args) {
		new VisualLine();
	}
}
