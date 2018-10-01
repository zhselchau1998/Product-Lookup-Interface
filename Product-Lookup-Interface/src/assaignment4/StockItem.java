
/**
 * Program #4, CS310-1, 5/1/18
 *  Zach Selchau, cssc0755
 */

package assaignment4;

public class StockItem implements Comparable<StockItem> { 
    String SKU; 
    String description; 
    String vendor; 
    float cost; 
    float retail; 
    
    // Constructor.  Creates a new StockItem instance.   
    public StockItem(String SKU, String description, String vendor, float cost, float retail) {
    	this.SKU=SKU;
    	this.description=description;
    	this.vendor=vendor;
    	this.cost=cost;
    	this.retail=retail;
    }
    
    // Follows the specifications of the Comparable Interface. 
    // The SKU is always used for comparisons, in dictionary order.   
    public int compareTo(StockItem n) {
    	return this.SKU.compareTo(n.SKU);
    }
    
    // Returns an int representing the hashCode of the SKU. 
    public int hashCode()  {
    	return (Math.abs(SKU.hashCode()));
    }
    
    // standard get methods 
    public String getDescription()  {
    	return this.description;
    }
    
    public String getVendor() {
    	return this.vendor;
    }
    
    public float getCost() {
    	return this.cost;
    }
    
    public float getRetail() {
    	return this.retail;
    }
    
    // All fields in one line, in order    
    public String toString() {
    	return SKU+" "+description+" "+vendor+" "+cost+" "+retail;
    }
}             
