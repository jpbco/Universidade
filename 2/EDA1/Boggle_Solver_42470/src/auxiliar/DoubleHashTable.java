package auxiliar;

public class DoubleHashTable extends HashTable{

	public DoubleHashTable(int size) {
		super(size);
		
	}

	private int hashFunc1(String word){
		int hashVal = word.hashCode();
		hashVal %= arraySize;
		if(hashVal < 0) {
			hashVal += arraySize;
		}
		return hashVal; 
	}
	
	private int hashFunc2(String word) {		
		return 7 - hashFunc1(word) % 7;
	}
	
	@Override
	public int procPos(String word) {
		int hashVal = hashFunc1(word);
		int step =hashFunc2(word); // number of steps we need to take to find an available slot
		
		while(hashArray[hashVal] != null) {
			if(hashArray[hashVal].equals(word))
				return hashVal;
			hashVal = hashVal + step;
			hashVal = hashVal % arraySize;
			
		}
		return hashVal;
	}
}
