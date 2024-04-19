import java.security.PublicKey;
import java.util.Random;

public class Player {
    static Random rnd = new Random();
    int prevrow;
    int prevcol;
    int row ;
    int col;
    Directions directions;
    Character symbol;
    static int  Score = 0;
    static int Life = 1000;
    static int IceAmount = 10;

    static Ice ice = null ;

    static String PlayerName;

    long timer = System.currentTimeMillis();
    public Player(int row , int col){
        this.row = row;
        this.col =col;
        this.prevrow =row;
        this.prevcol = col;
        this.symbol = 'P';
        this.directions = null;
    }

    // hepler function
    private void Move(Directions direiction , int CoEfficient){
        if(direiction == Directions.left){
            this.col += -1 *CoEfficient;
        }
        else if(direiction == Directions.right){
            this.col += 1 *CoEfficient;
        }
        else if(direiction == Directions.top){
            this.row += -1 * CoEfficient;
        }
        else if(direiction == Directions.down){
            this.row += 1 * CoEfficient;
        }
    };
    private boolean Check(Directions direction){
        boolean flag = true;
        this.Move(direction , 1);
        if(Maze.board[this.row][this.col].equals('#')){
            flag = false;
        }
        else if(Maze.board[this.row][this.col] instanceof  Computer){
            flag = false;
        }
        else if(Maze.board[this.row][this.col].equals(Ice.activeSymbol)){
            flag = false;
        }
        else if(Maze.board[this.row][this.col].equals(Fire.symbol)){
            flag = false;
        }
        this.Move(direction , -1);
        return flag;
    }
    //
    public void Move(Directions direction){
        boolean flag = Check(direction);
        if(!flag) return;
        this.prevrow = this.row;
        this.prevcol = this.col;
        Maze.board[this.row][this.col] = ' ';
        this.Move(direction ,1);

        // check states
        if(Maze.board[this.row][this.col] instanceof Character){
            if( (Character) Maze.board[this.row][this.col] =='1'){
                Player.Score += 3;
            }
            else if((Character)Maze.board[this.row][this.col] =='2'){
                Player.Score += 10;
            }
            else if((Character)Maze.board[this.row][this.col] =='3'){
                Player.Score += 30;
            }
            else if((Character)Maze.board[this.row][this.col] == Ice.passiveSymbol){
                Player.IceAmount++;
            }
        }

        Maze.board[this.row][this.col] = this;
    }

    public static Player span(){
        int[]row_col = Maze.findEmptySpace(); // int [2]{row col}
        Player newPlayer = new Player(row_col[0],row_col[1]);
        Maze.board[newPlayer.row][newPlayer.col] = newPlayer;
        return newPlayer;
    }
    public boolean canMove(){
        return this.timer < System.currentTimeMillis();
    }
    public void takeDamage(){
        // check computers
        if(!this.canMove()) return;
        for(Computer c : Computer.computers){
            int diffrow = Math.abs(c.row - this.row);
            int diffcol = Math.abs(c.col - this.col);

            if(diffrow + diffcol == 1) Player.Life -= 50;
        }
        // take damage from fire
        if(Maze.board[this.row+ 1][this.col].equals(Fire.symbol)){
            Player.Life -= 1;
        }
        if(Maze.board[this.row-1][this.col].equals(Fire.symbol)){
            Player.Life -= 1;
        }
        if(Maze.board[this.row][this.col+1].equals(Fire.symbol)){
            Player.Life -= 1;
        }
        if(Maze.board[this.row][this.col-1].equals(Fire.symbol)){
            Player.Life -= 1;
        }
    }

    public void ThrowIce(){
        if(Player.IceAmount <= 0) return;
        Player.ice = Ice.Spread(this.row , this.col);
        Player.IceAmount--;
    }
}
