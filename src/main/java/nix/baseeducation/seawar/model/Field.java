package nix.baseeducation.seawar.model;
import java.util.Arrays;

public class Field {

	private Cell[] cells;
	public int rowSize;
	public int aliveShips;
	
	public Field(int size){
		this.cells = new Cell[size * size];
		this.rowSize = size;
		for (int i = 0; i < cells.length; i++)
		{
			cells[i] = new Cell();
		}
	}
	
	public Cell getCell(int index){
		return cells[index];
	}
	
	public void setAllCellsVisible(){
		for (Cell each : cells)
		{
			each.setVisible(true);
		}
	}
	
	public boolean placeShip(int... ships){
		aliveShips = ships.length;
		return new SimpleShipPlacer().plaseShips(cells, ships);
	}
	
		
	public boolean hasAliveShips(){
		return aliveShips > 0;
	}
	
	public Cell[] getCells(){
		return cells;
	}
	
	public void proceedResult(int type, int cell){
		cells[cell].setVisible(true);
		cells[cell].setVisited(true);
		
		if (type == 0) {
			cells[cell].setEmpty(true);
		} else if(type == 1){
			cells[cell].setEmpty(false);
		} else{
			cells[cell].setEmpty(false);
			
			//TODO
			int another = cell;
			int previous = cell;
			
			int one = -1;
			int two = -1;
			int previousOne = -1;
			int previousTwo = -1;
			boolean isLooking = true;
			
			
			while(isLooking){
				isLooking = false;
				for(int each : getAroundCells(another)){
					if (each != -1) {
						cells[each].setVisible(true);
						
						if(!cells[each].isEmpty() && each != previous){
							if(one != -1){
								previousOne = another;
								one = each;
							} else{
								previousTwo = another;
								two = each;
							}
						}
					}
				}
				
				if(one != -1){
					another = one;
					one = -1;
					previous = previousOne;
					previousOne = -1;
					isLooking = true;
				} else if(two != -1){
					another = two;
					two = -1;
					previous = previousTwo;
					previousTwo = -1;
					isLooking = true;
				}
				
			}
		}
	}
	
	public int[] proceedShot(int cell){
		Cell current = cells[cell];
		if (current.isEmpty()) {
			current.setVisited(true);
			return new int[]{0, cell};
		} else{
			current.setVisited(true);
			
			int another = cell;
			int previous = cell;
			int founded = 0;
			
			
			int one = -1;
			int two = -1;
			int previousOne = -1;
			int previousTwo = -1;
			boolean isLooking = true;
			
			
			while(isLooking){
				isLooking = false;
				for(int each : getAroundCells(another)){
					if (each != -1) {
						if (!cells[each].isVisited() && !cells[each].isEmpty()){
							founded++;
							break;
						} else if((cells[each].isVisited() && !cells[each].isEmpty() && each != previous)){
							if(one != -1){
								previousOne = another;
								one = each;
							} else{
								previousTwo = another;
								two = each;
							}
						}
					}
				}
				
				if(one != -1){
					another = one;
					one = -1;
					previous = previousOne;
					previousOne = -1;
					isLooking = true;
				} else if(two != -1){
					another = two;
					two = -1;
					previous = previousTwo;
					previousTwo = -1;
					isLooking = true;
				}
				
				
			}
			
			if (founded != 0) {
				return new int[]{1, cell};				
			} else{
				aliveShips--;
				return new int[]{2, cell};
			}
		}		
	}
	
	private int getUpCell(int cell){
		int upCell = -1;
		if(!isInDiapason(cell)){
			return -1;
		}
		if(cell >= rowSize){
			upCell = cell - rowSize;
		}		
		return upCell;
	}
	
	private int getDownCell(int cell){
		int downCell = -1;
		if(!isInDiapason(cell)){
			return -1;
		}
		if(cell < cells.length - rowSize){
			downCell = cell + rowSize;
		}		
		return downCell;
	}
	
	private int getLeftCell(int cell){
		int leftCell = -1;
		if(!isInDiapason(cell)){
			return -1;
		}
		if(cell % rowSize != 0){
			leftCell = cell - 1;
		}		
		return leftCell;
	}
	
	private int getRightCell(int cell){
		int rightCell = -1;
		if(!isInDiapason(cell)){
			return -1;
		}
		if(cell % rowSize != rowSize - 1){
			rightCell = cell + 1;
		}		
		return rightCell;
	}
	
	private boolean isInDiapason(int index){
		return index >=0 && index < cells.length;
	}
	
	private int[] getAroundCells(int cell){
		
		int[] cells = new int[8];
		
		cells[0] = getUpCell(cell);
		cells[1] = getLeftCell(cells[0]);
		cells[2] = getRightCell(cells[0]);
		cells[3] = getLeftCell(cell);
		cells[4] = getRightCell(cell);
		cells[5] = getDownCell(cell);
		cells[6] = getLeftCell(cells[5]);
		cells[7] = getRightCell(cells[5]);
		
		return cells;
	}
	
	public int[] posibleMoves(int cell){
		int[] moves = new int[4];
		Arrays.fill(moves, -1);
		
		moves[0] = getUpCell(cell);
		moves[1] = getRightCell(cell);
		moves[2] = getDownCell(cell);
		moves[3] = getLeftCell(cell);
		
		if (moves[0] != -1 && cells[moves[0]].isVisible() && !cells[moves[0]].isEmpty()) {
			moves[0] = -1;
			moves[1] = -1;
			moves[3] = -1;
		} else if (moves[1] != -1 && cells[moves[1]].isVisible() && !cells[moves[1]].isEmpty()) {
			moves[0] = -1;
			moves[1] = -1;
			moves[2] = -1;
		} else if (moves[2] != -1 && cells[moves[2]].isVisible() && !cells[moves[2]].isEmpty()) {
			moves[1] = -1;
			moves[2] = -1;
			moves[3] = -1;
		} else if (moves[3] != -1 && cells[moves[3]].isVisible() && !cells[moves[3]].isEmpty()) {
			moves[0] = -1;
			moves[2] = -1;
			moves[3] = -1;
		}
		
		for(int i = 0; i < moves.length; i++){
			if(moves[i] != -1 && cells[moves[i]].isVisible()){
				moves[i] = -1;
			}
		}
		
		return moves;
	}
	
}
