package visualLine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.plaf.SliderUI;

public class Panel extends JPanel implements MouseListener, KeyListener,
		MouseMotionListener {

	// I want to have two lines drawn and have the extend infinitely. Have the
	// points dragable and display the coordinates in the side GUI. Also display
	// collision points

	// Right click spawns a point which forms a lines with the mouse location.

	// Have a button (circle or square) around points that you can click on and
	// drag in order to move the point.

	int width = 600;
	int height = 400;

	Image[] imageAr;

	Thread thread;
	Image image;
	Graphics g;

	boolean onePoint = false;
	// float[][] lines = new float[0][];
	float[][] lines = { { 10, 10, 70, 30 }, { 40, 10, 30, 30 } };
	int[] movedLoc;

	public Panel() {
		super();

		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		this.setSize(new Dimension(width, height));

		pStart();
	}

	// gets the height of the top JFrame bar and margin
	static int topInset = 0;

	static void setTopInset(int i) {
		topInset = i;
	}

	/**
	 * Methods go below here.
	 * 
	 */

	public void pStart() {
		imageInit();

	}

	void tick() {
		// Detect all collisions and display them. Also display the start and
		// stop of each line with blue and red circles.

		float[] slope = new float[lines.length];
		float[] fb1 = new float[lines.length];
		float[] interX = new float[lines.length];
		float[][] collision = new float[lines.length][];
		for (int s = 0; s < slope.length; s++) {
			System.out.println("lines[" + s + "] (" + lines[s][0] + ", "
					+ lines[s][1] + ")   (" + lines[s][2] + ", " + lines[s][3]
					+ ")");
			slope[s] = (lines[s][1] - lines[s][3])
					/ (lines[s][0] - lines[s][2]);
			fb1[s] = lines[s][1] - (slope[s] * lines[s][0]);
			System.out.println("y = " + slope[s] + "x + " + fb1[s]);
		}
		// for (int s = 0; s < lines.length - 1; s++) { interX[s] = (fb1[s] -
		// fb1[s + 1]) / (slope[s + 1] - slope[s]); System.out.println("interX["
		// + s + "] " + +interX[s]); collision[s] = new float[] { interX[s],
		// ((slope[s] * interX[s]) + fb1[s]) }; g.setColor(Color.MAGENTA);
		// g.drawOval((int) colClision[s][0] - 2, (int) collision[s][1] - 2, 4,
		// 4); }

		// now only draw points that are on a line.
		// This needs to check every line for every collisions with every other
		// line.

		// Side gui

		for (int s = 0; s < lines.length; s++) {
			System.out.println("linesS[" + s + "]   (" + lines[s][0] + ", "
					+ lines[s][1] + ")");
			for (int b = s + 1; b < lines.length; b++) {
				System.out.println("linesB[" + b + "]   (" + lines[b][0] + ", "
						+ lines[b][1] + ")");
				interX[s] = (fb1[s] - fb1[b]) / (slope[b] - slope[s]);
				System.out.println("interX[" + s + "] " + +interX[s]);
				collision[s] = new float[] { interX[s],
						((slope[s] * interX[s]) + fb1[s]) };

				// if the collision is on both lines.
				if ((collision[s][0] < lines[s][0] && collision[s][0] > lines[s][2])
						|| (collision[s][0] > lines[s][0] && collision[s][0] < lines[s][2])) {
					System.out.println("inBetweenOne");
					if ((collision[s][0] < lines[b][0] && collision[s][0] > lines[b][2])
							|| (collision[s][0] > lines[b][0] && collision[s][0] < lines[b][2])) {
						System.out.println("inBetweenBoth");
						g.setColor(Color.MAGENTA);
						g.drawOval((int) collision[s][0] - 2,
								(int) collision[s][1] - 2, 4, 4);
					}
				}
			}
		}

		// Go through the first lines and find the slope intercept equasion.
		// float slope1 = (lines[3] - lines[1]) / (lines[2] - lines[0]);
		// float fb1 = lines[1] - (slope1 * lines[0]);
		// float sb = lines[3] - (slope1 * lines[2]);
		// System.out.println("fb: " + fb1 + "   sb: " + sb);

		// Go through the first lines and find the slope intercept equasion.
		// float slope2 = (lines[7] - lines[5]) / (lines[6] - lines[4]);
		// float fb2 = lines[5] - (slope2 * lines[4]);
		// System.out.println("fb: " + fb2);

		// float interX = (fb1 - fb2) / (slope2 - slope1);
		// System.out.println("interY1: " + ((slope1 * interX) + fb1));
		// System.out.println("interY2: " + ((slope2 * interX) + fb2));
		// float[] collision = { interX, ((slope1 * interX) + fb1) };
		// System.out.println("collision (" + collision[0] + ", " + collision[1]
		// + ")");
		// g.setColor(Color.RED);
		// g.drawOval((int) collision[0] - 2, (int) collision[1] - 2, 4, 4);
		// if the collision is on both lines.
		// if ((interX < lines[0] && interX > lines[2])
		// || (interX > lines[0] && interX < lines[2])) {
		// System.out.println("inBetweenOne");
		// if ((interX < lines[5] && interX > lines[7])
		// || (interX > lines[5] && interX < lines[7])) {
		// System.out.println("inBetweenBoth");
		// }
		// }
	}

	void drawTick() {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.CYAN);
		System.out.println("lines.l: " + lines.length);

		if (onePoint) {
			for (int l = 0; l < lines.length - 1; l++) {
				g.setColor(Color.BLUE);
				g.drawOval((int) lines[l][0] - 2, (int) lines[l][1] - 2, 4, 4);
				g.setColor(Color.RED);
				g.drawOval((int) lines[l][2] - 2, (int) lines[l][3] - 2, 4, 4);
				g.setColor(Color.CYAN);
				g.drawLine((int) lines[l][0], (int) lines[l][1],
						(int) lines[l][2], (int) lines[l][3]);
			}
			g.setColor(Color.BLUE);
			g.drawOval((int) lines[lines.length - 1][0] - 2,
					(int) lines[lines.length - 1][1] - 2, 4, 4);
			g.setColor(Color.RED);
			g.drawOval(movedLoc[0] - 2, movedLoc[1] - 2, 4, 4);
			g.setColor(Color.CYAN);
			g.drawLine((int) lines[lines.length - 1][0],
					(int) lines[lines.length - 1][1], (int) (movedLoc[0]),
					(int) (movedLoc[1]));
		} else {
			for (int l = 0; l < lines.length; l++) {
				g.setColor(Color.BLUE);
				g.drawOval((int) lines[l][0] - 2, (int) lines[l][1] - 2, 4, 4);
				g.setColor(Color.RED);
				g.drawOval((int) lines[l][2] - 2, (int) lines[l][3] - 2, 4, 4);
				g.setColor(Color.CYAN);
				g.drawLine((int) lines[l][0], (int) lines[l][1],
						(int) lines[l][2], (int) lines[l][3]);
			}
		}
	}

	/**
	 * Methods go above here.
	 * 
	 */

	// This works with any type of array.
	float[] appendFloatAr(float[] st, float appendage) {
		float[] temp = new float[st.length + 1];
		for (int a = 0; a < st.length; a++) {
			temp[a] = st[a];
		}
		temp[temp.length - 1] = appendage;
		return temp;
	}

	float[][] appendFloatArAr(float[][] st, float[] appendage) {
		float[][] temp = new float[st.length + 1][];
		for (int a = 0; a < st.length; a++) {
			temp[a] = st[a];
		}
		temp[temp.length - 1] = appendage;
		return temp;
	}

	public void drwGm() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	public void imageInit() {
		/**
		 * imageAr = new Image[1]; ImageIcon ie = new
		 * ImageIcon(this.getClass().getResource( "res/image.png")); imageAr[0]
		 * = ie.getImage();
		 */

	}

	@Override
	public void mouseClicked(MouseEvent me) {
		// tick();
		// drwGm();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent me) {
		System.out.println("******");
		if (me.getButton() == MouseEvent.BUTTON3) {
			if (!onePoint) {
				lines = appendFloatArAr(lines,
						new float[] { me.getX(), me.getY() });
				onePoint = true;
				movedLoc = new int[] { me.getX(), me.getY() };
				drawTick();
				drwGm();
				System.out.println("false");
			} else {
				onePoint = false;
				System.out.println("true");
				lines[lines.length - 1] = appendFloatAr(
						lines[lines.length - 1], me.getX());
				lines[lines.length - 1] = appendFloatAr(
						lines[lines.length - 1], me.getY());
				drawTick();
				drwGm();
			}
		}
		if (me.getButton() == MouseEvent.BUTTON1) {
			drawTick();
			tick();
			drwGm();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent ke) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent me) {
		// System.out.println("moved");
		if (onePoint) {
			movedLoc = new int[] { me.getX(), me.getY() };
			drawTick();
			drwGm();
		}
	}
}
