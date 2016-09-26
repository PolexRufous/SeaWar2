package nix.baseeducation.seawar.controller;

import nix.baseeducation.seawar.player.Player;

public abstract class Game {

	
	protected Player player1;
	protected Player player2;
	
	public abstract void startNewGame();
	
}
