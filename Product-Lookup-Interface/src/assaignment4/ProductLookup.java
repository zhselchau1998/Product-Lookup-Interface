
/**
 * Program #4, CS310-1, 5/1/18
 *  Zach Selchau, cssc0755
 */

package assaignment4;

import java.util.Iterator; 

public class ProductLookup { 
	
	private DictionaryADT<String, StockItem> dataBase;
	
    // Constructor.  There is no argument-less constructor, or default size 
    public ProductLookup(int maxSize) {
    	//this.dataBase= new Hashtable<String, StockItem>(maxSize);
    	this.dataBase= new BinarySearchTree<String, StockItem>();
    	//this.dataBase= new BalancedTree<String, StockItem>();
    }
    
    // Adds a new StockItem to the dictionary 
    public void addItem(String SKU, StockItem item) {
    	dataBase.add( SKU, item);
    }
    
    // Returns the StockItem associated with the given SKU, if it is 
    // in the ProductLookup, null if it is not. 
    public StockItem getItem(String SKU) {
    	if(!dataBase.contains(SKU)) return null;
    	
    	return (StockItem) dataBase.getValue(SKU);
    }
    
    // Returns the retail price associated with the given SKU value. 
    // -.01 if the item is not in the dictionary 
    public float getRetail(String SKU)  {
    	if(!dataBase.contains(SKU)) return (float)-.01;
    	
    	StockItem tmp = dataBase.getValue(SKU);
    	return tmp.retail;
    }
    
    // Returns the cost price associated with the given SKU value. 
    // -.01 if the item is not in the dictionary 
    public float getCost(String SKU)   {
    	if(!dataBase.contains(SKU)) return (float)-.01;
    	
    	StockItem tmp = dataBase.getValue(SKU);
    	return tmp.cost;
    }
    
    // Returns the description of the item, null if not in the dictionary. 
    public String getDescription(String SKU) {
    	if(!dataBase.contains(SKU)) return null;
    	
    	StockItem tmp = dataBase.getValue(SKU);
    	return tmp.description;
    }
    
    // Deletes the StockItem associated with the SKU if it is 
    // in the ProductLookup.  Returns true if it was found and 
    // deleted, otherwise false.   
    public boolean deleteItem(String SKU) {
    	return dataBase.delete(SKU);
    }
    
    // Prints a directory of all StockItems with their associated 
    // price, in sorted order (ordered by SKU). 
    public void printAll() {
    	Iterator<String> it = dataBase.keys();
    	
    	while(it.hasNext()){
    		String tmp = it.next();
    		System.out.println(tmp+" "+dataBase.getValue(tmp).cost);
    	}
    }
    
    // Prints a directory of all StockItems from the given vendor,  
    // in sorted order (ordered by SKU). 
    public void print(String vendor) {
    	Iterator<String> it = dataBase.keys();
    	
    	while(it.hasNext()){
    		String tmp = it.next();
    		if(dataBase.getValue(tmp).vendor.compareTo(vendor)==0)
    			System.out.println(tmp+" "+dataBase.getValue(tmp).cost);
    	}
    }
    
    // An iterator of the SKU keys. 
    public Iterator<String> keys() {
    	return dataBase.keys();
    }
    // An iterator of the StockItem values.     
    public Iterator<StockItem> values(){
    	return dataBase.values();
    }
} 
