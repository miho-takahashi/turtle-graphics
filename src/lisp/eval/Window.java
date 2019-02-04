package lisp.eval;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

/**
 * 描画用ウィンドウ
 *
 * @author ryu2153
 */
public class Window extends JFrame {

	private class Turtle {
		int x;
		int y;
		double direct; // 亀の方向
		Color color;
		double radius; // 亀の半径
	}

	/**
	 * タートルグラフィックスの描画場所
	 */
	private Image buffer;
	/**
	 * Imageの横幅
	 */
	private int width = 480;
	/**
	 * Imageの高さ
	 */
	private int height = 480;
	/**
	 * ペンの色
	 */
	private Color penColor = Color.BLACK;

	/**
	 * 亀
	 */
	private Turtle turtle;

	public Window(int width, int height) {
		super("Turtle Graphics");

		this.turtle = new Turtle();
		this.turtle.color = Color.RED;
		this.turtle.radius = 10;

		this.width = width;
		this.height = height;

		setSize(this.width, this.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		this.buffer = createImage(this.width, this.height);
		Graphics g = this.buffer.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.width, this.height);

		repaint();
	}

	/**
	 * pauseの長さ(ミリ秒)
	 */
	private long delay = 500;

	/**
	 * delay ミリ秒間、動作を止める。
	 */
	private void pause() {
		try {
			Thread.sleep(delay);
		} catch (Exception e) {
		}
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * 亀を描画する。
	 *
	 * @param g
	 *            描画に使用するGraphics
	 */
	private void paintTurtle(Graphics g) {
		g.setColor(this.turtle.color);

		// 円
		int x = (int) (this.turtle.x - this.turtle.radius);
		int y = (int) (this.turtle.y - this.turtle.radius);
		g.drawOval(x, y, (int) (2.0 * this.turtle.radius), (int) (2.0 * this.turtle.radius));

		// 進行方向
		int x1 = (int) this.turtle.x;
		int y1 = (int) this.turtle.y;
		double radian = Math.PI * this.turtle.direct / 180;
		int x2 = (int) (this.turtle.x + this.turtle.radius * Math.cos(radian));
		int y2 = (int) (this.turtle.y + this.turtle.radius * Math.sin(radian));
		g.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(this.buffer, 0, 0, this);
		paintTurtle(g);
	}

	/**
	 * 線を描画する。
	 *
	 * @param x1,y1
	 *            始点座標
	 * @param x2,2y
	 *            終点座標
	 */
	public void drawLine(int x1, int y1, int x2, int y2, int color) {

		Graphics g = this.buffer.getGraphics();
		// g.setColor(this.penColor);
		switch (color) {
		case 0:
			g.setColor(Color.BLACK);
			break;
		case 1:
			g.setColor(Color.RED);
			break;
		case 2:
			g.setColor(Color.GREEN);
			break;
		case 3:
			g.setColor(Color.BLUE);
			break;
		default:
			System.out.println("Color Number Error.");
			g.setColor(Color.BLACK);
			break;
		}
		g.drawLine(x1, y1, x2, y2);
		this.turtle.x = x2;
		this.turtle.y = y2;
		this.turtle.direct = (Math.atan2(y2 - y1, x2 - x1)) * 180 / Math.PI;
//		System.out.println("direct = " + this.turtle.direct);
		repaint();
		pause();
	}
}