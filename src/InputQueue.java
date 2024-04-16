import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class InputQueue {
    static  Random rnd = new Random();
    static Queue<String> inputQueue = new LinkedList<>();
    static long timer = System.currentTimeMillis();
    public static void loadInputQueue(){
        for(int i = 0 ; i< 10 ; i++){
            inputQueue.add(GenerateRandomCommand());
        }
    }

    public static String GenerateRandomCommand(){
        String cmd = "";
        int interval = rnd.nextInt(30);
        if(interval < 5){
            cmd = Commands.SpanChest1;
        }
        else if(interval< 10){
            cmd = Commands.SpanChest2;
        }
        else if(interval < 15){
            cmd = Commands.SpanChest3;
        }
        else if(interval < 21){
            cmd = Commands.SpanFire;
        }
        else if(interval < 27){
            cmd =  Commands.SpanIce;
        }
        else {
            cmd = Commands.SpanComputer;
        }

        return cmd;
    }

    public static String[] allCommands(){
        String[] commands = new String[10];
        int s = inputQueue.size();
        for(int i = 0 ; i < s;i++){
            String cmd =  inputQueue.remove();
            commands[i] = cmd;
            inputQueue.add(cmd);
        }
        return  commands;
    }

    public static String GetCommand(){
        InputQueue.inputQueue.add(GenerateRandomCommand());
        return  InputQueue.inputQueue.remove();
    }
}
