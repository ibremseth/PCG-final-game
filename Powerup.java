package game;

//Isaac

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Powerup {
	public int x, y;
	public int state;
	public int type;
	public int diam = 16;
	public int counter = 3;
	public Color color;
	static final int BIG = 0;
	static final int NORMAL = 1;
	static final int SMALL = 2;
	static final int NORMALA = 4;
	static final int JUMP = 1;
	
	public Powerup(int place_x, int place_y, int t) {
		x = place_x;
		y = place_y;
		state = NORMAL;
		type = t;
		switch(t) {
		case JUMP:
			color = Color.GREEN;
			break;
		default:
			break;
		}
	}
	
	public boolean pickup(BoundingBox b) {
		return b.contains((x*GameGrid.CELLSIZE)+(GameGrid.CELLSIZE/2), (y*GameGrid.CELLSIZE)+(GameGrid.CELLSIZE/2));
	}
	
	public void update() {
		if(counter-- < 0) {
			switch(state) {
			case BIG:
				diam = 14;
				state = NORMAL;
				counter = 3;
				break;
			case NORMAL:
				diam = 12;
				state = SMALL;
				counter = 3;
				break;
			case SMALL:
				diam = 14;
				state = NORMALA;
				counter = 3;
				break;
			case NORMALA:
				diam = 16;
				state = BIG;
				counter = 3;
				break;
			}
		}
	}
	
	public void render(GraphicsContext gc) {
		gc.setFill(color);
		int locx = x*GameGrid.CELLSIZE;
		int locy = y*GameGrid.CELLSIZE;
		gc.fillOval(locx-GameMain.vleft+(GameGrid.CELLSIZE/2)-(diam/2), locy+(GameGrid.CELLSIZE/2)-(diam/2), diam, diam);
	}
}
