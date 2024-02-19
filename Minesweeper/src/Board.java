import java.util.Random;

public class Board {
    private byte row;
    private byte col;
    private byte bomb;
    public Tile[][] tiles;
    private Random random;
    private Flag flags;
    private GameTimer timer;
    private long flippables;
    private boolean gameStarted = false;
    private boolean gameLost = false;
    private boolean gameWon = false;
    private boolean numberSet = false;
    public void normal(){
        row = 10;
        col = 8;
        bomb = 10;
        flippables = (row*col) - bomb;
        tiles = new Tile [row][col];
        flags = new Flag((byte) 10);
        timer = new GameTimer(179);
        initialiseBoard();
    }
    public void ultimate(){
        row = 25;
        col = 20;
        bomb = 29;
        flippables = 471;
        tiles = new Tile[row][col];
        flags = new Flag((byte) 29);
        timer = new GameTimer(479);
        initialiseBoard();
    }
    private void initialiseBoard(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                tiles[i][j] = new Tile();
            }
        }
        random = new Random();
    }
    public void firstFlip(byte r, byte c){
        tiles[r][c].flip();
        flippables--;
        setBombs(r,c);
        if(!numberSet){
            setNumbers();
            numberSet = true;
        }
        if (tiles[r][c].getNumber() == 0) {
            boolean[][] visited = new boolean[row][col];
            System.out.println("first 0");
            flashFlip(r, c, visited);
        }
    }
    private void setBombs(byte r, byte c){
        int bombSet = 0;
        while(bombSet < bomb){
            int rRow = random.nextInt(row);
            int rCol = random.nextInt(col);
            if(!(rRow == r && rCol == c) && !tiles[rRow][rCol].isBomb()){
                tiles[rRow][rCol].setBomb(true);
                bombSet++;
            }
        }
    }
    private void setNumbers(){
        for (byte i = 0; i < row; i++) {
            for (byte j = 0; j < col; j++) {
                byte number = calculateBombs(i,j);
                tiles[i][j].setNumber(number);
            }
        }
    }
    private byte calculateBombs(byte r, byte c){
        byte adjacentBombs = 0;
        for (int i = Math.max(0, r - 1); i < Math.min(row, r + 2); i++) {
            for (int j = Math.max(0, c - 1); j < Math.min(col, c + 2); j++) {
                if(tiles[i][j].isBomb()){
                    adjacentBombs++;
                }
            }
        }
        return adjacentBombs;
    }
    private boolean isValidTile(int r, int c) {
        return r >= 0 && r < row && c >= 0 && c < col;
    }
    public void flashFlip(byte r, byte c, boolean[][] visited){
        if (visited[r][c]) {
            return;
        }
        visited[r][c] = true;
        if (tiles[r][c].getNumber() == 0) {
            for (int i = Math.max(0, r - 1); i <= Math.min(row - 1, r + 1); i++) {
            for (int j = Math.max(0, c - 1); j <= Math.min(col - 1, c + 1); j++) {
                    if(isValidTile(i,j) && !visited[i][j]){
                        tiles[i][j].flip();
                        flashFlip((byte) i, (byte) j, visited);
                    }
                }
            }
        }
        printBoard();
    }
    public byte getRow() {
        return row;
    }
    public byte getCol() {
        return col;
    }
    public byte viewFlags(){
        return flags.getFlags();
    }
    public long viewTimer(){
        return timer.getTime();
    }
    public void leftClickTile(byte r, byte c) {
        if (!gameStarted) {
            firstFlip(r, c);
            gameStarted = true;
            return;
        }
        if (!tiles[r][c].isFlagged()) {
            if (tiles[r][c].isBomb()) {
                gameLost = true;
            } else {
                tiles[r][c].flip();
                flippables--;
                if (tiles[r][c].getNumber() == 0) {
                    boolean[][] visited = new boolean[row][col];
                    flashFlip(r, c, visited);
                }
                if (flippables == 0) {
                    gameWon = true;
                }
            }
        }
    }
    public void rightClickTile(byte r, byte c) {
        if (!tiles[r][c].isFlipped()) {
            if (!tiles[r][c].isFlagged()) {
                tiles[r][c].setFlagged(true);
                flags.drop();
            } else {
                tiles[r][c].setFlagged(false);
                flags.lift();
            }
        }
    }
    public void restartGame() {
        gameStarted = false;
        gameLost = false;
        gameWon = false;
        numberSet = false;
        normal();
    }
    public void changeGameMode(String mode) {
        restartGame();
        if (mode.equals("normal")) {
            normal();
        } else if (mode.equals("ultimate")) {
            ultimate();
        }
    }
    public String viewRules() {
        return "Minesweeper rules";
    }
    public boolean isGameLost() {
        return gameLost;
    }
    public boolean isGameWon() {
        return gameWon;
    }
    public void printBoard() {
        System.out.println("Current Board State:");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (tiles[i][j].isFlipped()) {
                    if (tiles[i][j].isBomb()) {
                        System.out.print("* "); // Representing a flipped bomb
                    } else {
                        System.out.print(tiles[i][j].getNumber() + " ");
                    }
                } else {
                    System.out.print(". "); // Representing an unflipped tile
                }
            }
            System.out.println();
        }
    }
}