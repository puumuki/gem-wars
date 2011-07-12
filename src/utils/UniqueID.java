package utils;

public class UniqueID {

	private static long unigueID = 0; 
	
	public static long nextUniqueID() {
		return unigueID++;
	}
}
