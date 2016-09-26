package nix.baseeducation.seawar.starter;

import nix.baseeducation.seawar.controller.Game;
import nix.baseeducation.seawar.controller.PvEConsoleGame;

public class SimpleRunner {
    
    public static void main(String[] args) {
        Game game = new PvEConsoleGame(10);
        game.startNewGame();
    }

}
