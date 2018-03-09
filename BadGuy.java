package game;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BadGuy {
	public double x, y;
	public int dx;
	public boolean alive;
	public int count;
	public GameGrid g;

	public int height, width;
	
	public BadGuy(double start_x, double start_y, GameGrid grid) {
		x = start_x;
		y = start_y;
		dx = 6;
		height = 79;
		width = 39;
		alive = true;
		g = grid;
		count = 0;
	}
	
	public BoundingBox collisionBox()
	{
		return new BoundingBox(x, y, width, height);
	}
	
	public boolean kills(Hero h) {
		if(!alive) {
			if(count-- < 0) {
				alive = true;
			}
			return false;
		}
		if(h.collisionBox().intersects(collisionBox())) {
			if((h.y + h.height - h.dy < y) && (h.power == Powerup.ATTACK)) {
				h.jump(true);
				alive = false;
				count = 100;
				return false;
				
			}
			return true;
		}
		
		return false;
	}
	
	public void update() {
		if(0 == g.move(collisionBox(), dx)) {
			dx = -dx;
		}
		x = x + g.move(collisionBox(), dx);
	}
	
	public void render(GraphicsContext gc) {
		if(alive) {
			gc.setFill(Color.MAGENTA);
			gc.fillRect(x-GameMain.vleft, y, width, height);
		}
	}

}
