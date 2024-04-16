import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

public  class Commands {
    public static Hashtable<Object, Object> dictionary = new Hashtable<Object , Object>();

    //Command keys
//    public static String LeftKey = "LeftKey";
//    public static String RightKey = "RightKey";
//    public static String TopKey = "TopKey";
//    public static String DownKey = "DownKey";

    public static  String SpanChest1 = "SpanChes1";
    public static String SpanChest2 = "SpanChest2";
    public static String SpanChest3 = "SpanChest3";
    public static String SpanComputer = "SpanComputer";
    public static String SpanIce = "SpanIce";
    public static String SpanFire = "SpanFire";
    public static void init(){
        dictionary.put(37 , Directions.left);
        dictionary.put(38 , Directions.top);
        dictionary.put(39 , Directions.right);
        dictionary.put(40 ,Directions.down);
        dictionary.put(Commands.SpanChest1 ,Commands.SpanChest1);
        dictionary.put(Commands.SpanChest2 ,Commands.SpanChest1);
        dictionary.put(Commands.SpanChest3 ,Commands.SpanChest3);
        dictionary.put(Commands.SpanFire ,Commands.SpanFire);
        dictionary.put(Commands.SpanIce ,Commands.SpanIce);
        dictionary.put(Commands.SpanComputer ,Commands.SpanComputer);
    }
}
