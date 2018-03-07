package game;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class GameGrid {
	public static final int MWIDTH = 20;
	public static final int MHEIGHT = 15;
	int map[][] = new int[MWIDTH][MHEIGHT];
	static final int CELLSIZE = 40; // Number of pixels per map cell
	
	public GameGrid()
	{
		for (int row = 0; row < MHEIGHT; row++)
		 for (int col = 0; col < MWIDTH; col++)
			map[col][row] = 0;
	}
	
	public void createBlock(int x, int y)
	{
		map[x][y] = 1;
	}
	
	public int move(BoundingBox b, int x) {
		int row2 = ((int)b.getMaxY()/CELLSIZE);
		int row1 = ((int)b.getMinY()/CELLSIZE);
		if(x > 0) {
			int rightside = (int)b.getMaxX();
			int col = rightside/CELLSIZE;
			int edge = CELLSIZE*(col+1);
			if(rightside+x < edge) {
				return x;
			}
			if(col == MWIDTH-1) {
				return edge - rightside - 1;
			}
			for (int row = row1; row <= row2; row++) {
				if (map[col+1][row] != 0)
					return edge - rightside - 1;
			}
			
			return x;
		} else if(x < 0) {
			int leftside = (int)b.getMinX();
			int col = leftside/CELLSIZE;
			int edge = CELLSIZE*col;
			if(leftside+x > edge) {
				return x;
			}
			if(col == 0) {
				return 0 - leftside + 1;
			}
			for (int row = row1; row <= row2; row++) {
				if (map[col-1][row] != 0)
					return edge - leftside + 1;
			}
			
			return x;
			
		}
		
		return x;
	}
	
	public int checkBeneath(BoundingBox b, int y) {
		int rbottom = (int)b.getMaxY();
		int row = rbottom/CELLSIZE;
		int col1 = ((int)b.getMinX())/CELLSIZE;
		int col2 = ((int)b.getMaxX())/CELLSIZE;
		int edge = CELLSIZE*(row+1);
		if (rbottom+y < edge)
			return y;
		for (int col = col1; col <= col2; col++)
			if (map[col][row+1] != 0)
				return edge - rbottom - 1;
		
		return y;
	}

	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.BLACK);
		for (int row = 0; row < MHEIGHT; row++)
		 for (int col = 0; col < MWIDTH; col++)
			if (map[col][row] == 1)
				gc.fillRect(col*CELLSIZE, row*CELLSIZE, CELLSIZE, CELLSIZE);
	}

}
