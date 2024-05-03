package edu.wm.cs.cs301.connectn.model;

import java.io.*; 
import java.io.IOException; 
/*
 * the leaderboard class contains a method for retrieving the current leaderboard
 * and an update method for updating the text file that stores leaderboard information
 */
public class Leaderboard{ 
/*
 * this method reads text from the 3 text files that store leaderboard information and
 * returns this information in the form of an array of strings
 */
	public static String[] getLeaders() throws IOException{
		String[] result = {"","","","","",""};
		
		String smallName;
		String smallScore;
		String medName;
		String medScore;
		String largeName;
		String largeScore;
		
		File smallLeader = new File("resources/smallLeader.txt");
		File medLeader = new File("resources/medLeader.txt");
		File largeLeader = new File("resources/largeLeader.txt");
		smallLeader.createNewFile();
		medLeader.createNewFile();
		largeLeader.createNewFile();
	
		BufferedReader br1 = new BufferedReader(
			new FileReader(smallLeader));
		smallName = br1.readLine();
		smallScore = br1.readLine();
		br1.close();
	
		if (smallName != null) {
			result[0] = smallName;
			result[1] = smallScore;
		}
	
		BufferedReader br2 = new BufferedReader(
			new FileReader(medLeader));
		medName = br2.readLine();
		medScore = br2.readLine();
		br2.close();
		
		if (medName != null) {
			result[2] = medName;
			result[3] = medScore;
		}

		BufferedReader br3 = new BufferedReader(
			new FileReader(largeLeader));
		largeName = br3.readLine();
		largeScore = br3.readLine();
		br3.close();
		
		if (largeName != null) {
			result[4] = largeName;
			result[5] = largeScore;
		}
		
		return result;
	}
	/*
	 * this method takes 3 arguments: mode is the current game mode, string is the player name,and score is the number of turns that have
	 * taken place since the game started until it was won by the player. the method automatically detects
	 * whether the score parameter is less than the current leaderboard score, and if so, updates the text
	 * files to contain the correct information. if no such files exist, they are created within the leaderboard folder.
	 * 
	 */
	public static void updateLeaders(String mode, String name, int score) throws IOException{
		File smallLeader = new File("resources/smallLeader.txt");
		File medLeader = new File("resources/medLeader.txt");
		File largeLeader = new File("resources/largeLeader.txt");
		smallLeader.createNewFile();
		medLeader.createNewFile();
		largeLeader.createNewFile();
		if (mode.equals("Small")){
			BufferedReader br = new BufferedReader(
					new FileReader(smallLeader));
			String record = br.readLine();
			record = br.readLine();
			br.close();
			if ((record == null) || (Integer.parseInt(record) > score)) {
				BufferedWriter bw = new BufferedWriter(
						new FileWriter(smallLeader));
				bw.write(name);
				bw.newLine();
				bw.write(Integer.toString(score));
				bw.close();
			}
		} 
		else if (mode.equals("Medium")) {
			BufferedReader br = new BufferedReader(
					new FileReader(medLeader));
			String record = br.readLine();
			record = br.readLine();
			br.close();
			if ((record == null) || (Integer.parseInt(record) > score)) {
				BufferedWriter bw = new BufferedWriter(
						new FileWriter(medLeader));
				bw.write(name);
				bw.newLine();
				bw.write(Integer.toString(score));
				bw.close();
			} 
		}
		else {
			BufferedReader br = new BufferedReader(
					new FileReader(largeLeader));
			String record = br.readLine();
			record = br.readLine();
			br.close();
			if ((record == null) || (Integer.parseInt(record) > score)) {
				BufferedWriter bw = new BufferedWriter(
						new FileWriter(largeLeader));
				bw.write(name);
				bw.newLine();
				bw.write(Integer.toString(score));
				bw.close();
			}
		} 
	}
}