package game;

// Isaac

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
	final int FPS = 25; // frames per second
	final static int VWIDTH = 800;
	final static int VHEIGHT = 600;
	public static final int SCROLL = 150;  // Set edge limit for scrolling
	public static int vleft = 0;	// Pixel coord of left edge of viewable
	
	GameGrid grid;
	Hero h;
	Powerup p[];
	BadGuy bg[];
	GraphicsContext gc;
	int level = 2;
	
	/**
	 * Set up initial data structures/values
	 */

	void initialize()
	{
		Level l = new Level(level);
		grid = l.grid();
		p = l.powerups();
		bg = l.badguys();
		h = new Hero(150, 400, grid);
	}
	
	void setHandlers(Scene scene)
	{
		scene.setOnKeyPressed(
				e -> {
						String s = e.getCode().toString();
						switch (s) {
						case "LEFT" :
							h.setDir(-1);
							break;
						case "RIGHT" :
							h.setDir(1);
							break;
						case "UP" :
							h.jump();
							break;
						default:
							break;
						}
					}
				);
		
		scene.setOnKeyReleased(
				e -> {
						String s = e.getCode().toString();
						switch (s) {
						case "LEFT" :
							h.setDir(0);
							break;
						case "RIGHT" :
							h.setDir(0);
							break;
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
		h.update();
		for(Powerup i : p) {
			i.update();
			if(i.pickup(h.collisionBox())) {
				if(i.type == Powerup.ENDLEVEL) {
					level++;
					initialize();
				} else {
					h.getPowerup(i);
				}
			}
		}
		for(BadGuy b : bg) {
			b.update();
			if(b.kills(h)) {
				initialize();
			}
		}
		checkScrolling();
	}
	
	void checkScrolling()
	{
		// Test if hero is at edge of view window and scroll appropriately
		if (h.x < (vleft+SCROLL))
		{
			vleft = (int)h.x-SCROLL;
			if (vleft < 0)
				vleft = 0;
		}
		if ((h.x + h.width) > (vleft+VWIDTH-SCROLL))
		{
			vleft = (int)h.x+h.width-VWIDTH+SCROLL;
			if (vleft > (grid.width()-VWIDTH))
				vleft = grid.width()-VWIDTH;
		}
	}

	/**
	 *  Draw the game world
	 */
	void render(GraphicsContext gc) {
		// fill background
		gc.setFill(Color.CYAN);
		gc.fillRect(0, 0, VWIDTH, VHEIGHT);
		
		grid.render(gc);
		h.render(gc);
		for(Powerup i : p) {
			i.render(gc);
		}
		for(BadGuy b : bg) {
			b.render(gc);
		}
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

		Canvas canvas = new Canvas(VWIDTH, VHEIGHT);
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
