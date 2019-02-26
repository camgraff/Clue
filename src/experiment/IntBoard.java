package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx;

	public IntBoard(BoardCell[][] grid) {
		super();
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		this.grid = grid;
	}
	
	public void calcAdjacencies() {
		for(int r =0; r <grid.length; r++) {
			for(int c =0; c< grid[r].length; c++) {
				Set<BoardCell> adjCells = new HashSet<BoardCell>();
				if(r!=0) {
					adjCells.add(grid[r-1][c]);
				}
				if(r!=grid.length-1) {
					adjCells.add(grid[r+1][c]);
				}
				if(c!=0) {
					adjCells.add(grid[r][c-1]);
				}
				if(c!=grid[r].length-1) {
					adjCells.add(grid[r][c+1]);
				}
				adjMtx.put(grid[r][c], adjCells); 
			}
		}
	}
	
	public Set<BoardCell> getAdjList() {
		return null;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargest() {
		return null;
	}
}
