package EnginePackage;

import java.util.*;

public class Engine{

	private Candies[][] Table;
	private int movesLeft;
	private Candies[] possibleCandy;
	private int numOfPink, numOfBlue, numOfGreen, numOfYellow;

	//potrebni geteri
	public int getMovesLeft() { return movesLeft; }
	public int getPink() { return numOfPink; }
	public int getBLue() { return numOfBlue; }
	public int getGreen() { return numOfGreen; }
	public int getYellow() { return numOfYellow; } 
	public Candies getTableOfIndex(int i, int j) { return Table[i][j]; }
	public int getDimension() { return Table.length; }

	public Engine() 
	{
		Table = new Candies[8][8];
		movesLeft = 15;
		
		possibleCandy = new Candies[Candies.values().length - 1];
		possibleCandy[0] = Candies.pink;
		possibleCandy[1] = Candies.blue;
		possibleCandy[2] = Candies.yellow;
		possibleCandy[3] = Candies.green;
		possibleCandy[4] = Candies.orange;
		
		numOfPink = numOfBlue = numOfYellow = numOfGreen = 20;
		
		setTable();
	}
	
	private void initializeTable()
	{
		for(int i = 0; i<Table.length; i++)
			for(int j = 0; j<Table.length; j++)
				Table[i][j] = null;
	}

	private void setTable()
	{
		initializeTable();
		for(int i = 0; i<Table.length; i++)
			for(int j = 0; j<Table.length; j++)
				randWithNoMatch(i, j);
	}
	
	private boolean checkRow(int i)
    {
    	Candies candy = Table[i][0];
    	int numOfReps = 1;
    	for(int j = 1; j<Table.length; j++)
    	{
    		if(candy == null || candy == Candies.x)
    			{ return true; }
    		else if(Table[i][j] == candy)
    		{
    			numOfReps++;
    			if(numOfReps == 3)
    				{ return false; }
    		}
    		else
    			{ numOfReps = 1; }
    		candy = Table[i][j];
    	}
    	return true;
    }
	
	public boolean checkColumn(int j)
	{
		Candies candy = Table[0][j];
    	int numOfReps = 1;
		for(int i=1; i<Table.length; i++)
		{
			if(candy == null || candy == Candies.x)
				{ return true; }
			else if(Table[i][j] == candy)
			{
				numOfReps++;
				if(numOfReps == 3)
					{ return false; }
			}
			else
				{ numOfReps = 1; }
    		candy = Table[i][j];
		}
		return true;
	}
	
	private void randWithNoMatch(int i,int j)
	{
		Random r = new Random();
		int maxIndex = possibleCandy.length;
		Table[i][j] = possibleCandy[r.nextInt(maxIndex)];
		while(checkRow(i) == false || checkColumn(j) == false)
			Table[i][j] = possibleCandy[r.nextInt(maxIndex)];
	}
	
	private void updateScore(Candies candy, int num)
	{
		if(candy == Candies.pink)
			if(numOfPink - num < 0) { numOfPink = 0; }
			else { numOfPink -= num; }
		else if(candy == Candies.blue)
			if(numOfBlue - num < 0) { numOfBlue = 0; }
			else { numOfBlue -= num; }
		else if(candy == Candies.yellow)
			if(numOfYellow - num < 0) { numOfYellow = 0; }
			else { numOfYellow -= num; }
		else if(candy == Candies.green)
			if(numOfGreen - num < 0) { numOfGreen = 0; }
			else { numOfGreen -= num; }

	}
	
	private void switchPlaces(int x, int y, int x1, int y1)
	{
		Candies tmp = Table[x][y];
		Table[x][y] = Table[x1][y1];
		Table[x1][y1] = tmp;
	}

	public StateOfGame CurrentStateOfGame()
	{
		if(numOfPink == 0 && numOfBlue == 0 && numOfYellow == 0  && numOfGreen == 0 && movesLeft >= 0)
			return  StateOfGame.victory; 
		if(movesLeft == 0)
			return StateOfGame.gameOver;
		return StateOfGame.active;
	}

