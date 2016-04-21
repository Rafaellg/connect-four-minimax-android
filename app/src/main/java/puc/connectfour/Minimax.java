package puc.connectfour; /**
 * Minimax.java
 *
 *
 *  Computer calculates the best move by looking ahead by a specified
 * number of moves.
 *
 * color 1 wants to take the highest of its recursive generated values,
 *	but return the lowest.
 * color -1 wants to take the lowest of its recursive generated values,
 *	but return the highest.
 *
 *
 */

import android.view.View;
import android.widget.Toast;

import java.util.*;

//Create Minimax object from root node
public class Minimax
{
	private int[][] grid = new int[6][7];
	private int maxDepth;
    private GameActivity gameActivity;

	//Copy grid from given game grid, set max depth;
	// If maxDepth is set higher, game is harder
	public Minimax(int[][] game,int depth, GameActivity gameActivity)
	{
    	grid = copyGrid(game);
    	maxDepth = depth;
        this.gameActivity = gameActivity;
	}

	//Function to copy grid.
	private int[][] copyGrid(int[][]copy)
	{
		int[][] newgrid = new int [6][7];
		for (int row =0; row<6; row++)
    	{
    		for (int col=0; col<7; col++)
    		{
    			newgrid[row][col]=copy[row][col];
    		}
    	}
    	return newgrid;
	}

	//Return col value that the computer wants.
	public int calcValue()
	{
		//If it is computer's first turn, return col 3.
		// However, if player's first turn was col3, return col2.
		// Do this by looking at the board.
		int count = 0;
		for (int row =0; row<6; row++)
    	{
    		for (int col=0; col<7; col++)
    		{
    			if (grid[row][col]==1)
    			{
    				count++;
    			}
    		}
    	}
		if (count ==1)
		{
			if (grid[5][2]==1)
			{
				return 4;
			}
			else if (grid[5][4]==1)
			{
				return 2;
			}
			else
			{
				return 3;
			}
		}


		// If not first turn, return negamax
		return negamax(grid,-1000000,0,1);
	}

	//Recursively calls itself and returns the best col that computer should choose.
	private int negamax(int[][] game, int alpha, int depth, int color)
	{
		int bestPath = 0;
		int bestValue = alpha;
		int player;

		int[][] newGrid = copyGrid(game);

		if (color == 1)
		{
			player = 2;
		}
		else
		{
			player = 1;
		}

//        GameActivity cfm = GameActivity.getInstance();
//		ConnectFourModel cfm = new ConnectFourModel();
        gameActivity.setGrid(newGrid);

		//Determine if game is over in current state;
		// return 1000000 if comp win; else return -1000000
		if (gameActivity.win())
		{
			if (gameActivity.getPlayer() == 1)
			{bestValue = color*(1000000-depth);}
			else
			{bestValue = color*(-1000000+depth);}
		}
		//Determine if game is a draw

        //TODO arrumar essa linha do empate! ----------------------------------------------------------------------------------
//		else if(cfm.full()==true&&cfm.win()==false)
//		{
//			bestValue = 0;
//		}
		//Determine pathValue using eval() if depth is reached.
		else if (depth==maxDepth)
		{
			int mid = (eval(newGrid,player));
			if (mid!=0)
			{
				bestValue = color*(mid-depth);
			}
			else
			{
				bestValue = mid;
			}
		}



		else
		{
			//Generate moves for each col and find the
			// best score from each of the generated moves.
			for(int c=0;c<7;c++)
			{

				//Create a cfm for this column and attempt to dropOnCopy.
//				ConnectFourModel newcfm = new ConnectFourModel();
//				GameActivity newcfm = GameActivity.getInstance();
                //gameActivity.setGrid(newGrid);
				int r = drop(c);

				//Recursive call the generated game grid and compare the current value to move value
				// If move is higher, make it the new current value.

				//If dropOnCopy is successful
				if (r!=-1)
				{
					int[][] nGrid = new int[6][7];


					// Only if depth is < maxDepth recursive call
					if (depth < maxDepth)
					{
					nGrid = copyGrid(newGrid);
					nGrid[r][c] = player;
					int v = -negamax(nGrid,-1000000,depth+1,color*-1);
					if (v >=bestValue)
					{
						bestPath = c;
						bestValue = v;
					}
					}
				}
			}
		}
		if (depth==0)
		{
			//System.out.println("Turn end");
			return bestPath;
		}
		else
		{
			//System.out.println(bestValue);
			return bestValue;
		}

	}

	//Evaluates the current board based on current player and returns a value based on how good
	// current position is.
	// Neutral is given a score of 0.
	// 2 in a row is 10. (00__) or (0_0_) or (0__0) or (_0_0) or (__00)
	// Open-ended 2-in-a-row is 20. (_00_)
	// 3 in a row is 1000. (00_0) or (0_00) or (000_) or (_000)
	// Open-ended 3-in-a-row is 2000 (_000_).
	// return sum of these values.

