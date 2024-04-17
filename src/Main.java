import org.w3c.dom.ls.LSOutput;

import javax.swing.text.MaskFormatter;
import javax.swing.tree.DefaultTreeCellEditor;
import java.lang.ref.Cleaner;
import java.sql.Connection;
import java.sql.SQLOutput;
import java.time.Instant;
import java.util.*;

public class Main  {
    static Random rnd = new Random();
    static EnigmaConfig config = new EnigmaConfig();
    static Player player;

    public static void main(String[] args) throws  Exception {
        init();
//        Computer.span();


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
        // ending screen
        UI.EndScreen();
        End(); // read and write hig score txt
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

    public static void End(){
        //
    }
}