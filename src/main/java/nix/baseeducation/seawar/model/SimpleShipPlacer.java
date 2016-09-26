package nix.baseeducation.seawar.model;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SimpleShipPlacer extends ShipPlacer {

	private Cell[] cells;
	private boolean[] canBePlaced;
	private boolean[] marked;
	private int freeCells;
	int rowLenght;
	
	@Override
	public boolean plaseShips(Cell[] cells, int... shipsForPlace) {
		
		this.cells = cells;
		int[] ships = shipsForPlace;
		rowLenght = (int) Math.round(Math.sqrt(cells.length));
		
		if (!baseCheck(cells, ships)){
			return false;
		}
				
		descendingSort(ships);
				
		canBePlaced = new boolean[cells.length];
		Arrays.fill(canBePlaced, true);
		marked = new boolean[cells.length];
				
		freeCells = cells.length; 
				
		for (int eachShip : ships){
			if(!placeShip(eachShip)){
				return false;
			}
		}
		
		return true;
	}
	
	private boolean baseCheck(Cell[] cells, int... shipsForPlace)
	{
		for (int each : shipsForPlace){
			if (each > rowLenght || each <= 0){
				return false;
			}
		}
		
		return true;
	}

	private void descendingSort(int[] array){
		
		Arrays.sort(array);
		Integer[] arrayInteger = new Integer[array.length];
		for (int i = 0; i < array.length; i++){
			arrayInteger[i] = array[i];
		}
		
		List<Integer> list = Arrays.asList(arrayInteger);
		Collections.reverse(list);
		arrayInteger = (Integer[])list.toArray();
	
		for (int i = 0; i < array.length; i++){
			array[i] = arrayInteger[i];
		}
	}
	
	private boolean placeShip(int ship){

		if (freeCells <= 0){
			return false;
		}
		boolean isPlaced = false;
		Arrays.fill(marked, false);
		int markedCells = 0;
			
		while (!isPlaced)
		{
			if(markedCells >= marked.length){
				break;
			}
			
			int[] shipCells = new int[ship];
			int cellFound = 0;
				
				
			int beginIndex = (int) Math.round(Math.random() * (cells.length -1));
			if (!canBePlaced[beginIndex] || marked[beginIndex]){
				continue;
			}
				
			marked[beginIndex] = true;
			markedCells++;
			shipCells[cellFound++] = beginIndex;
			
			if(cellFound == ship){
				putShipToCells(shipCells);
				isPlaced = true;
				break;
			}

			int direction = 0;
			int wachedCell = beginIndex;
			int[] otherCells = new int[ship - 1];
			int otherIndex = 0;
			
			while (direction < 4 && otherIndex < otherCells.length){
				wachedCell = getCellByDirection(direction, wachedCell);
				if (wachedCell != -1){
					if(canBePlaced[wachedCell]){
						otherCells[otherIndex++] = wachedCell;
					}
				} else{
					wachedCell = beginIndex;
					direction++;
					if (direction % 2 == 0){
						otherCells = new int[ship -1];
					}
				}
			}
			
			if(otherIndex == otherCells.length){
				for (int i = 0; i < otherCells.length; i++){
					shipCells[i + 1] = otherCells[i];
				}
				
				putShipToCells(shipCells);
				isPlaced = true;
			}			
		}
			
		return isPlaced;
	}
	
	private void putShipToCells(int[] shipCells){
		for (int cell : shipCells){
			cells[cell].setEmpty(false);
			canBePlaced[cell] = false;
			for (int each : getAroundCells(cell)){
				if (each != -1){
					canBePlaced[each] = false;
				}
			}
		}
	}
	
	private int getUpCell(int cell){
		int upCell = -1;
		if(!isInDiapason(cell)){
			return -1;
		}
		if(cell >= rowLenght){
			upCell = cell - rowLenght;
		}		
		return upCell;
	}
	
	private int getDownCell(int cell){
		int downCell = -1;
		if(!isInDiapason(cell)){
			return -1;
		}
		if(cell < cells.length - rowLenght){
			downCell = cell + rowLenght;
		}		
		return downCell;
	}
	
	private int getLeftCell(int cell){
		int leftCell = -1;
		if(!isInDiapason(cell)){
			return -1;
		}
		if(cell % rowLenght != 0){
			leftCell = cell - 1;
		}		
		return leftCell;
	}
	
	private int getRightCell(int cell){
		int rightCell = -1;
		if(!isInDiapason(cell)){
			return -1;
		}
		if(cell % rowLenght != rowLenght - 1){
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
	
	private int getCellByDirection(int direction, int cell){
		switch(direction){
		case 0: return getUpCell(cell);
		case 2: return getRightCell(cell);
		case 1: return getDownCell(cell);
		case 3: return getLeftCell(cell);
		default: return -1;
		}
	}
}