	//Modifier
	// Vertical = *1
	// Diagonal = *2
	// Horizontal = *3
	private int eval(int[][] board,int player)
	{
		int v = 1;
		int d = 2;
		int h = 3;

		int twoIn = 10;
		int threeIn = 1000;

		int val = 0;
		//Check for horizontal 2-in-a-row.
    	for (int row=0;row<6;row++)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			//(xx00)
    			if (board[row][col] == player &&
    				board[row][col] == board[row][col+1] &&
    				board[row][col+2] == 0 &&
    				board[row][col+3] == 0)
    				{
    					val+= twoIn*h;
    				}
    			//(x0x0)
    			else if (board[row][col] == player &&
    				board[row][col+2] == player &&
    				board[row][col+1] == 0 &&
    				board[row][col+3] == 0)
    				{
    					val+= twoIn*h;
    				}
    			//(x00x)
    			else if (board[row][col] == player &&
    				board[row][col+3] == player &&
    				board[row][col+1] == 0 &&
    				board[row][col+2] == 0)
    				{
    					val+= twoIn*h;
    				}
    			//(0xx0)
    			else if (board[row][col] == 0 &&
    				board[row][col+1] == player &&
    				board[row][col+2] == player &&
    				board[row][col+3] == 0)
    				{
    					val+= 2*twoIn*h;
    				}
    			//(0x0x)
    			else if (board[row][col] == 0 &&
    				board[row][col+1] == player &&
    				board[row][col+2] == 0 &&
    				board[row][col+3] == player)
    				{
    					val+= twoIn*h;
    				}
    			//(00xx)
    			else if (board[row][col] == 0 &&
    				board[row][col] == board[row][col+1] &&
    				board[row][col+2] == player &&
    				board[row][col+3] == player)
    				{
    					val+= twoIn*h;
    				}
    		}
    	}

    	//Check for vertical spaced 2-in-a-row.
    	// 0
    	// x
    	// x

    	for (int row=5;row>1;row--)
    	{
    		for (int col = 0;col<7;col++)
    		{
    			if (board[row][col] == player &&
    				board[row][col] == board[row-1][col] &&
    				board[row-2][col] == 0)
    				{
    					val+= twoIn*v;
    				}
    		}
    	}
    	//Check for diagonal spaced 2-in-a-row (/).
    	//    0     x      x     x      0      0
    	//   0     0      x     0      x      x
    	//  x     0      0     x      0      x
    	// x     x      0     0      x      0
    	for (int row=5;row>2;row--)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			if (board[row][col] == player &&
    				board[row][col] == board[row-1][col+1] &&
    				board[row-2][col+2] == 0 &&
    				board[row-3][col+3] == 0)
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == player &&
    				board[row-1][col+1] == 0 &&
    				board[row-2][col+2] == 0 &&
    				board[row][col] == board[row-3][col+3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == 0 &&
    				board[row-1][col+1] == 0 &&
    				board[row-2][col+2] == player &&
    				board[row-3][col+3] == player)
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == 0 &&
    				board[row-1][col+1] == player &&
    				board[row][col] == board[row-2][col+2] &&
    				board[row-1][col+1] == board[row-3][col+3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == player &&
    				board[row-1][col+1] == 0 &&
    				board[row][col] == board[row-2][col+2] &&
    				board[row-1][col+1] == board[row-3][col+3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == 0 &&
    				board[row-1][col+1] == player &&
    				board[row-1][col+1] == board[row-2][col+2] &&
    				board[row][col] == board[row-3][col+3])
    				{
    					val+= 2*twoIn*d;
    				}
    		}
    	}
    	//Check for diagonal spaced 3-in-a-row (\).
    	// 0     x     x     x
    	//  x     0     x     x
    	//   x     x     0     x
    	//    x     x     x     0
    	for (int row=0;row<3;row++)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			if (board[row][col] == player &&
    				board[row][col] == board[row+1][col+1] &&
    				board[row+2][col+2] == 0 &&
    				board[row+3][col+3] == 0)
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == player &&
    				board[row+1][col+1] == 0 &&
    				board[row+2][col+2] == 0 &&
    				board[row][col] == board[row+3][col+3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == 0 &&
    				board[row+1][col+1] == 0 &&
    				board[row+2][col+2] == player &&
    				board[row+3][col+3] == player)
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == 0 &&
    				board[row+1][col+1] == player &&
    				board[row][col] == board[row+2][col+2] &&
    				board[row+1][col+1] == board[row+3][col+3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == player &&
    				board[row+1][col+1] == 0 &&
    				board[row][col] == board[row+2][col+2] &&
    				board[row+1][col+1] == board[row+3][col+3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[row][col] == 0 &&
    				board[row+1][col+1] == player &&
    				board[row+1][col+1] == board[row+2][col+2] &&
    				board[row][col] == board[row+3][col+3])
    				{
    					val+= twoIn*2*d;
    				}
    		}
    	}
		//Check for horizontal 3-in-a-row.
    	for (int row=0;row<6;row++)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			//(xx0x)
    			if (board[row][col] == player &&
    				board[row][col] == board[row][col+1] &&
    				board[row][col+2] == 0 &&
    				board[row][col] == board[row][col+3])
    				{
    					val+= threeIn*h;
    				}
    			//(x0xx)
    			else if (board[row][col] == player &&
    				board[row][col+1] == 0 &&
    				board[row][col] == board[row][col+2] &&
    				board[row][col] == board[row][col+3])
    				{
    					val+= threeIn*h;
    				}
    			//(0xxx)
    			else if (board[row][col] == 0 &&
    				board[row][col+1] == player &&
                    board[row][col+1] == board[row][col+2] &&
                    board[row][col+1] == board[row][col+3])
    				{
    					val+= threeIn*h;
    				}
    			//(xxx0)
    			else if (board[row][col] == player &&
    				board[row][col] == board[row][col+1] &&
    				board[row][col] == board[row][col+2] &&
    				board[row][col+3] == 0)
    				{
    					val+= threeIn*h;
    				}
    		}
    	}

    	//Check for vertical spaced 3-in-a-row.
    	// 0
    	// x
    	// x
    	// x
    	for (int row=5;row>2;row--)
    	{
    		for (int col = 0;col<7;col++)
    		{
    			if (board[row][col] == player &&
    				board[row][col] == board[row-1][col] &&
    				board[row][col] == board[row-2][col] &&
    				board[row-3][col] == 0)
    				{
    					val+= threeIn*v;
    				}
    		}
    	}
    	//Check for diagonal spaced 3-in-a-row (/).
    	//    0     x      x     x
    	//   x     0      x     x
    	//  x     x      0     x
    	// x     x      x     0
    	for (int row=5;row>2;row--)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			if (board[row][col] == player &&
    				board[row][col] == board[row-1][col+1] &&
    				board[row][col] == board[row-2][col+2] &&
    				board[row-3][col+3] == 0)
    				{
    					val+= threeIn*d;
    				}
    			else if (board[row][col] == player &&
    				board[row][col] == board[row-1][col+1] &&
    				board[row-2][col+2] == 0 &&
    				board[row][col] == board[row-3][col+3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[row][col] == player &&
    				board[row-1][col+1] == 0 &&
    				board[row][col] == board[row-2][col+2] &&
    				board[row][col] == board[row-3][col+3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[row][col] == 0 &&
    				board[row-1][col+1] == player &&
    				board[row-1][col+1] == board[row-2][col+2] &&
    				board[row-1][col+1] == board[row-3][col+3])
    				{
    					val+= threeIn*d;
    				}
    		}
    	}
    	//Check for diagonal spaced 3-in-a-row (\).
    	// 0     x     x     x
    	//  x     0     x     x
    	//   x     x     0     x
    	//    x     x     x     0
    	for (int row=0;row<3;row++)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			if (board[row][col] == 0 &&
    				board[row+1][col+1] == player &&
    				board[row+1][col+1] == board[row+2][col+2] &&
    				board[row+1][col+1] == board[row+3][col+3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[row][col] == player &&
    				board[row+1][col+1] == 0 &&
    				board[row][col] == board[row+2][col+2] &&
    				board[row][col] == board[row+3][col+3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[row][col] == player &&
    				board[row][col] == board[row+1][col+1] &&
    				board[row+2][col+2] == 0 &&
    				board[row][col] == board[row+3][col+3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[row][col] == player &&
    				board[row][col] == board[row+1][col+1] &&
    				board[row][col] == board[row+2][col+2] &&
    				board[row+3][col+3] == 0)
    				{
    					val+= threeIn*d;
    				}
    		}
    	}

    	//Check for open-ended 3-in-a-row. (0xxx0)
    	for (int row=0;row<6;row++)
    	{
    		for (int col = 0;col<3;col++)
    		{
    			//horizontal
    			if (board[row][col] == 0 &&
    				board[row][col+1] == player &&
    				board[row][col+2] == player &&
    				board[row][col+3] == player &&
    				board[row][col] == board[row][col+4])
    				{
    					val+= 2*threeIn*h;
    				}
    		}
    	}
    	for (int row=0;row<2;row++)
    	{
    		for (int col = 0;col<3;col++)
    		{
    			//diag(\)
    			if (board[row][col] == 0 &&
    				board[row+1][col+1] == player &&
    				board[row][col] == board[row+2][col+2] &&
    				board[row][col] == board[row+3][col+3] &&
    				board[row+4][col+4] == 0)
    				{
    					val+= 2*threeIn*d;
    				}
    		}
    	}
    	//diag(/)
		for (int row=5;row>3;row--)
    		{
    			for (int col = 0;col<3;col++)
    			{
    				if (board[row][col] == 0 &&
    					board[row-1][col+1] == player &&
    					board[row-2][col+2] == player &&
    					board[row-3][col+3] == player &&
    					board[row-4][col+4] == 0)
    					{
    						val+= 2*threeIn*d;
    					}
    			}
    		}
    	return val;
	}

    public int drop(int col) {
        for (int i = 5; i >= 0; i--) {
            if (grid[i][col] == 0) {
                grid[i][col] = 2;

                // Sai do for
                i = -1;
            }
        }

        if (GameActivity.getInstance().win()) {
            return -1;
        }

        return 3;
    }
}


