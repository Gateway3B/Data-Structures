import java.util.Arrays;
import java.util.LinkedList;

/*
 * Describe your matrix implementation.  Why did you choose the implementation you did?  What is the computational complexity of the operations in your matrix implementation?
 * 
 * Commentary(Matthew Weisfeld)
 * 
 * The SparseMatrix is implemented with a linked list that uses MatrixElement for its nodes and a separate integer size. This element has integer values for row, column, and data. Adding, getting, and removing elements from the matrix is done by manipulating the values in the nodes of the linked list. The values in the list are never sorted in any order and stay how they came in unless removed. The determinate function works just as described in the formula with j chosen to be 0. Minor works creating a new SparseMatrix with a size one smaller than the previous SparseMatrixand then populating its linked list with the nodes from the original linked list after some modifications. The nodes with row or col equal to the inputed row and col values are not added. Also the row and col values of those nodes whose values are higher than the inputs are respectively lowered. The toString function works by first translating the row and col values of all of the nodes in the linked list into a distance value from  0,0 reading from left to right up to down. These values are then put in a 1d matrix and sorted using the built in java sort function. Then the value of the integers in the 1d array are matched to the nodes in the linked list that have the same distance value. When found each one prints adds their relevant information to the string.
 * 
 * I chose this implementation because I saw it as the easiest to get working. Keeping the linked list unordered reduces the time complexity when working with it and reduces the work I have to do. Making the MatrixElement store the integer values makes it easy to manipulate them. This allows me to make minor a simple function with just 4 if statements for the 4 quadrants created by the 2d axis created by the inputed row and col inside a for loop. Det works the only way it can according to the determinant formula with the recursive and base cases.
 * 
 * Computational Complexity-
 * setSize: O(1);
 * addElement: O(n^3); matrix.get() is O(n) and matrix.get() is O(n) and the for loop is O(n)
 * removeElement: O(n^3); matrix.remove() is O(n) and matrix.get() is O(n) and the for loop is O(n)
 * getElement: O(n^3); matrix.get() is O(n) and matrix.get() is O(n) and the for loop is O(n)
 * minor: O(n^3); matrix.get() is O(n) and matrix.get() is O(n) and the for loop is O(n)
 * det: O(n!); Laplase
 * getString: O(n^3); matrix.get() is O(n) and matrix.get() is O(n) and matrix.get() is O(n) and the for loop is O(n)
 */

public class SparseMatrix implements SparseInterface{
	
	//Defines the node of the linked list with the variables row, col, and data.
	class MatrixElement {
		
		int row;
		int col;
		int data;
		
		public MatrixElement(int row, int col, int data)
		{
			this.row = row;
			this.col = col;
			this.data = data;
		}
	}
	
	//Tracks the size of the matrix.
	int size;
	//Creates the matrix.
	LinkedList<MatrixElement> matrix = new LinkedList<MatrixElement>();
	
	//Instantiates the matrix as size 5.
	public SparseMatrix()
	{
		this.size = 5;
	}
	
	//Deletes all values in the matrix, setting everything to 0.
	public void clear()
	{
		matrix.clear();
	}
	
	//Clears the matrix then sets a new size by modifying the variable size.
	public void setSize(int size)
	{
		//Throws error if the size is less than  1.
		if(size < 1)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		matrix.clear();
		this.size = size;
	}

	//Adds an element or replaces an element in the matrix.
	public void addElement(int row, int col, int data)
	{
		//Throws error if the inputs row and col are out of bounds of the size of the matrix.
		//Throws error if data == 0
		if(row >= this.size || col >= this.size || row < 0 || col < 0)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		int i;
		//Traverses the elements in the linked list and if it finds an element with the same row and col values it overrides that elements data.
		for(i = 0; i < matrix.size(); ++i)
		{
			if(matrix.get(i).row == row && matrix.get(i).col == col)
			{
				if(data == 0)
				{
					this.removeElement(row, col);
					return;
				}
				matrix.get(i).data = data;
				return;
			}
		}
		//If an already existing element is not found with the input row and col value a new element is created.
		if(data != 0)
		{
			matrix.add(0, new MatrixElement(row, col, data));
		}
	}
	
	//Removes an element from the linked list.
	public void removeElement(int row, int col)
	{
		//Throws error if the inputs row and col are out of bounds of the size of the matrix.
		if(row >= this.size || col >= this.size || row < 0 || col < 0)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		//Traverses the linked list till it finds an element with the same row and col value as the inputs then removes it.
		for(int i = 0; i<this.size; ++i)
		{
			if(matrix.get(i).row == row && matrix.get(i).col == col)
			{
				matrix.remove(i);
				break;
			}
		}
	}

