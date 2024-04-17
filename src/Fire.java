import java.util.Stack;

public class Fire {

    static long timer = System.currentTimeMillis();

    static  Character symbol = '-';

    private  static Stack<Node> areas = new Stack<Node>();

    private static class  Node{
        int row;
        int col;
        Node(int row , int col){
            this.row = row;
            this.col =col;
        }
    }
    public static void span(){
        int[] row_col = Maze.findEmptySpace();
        Fire.dfs(row_col[0],  row_col[1]);
        for(Node n : areas){
            Maze.board[n.row][n.col] = '-';
        }

        Fire.timer = System.currentTimeMillis() + 500;
    }
    private static void dfs(int row , int col){
        boolean flag = Check(row , col);
        if(!flag || Fire.areas.size() == 50) return;
        Node node = new Node(row , col);
        Fire.areas.add(node);
        dfs(row + 1 , col );
        dfs(row - 1 , col );
        dfs(row  , col +1 );
        dfs(row  , col -1);
    }
    private static boolean Check(int row , int col){
        if(row >= Maze.board.length || row == 0  || col  >= Maze.board[0].length || col <= 0) return false;
        for(Node n : Fire.areas){
            if(n.row == row && n.col == col) return false;
        }
        if(Maze.board[row][col] instanceof  Character){
            if((Character)Maze.board[row][col] == ' '){
                return  true;
            }
        }
        return false;
    }
    public static void removeFire(){
        // delete from ui
        for(Node n : areas){
            Maze.board[n.row][n.col]= ' ';
        }
        Fire.timer = System.currentTimeMillis();
        Fire.areas = new Stack<Node>();
    }
    public static boolean canRemoveFire(){
        return Fire.timer <= System.currentTimeMillis();
    }


}
