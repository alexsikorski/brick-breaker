package assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileReader {

    public static ArrayList<Integer> OpenAndRead() {
        ArrayList<Integer> leaderboardList = new ArrayList<Integer>(); // array that holds the contents of the txt


        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader("src/assignment2/leaderboard.txt"));

            String line = null;

            while ((line = br.readLine()) != null) {    // stops when no characters are found
                Integer result = Integer.parseInt(line);
                leaderboardList.add(result);
            }

            br.close();

        } catch (FileNotFoundException e) {
            System.err.println("LEADERBOARD.TXT NOT FOUND");

        } catch (IOException e) {
            System.err.println("UNABLE TO READ FILE");
        }
        return leaderboardList;
    }
}

