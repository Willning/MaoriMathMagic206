package testing;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import gui.ListMode;

/*
 * This class will store all local scores that have been gotten. This is a singleton which will have two lists of scores.
 * One for easy mode and one for hard mode
 */
public class HighScoreManager {
	
	private List<Integer> _easyScores = new ArrayList<Integer>();
	private List<Integer> _hardScores = new ArrayList<Integer>();
	private int _easyGamesPlayed;
	private int _easyHighScore;
	private int _hardGamesPlayed;
	private int _hardHighScore;
	
	private static HighScoreManager instance = null;
	
	private HighScoreManager() {
		// Singleton
	}
	
	/**
	 * Get the singleton instance of the HighScoreManager.
	 */
	public static HighScoreManager getInstance() {
		if (instance == null) {
			return new HighScoreManager();
		}
		return instance;
	}
	
	/**
	 * Add a score to the highscore list.
	 */
	public void addScore(ListMode mode, int score) {
		if (mode.equals(ListMode.EASY)) {
			_easyScores.add(score);
			Collections.sort(_easyScores);
			Collections.reverse(_easyScores);
		} 
		else if (mode.equals(ListMode.HARD)) {
			_hardScores.add(score);
			Collections.sort(_hardScores);
			Collections.reverse(_hardScores);
		}
	}
	
	/**
	 * Get the relevant highscore list.
	 */
	public List<Integer> returnList(ListMode mode) {
		if (mode.equals(ListMode.EASY)) {
			return _easyScores;
		}
		else if (mode.equals(ListMode.HARD)) {
			return _hardScores;
		}
		return null;	
	}
}
