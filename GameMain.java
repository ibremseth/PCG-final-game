package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMain extends Application  {
	
	// TODO Auto-generated method stub
	final String appName = "Homework 5: The Rough Draft";
	final int FPS = 40; // frames per second
	static int WIDTH = 600;
	static int HEIGHT = 500;

	GraphicsContext gc;
	
	/**
	 * Set up initial data structures/values
	 */
	void initialize()
	{
	}
	
	void setHandlers(Scene scene)
	{
		scene.setOnKeyPressed(
				e -> {
						String s = e.getCode().toString();
						switch (s) {
						default:
							break;
						}
					}
				);
	}

	/**
	 *  Update variables for one time step
	 */
	public void update()
	{
		
	}

	/**
	 *  Draw the game world
	 */
	void render(GraphicsContext gc) {
		// fill background
		gc.setFill(Color.CYAN);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		
	}

	/*
	 * Begin boiler-plate code...
	 * [Animation and events with initialization]
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		gc = canvas.getGraphicsContext2D();
		Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
		gc.setFont(theFont);

		// Initial setup
		initialize();
		setHandlers(theScene);
		
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					// update position
					update();
					// draw frame
					render(gc);
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
	/*
	 * ... End boiler-plate code
	 */
}
