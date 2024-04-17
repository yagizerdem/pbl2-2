import java.util.*;

public class Ice {
    static Random rnd = new Random();
    private Queue<Node> areas = new LinkedList<>();
    public  long timer = System.currentTimeMillis();
    int uuid = 0;
    static Character passiveSymbol = '@';
    static Character activeSymbol = '+';

    private  class Node{
        int row;
        int col;
        Node(int row , int col){
            this.row = row;
            this.col = col;
        }
    }

    public static void span(){
        int[] row_col = Maze.findEmptySpace();
        if(row_col != null){
            Maze.board[row_col[0]][row_col[1]] = Ice.passiveSymbol;
        }
    }

    public Ice(int startrow , int startcol){
        this.uuid = rnd.nextInt(99999);
        Node terminal = new Node(startrow , startcol);
        areas.add(terminal);
        this.bfs();

        // remove player position
        int s = this.areas.size();
        for(int i = 0 ; i < s; i++){
            Node temp = this.areas.poll();
            if(temp.row == terminal.row && temp.col == terminal.col){
                continue; // remove terminal node (ice block at player position);
            }
            this.areas.add(temp);
        }
        // put ice on map
        freezeMap();
        timer = System.currentTimeMillis() +  500; //apper half second
    }
    public static Ice Spread(int startrow , int startcol){
        Ice newIce = new Ice(startrow , startcol);
        return newIce;
    }
    private void bfs(){
        if(areas.size() >= 50) return;
        if(!this.checkAllNodes()) return;
        Node n = areas.poll();
        Node left = new Node(n.row -1 , n.col );
        Node right = new Node(n.row +1 , n.col);
        Node top = new Node(n.row , n.col - 1);
        Node bottom = new Node(n.row  , n.col +1);

        if(this.check(left.row , left.col)) areas.add(left);
        if(this.check(right.row , right.col)) areas.add(right);
        if(this.check(top.row , top.col)) areas.add(top);
        if(this.check(bottom.row , bottom.col)) areas.add(bottom);
        areas.add(n);
        this.bfs();
    }
    private boolean check(int row , int col){
        for(Node n : this.areas ){
            if(n.row == row && n.col==col) return false;
        }
        if(Maze.board[row][col] instanceof Character && (Character) Maze.board[row][col] == ' ' ) return true;
        return false;
    }
    private boolean checkAllNodes(){
        boolean flag  = false;
        for(Node n : this.areas ){
            if(Maze.board[n.row][n.col] instanceof Character){
                if((Character)Maze.board[n.row][n.col] == ' ') {
                    flag = true;
                    break;
                }
            }
            if(Maze.board[n.row][n.col] instanceof Player){
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void freezeMap(){
        for(Node n : this.areas){
            int row = n.row;
            int col = n.col;
            Maze.board[row][col] = Ice.activeSymbol;
        }
    }

    public boolean CheckMeltIce(){
       return this.timer <= System.currentTimeMillis();
    }
    public void Melt(){
        for(Node n : this.areas){
            Maze.board[n.row][n.col] = ' ';
        }
    }
}
