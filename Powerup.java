package game;

//Isaac

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Powerup {
	
	static boolean ENDLEVELinfo = false;
	static boolean JUMPinfo = false;
	static boolean ATTACKinfo = false;
	public boolean firstType = false;
	
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
	static final int ENDLEVEL = 1;
	static final int JUMP = 2;
	static final int ATTACK = 3;
	
	public Powerup(int place_x, int place_y, int t) {
		x = place_x;
		y = place_y;
		state = NORMAL;
		type = t;
		switch(t) {
		case JUMP:
			color = Color.GREEN;
			break;
		case ENDLEVEL:
			color = Color.GOLD;
			break;
		case ATTACK:
			color = Color.ORANGE;
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
	
	public void renderInfo(GraphicsContext gc) {
		
		switch(type) {
		case ENDLEVEL:
			if(!ENDLEVELinfo) {
				firstType = true;
			}
			break;
		case JUMP:
			//System.out.println("HERE");
			if(!JUMPinfo) {
				firstType = true;
			}
			break;
		case ATTACK:
			if(!ATTACKinfo) {
				firstType = true;
			}
			break;
		}
		
		if(this.firstType) {
			gc.setFill(Color.WHITE);
			gc.setStroke(Color.BLACK);
			gc.fillRect((GameMain.VWIDTH/2)-75, 100, 150, 100);
			gc.strokeRect((GameMain.VWIDTH/2)-75, 100, 150, 100);
			
			gc.setFill(Color.BLACK);
			String s1;
			String s2;
			String s3;
			switch(type) {
			case ENDLEVEL:
				s1 = "The Gold power up";
				s2 = "transports you to";
				s3 = "the next level!";
				ENDLEVELinfo = true;
				break;
			case JUMP:
				s1 = "The Green power up";
				s2 = "lets you jump much";
				s3 = "higher!";
				JUMPinfo = true;
				break;
			case ATTACK:
				s1 = "The Orange power";
				s2 = "up lets you jump on";
				s3 = "monsters heads!";
				ATTACKinfo = true;
				break;
			default:
				s1 = "Should never get here";
				s2 = "It really shouldn't";
				s3 = "But oh well :(";
				break;
			}
	
			Font theFont = Font.font("Helvetica", FontWeight.NORMAL, 15);
			gc.setFont(theFont);
			gc.fillText(s1, (GameMain.VWIDTH/2)-70, 120);
			gc.fillText(s2, (GameMain.VWIDTH/2)-70, 150);
			gc.fillText(s3, (GameMain.VWIDTH/2)-70, 180);
			
		}
	}
	
	public void render(GraphicsContext gc) {
		gc.setFill(color);
		int locx = x*GameGrid.CELLSIZE;
		int locy = y*GameGrid.CELLSIZE;
		gc.fillOval(locx-GameMain.vleft+(GameGrid.CELLSIZE/2)-(diam/2), locy+(GameGrid.CELLSIZE/2)-(diam/2), diam, diam);
	}
}
