package puc.connectfour;

//Cria um objeto minimax a partir do no raiz
public class Minimax
{
	private int[][] board = new int[7][6];
	private int maxDepth;

	// Copia a board recebida
	// Se maxDepth é maior, jogo é mais difícil
	public Minimax(int[][] game,int depth)
	{
    	board = copyGrid(game);
    	maxDepth = depth;
	}

	// Funcao para copiar a board
	private int[][] copyGrid(int[][]copy)
	{
		int[][] newgrid = new int [7][6];
		for (int row =0; row<6; row++)
    	{
    		for (int col=0; col<7; col++)
    		{
    			newgrid[col][row]=copy[col][row];
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
    			if (board[col][row]==1)
    			{
    				count++;
    			}
    		}
    	}
		if (count ==1)
		{
			if (board[2][5]==1)
			{
				return 4;
			}
			else if (board[4][5]==1)
			{
				return 2;
			}
			else
			{
				return 3;
			}
		}


		// If not first turn, return max
		return max(board,-1000000,0,1);
	}

	//Recursively calls itself and returns the best col that computer should choose.
	private int max(int[][] game, int alpha, int depth, int color)
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
		ConnectFourModel cfm = new ConnectFourModel();
		cfm.setGrid(newGrid,player);

		//Determine if game is over in current state;
		// return 1000000 if comp win; else return -1000000
		if (cfm.win())
		{
			if (cfm.getPlayer() == 1)
			{bestValue = color*(1000000-depth);}
			else
			{bestValue = color*(-1000000+depth);}
		}
		//Determine if game is a draw

		else if(cfm.full()==true&&cfm.win()==false)
		{
			bestValue = 0;
		}
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

				//Create a cfm for this column and attempt to drop.
				ConnectFourModel newcfm = new ConnectFourModel();
				newcfm.setGrid(newGrid,player);
				int r = newcfm.drop(c);

				//Recursive call the generated game board and compare the current value to move value
				// If move is higher, make it the new current value.