	//Returns the value of the element at the specified row and col.
	public int getElement(int row, int col)
	{
		//Throws error if the inputs row and col are out of bounds of the size of the matrix.
		if(row >= this.size || col >= this.size || row < 0 || col < 0)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		//Traverses the linked list till it finds an element with the row and col inputed then returns its data.
		for(int i = 0; i<this.matrix.size(); ++i)
		{
			if(matrix.get(i).row == row && matrix.get(i).col == col)
			{
				return matrix.get(i).data;
			}
		}
		return 0;
	}
	
	//Returns a new matrix that deletes the elements on the specified row and col and reduces the size of the matrix by 1.
	public SparseMatrix minor(int row, int col)
	{
		//Throws error if the inputs row and col are out of bounds of the size of the matrix.
		if(row >= this.size || col >= this.size || row < 0 || col < 0)
		{
			throw new ArrayIndexOutOfBoundsException();
		}
		//Creates a new SparseMatrix.
		SparseMatrix minor = new SparseMatrix();
		minor.setSize(this.getSize()-1);
		//Traverses the linked list and fills the linked list of minor.
		for(int i = 0; i < this.matrix.size(); ++i)
		{
			//Adds elements that have row and col less than input row and col.
			if(row > matrix.get(i).row && col > matrix.get(i).col)
			{
				minor.addElement(matrix.get(i).row, matrix.get(i).col, matrix.get(i).data);
			}
			//Adds elements with row greater than input row and col less than input col and updates the row to be one less because a row has been removed.
			else if(row < matrix.get(i).row && col > matrix.get(i).col)
			{
				minor.addElement(matrix.get(i).row-1, matrix.get(i).col, matrix.get(i).data);
			}
			//Adds elements with row less than input row and col greater than input col and updates the col to be one less because a row has been removed.
			else if(col < matrix.get(i).col && row > matrix.get(i).row)
			{
				minor.addElement(matrix.get(i).row, matrix.get(i).col-1, matrix.get(i).data);
			}
			//Adds elements with row greater than input row and col greater than input col and updates the row and col to be one less because a row and col have been removed.
			else if(col < matrix.get(i).col && row < matrix.get(i).row)
			{
				minor.addElement(matrix.get(i).row-1, matrix.get(i).col-1, matrix.get(i).data);
			}
		}
		return minor;
	}
	
	//Calculates the determinate of the SparseMatrix.
	public int determinant()
	{
		//Edge case when the size of the matrix is 1x1. Will only happen if the matrix is defined as 1 before det is called.
		if(this.size == 1)
		{
			return this.getElement(0,0);
		}
		//Base case when the size of the matrix is 2x2.
		if(this.size == 2)
		{
			return (this.getElement(0, 0) * this.getElement(1, 1)) - (this.getElement(0, 1) * this.getElement(1,  0));
		}
		int det = 0;
		//Calculates det by recursively calling minor according to the determinate algorithm. 
		for(int col = 0; col < this.size; ++col)
		{
			det += this.minor(0, col).determinant() * this.getElement(0, col) * ((int)java.lang.Math.pow(-1,col));
		}
		return det;
	}
	
	//Returns a string containing the lined list values.
	public String toString()
	{
		String str = "";
		int[] values = new int[this.matrix.size()];
		
		//Copies the values of the distance from 0 traversing from left to right up to down of all the elements in the linked list to a 1d array.
		for(int i = 0; i < this.matrix.size(); ++i)
		{
			values[i] = this.matrix.get(i).row * this.getSize() + this.matrix.get(i).col;
		}
		
		//Sorts the 1d array.
		Arrays.sort(values);
		
		//Traverses the sorted values of the 1d array and finds the element in the linked list with the corresponding value/location.
		for(int i = 0; i < values.length; ++i)
		{
			for(int j = 0; j < this.matrix.size(); ++j)
			{
				if(values[i] == this.matrix.get(j).row * this.getSize() + this.matrix.get(j).col)
				{
					str += this.matrix.get(j).row + " " + this.matrix.get(j).col + " " + this.matrix.get(j).data + "\n";
				}
			}
		}
		return str;
	}
	
	//Returns the size of the SparseMatrix
	public int getSize()
	{
		return this.size;
	}

}
