// Methods for the Pong ball class
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

/**
 * This is the Ball class for the game PONG.
 */
public final class Ball extends Circle {
    /**
	 * Constant for speed increase after each paddle hit.
     */
    private final double SPEED_FACTOR = -1.05;

    /**
	 * speed of the ball in pixels per game update.
     */
    private double speed;

    /**
	 * radius of the ball.
     */
    private double radius;

    /**
	 * initial x position.
     */
    private double startX;

    /**
	 * Initial Y position.
     */
    private double startY;

    /**
	 * Current X position.
     */
    private double posX;

    /**
	 * Current Y position.
     */
    private double posY;

    /**
	 * Current velocity of ball in X direction.
     */
    private double velX;

    /**
	 * Current velocity of ball in Y direction.
     */
    private double velY;

    /**
	 * Empty constructor for the Ball.
     */
    private Ball() {
    }


    /**
	 * Constructor for Ball.
     * @param x initial x position.
     * @param y initial y position.
     * @param r radius of the ball.
     */
    public Ball(final double x, final double y, final double r) {
        startX = x;
        startY = y;
        radius = r;
        setRadius(radius);
        setFill(Color.BLACK);
        reset();
    }

    /**
	 * Bounces the ball off the borders.
     */
    public void bounceBorders() {
        velY *= -1.0;
    }


    /**
	 * Bounces the ball off the paddles.
     */
    public void bouncePaddles() {
        velX *= SPEED_FACTOR;
    }


    /**
	 * Resets the ball, moving it back to the center of the screen and sending it in a random direction.
     * @param s the speed of the ball after reset. Can be set to 0 to stop the ball.
     */
    public void reset(final double s) {
        setCenterX(startX);
        setCenterY(startY);
        this.speed = s;

        double startAngle = (Math.PI / 2) * Math.random() - (Math.PI / 4);
        velX = (speed * Math.cos(startAngle));
        velY = (speed * Math.sin(startAngle));
    }


    /**
	 * If the speed after reset is not specified, it defaults to 2 pixels per update.
     */
    public void reset() {
        reset(2);
    }

    /**
	 * Getter for the speed variable.
     * @return speed .
     */
    public double getSpeed() {
        return speed;
    }

    /**
	 * Setter for the speed variable.
     * @param s .
     */
    public void setSpeed(final double s) {
        speed = s;
    }


    /**
	 * Getter for the startX variable.
     * @return startX .
     */
    public double getStartX() {
        return startX;
    }

    /**
     * @param x .
     */
    public void setStartX(final double x) {
        startX = x;
    }

    /**
	 * Getter for the startY variable.
     * @return startY .
     */
    public double getStartY() {
        return startY;
    }

    /**
     * @param y .
     */
    public void setStartY(final double y) {
        startY = y;
    }

    /**
	 * Getter for the posX variable.
     * @return posX .
     */
    public double getPosX() {
        return posX;
    }

    /**
     * @param x .
     */
    public void setPosX(final double x) {
        posX = x;
    }

    /**
	 * Getter for the posY variable.
     * @return posY .
     */
    public double getPosY() {
        return posY;
    }

    /**
     * @param y .
     */
    public void setPosY(final double y) {
        posY = y;
    }

    /**
	 * Getter for the velX variable.
     * @return velX .
     */
    public double getVelX() {
        return velX;
    }

    /**
     * @param x .
     */
    public void setVelX(final double x) {
        velX = x;
    }

    /**
	 * Getter for the velY variable.
     * @return velY .
     */
    public double getVelY() {
        return velY;
    }

    /**
     * @param y .
     */
    public void setVelY(final double y) {
        velY = y;
    }
}

