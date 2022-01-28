import java.util.*;

	public class HashedDictionary<K, V> implements DictionaryInterface<K, V>
	 {
	 // The dictionary:
	 private int numberOfEntries;
	 private static final int DEFAULT_CAPACITY = 5; // Must be prime
	 private static final int MAX_CAPACITY = 10000;
	
	 // The hash table:
	 private TableEntry<K, V>[] hashTable;
	 private int tableSize; // Must be prime
	 private static final int MAX_SIZE = 2 * MAX_CAPACITY;
	 private boolean initialized = false;
	 private int lastindex;
	 private int ssforpaf;
	 private int linearordouble;
	 private int totalCollision=0;
	 private double loadfactor;
	// that can be filled
	 public HashedDictionary()
	 {
		 this(DEFAULT_CAPACITY);
		 tableSize = DEFAULT_CAPACITY;
	 } // end default constructor
	 public static int getNextPrime(int num) {
	      num++;
	      for (int i = 2; i < num; i++) {
	         if(num%i == 0) {
	            num++;
	            i=2;
	         } else {
	            continue;
	         }
	      }
	      return num;
	   }
	 
	 public HashedDictionary(int initialCapacity)
	  {
		 //checkCapacity(initialCapacity);
		 numberOfEntries = 0; // Dictionary is empty,// Set up hash table:
		// Initial size of hash table is same as initialCapacity if it is prime; // otherwise increase it until it is prime size
		 int tableSize = getNextPrime(initialCapacity);
		// checkSize(tableSize); // Check for max array size
		// The cast is safe because the new array contains null entries
		
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] temp = (TableEntry<K, V>[])new TableEntry[tableSize]; hashTable = temp;
		initialized = true;
		
	  }
	 private void LoadFactor() {
		 if((double)((double)numberOfEntries/(double)hashTable.length)>loadfactor)
			 enlargeHashTable();
		
			 
	 }
	 
	 private int SSF(K key) {
		 	int hash = 0;		
			int n = ((String) key).length();
			for (int i = 0; i < n; i++)
			   hash = hash +((String) key).charAt(i) - 96;
			hash = hash%  hashTable.length;;
			if(hash >0)
			return hash;
			if (hash < 0)
				 hash = hash + hashTable.length; 
				 return hash;
		                       } // end getHashIndex
	
	 private int PAF(K key) {
			int hash = 0;
			int g = 31;
			int n = ((String) key).length();
			for (int i = 0; i < n; i++)
			   hash = g* hash +(((String) key).charAt(i) - 96);
			hash = hash%  hashTable.length;;
			if(hash >0)
			return hash;
			if (hash < 0)
				 hash = hash + hashTable.length; 
				 return hash;
		}
	 public V getValue(K key, int doubleorlinear, int ssforpaf) {
		 int index = 0;
		 if(ssforpaf==1)		 
			 index = SSF(key);
		 else
			 index = PAF(key);
		 index = locate(index, key, doubleorlinear);
		 if (index != -1 &&  hashTable[index].isIn()) {
			 hashTable[index].sll.display();
			 System.out.println("\n\n"+ hashTable[index].sll.size()+ " document found");
			 
		 }
		 		 
		 else System.out.println("\t Key Not Found.");
		return null;
	 }
	 public boolean getForSearch(K key, int doubleorlinear, int ssforpaf) {
		 int index = 0;
		 if(ssforpaf==1)		 
			 index = SSF(key);
		 else
			 index = PAF(key);
		 index = locate(index, key, doubleorlinear);
		 if ( index>0&& hashTable[index].isIn())
		 	return true;//KEY HAS FOUND
		 else 
		 return false;
		
	 }
		 
	 
	 
	 public V remove(K key, int doubleorlinear, int ssforpaf) {
		 int index ;
		 V removedValue = null;
		 if(ssforpaf==1)
			 index = SSF(key);
		 else
			 index = PAF(key);
		 index = locate(index, key, doubleorlinear);
		 if (index != -1)
		 { // Key found; flag entry as removed and return its value
		           removedValue = hashTable[index].getValue();		           
		           hashTable[index].setToRemoved();
		           numberOfEntries--;
		 } // end if
		        // Else key not found; return null
		 return removedValue; } // end remove
	 
	 
	 private int locate(int index, K key ,int doubleorlinear) {
		 boolean found = false;
		 if(doubleorlinear==1) {
			 while ( !found && (hashTable[index] != null) ) {
				 if ( hashTable[index].isIn() && key.equals(hashTable[index].getKey()) )
				 found = true; 
				 else 
				                     index = (index + 1) % hashTable.length; // Linear probing
				  } 
		 }
		 else {
			 int h1 = index%13;
				int k = 0;
				int h2 = h1+ k*(7- index%7);
				while (hashTable[h2] != null) {
					
					 if (hashTable[h2].isIn()) { 
						 if(hashTable[h2].getKey().equals(key)) {
							 found = true;
							 break;
						 	}	
						 k++;
					 	}
					 else
						 k++;
					 h2= h1+ k*(7- index%7);
					 
				}
				index = h2;
				
		 }
		 
		
		 int result = -1;
		 if (found)
		 result = index;
		 return result;
		 
	 } // end locate
	 
		
	 
	 public void add(K key, V value, int ssf_paf, int linear_probing, double loadfactorr) {
		 loadfactor = loadfactorr;
		 ssforpaf = ssf_paf;
		 linearordouble = linear_probing;
		 if ((key == null) || (value == null))
		 throw new IllegalArgumentException(); else
		        {
		             
		           int index;
		  if(ssf_paf ==1 )// Value to return
			  index = SSF(key);		  
		  else
			 index = PAF(key);
		  
		  
		  if(linear_probing==1)
			  linearprobing(index, key, value);
		  else
			  doubleHashing(index, key, value);
		     
		  } 
		 } 
	 
	 private void enlargeHashTable() {
		 TableEntry<K, V>[] oldTable = hashTable;
		 int oldSize = hashTable.length;
		 int newSize=0;
		totalCollision=0;
		 
		while(!checkSize(newSize))
			 newSize = getNextPrime(oldSize + oldSize);
		        // The cast is safe because the new array contains null entries
		 @SuppressWarnings("unchecked")
		 TableEntry<K, V>[] temp = (TableEntry<K, V>[])new TableEntry[newSize];
		 hashTable = temp;
		 tableSize = newSize;
		 numberOfEntries = 0; 
		 for (int index = 0; index < oldSize; index++)
		 {
		 if ( (oldTable[index] != null) && oldTable[index].isIn() ) {
			 add(oldTable[index].getKey(), oldTable[index].getValue(),ssforpaf, linearordouble,loadfactor);			 
				hashTable[lastindex].sll.head= 	oldTable[index].sll.head;
		 }
			 
		        } // end for
		     } // end enlargeHa

	 public int totalCollisionvalue() {
		 return totalCollision;
	 }
	 private boolean checkSize(int newSize) {
		if(lastindex>newSize)
			return false;
		else return true;
		
	}
	 private void doubleHashing(int index, K key, V value) {
		int h1 = index%13;
		int k = 0;
		int h2 = h1+ k*(7- index%7);
		while (hashTable[h2] != null) {
			
			 if (hashTable[h2].isIn()) { 
				 if(hashTable[h2].getKey().equals(key)) {
					 if(!hashTable[h2].getsll().search((String) value))//if returns false means this text not in linked list
						 hashTable[h2].getsll().add(value,1);
					 break;
				 	}	
				 k++;
				 totalCollision++;
			 	}
			 else
				 k++;
			 h2= h1+ k*(7- index%7);
			 totalCollision++;
			 lastindex = h2;
			 if(h2>=hashTable.length)
				 enlargeHashTable();
		}
		lastindex = h2;
		if ( (hashTable[h2] == null) || hashTable[h2].getState().equals(States.REMOVED)) { 
			 
			 hashTable[h2] = new TableEntry<>(key, value);
			 numberOfEntries++;
			 LoadFactor();
		 
		 }
		
		
	 }

	private void linearprobing(int index, K key, V value) {
		 int removedStateIndex = -1; // Index of first location in removed state while ( !found && (hashTable[index] != null) )
		 while ( index< hashTable.length&& (hashTable[index] != null) )
		 {
			 if (hashTable[index].isIn()) { 
				 if(hashTable[index].getKey().equals(key)) {
					 if(!hashTable[index].getsll().search((String) value))//if returns false means this text not in linked list
						 hashTable[index].getsll().add(value,1);
					 break;
				 }
					 
					 index = (index + 1); 
					 totalCollision++;
				 }
			 else {
                 
				 if (removedStateIndex == -1) removedStateIndex = index;
                 index = (index + 1);
                 totalCollision++;
              } 
			
			
			 
		 } 
		 lastindex = index;
		 if(index>= hashTable.length) {
			 
			 enlargeHashTable();
		 }
		 assert (index >= 0) && (index < hashTable.length);
		 if ( (hashTable[index] == null) || hashTable[index].getState().equals(States.REMOVED)) { 
			 
			 hashTable[index] = new TableEntry<>(key, value);
			 numberOfEntries++;
			 LoadFactor();
		 
		 }
	 	} // 
		
	 
	 
	public static class TableEntry<S, T> {
		private Sll sll = new Sll();
		private S key;
		private T value;
		private States state; // Flags whether this entry is in the hash table private enum States {CURRENT, REMOVED} // Possible values of state
		private TableEntry(S searchKey, T dataValue) {
		                            key = searchKey;
		                            value =  dataValue;
		                            state = States.CURRENT;	                            
		                            sll.add(dataValue, 1);
		} // end constructor
		
		
		Sll getsll() {
			return sll;
		}
		Boolean isIn() {
			if(state == States.CURRENT)
				return true;
			else return false;
		}
		void setToRemoved() {
			state = States.REMOVED;
		}

		S getKey() {
			return key; }
		// end getKey
		T getValue() {
			return (T) value; } // end getValue
		States getState(){
			return state;
		}
		void setValue(T newValue) {
	          value = newValue;
	      } // end setValue
		}
		


	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Iterator<K> getKeyIterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterator<V> getValueIterator() {
		
		return null;
	}
	@Override
	public boolean isEmpty() {
		
		return false;
	}
	@Override
	public int getSize() {
		
		return 0;
	}
	@Override
	public void clear() {
		
		
	}

public enum States {
	CURRENT ,
	REMOVED

}

@Override
public V add(K key, V value) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public V remove(K key) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public V getValue(K key) {
	// TODO Auto-generated method stub
	return null;
}
	 
	
	 }
	


