package game;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
	}
	
	public void setDir(int d) {
		dir = d;
	}
	
	public void jump() {
		if(inair == 0) {
			if(power == Powerup.JUMP) {
				dy = -40;
			} else {
				dy = -30;
			}
			inair = 1;
		}
	}
	
	public void getPowerup(Powerup p) {
		color = p.color;
		power = p.type;
		power_count = 50;
	}
	
	public BoundingBox collisionBox()
	{
		return new BoundingBox(x, y, width, height);
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
	}
}
