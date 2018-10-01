
/**
 * Program #4, CS310-1, 5/1/18
 *  Zach Selchau, cssc0755
 */

package assaignment4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BinarySearchTree <K extends Comparable<K>, V extends Comparable<V>> implements DictionaryADT<K, V>{

	//Variables
	int currSize;
	int modCount;
	DictionaryNode<K,V> root;
	DictionaryNode<K,V>[] searchArr;
	int searchArrTracker;
	
	//Constructor
	public BinarySearchTree (){
		this.currSize=0;
		this.modCount=0;
		this.root=null;
	}
	
	//Functions
	public boolean contains(K key) {
		if(this.isEmpty()) return false;
		DictionaryNode<K,V> tmp = new DictionaryNode(key, null);
		DictionaryNode<K,V> currNode = root;
		while(currNode!=null){
			if(currNode.compareTo(tmp)==0) return true;
			else if(currNode.compareTo(tmp)<0)currNode=currNode.right;
			else if(currNode.compareTo(tmp)>0)currNode=currNode.left;
		}
		
		return false;
	}

	public boolean add(K key, V value) {
		//Variables
		DictionaryNode<K,V> tmp = new DictionaryNode(key, value);
		DictionaryNode<K,V> currNode = root;
		DictionaryNode<K,V> parentNode = currNode;
		int LorR = 0;
		//Quick check if empty
		if(this.isEmpty()){
			root = tmp;
			currSize++;
			modCount++;
			return true;
		}
		//Only 1 of every item allowed
		if(this.contains(key))return false;
		
		//Search for insert position
		while(currNode!=null){
			if(currNode.compareTo(tmp)<0){
				parentNode=currNode;
				currNode=currNode.right;
				LorR=1;
			}
			else if(currNode.compareTo(tmp)>0){
				parentNode=currNode;
				currNode=currNode.left;
				LorR=0;
			}
		}
		
		//Insert
		currSize++;
		modCount++;
		if(LorR==1) parentNode.right=tmp;
		else parentNode.left=tmp;
		return true;
	}

	public boolean delete(K key) {
		if(this.isEmpty())return false;//Quick check
		//Variables
		DictionaryNode<K,V> deletingNode = new DictionaryNode(key,null);
		DictionaryNode<K,V> parentNode = root;
		DictionaryNode<K,V> currNode = root;
		int LorR = 0;
		
		if(root.compareTo(deletingNode)==0){//If the deleting node is the root
			
			
						
			if(root.right==null){
				currSize--;
				modCount++;
				root=root.left;//One sided tree
				return true;
			}
			if(root.left==null){
				currSize--;
				modCount++;
				root=root.right;//Other sided tree
				return true;
			}
			
			DictionaryNode<K,V> subTree = root.right.left;//Save the sub tree
			
			root.right.left=root.left;//Get rid of the root node and replace with root.right
			root=root.right;
			
			currNode=root.left;//Re-insert the subtree
			parentNode=root;
			while(currNode!=null){
				parentNode=currNode;
				currNode=currNode.right;
			}
			parentNode.right=subTree;
			
			currSize--;
			modCount++;
			return true;
		}
		
		while(currNode!=null){//Go through until found a correct node
			if(currNode.compareTo(deletingNode)>0){
				parentNode=currNode;
				currNode=currNode.left;
				LorR = 0;
			}
			if(currNode.compareTo(deletingNode)<0){
				parentNode=currNode;
				currNode=currNode.right;
				LorR = 1;
			}
			
			if(currNode.compareTo(deletingNode)==0){//All other instances
				
				
							
				if(currNode.right==null){
					currSize--;
					modCount++;
					if(LorR==1)parentNode.right=currNode.left;//One sided tree
					else parentNode.left=currNode.left;
					return true;
				}
				if(currNode.left==null){
					currSize--;
					modCount++;
					if(LorR==1)parentNode.right=currNode.right;//One sided tree
					else parentNode.left=currNode.right;
					return true;
				}
				
				DictionaryNode<K,V> subTree = currNode.right.left;//Save the sub tree
				
				currNode.right.left=currNode.left;//Get rid of the curr node and replace with curr.right
				if(LorR==1)parentNode.right=currNode.right;//One sided tree
				else parentNode.left=currNode.right;
				
				currNode=currNode.left;//Re-insert the subtree
				while(currNode!=null){
					parentNode=currNode;
					currNode=currNode.right;
				}
				parentNode.right=subTree;
				
				currSize--;
				modCount++;
				return true;
			}
		}
		
		
		return false;//No element was found
	}

	public V getValue(K key) {
		if(this.isEmpty()) return null;
		DictionaryNode<K,V> tmp = new DictionaryNode(key, null);
		DictionaryNode<K,V> currNode = root;
		while(currNode!=null){
			if(currNode.compareTo(tmp)==0) return currNode.value;
			if(currNode.compareTo(tmp)<0)currNode=currNode.right;
			if(currNode.compareTo(tmp)>0)currNode=currNode.left;
		}
		
		return null;
	}

	public K getKey(V value) {
		if(this.isEmpty())return null;
		searchArr= new DictionaryNode[currSize];
		
		//printInOrder(root);
		searchArrTracker = 0;
		fillSearchArr(root);
				
		for(int i=0; i<currSize;i++){
			if(i%1000== 0){
				i=i;
			}
			if(searchArr[i].value.compareTo(value)==0)return searchArr[i].key;
		}
		return null;
	}

	public int size() {
		return currSize;
	}

	public boolean isFull() {
		return false;
	}

	public boolean isEmpty() {
		return root==null;
	}

	public void clear() {
		root=null;
	}
	
	private void fillSearchArr(DictionaryNode<K,V> n){
		if(n==null)return;
		fillSearchArr(n.left);
		searchArr[searchArrTracker++]=n;
		fillSearchArr(n.right);
	}
	
	/*private void printInOrder(DictionaryNode<K,V> n){
		if(n==null)return;
		printInOrder(n.left);
		System.out.println(n.key+". In spot: "+searchArrTracker++);
		printInOrder(n.right);
	}*/

	//Iterators
	public Iterator<K> keys() {
		return new KIteratorHelper();
	}

	public Iterator<V> values() {
		return new VIteratorHelper();
	}

	//Classes
	private class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>{


		K key;
		V value;
		DictionaryNode<K,V> left;
		DictionaryNode<K,V> right;
		
		public DictionaryNode(K k, V v){
			this.key = k;
			this.value = v;
			this.left = null;
			this.right = null;
		}
		
		public int compareTo(DictionaryNode<K, V> node) {
			return ((Comparable<K>)key).compareTo((K)node.key);
		}
		
	}
	
	abstract class IteratorHelper<E> implements Iterator<E>{
		protected long modCounter;
		protected DictionaryNode<K,V>[] nodes;
		protected int idx, index;
		
		public IteratorHelper(){
			modCounter = modCount;
			idx = index = 0;
			nodes = new DictionaryNode [currSize];
			inOrderFillArr(root);
		}
		
		public boolean hasNext(){
			if(modCounter!=modCount)
				throw new ConcurrentModificationException();
			//if(next()==null) return false;
			return idx < currSize;
		}
		
		public abstract E next();
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	
		private void inOrderFillArr(DictionaryNode<K,V> n){
			if(n==null)return;
			inOrderFillArr(n.left);
			nodes[index++]=n;
			inOrderFillArr(n.right);
		}
	}

	private class KIteratorHelper<K> extends IteratorHelper<K>{
		public KIteratorHelper(){
			super();
		}
		public K next(){
			//if(nodes[idx]==null) return null;
			return (K) nodes[idx++].key;
		}
	}
	
	private class VIteratorHelper<V> extends IteratorHelper<V>{
		public VIteratorHelper(){
			super();
		}
		public V next(){
			//if(nodes[idx]==null) return null;
			return (V) nodes[idx++].value;
		}
	}
}
