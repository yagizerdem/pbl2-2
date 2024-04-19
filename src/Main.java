import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.tree.DefaultTreeCellEditor;
import java.io.*;
import java.lang.ref.Cleaner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLOutput;
import java.time.Instant;
import java.util.*;
import java.util.Scanner;

public class Main  {
    static Random rnd = new Random();
    static EnigmaConfig config = new EnigmaConfig();
    static Player player;


    public static void main(String[] args) throws  Exception {
        init();
//        Computer.span();

        // start screen
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter their name
        System.out.print("Please enter your name: ");

        // Read the user's input as a String
        Player.PlayerName = scanner.nextLine();

        while (true){
            UI.ClearLayout();
            if(SD.keypr ==1){
                // listeners
                if(SD.rkey >=37 && SD.rkey <= 40){
                    player.Move((Directions)Commands.dictionary.get(SD.rkey));
                }
                // enter key
                else if(SD.rkey == 10 && Player.ice == null){
                    player.ThrowIce();
                }
            }
            if(canExecuteCommand()){
                String command = InputQueue.GetCommand();
                ExecuteCommand(command);
                InputQueue.timer = System.currentTimeMillis() + 2000;
            }
            // check player take damage
            player.takeDamage(); // take damage IF possible
            // move cpu
            for(Computer c : Computer.computers){
                c.Move();
            }
            // take damage cpu
            for(Computer c : Computer.computers){
                c.takeDamage();
            }
            // delte eleminated cpu
            Computer.killCpu();

            if(Player.ice != null && Player.ice.CheckMeltIce()){
                Player.ice.Melt();
                Player.ice = null;
            }

            if(Fire.canRemoveFire()){
                Fire.removeFire();
            }

            if(Player.Life <= 0){
                break;
            }

            UI.PrintLayout();
            SD.timeSpendInGame += 50.0 / 1000.0;
            Clear();
            Thread.sleep(50);
        }
//         ending screen
        UI.EndScreen();
        End(Player.Score); // read and write hig score txt
    }

    public static void init(){
        Maze.init();
        Commands.init();
        player = Player.span();
        InputQueue.loadInputQueue();
    }

    public static void Clear(){
        SD.keypr = 0;
    }

    public static boolean canExecuteCommand(){
        return InputQueue.timer < System.currentTimeMillis();
    }

    public static void ExecuteCommand(String cmd){
        if(Maze.isFull()) return;
        int[] row_col = Maze.findEmptySpace(); // genreate random row and col
        if(cmd == Commands.SpanChest1){
            Maze.board[row_col[0]][row_col[1]] = '1';
        }
        else if(cmd == Commands.SpanChest2){
            Maze.board[row_col[0]][row_col[1]] = '2';
        }
        else if(cmd == Commands.SpanChest3){
            Maze.board[row_col[0]][row_col[1]] = '3';
        }
        else if(cmd == Commands.SpanComputer){
            Computer.span();
        }
        else if(cmd == Commands.SpanIce){
            Ice.span();
        }
        else if(cmd == Commands.SpanFire){
            Fire.span();
        }
    }

    public static void End(int playerPoint){
        class Node{
            public Node(String name , int points){
                this.name = name;
                this.points = points;
            }
            String name;
            int points;
        }

        Path currentPath = Paths.get("").toAbsolutePath();
        String filePath = currentPath + "\\src\\HighScores.txt";
        List<Node> records = new ArrayList<>();
        Node playerNode = new Node(Player.PlayerName , Player.Score);
        records.add(playerNode);
        try{
            // Read existing content from the file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                Node newRecord = new Node(line.split(";")[0] , Integer.parseInt(line.split(";")[1]));
                records.add(newRecord);
            }
            reader.close();
        }catch (Exception e){
            UI.ClearLayout();
            System.out.println("Error occured : ");
            System.out.println(e);
        }
        // buble sortal
        for(int i = 0; i<  records.size() -1 ; i++){
            for(int j = 0; j  < records.size() - 1 -i; j++){
                Node n1 = records.get(j+1);
                Node n2 =  records.get(j);
                if(n2.points < n1.points){
                    Collections.swap(records , j+1 ,j);
                }
            }
        }
        // overwrite all recored
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(Node n : records){
                String record = n.name + ";"+n.points;
                writer.write(record);
                writer.newLine();
            }
            System.out.println("Items written to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}