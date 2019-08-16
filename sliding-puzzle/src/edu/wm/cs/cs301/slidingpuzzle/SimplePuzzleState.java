package edu.wm.cs.cs301.slidingpuzzle;

import java.util.Arrays;
import java.util.Random;

public class SimplePuzzleState implements PuzzleState {
	
	//board using 2d array 
	private int [][] board;
	//state before the current state
	private PuzzleState parent;
	//operation used to get to current state
	private Operation operation;
	//number of puzzle states to get from initial to current state
	private int pathLength;
	//number of empty space(s) on the board
	private int empty;
	private int dimension;
	
	
	//constructor 
	public SimplePuzzleState() {
		this.board = null;
		this.parent = null;
		this.operation = null;
		this.pathLength = 0;
		this.empty = 0;
		this.dimension = 0;
			
	}
	
	//constructor to store objects (for the move method)
	public SimplePuzzleState(int [][] board, PuzzleState parent, Operation operation, int pathLength) {
		this.board = board;
		this.parent = parent;
		this.operation = operation;
		this.pathLength = pathLength;
		
		
	}
	
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(this.board);
	}
	
	@Override
	public boolean equals(Object obj) {
		//self check
		if (this == obj)
			return true;
		//check for null
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		SimplePuzzleState state = (SimplePuzzleState) obj;
		if (!Arrays.deepEquals(board, state.board))
			return false;
			
		return true;
	}

	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		//must be null because it is the initial state
		this.parent = null;
		this.operation = null;
		
		//rest of the instance variables
		this.board = null;
		//this.pathLength = 0;
	
		// set the instance variable for the dimension of the puzzle to the given parameter value
		this.dimension = dimension;
		// set the instance variable for the number of empty slots to the given parameter value
		this.empty = numberOfEmptySlots;
		
		//create board with given dims, then populate each dim with number
		this.board = new int[dimension][dimension];
		
		
		//number of filled slots
		int limit = (dimension*dimension) - numberOfEmptySlots;
		int i = 1;
		
		
		//loop to populate array
	
			//each row
			for (int r = 0; r < dimension; r++ ) {
				//each column
				for (int c = 0; c < dimension; c++) {
					
					if (i < limit + 1) {
						this.board[r][c] = i;
					}
					
					else {
						this.board[r][c] = 0;
					}
					i++;
						
					}
					
				}
			}
			

	@Override
	public int getValue(int row, int column) {
			return this.board[row][column];
		
	}
	

	@Override
	public PuzzleState getParent() {
		return this.parent;
	}


	@Override
	public Operation getOperation() {
		return this.operation;
	}

	
	
	
	@Override
	public int getPathLength() {
		return this.pathLength;
	}

	
	
	
	@Override
	public PuzzleState move(int row, int column, Operation op) {
		System.out.println(getValue(row, column));
		
		// can't move if its on an empty space to begin with
		if (getValue(row, column) == 0) {
			return null; 
		}
		
		//create new instance 
		SimplePuzzleState newState = new SimplePuzzleState();


		//create new board and copy over the data, newState now inherits properties from SimplePuzzleState
		newState.dimension = dimension;
		newState.board = new int[dimension][dimension];
		//populate new board with values
		//for each row
		for (int r = 0; r < dimension; r++ ) {
			//for each column
			for (int c = 0; c < dimension; c++) {
				newState.board[r][c] = this.board[r][c];
			}
		}
		
		
			if (op == Operation.MOVERIGHT) {
				if (isEmpty(row, column+1) == true) {
					//store operation
					newState.operation = op;
					//store parent
					newState.parent = this;
				
					//updates board after the move
					int moveValue = newState.getValue(row, column);
					newState.board[row][column] = 0;
					newState.board[row][column+1] = moveValue;
					newState.pathLength = newState.pathLength + 1;
					return newState;
				}
					
				else {
						return null;
					}
					
				}
				
			
			else if (op == Operation.MOVELEFT) {
				if (isEmpty(row, column-1 ) == true) {
					//store operation
					newState.operation = op;
					//store parent
					newState.parent = this;
				
					//updates board after the move
					int moveValue = newState.getValue(row, column);
					newState.board[row][column] = 0;
					newState.board[row][column-1 ] = moveValue;
					newState.pathLength = newState.pathLength + 1;
					return newState;
				}
					
				else {
						return null;
					}
					
				
			
			}
			
			else if (op == Operation.MOVEUP) {
			
				if (isEmpty(row -1, column) == true) {
					//store operation
					newState.operation = op;
					//store parent
					newState.parent = this;
				
					//updates board after the move
					int moveValue = newState.getValue(row, column);
					newState.board[row][column] = 0;
					newState.board[row-1][column] = moveValue;
					newState.pathLength = newState.pathLength + 1;
					return newState;
					
					
				}
			
				else {
						return null;
			
					}
			}
			else if (op == Operation.MOVEDOWN) {
		
				if (isEmpty(row+1 , column) == true) {
					//store operation
					newState.operation = op;
					//store parent
					newState.parent = this;
				
					//updates board after the move
					int moveValue = newState.getValue(row, column);
					newState.board[row][column] = 0;
					newState.board[row+1][column] = moveValue;
					newState.pathLength = newState.pathLength + 1;
					return newState;
					
				}
			
				else {
						return null;
			}
	}
		return null;
	}

	
	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		PuzzleState resultingState = new SimplePuzzleState();
		resultingState = this;
		
		int startingRow;
		startingRow = startRow;
		int startingColumn;
		startingColumn = startColumn;
		
		
		int vertically;
		vertically = startingRow - endRow;
		
		int horizontally;
		horizontally = startingColumn - endColumn;
			
		if (vertically != 0 || horizontally != 0) {
			
			
	
			if ((isEmpty(startingRow - 1, startingColumn) == true) && (vertically > 0)){
						resultingState = resultingState.move(startingRow, startingColumn, Operation.MOVEUP);
						vertically -= 1;
						startingRow -= 1;
						return resultingState.drag(startingRow, startingColumn, endRow, endColumn);	
			}
						
	
		
			if ((isEmpty(startingRow + 1, startingColumn) == true)  && (vertically < 0)){
					
						resultingState = resultingState.move(startingRow, startingColumn, Operation.MOVEDOWN);
						vertically += 1;
						startingRow += 1;
						return resultingState.drag(startingRow, startingColumn, endRow, endColumn);
		
					}
				
	
			
				
			if ((isEmpty(startingRow, startingColumn - 1)== true)&& (horizontally > 0)){
				
						resultingState = resultingState.move(startingRow, startingColumn, Operation.MOVELEFT);
						horizontally -= 1;
						startingColumn -= 1;
						return resultingState.drag(startingRow, startingColumn, endRow, endColumn);
					}
					
	
			
				
			if ((isEmpty(startingRow, startingColumn + 1)== true)&&(horizontally < 0)){
					
						resultingState = resultingState.move(startingRow, startingColumn, Operation.MOVERIGHT);
						horizontally += 1;
						startingColumn += 1;
						return resultingState.drag(startingRow, startingColumn, endRow, endColumn);
					}
		}
		
		return this;
		
		
		
		}
		
	
	
	
	
	
	
	
	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		return null;
	}
	
	
	
	@Override
	public boolean isEmpty(int row, int column) {
		
		
		int dimensions;
		dimensions = row*column;
		
		if (row < board.length && row >= 0 && column < board.length && column >= 0 && dimensions >= 0) {
		

		
			if (this.board[row][column] == 0) {
				return true;
				
			}
			
		}
		
		else {
			return false;
		}
		return false;
		
	}
		
	
	@Override
	public PuzzleState getStateWithShortestPath() {
		return this;
	}


	
}
