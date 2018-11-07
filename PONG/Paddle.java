//Methods for the Pong paddle class
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * to do.
 */
public class Paddle extends Rectangle {

    /**
     */
    private double vel = 0;

    /**
     */
    private int score = 0;

    /**
     */
    private Paddle() {
    }

    /**
     * @param x .
     * @param y .
     * @param width .
     * @param height .
     */
    public Paddle(final double x, final double y, final double width, final double height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setFill(Color.GREEN);
    }

    /**
     * @param dy .
     */
    public void move(final double dy) {
        setY(getY() + dy);
    }

    /**
     * @return .
     */
    public int getScore() {
        return score;
    }

    /**
     * @param s .
     */
    public void setScore(final int s) {
        score = s;
    }
	
	/**
	 * @return .
	 */
	public double getVel() {
		return vel;
	}
	
	/**
	 * @param v .
	 */
	public void setVel(final double v) {
		vel = v;
	}
	
}

