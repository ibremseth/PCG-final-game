package game;

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

	public void render(GraphicsContext gc)
	{
		gc.setFill(Color.BLACK);
		for (int row = 0; row < MHEIGHT; row++)
		 for (int col = 0; col < MWIDTH; col++)
			if (map[col][row] == 1)
				gc.fillRect(col*CELLSIZE, row*CELLSIZE, CELLSIZE, CELLSIZE);
	}

}
