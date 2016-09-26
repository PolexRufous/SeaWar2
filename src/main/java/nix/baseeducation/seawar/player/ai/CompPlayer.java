package nix.baseeducation.seawar.player.ai;
import java.util.Arrays;

import nix.baseeducation.seawar.player.Player;


public class CompPlayer extends Player {

	int[] lastResult;
	int[] wachedShip;
	
	public CompPlayer(int fieldSize){
		super(fieldSize);
	}
	
	@Override
	public String toString()
	{
		return "Computer player";
	}

	@Override
	public boolean hasAliveShips() {
		return ownField.hasAliveShips();
	}

	@Override
	public int makeMove() {
		int move = 0;
		if (wachedShip == null){
			while(true){
				int possible = (int) (Math.random() * (ownField.rowSize * ownField.rowSize) - 1);
				if(!enemyField.getCell(possible).isVisible()){
					move = possible;
					break;
				}
			}
		} else{
			for(int each : wachedShip){
				for(int eachOfEach : enemyField.posibleMoves(each)){
					if(eachOfEach != -1){
						return eachOfEach;
					}
				}
			}
				
		}
		
		return move;
	}

	@Override
	public int[] getMoveResult(int move) {
		
		return ownField.proceedShot(move);
	}

	@Override
	public void setMoveResult(int[] moveResult) {
		
		
		int resultType = moveResult[0];
		int cell = moveResult[1];
		
		if (resultType == 1) {
			if (wachedShip == null) {
				wachedShip = new int[4];
				Arrays.fill(wachedShip, -1);
				wachedShip[0] = cell;
			} else{
				for(int i = 0; i < wachedShip.length; i++){
					if (wachedShip[i] == -1) {
						wachedShip[i] = cell;
						break;
					}
				}
			}
			
		} else if(resultType == 2){
			wachedShip = null;
		}
		
		enemyField.proceedResult(resultType, cell);
		
	}


	
}
