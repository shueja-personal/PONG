// import statements
import java.util.concurrent.atomic.AtomicLong;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
//import javafx.scene.text.FontPosture;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

//import javafx.scene.transform.Transform;
//import javafx.scene.transform.Rotate;
import javafx.animation.AnimationTimer;


/**
 * Plays PONG.
 */
public final class PONG extends Application {

    /**
     */
    private static boolean gameOver = false;  /**        */

    /**
     */
    private static int winningPlayer = 0; //set num of winner to unused value

    /**
     */
    private static final int CANVAS_WIDTH = 1000;

    /**
     */
    private static final int CANVAS_HEIGHT = 500;

    /**
     */
    private static final double BORDER_WIDTH = 3.0;

    /**
     */
    private static final int BALL_START_X = CANVAS_WIDTH / 2;

    /**
     */
    private static final int BALL_START_Y = CANVAS_HEIGHT / 2;

    /**
     */
    private static final int BALL_RADIUS = 10;

    /**
     */
    private static final int FONT_SIZE = 18;

    /**
     */
    private static final int MAX_SCORE = 21;

    /**
     */
    private static final int SCOREBOARD_X = 450;

    /**
     */
    private static final int SCOREBOARD_Y = 50;

    /**
     */
    private static final int RESTART_X = 350;

    /**
     */
    private static final int RESTART_Y = 100;
    //private static final int = ;

    /**
     * @param args Provides arguments.
     */
    public static void main(final String[] args) {
        launch(args);
    }


