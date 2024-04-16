import java.util.*;

public class Computer {
    static List<Computer> computers = new ArrayList<>();
    static Random rnd  = new Random();
    int row;
    int col;
    static Character symbol = 'C';

    static int allPoints = 0;

    int[] target;

    long timer = System.currentTimeMillis(); // move timer

    Computer(int row , int col){
        this.row = row;
        this.col = col;
        this.target = null;
    }
    public void Move(){
        if(this.target == null){
            int[] chesPosition = findRandomChest();
            if(chesPosition == null) target = findRandomPosition();
            else target = chesPosition;
        }
        // shortest path finding
        int[] nextStep = findPath(Maze.board , new int[]{this.row , this.col} ,new int[]{this.target[0] , this.target[1]});
        if(nextStep == null){
            // find new target
            target = null;
            return;
        }

        // move
        Maze.board[this.row][this.col] = ' ';
        this.row = nextStep[0];
        this.col = nextStep[1];
        // check states after move
        if(Maze.board[this.row][this.col] instanceof Character){
            // taking chests and gaining score (computer)
            char ch = (Character) Maze.board[this.row][this.col];
            boolean isOneTwoOrThree = (ch == '1' || ch == '2' || ch == '3') ? true : false;
            if(ch == '1'){
                Computer.allPoints += 9;
            }
            else if(ch == '2'){
                Computer.allPoints += 30;
            }
            else if(ch == '3'){
                Computer.allPoints += 90;
            }
            if(isOneTwoOrThree) this.target = null;
        }

        Maze.board[this.row][this.col] = this;
        this.timer = System.currentTimeMillis() + 400;
    }

    private static class Cell{
        int x ;
        int y;
        int dist;
        Cell prev;
        Cell(int x , int y , int dist , Cell cell){
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.prev = cell;
        }
    }
    private int[] findPath(Object[][] matrix ,  int[] from , int[] to){
        int sx = from[0] , sy = from[1];
        int dx = to[0] , dy = to[1];
        int m = matrix.length;
        int n = matrix[0].length;

        Cell[][] cells = new Cell[m][n];
        ArrayList<Character> availableSpaces = new ArrayList<>(Arrays.asList(' ','1','2','3'));

        if(!(matrix[sx][sy] instanceof Computer)||!(matrix[dx][dy] instanceof Character)) {
            return null;
        };
        if(!availableSpaces.contains((Character)matrix[dx][dy])) {
            return null;
        };

        for(int i = 0; i < m ; i++){
            for(int j = 0 ; j< n ; j++){
                if(!(i == sx && j == sy)&&!(matrix[i][j] instanceof Character)) continue;
                if(!(i == sx && j == sy)&&!availableSpaces.contains((Character)matrix[i][j])) continue;
                Cell newCell = new Cell(i,j,Integer.MAX_VALUE,null);
                cells[i][j] = newCell;
            }
        }

        Queue<Cell> queue = new LinkedList<>();
        Cell dest = null;
        Cell p = null;
        Cell src = cells[sx][sy];
        src.dist = 0;
        queue.add(src);
        while ((p = queue.poll()) != null){
            if(p.x == dx && p.y == dy){
                dest = p;
                break;
            }
            visit(queue , cells , p.x+1 , p.y,p);
            visit(queue , cells , p.x-1 , p.y,p);
            visit(queue , cells , p.x , p.y +1,p);
            visit(queue , cells , p.x , p.y-1,p);
        }
        if(dest == null){
            return null;
        }
        else{
            // path found
            LinkedList<Cell> path = new LinkedList<>();
            Cell temp = dest;
            path.addFirst(temp);

            while ((temp = temp.prev) != null){
                path.addFirst(temp);
            }
            path.removeFirst();
            Cell targetCell = path.removeFirst();
            int[] targetLocation = new int[2];
            targetLocation[0] = targetCell.x;
            targetLocation[1] = targetCell.y;
            return targetLocation;
        }
    }
    private void visit(Queue<Cell> queue , Cell[][] cells , int x , int y , Cell parent){
        if(x < 0 || x > cells.length|| y <0 || y > cells[0].length || cells[x][y] == null){
            return;
        }
        int dist = parent.dist+1;
        Cell p= cells[x][y];
        if(p.dist > dist){
            p.dist = dist;
            p.prev = parent;
            queue.add(p);
        }
    }

    private int[] findRandomChest(){
        List<Character> list = new ArrayList<Character>(Arrays.asList('1','2','3'));
        List<int[]> chestPositions = new ArrayList<>();
        for(int i = 0 ; i <Maze.board.length ;  i++){
            for(int j = 0; j <Maze.board[0].length ; j++){
                Object item = Maze.board[i][j];
                if(!(item instanceof  Character)) continue;
                if(!list.contains(item)) continue;
                chestPositions.add(new int[]{i,j});
            }
        }
        if(chestPositions.size() == 0) return  null;
        int[] position = chestPositions.get(rnd.nextInt(chestPositions.size()));
        return  position;
    }
    private int[] findRandomPosition(){
        int row = rnd.nextInt(23);
        int col  = rnd.nextInt(53);
        return new int[]{row,col};
    }

    public static Computer span(){
        int[]row_col = Maze.findEmptySpace();
        Computer newComputer =   new Computer(row_col[0],row_col[1]);
        Maze.board[newComputer.row][newComputer.col] = newComputer;
        Computer.computers.add(newComputer);
        return  newComputer;
    }
}
