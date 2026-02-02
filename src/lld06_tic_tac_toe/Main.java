package lld06_tic_tac_toe;

/**
Problem statement: Design Tic Tac Toe
*/

//  < =========================================== Solution ============================================== >

/*
-Calrifying question: 
 To align on requirements, I’ll assume this is a standard two-player Tic Tac Toe game on a fixed 3×3 board, where players alternate  turns placing X and O, and the game ends on a win via row, column, or diagonal, or a draw when the board is full. Does that sound  correct.

If they hesitate, then ask one more:
  Should I consider extending this beyond 3×3 or keep it simple?
*/

/**
 - What thing must exsit to run the game? 
   1. Board
   2. Player
   3. Game(cordinates the players and board)
 */

/**
 - Action that bind each compoenet together: 
      Ask these three questions: 
        - How does it start?
        - How does it move?
        How does it stop?
  
  - Life cycle of the game: 
    - choose cell - Game (Game owns when a move is allowed and who may act.)
    - mark cell with symbol - Board (it's know either cell is empty or not)
    - can't overriride already marked cell(enforce rules) --> board (it's know about cell status)
    - it  row or column will have same symbol then user will win. --> Board -> Game(Board answer hasWinningLine, Game decide what to do next)
    - If No cell left and no row and column with same symbol then it is draw.  Board -> Game(Board answer hasWinningLine, Game decide what to do next)
 */

/**
 Conclusion: 
  - Game owns flow and lifecycle decisions
  -Board owns grid state and pattern detection
  -Player owns identity only
 */

/**
                           Game
        ┌─────────────────────────────────────┐
        │ - id                                │
        │ - currentPlayer                    │
        │ - gameStatus                       │
        │                                     │
        │ + startGame()                      │
        │ + playMove(pos)                    │
        │ + switchTurn()                     │
        │ + evaluateGameStatus()             │
        │                                     │
        └───────────────┬───────────────┬────┘
                        │ has-a          │ has-a
                        │                │
                        ▼                ▼
                  Board                    Player
        ┌─────────────────────┐   ┌─────────────────────┐
        │ - boardSize          │   │ - id                │
        │ - grid[ ][ ] :Symbol │   │ - name              │
        │                      │   │ - symbol (X / O)    │
        │ + placeSymbol(pos,s) │   │                     │
        │ + isValidCell(pos)   │   │ + getSymbol()       │
        │ + isCellEmpty(pos)   │   │ + getName()         │
        │ + hasWinningLine()   │   │ + getId()           │
        │ + isFull()           │   │                     │
        └─────────────────────┘   └─────────────────────┘

 */

/**
 - Each type of player, Board and Game will be one, data is  not changing so no interface n
 - Enum for Symbol(X, O, EMPTY) and GameStatus (IN_PROGRESS, DRAW, WIN)
 */

class Player {
    private String id;
    private String name;
    private char symbol;

    public Player(String id, String name, char symbol) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
}


class Board {
    int boardSize;
    char[][] grid; 

    public Board(int size) {
      this.boardSize = size;
      grid = new char[size][size];
      for(int i = 0; i < size; i++) {
        for(int j = 0; j < size; j++) {
          grid[i][j] = ' ';
        }
      }
    }

    private boolean isCellEmpty(int row, int col) {
        return grid[row][col] == ' ';
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }

    public boolean placeSymbol(int row, int col, char symbol) {
          if (!isValidCell(row, col)) return false;
          if (!isCellEmpty(row, col)) return false;
          grid[row][col] = symbol;   // 🔑 missing mutation
          return true;
    } 

    boolean isFull() {
      for(int i = 0; i < boardSize; i++) {
        for(int j = 0; j < boardSize; j++) {
          if(grid[i][j] == ' ') {
            return false;
          }
        }
      }
      return true;
    }

    boolean hasWinningLine(char symbol) {
      // check rows 
      for(int i = 0; i < boardSize; i++) {
        for(int j = 0; j < boardSize; j++) {
          if(grid[i][j] != symbol) {
            break;
          }
          if(j == boardSize - 1) {
            return true;
          }
        }
      }
      //check columns
      for(int i = 0; i < boardSize; i++) {
        for(int j = 0; j < boardSize; j++) {
          if(grid[j][i] != symbol) {
            break;
          }
          if(j == boardSize - 1) {
            return true;
          }
        }
      }
      //check main digonal 
      for(int i = 0; i < boardSize; i++) {
         if(grid[i][i] != symbol) {
            break;
         } 
         if(i == boardSize - 1) {
            return true;
         }
      }

      //check anti digonal 
      for(int i = boardSize - 1; i >= 0; i--) {

          if(grid[i][boardSize - i - 1] != symbol) {
              break;
          }

          if(i == 0) {
            return true;
          }

      }

      return false;
    }

}

enum GameStatus { RUNNING, ENDED }


class Game {
    private String id; 
    private GameStatus gameStatus;
    private Player currentPlayer; 
    private Board board;
    private Player player1;
    private Player player2;

    
    Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }
    
    void startGame() {
      this.gameStatus = GameStatus.RUNNING;
      this.currentPlayer = player1;
      System.out.print(currentPlayer.getName() + " Please enter your symbol!");
    }

    void switchTurn() {
      if(currentPlayer.getId().equals(player1.getId())) {
         this.currentPlayer = player2;
      } else {
        this.currentPlayer = player1;
      }
    }

    void playMove(int x, int y) {
        if (gameStatus != GameStatus.RUNNING) {
            System.out.println("Game already ended");
            return;
        }

        boolean success = board.placeSymbol(x, y, currentPlayer.getSymbol());
        
        if (!success) {
            System.out.println("Invalid move, try again");
            return;
        }

        evaluateGameStatus();
    }

    void evaluateGameStatus() {
        if(board.hasWinningLine(currentPlayer.getSymbol())) {
            System.out.println("congratulations! " + currentPlayer.getName() + " you are winner");
            this.gameStatus = GameStatus.ENDED;
            System.out.print(gameStatus);
            //terminate program 
        } else if(board.isFull()) {
            System.out.println("There is no winner, game draw");
            this.gameStatus = GameStatus.ENDED;
            System.out.print(gameStatus);
            //terminate program
        } else {
            switchTurn();
        }
    }

}

public class Main {

    public static void main(String[] args) {
        Player player1 = new Player("1", "hemant", 'X');
        Player player2 = new Player("2", "vasant", 'O');
        Board board = new Board(3);
        Game game = new Game(board, player1, player2);
        game.startGame();
    }
}
