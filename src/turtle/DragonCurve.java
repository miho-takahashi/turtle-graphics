package turtle;

/**
 * ドラゴン曲線
 * 
 * @author tetsuya
 *
 */
public class DragonCurve {
	private TurtleGraphics turtleGraphics;

	public DragonCurve() {
		this.turtleGraphics = new TurtleGraphics();
		this.turtleGraphics.pendown();
	}

	/**
	 * 図形を描画する。
	 * 
	 * @param length 置換する線分の長さ
	 * @param level  置換レベル
	 */
	public void draw(double length, int level) {
		if (level == 0) {
			this.turtleGraphics.forward(length);
			return;
		}
		double nextLength = length * Math.sqrt(2.0) / 2;

		this.turtleGraphics.pendown();
		this.turtleGraphics.left(45);
		draw(nextLength, level - 1);

		this.turtleGraphics.right(90);
		this.turtleGraphics.penup();
		this.turtleGraphics.forward(nextLength);
		this.turtleGraphics.right(180);
		this.turtleGraphics.pendown();
		draw(nextLength, level - 1);

		this.turtleGraphics.right(180);
		this.turtleGraphics.penup();
		this.turtleGraphics.forward(nextLength);
		this.turtleGraphics.left(45);
		this.turtleGraphics.pendown();
	}

	public static void main(String[] args) {
		DragonCurve dragonCurve = new DragonCurve();
		dragonCurve.draw(120, 9);
	}
}
