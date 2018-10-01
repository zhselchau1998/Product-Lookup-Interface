
/**
 * Program #4, CS310-1, 5/1/18
 *  Zach Selchau, cssc0755
 */

package assaignment4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.TreeMap;

public class BalancedTree <K extends Comparable<K>,V extends Comparable<V>> implements DictionaryADT<K, V>{

	int modCount;
	TreeMap<K,V> tree;
	
	public BalancedTree(){
		modCount=0;
		tree= new TreeMap<K,V>();
	}
	
	public boolean contains(K key) {
		return tree.containsKey(key);
	}

	
	public boolean add(K key, V value) {
		if(tree.containsKey(key)) return false;
		modCount++;
		tree.put(key,value);
		return true;
	}

	
	public boolean delete(K key) {
		modCount++;
		if (tree.remove(key) != null)
			return true;
		return false;
	}

	
	public V getValue(K key) {
		return tree.get(key);
	}

	
	public K getKey(V value) {
		K curr= tree.firstKey();//Sets curr to first entry
		while(tree.lowerEntry(curr)!=null){
			if(tree.get(curr).compareTo(value)==0)return curr;
			curr=tree.lowerEntry(curr).getKey();
		}
		if(tree.get(curr).compareTo(value)==0)return curr;
		return curr;
	}

	
	public int size() {
		return tree.size();
	}

	
	public boolean isFull() {
		return false;
	}

	
	public boolean isEmpty() {
		return tree.isEmpty();
	}

	
	public void clear() {
		modCount++;
		tree.clear();
		
	}

	
	public Iterator<K> keys() {
		return tree.keySet().iterator();
	}

	
	public Iterator<V> values() {
		return tree.values().iterator();
	}
	
	
}