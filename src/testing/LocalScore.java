package testing;

import java.util.ArrayList;
import java.util.List;

import gui.ListMode;

/*
 * This class will store all local scores that have been gotten. This is a singleton which will have two lists of scores.
 * One for easy mode and one for hard mode
 */
public class LocalScore {
	
	private static List<Integer> EasyScores = new ArrayList<Integer>();
	private static  List<Integer> HardScores = new ArrayList<Integer>();
	
	private static LocalScore instance = null;
	
	private LocalScore(){
		//Claaasic singleton
	}
	
	public static LocalScore getInstance(){
		if (instance == null ){
			return new LocalScore();
		}
		return instance;
		
	}
	
	public void addScore(ListMode mode, int score){
		if (mode.equals(ListMode.EASY)){
			EasyScores.add(score);
		} 
		else if (mode.equals(ListMode.HARD)) {
			HardScores.add(score);
		}
		
	}
	
	public List<Integer> returnList(ListMode mode){
		if (mode.equals(ListMode.EASY)){
			return EasyScores;
		}
		else if (mode.equals(ListMode.HARD)){
			return HardScores;
		}
		return null;
		
		
	}
	
	
	
	
}
