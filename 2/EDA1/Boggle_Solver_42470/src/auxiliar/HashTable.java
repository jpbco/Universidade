package auxiliar;

public abstract class HashTable implements MyHashTableInterface {

	String[] hashArray;
	int arraySize; // espaaço disponivel
	int size = 0; // espaço ocupado

	public HashTable(int size) {
		if (isPrime(size)) {
			hashArray = new String[size];
			arraySize = size;
		} else {
			int primeCount = getNextPrime(size);
			hashArray = new String[primeCount];
			arraySize = primeCount;

		}

	}

	private boolean isPrime(int num) {
		for (int i = 2; i * i <= num; i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	private int getNextPrime(int minNumber) {
		for (int i = minNumber; true; i++) {
			if (isPrime(i)) {
				return i;
			}
		}
	}

	public void insert(String word) {
		int hashVal = procPos(word);
		hashArray[hashVal] = word;
		size++;
	}

	public String find(String word) {
		int hashVal = procPos(word);
		return hashArray[hashVal];

	}

	public int size() {
		return size;
	}



}
