package nix.baseeducation.seawar.player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import nix.baseeducation.seawar.model.Cell;


public class PersonConsolePlayer extends Player {

	public PersonConsolePlayer(int fieldSize){
		super(fieldSize);
	}

	@Override
	public boolean hasAliveShips() {
		return ownField.hasAliveShips();
	}
	
	@Override
	public String toString()
	{
		return "Person player";
	}

	public void drawFields(){
		Cell[] cells = ownField.getCells();
		System.out.println("Your field:");
		drawCells(cells);
		System.out.println();
		
		cells = enemyField.getCells();
		System.out.println("Enemy field:");
		drawCells(cells);
		System.out.println();
	}
	
	private void drawCells(Cell[] cells)
	{
		int rowSize = (int)Math.sqrt(cells.length);
		System.out.print("   ");
		
		for(int i = 0; i < rowSize; i ++){
			if((i / 10) < 1){
				System.out.print(" " + i + " ");
			} else if(i / 100 < 1){
				System.out.print(" " + i);
			} else{
				System.out.print(i);
			}
		}
		System.out.println();
		
		for(int i = 0; i < cells.length; i ++){
			
			if(i % rowSize == 0){
			
				if((i / rowSize / 10) < 1){
					System.out.print(" " + i/rowSize + " ");
				} else if((i / rowSize / 100) < 1){
					System.out.print(" " + i/rowSize);
				} else{
					System.out.print(i/rowSize);
				}
			}
			
			if(cells[i].isVisible()){
				if(cells[i].isVisited()){
					if(cells[i].isEmpty()){
						System.out.print(" x ");
					} else{
						System.out.print("[x]");
					}
				} else{
					if(cells[i].isEmpty()){
						System.out.print(" ~ ");
					} else{
						System.out.print("[o]");
					}
				}
					
			} else{
				System.out.print(" O ");
			}
			
			if ((i + 1) % rowSize == 0){
				System.out.println("");
			}
		}
	}

	@Override
	public int makeMove() {
		int index = getIngexFromConsole();
		while (index == -1) {
			System.out.println("Incorrect move.");
			index = getIngexFromConsole();
		}
				
		return index;
	}
	
	@Override
	public int[] getMoveResult(int move) {
		return ownField.proceedShot(move);
	}

	@Override
	public void setMoveResult(int[] moveResult) {
		int resultType = moveResult[0];
		int cell = moveResult[1];
		enemyField.proceedResult(resultType, cell);
		drawFields();
	}
	
	private int getIngexFromConsole()
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Make your move!");
		System.out.println("Enter two digits with spase. First � column, second � row.");
		
		int index = -1;
		String input;
		
		try
		{
			input = reader.readLine();
			String[] digits = input.split(" ");
			if (digits.length != 2) {
				return -1;
			}
			index = Integer.parseInt(digits[0]) + Integer.parseInt(digits[1]) * enemyField.rowSize;
			if (index <0 || index > enemyField.rowSize * enemyField.rowSize - 1) {
				index = -1;
			}
		}
		catch (IOException | NumberFormatException e)
		{
			System.out.println("Input exception. Try again.");
		}
		
		return index;
	}
}
