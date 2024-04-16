import javax.swing.text.EditorKit;
import java.sql.SQLOutput;

public class UI {
    public static void PrintBoardUI(){
        EnigmaConfig.cnt.setCursorPosition(0,0);
        for(int i = 0 ; i < 23 ;i++){
            for(int j = 0; j< 53;j++){
                if(Maze.board[i][j] instanceof Player){
                    EnigmaConfig.cn.setTextAttributes(EnigmaConfig.att1);
                    System.out.print(((Player) Maze.board[i][j]).symbol);
                    EnigmaConfig.cn.setTextAttributes(EnigmaConfig.att0);
                }
                else if(Maze.board[i][j] instanceof Computer){
                    EnigmaConfig.cn.setTextAttributes(EnigmaConfig.att2);
                    System.out.print(((Computer) Maze.board[i][j]).symbol);
                    EnigmaConfig.cn.setTextAttributes(EnigmaConfig.att0);
                }
                else System.out.print(Maze.board[i][j]);
            }
            System.out.println();
        }
    }

    public static void ClearLayout(){
        EnigmaConfig.cnt.setCursorPosition(0,0);
        for(int i= 0; i< 35 ; i++){
            System.out.print(" ".repeat(80));
            System.out.println();
        }
    }
    public static void PrintLayout(){
        PrintBoardUI();
        PrintTimer();
        PrintHealt();
        PrintPoints();
        PrintIce();
        PrintInputQueue();
        PrintComputerCount();
        PrintComputerScore();
    }

    public static void PrintTimer(){
        EnigmaConfig.cnt.setCursorPosition(60 , 18);
        System.out.print("Time : "+ Math.floor(SD.timeSpendInGame));
    }

    public static void PrintPoints(){
        EnigmaConfig.cnt.setCursorPosition(60 , 12);
        System.out.print("Player Points : " + Player.Score);
    }
    public static void PrintHealt(){
        EnigmaConfig.cnt.setCursorPosition(60 , 13);
        System.out.print("Player Healt : " + Player.Life);
    }
    public static void PrintIce(){
        EnigmaConfig.cnt.setCursorPosition(60 , 14);
        System.out.print("Player Ice Amount : " + Player.IceAmount);
    }

    public static void PrintInputQueue(){
        String[] allCommands = InputQueue.allCommands();
        String builder = "";
        for(String cmd : allCommands){
            if(cmd.equals(Commands.SpanChest1)){
                builder += "1";
            }
            else if(cmd.equals(Commands.SpanChest2)){
                builder += "2";
            }
            else if(cmd.equals(Commands.SpanChest3)){
                builder += "3";
            }
            else if(cmd.equals(Commands.SpanIce)){
                builder += Ice.passiveSymbol;
            }
            else if(cmd.equals(Commands.SpanFire)){
                builder += Fire.symbol;
            }
            else if(cmd.equals(Commands.SpanComputer)){
                builder += Computer.symbol;
            }
        }
        EnigmaConfig.cnt.setCursorPosition(60,1);
        System.out.print("Input queue " + builder);
    }

    public static void PrintComputerScore(){
        EnigmaConfig.cnt.setCursorPosition(60 , 20);
        System.out.print("C.Socre : " + Computer.allPoints);
    }
    public static void PrintComputerCount(){
        EnigmaConfig.cnt.setCursorPosition(60 , 21);
        System.out.print("C.Count : " + Computer.computers.size());
    }

    public static void EndScreen(){
        ClearLayout();
        EnigmaConfig.cnt.setCursorPosition(0,0);
        System.out.println("#".repeat(32));
        System.out.println("#".concat(" ".repeat(30).concat("#")));
        System.out.println("#".concat(" ".repeat(30).concat("#")));
        System.out.println("#".concat(" ".repeat(30).concat("#")));
        System.out.println("#".repeat(32));
        EnigmaConfig.cnt.setCursorPosition(2,2);
        System.out.print("game end ...");
        EnigmaConfig.cnt.setCursorPosition(2,3);
        System.out.print("Your score is : " + Player.Score);
    }
}
