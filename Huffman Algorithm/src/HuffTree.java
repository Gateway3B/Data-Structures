class HuffTree implements Comparable{
		private
		int character;
		int frequency;
		
		String code = new String();
		
		HuffTree left = null;
		HuffTree right = null;
		
		public HuffTree(int character)
		{
			this.character = character;
			frequency = 1;
		}
		
		public HuffTree getRight()
		{
			return this.right;
		}
		
		public HuffTree getLeft()
		{
			return this.left;
		}
		
		public void setRight(HuffTree right)
		{
			this.right = right;
		}
		
		public void setLeft(HuffTree left)
		{
			this.left = left;
		}
		
		//Generates the code for this element in the tree and all of those below it.
		public void makeCode()
		{
			if(right.checkChar(-1))
			{
				this.right.makeCodeHelper("1");
			}else
			{
				right.code = "1" + right.code;
			}
			
			if(left.checkChar(-1))
			{
				this.left.makeCodeHelper("0");
			}else
			{
				left.code = "0" + left.code;
			}
		}
		
		//Generates the code for all of the nodes below this one.
		public void makeCodeHelper(String num)
		{
			if(right.checkChar(-1))
			{
				this.right.makeCodeHelper(num);
			}else
			{
				right.code = num + right.code;
			}
			
			if(left.checkChar(-1))
			{
				this.left.makeCodeHelper(num);
			}else
			{
				left.code = num + left.code;
			}
		}
		
		public String getCode()
		{
			return this.code;
		}
		
		public boolean checkChar(int character)
		{
			if(this.character == character)
			{
				return true;
			}
			return false;
		}
		
		public void incFreq()
		{
			++this.frequency;
		}
		
		public void setFreq(int freq)
		{
			this.frequency = freq;
		}
		
		public int getChar()
		{
			return this.character;
		}
		
		public int getFreq()
		{
			return this.frequency;
		}
		
		@Override
		public int compareTo(Object comparehuff) {
	        int comparefreq=((HuffTree)comparehuff).getFreq();
	        return (this.frequency-comparefreq);
	    }
		
		public String toString() {
	        return (char)this.character + " " + this.frequency + "\n";
	    }
	}