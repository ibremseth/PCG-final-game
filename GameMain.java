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
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMain extends Application  {
	
	// TODO Auto-generated method stub
	final String appName = "Homework 5: The Rough Draft";
	final int FPS = 25; // frames per second
	final static int VWIDTH = 800;
	final static int VHEIGHT = 600;
	final static int BWIDTH = 1500;
	public static final int SCROLL = 150;  // Set edge limit for scrolling
	public static int vleft = 0;	// Pixel coord of left edge of viewable
	
	boolean transition = true;
	int transitionindex = 0;
	int transitioncounter = 500;
	boolean blink = true;
	int blinkcounter = 20;
	
	GameGrid grid;
	Image background;
	Image block;
	Image goblin;
	Hero h;
	Powerup p[];
	BadGuy bg[];
	GraphicsContext gc;
	int level = 1;
	
	/**
	 * Set up initial data structures/values
	 */

	void initialize()
	{

		background = new Image("background.png");
		block = new Image("block.png");
		Image lwiz = new Image("Left.png", 240, 400, false, false);
		Image rwiz = new Image("Right.png", 240, 400, false, false);
		goblin = new Image("goblin.png", 40, 80, false, false);
		
		Level l = new Level(level, block, goblin);
		grid = l.grid();
		p = l.powerups();
		bg = l.badguys();
		h = new Hero(100, 400, grid, lwiz, rwiz);
	}
	
	void resetLevel() {
		Level l = new Level(level, block, goblin);
		grid = l.grid();
		p = l.powerups();
		bg = l.badguys();
		h = h.resetHero(100, 400, grid);
	}

	void renderTransisionScreen(GraphicsContext gc, int s) {
		gc.setFill(Color.CYAN);
		gc.fillRect(0, 0, VWIDTH, VHEIGHT);
		Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
		gc.setFont(theFont);
		
		String str;
		
		switch(s) {
		case -2:
			gc.setFill(Color.BLACK);
			str = "YOU LOST THE GAME";
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText(str, (VWIDTH/2), 200.0);
			break;
		case -1:
			gc.setFill(Color.BLACK);
			str = "You lost a life!";
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText(str, (VWIDTH/2), 200.0);
			break;
		case 0:
			gc.setFill(Color.BLACK);
			str = "Welcome to Wizard Warriors 2: The Awakening!";
			String str2 = "You are a wizard on your way to your Annual Wizard Convention.";
			String str3 = "Unfortunately there are many obstacles in your way, but with the powers";
			String str4 = "you get from mysterious glowing orbs you can do this!";
			gc.setTextAlign(TextAlignment.CENTER);
			gc.fillText(str, (VWIDTH/2), 150);
			Font theWelcomeFont = Font.font("Helvetica", FontWeight.BOLD, 15);
			gc.setFont(theWelcomeFont);
			gc.fillText(str2, (VWIDTH/2), 200);
			gc.fillText(str3, (VWIDTH/2), 250);
			gc.fillText(str4, (VWIDTH/2), 300);
			
			if(blinkcounter-- < 0) {
				blink = !blink;
				blinkcounter = 20;
			}
			if(blink) {
				String blinkString = "PRESS SPACE TO SKIP";
				Font theBlinkFont = Font.font("Helvetica", FontWeight.BOLD, 18);
				gc.setFont(theBlinkFont);
				gc.setFill(Color.YELLOW);
				gc.fillText(blinkString, (VWIDTH/2), 400);
			}
		default:
			
			break;
		}
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
							h.jump(false);
							break;
						case "SPACE" :
							transition = false;
							transitioncounter = 25;
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
					resetLevel();
				} else {
					h.getPowerup(i);
				}
			}
		}
		for(BadGuy b : bg) {
			b.update();
			if(b.kills(h)) {
				transition = true;
				if(h.die()) {
					
					level = 1;
					transitionindex = -2;
					initialize();
					return;
				} 
				transitionindex = -1;
				resetLevel();
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
		if(transition) {
			if(transitioncounter-- > 0) {
				renderTransisionScreen(gc, transitionindex);
				return;
			} else {
				transition = false;
				transitioncounter = 25;
			}
		}
		
		// fill background
		//gc.setFill(Color.CYAN);
		//gc.fillRect(0, 0, VWIDTH, VHEIGHT);
		int cut = (vleft/2) % BWIDTH;
		gc.drawImage(background, -cut, 0);
		gc.drawImage(background, BWIDTH-cut, 0);
		
		gc.setFill(Color.RED);
		for(int i = 0; i <= h.lives; i++) {
			gc.fillOval((1+i)*GameGrid.CELLSIZE, 30, 10, 10);
		}
		
		gc.setFill(Color.BLACK);
		Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
		gc.setFont(theFont);
		gc.fillText("Level "+level, VWIDTH-100, 30);
		
		grid.render(gc);
		h.render(gc);
		for(BadGuy b : bg) {
			b.render(gc);
		}
		for(Powerup i : p) {
			i.render(gc);
			if(Math.abs((i.x*GameGrid.CELLSIZE) - h.x) <= 100) {
				i.renderInfo(gc);
			}
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
