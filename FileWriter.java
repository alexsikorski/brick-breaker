package assignment2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {
    public void WriteCurrentScore(int finalScore) {
        try (
                java.io.FileWriter fw = new java.io.FileWriter("src/assignment2/leaderboard.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)
        ) {

            String str = Integer.toString(finalScore);
            out.println(str);

        } catch (IOException e) {
            System.err.println("UNABLE TO READ FILE");
        }
    }
}