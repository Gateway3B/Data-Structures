import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Sorts {

	//pdf for report outside of zip
	//firstname_lastname.zip
	//Array used in the quick sort because the sort does not return the array recursively I need an outside array to use.
	int[] quickArray;
	//Array used to hold the average run values of the sorts on different lists.
	long[] average = new long[24];
	
	//Creates random, forward, and backward lists for the input length.
	public void makeLists(int length)
	{
		File file1 = new File(length + "Random" + ".txt");
		File file2 = new File(length + "Forward" + ".txt");
		File file3 = new File(length + "Backward" + ".txt");
		try {
			PrintWriter write = new PrintWriter(file1);
			Random rand = new Random();
			for(int i = 0; i < length; ++i)
			{
				write.println(rand.nextInt(length));
			}
			write.flush();
			write.close();
			
			FileReader read = new FileReader(length + "Random" + ".txt");
			BufferedReader bufferedReader = new BufferedReader(read);
			int[] sorted = new int[length];
			int index = 0;
			String line;
			while((line = bufferedReader.readLine()) != null)
			{
				sorted[index] = Integer.parseInt(line);
				++index;
			}
			sorted = merge(sorted);
			
			write = new PrintWriter(file2);
			for(int i = 0; i < length; ++i)
			{
				write.println(sorted[i]);
			}
			write.flush();
			write.close();
			
			write = new PrintWriter(file3);
			for(int i = length-1; i >=0; --i)
			{
				write.println(sorted[i]);
			}
			bufferedReader.close();
			write.flush();
			write.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("FileCreationError");
		}
		catch(IOException ex) {
            System.out.println("Error reading file");
        }
	}
	
	//Converts a file with integers to an array.
	public int[] fileToArray(String fileName, int size)
	{
		int[] array = new int[size];
		try {
		FileReader read = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(read);
		String line;
		int index = 0;
		while((line = bufferedReader.readLine()) != null)
		{
			array[index] = Integer.parseInt(line);
			++index;
		}
		bufferedReader.close();
		return array;
		}
		catch(IOException ex) {
            //System.out.println("Error reading file");
        }
		return array;
	}
	
	//Implementation of a merge sort.
	public int[] merge(int[] array)
	{
		//Base case where the list is 1 long.
		if(array.length < 2)
		{
			return array;
		}
		
		//Splits the list into two parts.
		int sizeLow = (int)array.length/2;
		int sizeUp = (int)Math.ceil(array.length/(double)2);
		
		int[] lower = new int[sizeLow];
		int[] upper = new int[sizeUp];
		
		for(int i = 0; i < sizeLow; ++i)
		{
			lower[i] = array[i];
		}
		for(int i = sizeLow; i < array.length ; ++i)
		{
			upper[i-sizeLow] = array[i];
		}
		
		//Recursively calls merge on the two parts.
		lower = merge(lower);
		upper = merge(upper);
		
		//Merges the two sorted lists
		int[] merged = new int[array.length];
		int upIndex = 0;
		int lowIndex = 0;
		for(int i = 0; i < array.length; ++i)
		{
			if(sizeLow == lowIndex)
			{
				for(int j = upIndex; j < sizeUp; ++j, ++i)
				{
					merged[i] = upper[j];
				}
				break;
			}
			if(sizeUp == upIndex)
			{
				for(int j = lowIndex; j < sizeLow; ++j, ++i)
				{
					merged[i] = lower[j];
				}
				break;
			}
			if(upper[upIndex] >= lower[lowIndex])
			{
				merged[i] = lower[lowIndex];
				++lowIndex;
			}else{
				merged[i] = upper[upIndex];
				++upIndex;
			}
		}
	return merged;
	}
	
	//Partitions a segment from i to k of the array quickArray into values greater and less than a pivot.
	public int partition(int i, int k)
	{
		int mid = i + ((k-i)/2);
		
		int pivot = quickArray[mid];
		
		int l = i;
		int h = k;
		
		while(true)
		{
			while(quickArray[l] < pivot)
			{
				++l;
			}
			while(quickArray[h] > pivot)
			{
				--h;
			}
			if(l >= h)
			{
				break;
			} else {
				int temp = quickArray[l];
				quickArray[l] = quickArray[h];
				quickArray[h] = temp;
				--h;
				++l;
			}
		}
		return h;
	}
	
	//Implementation of the quick sort.
	public void quick(int i, int k)
	{
		int j = 0;
		
		if(i >= k)
		{
			return;
		}
		
		j = partition(i, k);
		
		quick(i, j);
		quick(j+1, k);
		
		return;
	}
	
	//Helper method that calls the quick sort. This is used because quick doesn't return anything and the array used is global.
	public int[] quickHelper(int[] array)
	{
		quickArray = array;
		quick(0, array.length-1);
		return quickArray;
	}
	
	//Runs quick and merge sort for the inputed file and displays their run time in the console.
	public long runner(String run, int size, int sort)
	{
		if(sort == 0)
		{
		//Merge
		long t = System.nanoTime();
		merge(fileToArray(run, size));
		long s = System.nanoTime();
		t = s-t;
		return t;
		}else {
		//Quick
		long t = System.nanoTime();
		quickHelper(fileToArray(run, size));
		long s = System.nanoTime();
		t = s-t;
		return t;
		}
	}
	
	//Calculates the average run time for the sorting methods on the list after an inputed amount of runs.
	public void averageCalc(int runs)
	{
		makeLists(5000);
		makeLists(10000);
		makeLists(20000);
		makeLists(50000);

		for(int i = 0; i < runs; ++i)
		{
			//5000 lists.
			
			average[0] += runner("5000Random", 5000, 0);
			average[1] += runner("5000Forward", 5000, 0);
			average[2] += runner("5000Backward", 5000, 0);
			
			average[3] += runner("10000Random", 10000, 0);
			average[4] += runner("10000Forward", 10000, 0);
			average[5] += runner("10000Backward", 10000, 0);
			
			average[6] += runner("20000Random", 20000, 0);
			average[7] += runner("20000Forward", 20000, 0);
			average[8] += runner("20000Backward", 20000, 0);
			
			average[9] += runner("50000Random", 50000, 0);
			average[10] += runner("50000Forward", 50000, 0);
			average[11] += runner("50000Backward", 50000, 0);
			
			average[12] += runner("5000Random", 5000, 1);
			average[13] += runner("5000Forward", 5000, 1);
			average[14] += runner("5000Backward", 5000, 1);
			
			average[15] += runner("10000Random", 10000, 1);
			average[16] += runner("10000Forward", 10000, 1);
			average[17] += runner("10000Backward", 10000, 1);
			
			average[18] += runner("20000Random", 20000, 1);
			average[19] += runner("20000Forward", 20000, 1);
			average[20] += runner("20000Backward", 20000, 1);
			
			average[21] += runner("50000Random", 50000, 1);
			average[22] += runner("50000Forward", 50000, 1);
			average[23] += runner("50000Backward", 50000, 1);
		}
		
		for(int i = 0; i < 24; ++i)
		{
			average[i] /= runs;
		}
		System.out.println("\tR5000\t10000\t20000\t50000\tF5000\t10000\t20000\t50000\tB5000\t10000\t20000\t50000");
		System.out.print("Merge:\t"+average[0]+"\t"+average[3]+"\t"+average[6]+"\t"+average[9]+"\t");
		System.out.print(average[1]+"\t"+average[4]+"\t"+average[7]+"\t"+average[10]+"\t");
		System.out.println(average[2]+"\t"+average[5]+"\t"+average[8]+"\t"+average[11]+"\t");
		
		System.out.print("Quick:\t"+average[12]+"\t"+average[15]+"\t"+average[18]+"\t"+average[21]+"\t");
		System.out.print(average[13]+"\t"+average[16]+"\t"+average[19]+"\t"+average[22]+"\t");
		System.out.println(average[14]+"\t"+average[17]+"\t"+average[20]+"\t"+average[23]+"\t");
	}
	
	public static void main(String[] args)
	{
		Sorts a = new Sorts();
		a.averageCalc(500);
	}
}
	