    /**
     * @param theStage Provides stage.
     */
    public void start(final Stage theStage) {
        theStage.setTitle("PONG");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        //canvas.(Color.BLACK);
        root.getChildren().add(canvas);

        Rectangle topBorder = new Rectangle(0.0, 0.0, canvas.getWidth(), BORDER_WIDTH);

        root.getChildren().add(topBorder);

        Rectangle bottomBorder = new Rectangle(0.0, canvas.getHeight(),
            canvas.getWidth(), BORDER_WIDTH);
        root.getChildren().add(bottomBorder);

        double bX = BALL_START_X;
        double bY = BALL_START_Y;
        double bR = BALL_RADIUS;
        Ball theBall = new Ball(bX, bY, bR);
        theBall.setFill(Color.RED);
        root.getChildren().add(theBall);

        final double paddleWidth = 10;
        final double paddleHeight = 100;
        Paddle p1Paddle = new Paddle(0, canvas.getHeight() / 2
            - paddleHeight / 2, paddleWidth, paddleHeight);
//      p1Paddle.posY = p1Paddle.getY();
        root.getChildren().add(p1Paddle);

        Paddle p2Paddle = new Paddle(canvas.getWidth() - paddleWidth, canvas.getHeight() / 2
            - paddleHeight / 2, paddleWidth, paddleHeight);
        root.getChildren().add(p2Paddle);

        theScene.setOnKeyReleased(
            new EventHandler<KeyEvent>() {
                public void handle(final KeyEvent e) {
                    if (gameOver) {
                        if (e.getCode() == KeyCode.SPACE) {
                            p1Paddle.setScore(0);
                            p2Paddle.setScore(0);
                            theBall.reset();
                            gameOver = false;
                        }
                    }
                    //String code = e.getCode().toString();
                    if (e.getCode() == KeyCode.UP) {
                        if (p2Paddle.getVel() > 0) {
                            p2Paddle.setVel(-1.0);
                        } else {
                            p2Paddle.setVel(p2Paddle.getVel() - 1.0);
                        }
                    }
                    if (e.getCode() == KeyCode.DOWN) {
                        if (p2Paddle.getVel() <= 0) {
                            p2Paddle.setVel(1.0);
                        } else {
                        p2Paddle.setVel(p2Paddle.getVel() + 1.0);
                        }
                    }
                    if (e.getCode() == KeyCode.W) {
                        if (p1Paddle.getVel() > 0) {
                            p1Paddle.setVel(-1.0);
                        } else {
                        p1Paddle.setVel(p1Paddle.getVel() - 1.0);
                        }
                    }
                    if (e.getCode() == KeyCode.S) {
                        if (p1Paddle.getVel() <= 0) {
                            p1Paddle.setVel(1.0);
                        } else {
                        p1Paddle.setVel(p1Paddle.getVel() + 1.0);
                        }
                    }
                }
            });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setLineWidth(2);
        Font theFont = Font.font("Comic Sans", FontWeight.BOLD, FONT_SIZE);
        gc.setFont(theFont);

        Image space = new Image("background.png");

        final long startNanoTime = System.nanoTime();
        final long updateIntervalNanoTime = 1000000000 / 100; //every 1/10th sec
        AtomicLong updatesCompleted = new AtomicLong(0);

        new AnimationTimer() {
            public void handle(final long currentNanoTime) {
                final long totalUpdates = (currentNanoTime - startNanoTime)
                    / updateIntervalNanoTime;
                long updatesPending = totalUpdates - updatesCompleted.get();

                // See if there is any work to do/update
                if (0 < updatesPending) {
                    // Do all the updates
                    for (long i = updatesPending; i > 0; i--) {
                        if (theBall.getCenterX() > canvas.getWidth() - theBall.getRadius()) {
                            p1Paddle.setScore(p1Paddle.getScore() + 1);
                            theBall.reset(); // mechanics for scoring
                        }

                        if (theBall.getCenterX() < theBall.getRadius()) {
                            p2Paddle.setScore(p2Paddle.getScore() + 1);
                            theBall.reset(); // mechanics for scoring
                            theBall.bouncePaddles();
                        }

                        if (theBall.getCenterY() >= canvas.getHeight() - theBall.getRadius()) {
                            theBall.bounceBorders(); //Bouncing off the borders
                        }

                        if (theBall.getCenterY() <= theBall.getRadius()) {
                            theBall.bounceBorders(); //Bouncing off the borders
                        }

                        if (theBall.getCenterX() <= (theBall.getRadius() + paddleWidth)
                                && theBall.getCenterY() > p1Paddle.getY()
                                && theBall.getCenterY() < p1Paddle.getY() + paddleHeight) {
                            theBall.setCenterX(paddleWidth + theBall.getRadius());
                            theBall.bouncePaddles();
                        }

                        if (theBall.getCenterX() >= canvas.getWidth() - (theBall.getRadius() + paddleWidth)
                                && theBall.getCenterY() > p2Paddle.getY()
                                && theBall.getCenterY() < p2Paddle.getY() + paddleHeight) {
                            theBall.setCenterX(canvas.getWidth() - (paddleWidth + theBall.getRadius()));
                            theBall.bouncePaddles();
                        }
                        if (p1Paddle.getY() > canvas.getHeight()
                                - p1Paddle.getHeight()) {
                            p1Paddle.setVel(0); //stopping at the borders
                            p1Paddle.setY(canvas.getHeight()
                                - p1Paddle.getHeight());
                        }
                        if (p1Paddle.getY() < 0) {
                            p1Paddle.setVel(0); //stopping at the borders
                            p1Paddle.setY(0);
                        }
                        if (p2Paddle.getY() > canvas.getHeight()
                                - p2Paddle.getHeight()) {
                            p2Paddle.setVel(0); //stopping at the borders
                            p2Paddle.setY(canvas.getHeight()
                                - p2Paddle.getHeight());
                        }
                        if (p2Paddle.getY() < 0) {
                            p2Paddle.setVel(0); //stopping at the borders
                            p2Paddle.setY(0);
                        }

                        if ((p1Paddle.getScore() >= MAX_SCORE)) {
                            gameOver = true;
                            winningPlayer = 1;
                        }

                        if ((p2Paddle.getScore() >= MAX_SCORE)) {
                            gameOver = true;
                            winningPlayer = 2;
                        }

                        theBall.setCenterX(theBall.getCenterX() + theBall.getVelX());
                        theBall.setCenterY(theBall.getCenterY() + theBall.getVelY());
                        p1Paddle.setY(p1Paddle.getY() + p1Paddle.getVel());
                        p2Paddle.setY(p2Paddle.getY() + p2Paddle.getVel());
                    }
                    updatesCompleted.set(totalUpdates);

                    // Re-draw the screen once
                    // background image clears canvas
                    gc.drawImage(space, 0, 0);
                    if (gameOver) {
                        theBall.reset(0);
                        gc.fillText("Game Over, P" + winningPlayer
                            + " Wins", SCOREBOARD_X, SCOREBOARD_Y);
                        gc.fillText("Press space to restart, "
                            + "or ESC to go to Game Center.", RESTART_X, RESTART_Y);
                    } else {
                        gc.fillText("P1:" + p1Paddle.getScore()
                            + "        P2:" + p2Paddle.getScore(), SCOREBOARD_X, SCOREBOARD_Y);
                    }
                    theBall.setCenterX(theBall.getCenterX());
                    theBall.setCenterY(theBall.getCenterY());

                    //p1Paddle.setY(p1Paddle.getY());
                    //p2Paddle.setY(p2Paddle.getY());
                    p1Paddle.setY(theBall.getCenterY() - 50);
                    p2Paddle.setY(theBall.getCenterY() - 50);
                }
            }
        }.start();

        theStage.show();
    }
}