	public boolean play(int x, int y, int x1, int y1)
	{
		if ( (x == x1 && Math.abs(y - y1) == 1) || (Math.abs(x - x1) == 1) && y == y1)
		{
			if(movesLeft > 0)
			{
				switchPlaces(x, y, x1, y1);
				boolean match1 = match(x, y);
				boolean match2 = match(x1, y1);
				
				if(match1== true || match2 == true)
				{
					--movesLeft;
					return true;
				}
				else
				{
					switchPlaces(x, y, x1, y1);
					return false;
				}
			}
			return false;	
		}
		return false;
	}
	
	public boolean fullTable()
	{
		for(int i = 0; i < Table.length; i++)
			for(int j = 0; j < Table.length; j++)
				if(Table[i][j] == Candies.x || Table[i][j] == null)
					return false;
		return true;
	}
	
	private boolean match(int crI, int crJ)
	{
		
		int horizontal = 0;
		int vertical = 0;
		boolean [] arrayOfHorizontalMatches = new boolean[Table.length];
		boolean [] arrayOfVerticalMatches = new boolean[Table.length];
		for(int i=0; i<Table.length; i++)
			arrayOfHorizontalMatches[i] = arrayOfVerticalMatches[i] = false;
	
		boolean flag = false;
		Candies candy = Table[crI][crJ];
		
		//spoj od 3 ili vise u redu
		int i = crI;
		int j = crJ;
		while(j<Table.length && Table[i][j] == candy)
		{
			arrayOfHorizontalMatches[j] = true;
			horizontal++;
			j++;
		}
		
		i = crI;
		j = crJ-1;
		while(j>=0 && Table[i][j] == candy)
		{
			arrayOfHorizontalMatches[j] = true;
			horizontal++;
			j--;
		}
		
		//spoj od tri ili vise u koloni
		i = crI;
		j = crJ;
		while(i<Table.length && Table[i][j] == candy)
		{
			arrayOfVerticalMatches[i] = true;
			i++;
			vertical++;
		}
		
		i = crI-1;
		j = crJ;
		while(i>=0 && Table[i][j]== candy)
		{
			arrayOfVerticalMatches[i] = true;
			i--;
			vertical++;
		}
		
		if(horizontal >= 3)
		{
			flag = true;
			updateScore(candy, horizontal);
			for(int k = 0; k < arrayOfHorizontalMatches.length; k++)
				if(arrayOfHorizontalMatches[k] == true)
					Table[crI][k] = Candies.x;
		}
		
		if(vertical >= 3)
		{
			flag = true;
			updateScore(candy, vertical);
			for(int k = 0; k < arrayOfVerticalMatches.length; k++)
			{
				if(arrayOfVerticalMatches[k] == true)
					Table[k][crJ] = Candies.x;
			}
		}
		return flag;
	}
	
	public boolean newMatch()
	{
		for(int i = 0; i<Table.length; i++)
			for(int j = 0; j<Table.length; j++)
				if(Table[i][j] != Candies.x)
					if(match(i, j) == true)
						return true;
		return false;
	}
	
	public void dropCandies() {
	    for (int j = 0; j < Table.length; j++) 
	    { 
	        for (int i = Table.length - 1; i > 0; i--) 
	        { 
	            if (Table[i][j] == Candies.x) 
	            {
	                int k = i - 1;
	                while (k >= 0 && Table[k][j] == Candies.x)
	                	k--;
	                
	                if (k >= 0) 
	                    switchPlaces(j, k, i, j); 
	                else 
	                    break;
	              
	            }
	        }
	    }
	}

	public void printTable()
	{
		String candy;
		for(int i = 0; i<Table.length; i++)
		{
			for(int j = 0; j<Table.length; j++)
			{
				if(Table[i][j] == Candies.pink) candy = " pi ";
				else if(Table[i][j] == Candies.blue) candy = " bl ";
				else if(Table[i][j] == Candies.green) candy = " gr ";
				else if(Table[i][j] == Candies.yellow) candy = " ye ";
				else candy = " xx ";
				System.out.print(candy);
			}
				
				System.out.println(" ");		
		}
		System.out.println("________________________________________");
	}

	public void addNewCandies()
	{
		Random r = new Random();
		for(int i=0; i<Table.length; i++)
			for(int j=0; j<Table.length; j++)
				if(Table[i][j] == Candies.x)
					Table[i][j] = possibleCandy[r.nextInt(possibleCandy.length)];
				
	}
}
