package nix.baseeducation.seawar.controller;

import nix.baseeducation.seawar.player.PersonConsolePlayer;
import nix.baseeducation.seawar.player.Player;
import nix.baseeducation.seawar.player.ai.CompPlayer;

public class PvEConsoleGame extends Game {

	private Player winner;
	
	@Override
	public void startNewGame() {
		
		((PersonConsolePlayer) player1).drawFields();
		
		do
		{
			int moveResultType = 0;
			int move;
			int[] moveResult;
			
			do
			{
				move = player1.makeMove();
				moveResult = player2.getMoveResult(move);
				moveResultType = moveResult[0];
				player1.setMoveResult(moveResult);
			} while(moveResultType != 0);
			
			do
			{
				move = player2.makeMove();
				moveResult = player1.getMoveResult(move);
				moveResultType = moveResult[0];
				player2.setMoveResult(moveResult);
			} while (moveResultType != 0);
			
			
		} while (!this.isEnded());
		
		System.out.println("Game is over!");
		System.out.println("Winner - " + winner.toString());
	}

	
	public PvEConsoleGame(int fieldSize)
	{
		player1 = new PersonConsolePlayer(fieldSize);
		player2 = new CompPlayer(fieldSize);
	}
	
	private boolean isEnded()
	{
		if(player1.hasAliveShips() && player2.hasAliveShips())
		{
			return false;
		} else if (player1.hasAliveShips()){
			winner = player1;
			return true;
		} else {
			winner = player2;
			return true;
		}
		
	}
}