				//If drop is successful
				if (r!=-1)
				{
					int[][] nGrid = new int[7][6];

					// Only if depth is < maxDepth recursive call
					if (depth < maxDepth)
					{
					nGrid = copyGrid(newGrid);
					nGrid[c][r] = player;
					int v = -max(nGrid,-1000000,depth+1,color*-1);
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
			return bestPath;
		}
		else
		{
			return bestValue;
		}

	}

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
    			if (board[col][row] == player &&
    				board[col][row] == board[col+1][row] &&
    				board[col+2][row] == 0 &&
    				board[col+3][row] == 0)
    				{
    					val+= twoIn*h;
    				}
    			//(x0x0)
    			else if (board[col][row] == player &&
    				board[col+2][row] == player &&
    				board[col+1][row] == 0 &&
    				board[col+3][row] == 0)
    				{
    					val+= twoIn*h;
    				}
    			//(x00x)
    			else if (board[col][row] == player &&
    				board[col+3][row] == player &&
    				board[col+1][row] == 0 &&
    				board[col+2][row] == 0)
    				{
    					val+= twoIn*h;
    				}
    			//(0xx0)
    			else if (board[col][row] == 0 &&
    				board[col+1][row] == player &&
    				board[col+2][row] == player &&
    				board[col+3][row] == 0)
    				{
    					val+= 2*twoIn*h;
    				}
    			//(0x0x)
    			else if (board[col][row] == 0 &&
    				board[col+1][row] == player &&
    				board[col+2][row] == 0 &&
    				board[col+3][row] == player)
    				{
    					val+= twoIn*h;
    				}
    			//(00xx)
    			else if (board[col][row] == 0 &&
    				board[col][row] == board[col+1][row] &&
    				board[col+2][row] == player &&
    				board[col+3][row] == player)
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
    			if (board[col][row] == player &&
    				board[col][row] == board[col][row-1] &&
    				board[col][row-2] == 0)
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
    			if (board[col][row] == player &&
    				board[col][row] == board[col+1][row-1] &&
    				board[col+2][row-2] == 0 &&
    				board[col+3][row-3] == 0)
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == player &&
    				board[col+1][row-1] == 0 &&
    				board[col+2][row-2] == 0 &&
    				board[col][row] == board[col+3][row-3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == 0 &&
    				board[col+1][row-1] == 0 &&
    				board[col+2][row-2] == player &&
    				board[col+3][row-3] == player)
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == 0 &&
    				board[col+1][row-1] == player &&
    				board[col][row] == board[col+2][row-2] &&
    				board[col+1][row-1] == board[col+3][row-3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == player &&
    				board[col+1][row-1] == 0 &&
    				board[col][row] == board[col+2][row-2] &&
    				board[col+1][row-1] == board[col+3][row-3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == 0 &&
    				board[col+1][row-1] == player &&
    				board[col+1][row-1] == board[col+2][row-2] &&
    				board[col][row] == board[col+3][row-3])
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
    			if (board[col][row] == player &&
    				board[col][row] == board[col+1][row+1] &&
    				board[col+2][row+2] == 0 &&
    				board[col+3][row+3] == 0)
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == player &&
    				board[col+1][row+1] == 0 &&
    				board[col+2][row+2] == 0 &&
    				board[col][row] == board[col+3][row+3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == 0 &&
    				board[col+1][row+1] == 0 &&
    				board[col+2][row+2] == player &&
    				board[col+3][row+3] == player)
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == 0 &&
    				board[col+1][row+1] == player &&
    				board[col][row] == board[col+2][row+2] &&
    				board[col+1][row+1] == board[col+3][row+3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == player &&
    				board[col+1][row+1] == 0 &&
    				board[col][row] == board[col+2][row+2] &&
    				board[col+1][row+1] == board[col+3][row+3])
    				{
    					val+= twoIn*d;
    				}
    			else if (board[col][row] == 0 &&
    				board[col+1][row+1] == player &&
    				board[col+1][row+1] == board[col+2][row+2] &&
    				board[col][row] == board[col+3][row+3])
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
    			if (board[col][row] == player &&
    				board[col][row] == board[col+1][row] &&
    				board[col+2][row] == 0 &&
    				board[col][row] == board[col+3][row])
    				{
    					val+= threeIn*h;
    				}
    			//(x0xx)
    			else if (board[col][row] == player &&
    				board[col+1][row] == 0 &&
    				board[col][row] == board[col+2][row] &&
    				board[col][row] == board[col+3][row])
    				{
    					val+= threeIn*h;
    				}
    			//(0xxx)
    			else if (board[col][row] == 0 &&
    				board[col+1][row] == player &&
    				board[col+1][row] == board[col+2][row] &&
    				board[col+1][row] == board[col+3][row])
    				{
    					val+= threeIn*h;
    				}
    			//(xxx0)
    			else if (board[col][row] == player &&
    				board[col][row] == board[col+1][row] &&
    				board[col][row] == board[col+2][row] &&
    				board[col+3][row] == 0)
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
    			if (board[col][row] == player &&
    				board[col][row] == board[col][row-1] &&
    				board[col][row] == board[col][row-2] &&
    				board[col][row-3] == 0)
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
    			if (board[col][row] == player &&
    				board[col][row] == board[col+1][row-1] &&
    				board[col][row] == board[col+2][row-2] &&
    				board[col+3][row-3] == 0)
    				{
    					val+= threeIn*d;
    				}
    			else if (board[col][row] == player &&
    				board[col][row] == board[col+1][row-1] &&
    				board[col+2][row-2] == 0 &&
    				board[col][row] == board[col+3][row-3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[col][row] == player &&
    				board[col+1][row-1] == 0 &&
    				board[col][row] == board[col+2][row-2] &&
    				board[col][row] == board[col+3][row-3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[col][row] == 0 &&
    				board[col+1][row-1] == player &&
    				board[col+1][row-1] == board[col+2][row-2] &&
    				board[col+1][row-1] == board[col+3][row-3])
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
    			if (board[col][row] == 0 &&
    				board[col+1][row+1] == player &&
    				board[col+1][row+1] == board[col+2][row+2] &&
    				board[col+1][row+1] == board[col+3][row+3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[col][row] == player &&
    				board[col+1][row+1] == 0 &&
    				board[col][row] == board[col+2][row+2] &&
    				board[col][row] == board[col+3][row+3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[col][row] == player &&
    				board[col][row] == board[col+1][row+1] &&
    				board[col+2][row+2] == 0 &&
    				board[col][row] == board[col+3][row+3])
    				{
    					val+= threeIn*d;
    				}
    			else if (board[col][row] == player &&
    				board[col][row] == board[col+1][row+1] &&
    				board[col][row] == board[col+2][row+2] &&
    				board[col+3][row+3] == 0)
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
    			if (board[col][row] == 0 &&
    				board[col+1][row] == player &&
    				board[col+2][row] == player &&
    				board[col+3][row] == player &&
    				board[col][row] == board[col+4][row])
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
    			if (board[col][row] == 0 &&
    				board[col+1][row+1] == player &&
    				board[col][row] == board[col+2][row+2] &&
    				board[col][row] == board[col+3][row+3] &&
    				board[col+4][row+4] == 0)
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
    				if (board[col][row] == 0 &&
    					board[col+1][row-1] == player &&
    					board[col+2][row-2] == player &&
    					board[col+3][row-3] == player &&
    					board[col+4][row-4] == 0)
    					{
    						val+= 2*threeIn*d;
    					}
    			}
    		}
    	return val;
	}

}


