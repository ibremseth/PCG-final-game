package game;

public class Level {

	public Powerup p[];
	public GameGrid g;
	public BadGuy bg[];
	
	public Level(int lev) {			
		g = new GameGrid();
		
		// create floor
		for (int i = 0; i < GameGrid.MWIDTH; i++)
		g.createBlock(i, GameGrid.MHEIGHT-1);

		switch(lev) {
			case 1:
				p = new Powerup[2];
				p[0] = new Powerup(6,11,Powerup.JUMP);
				p[1] = new Powerup(28,11,Powerup.ENDLEVEL);
				
				bg = new BadGuy[0];
				
				// Now place specific blocks (depends on current map size)
				g.createBlock(10,13);
				g.createBlock(11,13); g.createBlock(11,12);
				g.createBlock(12,13); g.createBlock(12,12); g.createBlock(12,11);
				g.createBlock(13, 13);
				g.createBlock(17,13); g.createBlock(17,12); g.createBlock(17,11);g.createBlock(17, 10);
				g.createBlock(18,13); g.createBlock(18,12);
		
				g.createBlock(23,13); g.createBlock(23,12); g.createBlock(23,11);g.createBlock(23, 10);
				g.createBlock(24,13); g.createBlock(24,12);
				
				end(g, 31);
				break;
			case 2:
				p = new Powerup[3];
				p[0] = new Powerup(6,11,Powerup.ATTACK);
				p[1] = new Powerup(20,10,Powerup.ATTACK);
				p[2] = new Powerup(30,11,Powerup.ENDLEVEL);
				
				bg = new BadGuy[2];
				bg[0] = new BadGuy(15*GameGrid.CELLSIZE, 12*GameGrid.CELLSIZE, g);
				bg[1] = new BadGuy(23*GameGrid.CELLSIZE, 12*GameGrid.CELLSIZE, g);
				
				g.createBlock(12, 13);
				g.createBlock(20, 13);
				g.createBlock(25, 13);
				
				end(g, 32);
				break;
			case 3:
				p = new Powerup[3];
				p[0] = new Powerup(6,11,Powerup.ATTACK);
				p[1] = new Powerup(11,9,Powerup.JUMP);
				p[2] = new Powerup(24,11,Powerup.ENDLEVEL);
				
				bg = new BadGuy[2];
				bg[0] = new BadGuy(10*GameGrid.CELLSIZE, 12*GameGrid.CELLSIZE, g);
				bg[1] = new BadGuy(15*GameGrid.CELLSIZE, 12*GameGrid.CELLSIZE, g);

				g.createBlock(8, 13);
				g.createBlock(10, 10);
				g.createBlock(11, 10);
				g.createBlock(12, 10);
				
				g.createBlock(18, 13);g.createBlock(18, 12);g.createBlock(18, 11);g.createBlock(18, 10);
				
				end(g, 26);
				break;
			default:
				p = new Powerup[3];
				p[0] = new Powerup(0,11,Powerup.JUMP);
				p[1] = new Powerup(17,11,Powerup.JUMP);
				p[2] = new Powerup(32,11,Powerup.JUMP);
				
				bg = new BadGuy[0];
				
				// Now place specific blocks (depends on current map size)
				g.createBlock(5,11);g.createBlock(5,10);
				g.createBlock(6,13);g.createBlock(6,12);g.createBlock(6,11);
				g.createBlock(7,11);g.createBlock(7,10);
				
				g.createBlock(9,12);g.createBlock(9,11);
				g.createBlock(10,13);g.createBlock(10,10);
				g.createBlock(11,12);g.createBlock(11,11);
				
				g.createBlock(13,12);g.createBlock(13,11);g.createBlock(13,10);
				g.createBlock(14,13);
				g.createBlock(15,12);g.createBlock(15,11);g.createBlock(15,10);
				
				g.createBlock(19,12);g.createBlock(19,11);g.createBlock(19,10);
				g.createBlock(20,13);
				g.createBlock(21,12);g.createBlock(21,11);g.createBlock(21,10);
				g.createBlock(22,13);
				g.createBlock(23,12);g.createBlock(23,11);g.createBlock(23,10);
				
				g.createBlock(25,13);g.createBlock(25,12);g.createBlock(25,10);
				
				g.createBlock(27,13);g.createBlock(27,12);g.createBlock(27,11);g.createBlock(27,10);
				g.createBlock(28,10);
				g.createBlock(29,13);g.createBlock(29,12);g.createBlock(29,11);g.createBlock(29,10);
				
				end(g, 34);
				break;
		}
	}
	
	public void end(GameGrid g, int end) {
		for (int i = 0; i < GameGrid.MHEIGHT; i++)
		g.createBlock(end, i);
	}
	
	public GameGrid grid() {
		return g;
	}
	
	public Powerup[] powerups() {
		return p;
	}
	
	public BadGuy[] badguys() {
		return bg;
	}
}
