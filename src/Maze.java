import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Random;

public class Maze {
    static Random rnd = new Random();
    static Object[][] board = new Object[23][53];

    public static void init(){
        String filePath = "C:\\Users\\yagiz\\Desktop\\pbl2-2\\maxe.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for(int i = 0 ; i < line.length();i++){
                    board[row][i] = Character.valueOf(line.toCharArray()[i]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] findEmptySpace(){
        int row;
        int col;
        do{
            row = rnd.nextInt(23);
            col = rnd.nextInt(53);
        }while (!(Maze.board[row][col] instanceof  Character && (Character) Maze.board[row][col] == ' '));
        return new int[]{row ,col};
    }
    public static boolean isFull(){
        for(int i = 0 ; i < Maze.board.length;i++){
            for(int j = 0;j < Maze.board[0].length;j++){
                if(Maze.board[i][j] instanceof Character && (Character)Maze.board[i][j] == ' ') return false;
            }
        }
        return true;
    }
}
