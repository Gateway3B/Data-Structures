
public class Holder {



	//import java.io.BufferedReader;
	//import java.io.File;
	//import java.io.FileReader;
	//import java.io.IOException;
	//import java.util.ArrayList;
	//import java.util.Collections;
	//import java.util.Comparator;
	//
	//public class HuffTree implements HuffmanCoding{
	//	
//		//List containing nodes of huffElement.
//		ArrayList<huffElement> freq = new ArrayList<huffElement>();
//		//List containing the final huffTree.
//		ArrayList<huffElement> freqTree = new ArrayList<huffElement>();
	//	
//		//Element in the huffTree.
//		class huffElement implements Comparable{
//			private
//			int character;
//			int frequency;
//			
//			String code = new String();
//			
//			huffElement left = null;
//			huffElement right = null;
//			
//			public huffElement(int character)
//			{
//				this.character = character;
//				frequency = 1;
//			}
//			
//			public huffElement getRight()
//			{
//				return this.right;
//			}
//			
//			public huffElement getLeft()
//			{
//				return this.left;
//			}
//			
//			public void setRight(huffElement right)
//			{
//				this.right = right;
//			}
//			
//			public void setLeft(huffElement left)
//			{
//				this.left = left;
//			}
//			
//			//Generates the code for this element in the tree and all of those below it.
//			public void makeCode()
//			{
//				if(right.checkChar(-1))
//				{
//					this.right.makeCodeHelper("1");
//				}else
//				{
//					right.code = "1" + right.code;
//				}
//				
//				if(left.checkChar(-1))
//				{
//					this.left.makeCodeHelper("0");
//				}else
//				{
//					left.code = "0" + left.code;
//				}
//			}
//			
//			//Generates the code for all of the nodes below this one.
//			public void makeCodeHelper(String num)
//			{
//				if(right.checkChar(-1))
//				{
//					this.right.makeCodeHelper(num);
//				}else
//				{
//					right.code = num + right.code;
//				}
//				
//				if(left.checkChar(-1))
//				{
//					this.left.makeCodeHelper(num);
//				}else
//				{
//					left.code = num + left.code;
//				}
//			}
//			
//			public String getCode()
//			{
//				return this.code;
//			}
//			
//			public boolean checkChar(int character)
//			{
//				if(this.character == character)
//				{
//					return true;
//				}
//				return false;
//			}
//			
//			public void incFreq()
//			{
//				++this.frequency;
//			}
//			
//			public void setFreq(int freq)
//			{
//				this.frequency = freq;
//			}
//			
//			public int getChar()
//			{
//				return this.character;
//			}
//			
//			public int getFreq()
//			{
//				return this.frequency;
//			}
//			
//			@Override
//			public int compareTo(Object comparehuff) {
//		        int comparefreq=((huffElement)comparehuff).getFreq();
//		        return (this.frequency-comparefreq);
//		    }
//			
//			public String toString() {
//		        return (char)this.character + " " + this.frequency + "\n";
//		    }
//		}
	//	
//		//Comparator that allows for the arraylist to be sorted by character instead of frequency.
//		public class Sort2 {
//		    final Comparator<huffElement> comp2 = new Comparator<huffElement>() {
//		        public int compare(huffElement e1, huffElement e2) {
//		            return e1.getChar()-e2.getChar();
//		        }
//		    };
//		}
	//	
//		//Reads the input file and both returns the frequency of each character as a printed string and saves them in huffElements.
//		public String getFrequencies(File inputFile) 
//		{
//			int letter;
//			BufferedReader bufferedReader = null;
//			try {
//				FileReader read = new FileReader(inputFile);
//				bufferedReader = new BufferedReader(read);
//				while((letter = bufferedReader.read()) != -1)
//				{
//					boolean add = true;
//					for(huffElement e : freq)
//					{
//						if(e.checkChar(letter))
//						{
//							e.incFreq();
//							add = false;
//						}
//					}
//					if(add)
//					{
//						freq.add(new huffElement(letter));
//					}
//				}
//				bufferedReader.close();
//			} catch (IOException e)
//			{
//				System.out.println("IOException Error");
//			}
//			
//			Sort2 c = new Sort2();
//			
//			Collections.sort(freq, c.comp2);
//					
//			String freqChart = new String();
//			
//			for(huffElement e : freq)
//			{
//				freqChart += e.toString();
//			}
//			
//			
//			System.out.println(freqChart);
//			return freqChart;
//		}
	//
//		//Creates a huffTree out of an input file.
//		public HuffTree buildTree(File inputFile)
//		{
//			HuffTree a = new HuffTree();
//			getFrequencies(inputFile);
//			huffBuild();
//			a.freqTree = new ArrayList(freq);
//			huffBreak();
//			Collections.sort(freq);
//			Collections.reverse(freq);
//			a.freq = freq;
//			return a;
//		}
	//	
//		//Helper class that does the actual building of the huffTree.
//		public void huffBuild()
//		{
//			if(freq.size() == 1)
//			{
//				return;
//			}
//			Collections.sort(freq);
//			huffElement e = new huffElement(-1);
//			e.setRight(freq.get(1));
//			e.setLeft(freq.get(0));
//			e.setFreq(e.getRight().getFreq() + e.getLeft().getFreq());
//			e.makeCode();
//			freq.remove(0);
//			freq.remove(0);
//			freq.add(e);
//			huffBuild();
//		}
	//	
//		//Breaks a formed huffTree in order to get a list of huffElements with their huffTree codes.
//		public void huffBreak()
//		{
//			for(huffElement e : freq)
//			{
//				if(e.checkChar(-1))
//				{
//					freq.add(freq.indexOf(e), e.getRight());
//					freq.add(freq.indexOf(e), e.getLeft());
//					freq.remove(e);
//					huffBreak();
//					break;
//				}
//			}
//		}
	//
//		//Takes an input file and its huffTree and generates a code of 1s and 0s.
//		public String encodeFile(File inputFile, HuffTree huffTree)
//		{
//			String code = new String();
//			int letter;
//			BufferedReader bufferedReader = null;
//			try {
//				FileReader read = new FileReader(inputFile);
//				bufferedReader = new BufferedReader(read);
//				while((letter = bufferedReader.read()) != -1)
//				{
//					for(huffElement e : huffTree.freq)
//					{
//						if(e.checkChar(letter))
//						{
//							code += e.getCode();
//							break;
//						}
//					}
//				}
//				bufferedReader.close();
//			} catch (IOException e)
//			{
//				System.out.println("IOException Error");
//			}
//			return code;
//		}
	//
//		//Takes an input of a code and a huffTree and outputs the decoded file.
//		public String decodeFile(String code, HuffTree huffTree)
//		{
//			huffElement target = huffTree.freqTree.get(0);
//			String deCode = new String();
//			for(int i = 0; i < code.length(); ++i)
//			{
//				if(!(target.checkChar(-1)))
//				{		
//					deCode += (char)target.getChar();
//					target = huffTree.freqTree.get(0);
//				}
//				if(code.charAt(i) == '1')
//				{
//					target = target.getRight();
//				}
//				
//				if(code.charAt(i) == '0')
//				{
//					target = target.getLeft();
//				}
//			}
//			deCode += (char)target.getChar();
//			return deCode;
//		}
	//
//		//Prints characters and their codes as saved in arraylist freq.
//		public String traverseHuffmanTree(HuffTree huffTree)
//		{
//			Sort2 c = new Sort2();
//			Collections.sort(freq, c.comp2);
//			String codes = new String();
//			for(huffElement e : huffTree.freq)
//			{
//				codes += (char)e.getChar() + " " + e.getCode() + "\n";
//			}
//			System.out.println(codes);
//			return codes;
//		}
	//
//		public static void main(String[] args) throws IOException
//		{
//			HuffTree a = new HuffTree();
//			File b = new File("illiad.txt");
//			a = a.buildTree(b);
//			String c = a.encodeFile(b, a);
//			System.out.println(c);
//			a.traverseHuffmanTree(a);
//			System.out.println(a.decodeFile(c, a));
//			
//		}
	//}

}
