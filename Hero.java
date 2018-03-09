package game;

//Isaac

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Hero {
	
	public double x,y;
	public int power;
	public int power_count;
	public int width = 40;
	public int height = 60;
	public Color color;
	public int dx = 0, dy = 0;
	public int dir = 0;
	public int inair = 0;
	public int lives;
	public GameGrid g;
	static final int GRAVITY = 4;
	
	public Hero(int start_x, int start_y, GameGrid grid) {
		x = start_x;
		y = start_y;
		g = grid;
		power = 0;
		power_count = 0;
		dx = 10;
		color = Color.BLUE;
		
		lives = 2;
	}
	
	public Hero resetHero(int start_x, int start_y, GameGrid grid) {
		x = start_x;
		y = start_y;
		g = grid;
		power = 0;
		power_count = 0;
		dx = 10;
		color = Color.BLUE;
		
		return this;
	}
	
	public void setDir(int d) {
		dir = d;
	}
	
	public void jump(boolean override) {
		if((inair == 0) || override) {
			if(power == Powerup.JUMP) {
				dy = -39;
			} else {
				dy = -30;
			}
			inair = 1;
		}
	}
	
	public void getPowerup(Powerup p) {
		color = p.color;
		power = p.type;
		power_count = 150;
	}
	
	public BoundingBox collisionBox()
	{
		return new BoundingBox(x, y, width, height);
	}
	
	public boolean die() {
		if(lives-- < 0) {
			lives = 2;
			return true;
		}
		
		return false;
	}
	
	public void update() {
		x = x + g.move(collisionBox(), dir*dx);
		if(inair == 1) {
			dy = dy + GRAVITY;
			if (dy > GameGrid.CELLSIZE - 1) {
				dy = GameGrid.CELLSIZE - 1;
			}
			y = y + g.checkBeneath(collisionBox(), dy);
		} 
		if (2 != g.checkBeneath(collisionBox(), 2)) {
			inair = 0;
		} else {
			inair = 1;
		}
		
		if(power_count-- <=0) {
			power = 0;
			color = Color.BLUE;
		}
	}

	public void render(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillOval(x-GameMain.vleft, y, width, height);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3);
		gc.strokeOval(x-GameMain.vleft, y, width, height);
		
		
		if(power != 0) {
			gc.setFill(color);
			gc.fillArc((GameMain.VWIDTH/2)-40, 20, 30, 30, 0, ((double)power_count)*(360.0/150.0), ArcType.ROUND);
			gc.strokeArc((GameMain.VWIDTH/2)-40, 20, 30, 30, 0, ((double)power_count)*(360.0/150.0), ArcType.ROUND);
		}
	}
}
