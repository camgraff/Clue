package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell cell : adjMtx.get(thisCell)) {
			if (visited.contains(cell)) {
				continue;
			}
			visited.add(cell);
			if (numSteps == 1) {
				targets.add(grid[cell.getRow()][cell.getColumn()]);
			} else {
				findAllTargets(cell, numSteps - 1);
			}
			visited.remove(cell);
		}
	}

	public IntBoard() {
		//super();
		grid = new BoardCell[4][4];
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 4; c++) {
				grid[r][c] = new BoardCell(r, c);
			}
		}
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		calcAdjacencies();
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
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
		
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public BoardCell getCell(int r, int c) {
		return grid[r][c];
	}
}
