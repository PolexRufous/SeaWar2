package nix.baseeducation.seawar.player;

import nix.baseeducation.seawar.model.Field;

public abstract class Player {

	protected Field ownField;
	protected Field enemyField;
	
	public abstract int makeMove();
	public abstract int[] getMoveResult(int move);
	public abstract void setMoveResult(int[] moveResult);
	
	public Player(int fieldSize){
		this.ownField = new Field(fieldSize);
		this.enemyField = new Field(fieldSize);
		ownField.setAllCellsVisible();
		ownField.placeShip(4, 3, 3, 2, 2, 2, 1, 1, 1, 1);
	}
	
	
	public Field getOwnField()
	{
		return ownField;
	}
	
	public Field getenemyField()
	{
		return enemyField;
	}
			
	public abstract boolean hasAliveShips();
	
}
