
/**
 * Program #4, CS310-1, 5/1/18
 *  Zach Selchau, cssc0755
 */

package assaignment4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Hashtable <K extends Comparable<K>,V extends Comparable<V>> implements DictionaryADT<K, V>{

	private int currSize;
	private int maxSize;
	private int tableSize;
	private long modCount;
	private UnorderedList<DictionaryNode<K,V>> [] list;
	
	//Constructor
	public Hashtable(int n){
		this.currSize=0;
		this.maxSize=n;
		this.tableSize= (int) (maxSize* 1.3f);
		this.modCount=0;
		this.list= new UnorderedList [tableSize];
		
		for(int i=0; i<tableSize; i++){
			this.list[i]=new UnorderedList<DictionaryNode<K,V>>();
		}
	}

	public boolean contains(K key) {
		return list[getHashIdx(key)].contains(new DictionaryNode<K,V>(key, null));
	}

	public boolean add(K key, V value) {
		//Checks
		if(isFull())
			return false;
		
		if(list[getHashIdx(key)].contains(new DictionaryNode<K,V>(key, null)))
			return false;
		//Add a new node
		list[getHashIdx(key)].addFirst(new DictionaryNode<K,V>(key, value));
		currSize++;
		modCount++;
		return true;
	}

	public boolean delete(K key) {
		if(!contains(key)) return false;
		//Delete given key
		currSize--;
		modCount++;
		return list[getHashIdx(key)].delete(new DictionaryNode<K,V>(key,null));
	}

	public V getValue(K key) {
		DictionaryNode<K,V> tmp = list[getHashIdx(key)].get(new DictionaryNode<K,V>(key,null));
		if(tmp==null)return null;
		return tmp.value;
	}

	public K getKey(V value) {
		if(isEmpty()) return null;//Check
		//Tmp elements
		DictionaryNode<K,V> [] nodes = new DictionaryNode[currSize];
		int j=0;
		//Put everything in an array
		for(int i=0; i < currSize; i++)
			for(DictionaryNode<K, V> n : list[i])
				nodes[j++]=n;
		//Go through and return the node with the correct value
		for(int i=0; i<currSize; i++)
			if(nodes[i].value.compareTo(value)==0)
				return nodes[i].key;
		return null;
	}

	public int size() {
		return currSize;
	}

	public boolean isFull() {
		return currSize>=maxSize;
	}

	public boolean isEmpty() {
		return currSize==0;
	}

	public void clear() {
		for(int i=0; i<tableSize; i++){//Reset all lists in hashtable
			if(!list[i].isEmpty())
				this.list[i]=new UnorderedList<DictionaryNode<K,V>>();
		}
		currSize=0;
		modCount++;
	}

	//Iterators
	public Iterator<K> keys() {
		return new KIteratorHelper<K>();
	}

	public Iterator<V> values() {
		return new VIteratorHelper<V>();
	}
	
	//Private functions
	private int getHashIdx(K k){
		return Math.abs(k.hashCode()%tableSize);
	}
	
	private DictionaryNode<K, V>[] sort(DictionaryNode<K, V> [] arr){
		
		if(arr == null || arr.length == 0){
			return null;
		}
		//Swap end node with one in the middle just in case of an almost sorted list
		/*int endIdx = arr.length-1;
		int midIdx = arr.length/2;
		DictionaryNode<K, V> tmp = arr[arr.length-1];
		arr[arr.length-1]=arr[arr.length/2];
		arr[arr.length/2]=tmp;
		*/
		return quickSort(arr, 0, arr.length-1);
			
	}
	
	private DictionaryNode<K, V>[] quickSort(DictionaryNode<K, V> [] arr, int lower, int upper){
		int l = lower;
		int u = upper;
		
		DictionaryNode<K,V> pivot= arr[upper];
		
		while(l <= u){
			while((arr[l]).compareTo(pivot)<0) l++;//Find an element in lower half thats greater than pivot
			while(arr[u].compareTo(pivot)>0) u--;//Find an element in upper half thats lesser than pivot
			if(l <= u){
				DictionaryNode<K, V> tmp = arr[l];//Swap
				arr[l++]=arr[u];
				arr[u--]=tmp;
			}
		}//Recurssive call
		if(lower < u) arr=quickSort(arr, lower, u);
		if(upper > l) arr=quickSort(arr, l, upper);
		return arr;
	}
	
	//Classes
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected DictionaryNode<K,V> [] nodes;
		protected int idx;
		protected long modCounter;
		
		public IteratorHelper(){
			nodes = new DictionaryNode[currSize];
			idx=0;
			int j=0;
			modCounter = modCount;
			for(int i=0; i < tableSize; i++)
				for(DictionaryNode<K, V> n : list[i])
					nodes[j++]=n;
			nodes= (nodes);
			
		}
		
		public boolean hasNext(){
			if(modCounter!=modCount)
				throw new ConcurrentModificationException();
			return idx < currSize;
		}
		
		public abstract E next();
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

	private class KIteratorHelper<K> extends IteratorHelper<K>{
		public KIteratorHelper(){
			super();
		}
		public K next(){
			return (K) nodes[idx++].key;
		}
	}
	
	private class VIteratorHelper<V> extends IteratorHelper<V>{
		public VIteratorHelper(){
			super();
		}
		public V next(){
			return (V) nodes[idx++].value;
		}
	}
	
 	private class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>{

		K key;
		V value;
		
		public DictionaryNode(K k, V v){
			this.key = k;
			this.value = v;
		}
		@Override
		public int compareTo(DictionaryNode<K, V> node) {
			return ((Comparable<K>)key).compareTo((K)node.key);
		}
		
	}
	
	public class UnorderedList<E extends Comparable <E>> implements Iterable<E> {
		private Node<E> head;
		private int size;
		
		public UnorderedList(){
			this.head=null;
			this.size=0;
		}
		
		public boolean addFirst(E data){
			Node<E> newNode= new Node(data);
			if(head==null) head=newNode;
			else{
				newNode.next=head;
				head=newNode;
			}
			size++;
			return true;
		}
		
		public boolean isEmpty(){
			return this.size==0;
		}
		
		public boolean delete(E obj){
			boolean removedItem=false;
			Node<E> prev=head;//Search
			Node<E> curr=head;
			while(curr!=null){
				if(curr.data.compareTo(obj)==0){
					size--;
					removedItem=true;

					if(size==0)head=null;
					else if(curr==head){
						head=curr.next;//Deleting first node
					//	curr=curr.next;
					}
					else if(curr.next==null){//Deleting last node
						prev.next=null;
					}
					else {
						prev.next=curr.next;//Deleting a node not head or tail
					}
					curr=null;
				}else{
					prev=curr;
					curr=curr.next;
				}
			}
			return removedItem;
		}
		
		public boolean contains(E obj){
			if(this.isEmpty())return false;
			Node<E> curr=head;//Search
			E tmp = null;
			if(curr!=null)
				tmp = curr.data;
			while(curr!=null){
				tmp = curr.data;
				if(tmp.compareTo(obj)==0)return true;
				curr=curr.next;
			}
			return false;
		}
		
		public E get(E obj){
			if(this.isEmpty())return null;
			Node<E> curr=head;
			//Node<E> prev=curr;
			E tmp;
			while(curr!=null){
				tmp = curr.data;
				if(tmp.compareTo(obj)==0)return tmp;
				curr=curr.next;
			}
			
			return null;
		}

		public Iterator<E> iterator() {
			return new LLIterator();
		}
		
		class LLIterator implements Iterator<E>{
			
			private Node<E> curr;
			private int count;
			
			public LLIterator(){
				curr=head;
				count=1;
			}
			
			public boolean hasNext() {
				return count<=size;
			}

			public E next() {
				if(!hasNext()) throw new NoSuchElementException();
				E tmp=curr.data;
				curr=curr.next;
				count++;
				return tmp;
			}
			
		}
		
		class Node<T extends Comparable <T>>{
			T data;
			Node next;
			public Node(T data){
				this.data=data;
				this.next=null;
			}
		}
	}

	
		
	
	
}
