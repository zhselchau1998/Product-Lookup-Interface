
/**
 * Program #4, CS310-1, 5/1/18
 *  Zach Selchau, cssc0755
 */

package assaignment4;

public class Test{

	static int size = 10;
	static boolean usingHash = true;
	static ProductLookup thing = new ProductLookup(size);
	static String itemOne= "pickle";
	static String itemTwo= "salmon";
	static String itemThree= "eggs";
	static String itemFour= "radish";
	static String vendorOne= "Spencers";
	static String vendorTwo= "Macy's";
	
	public static void main(String [] args){
		
		thing.printAll();
		
		test1(size);
		test2(size);
		printAlls();
		
		test3(size);
		printAlls();
	}
	
	public static void test1(int size){//Insert test
		int checkSize = 0;
		System.out.println("+++++++++Insertion test+++++++++");
		if(size>0){
			thing.addItem(itemOne, new StockItem(itemOne, "A pickled pickle", vendorOne, (float) .50, (float) 1.99));
			System.out.println("Inserted pickle");
			checkSize++;
		}
		if(size>1){
			thing.addItem(itemTwo, new StockItem(itemTwo, "A fish fillet", vendorOne, (float) 3.00, (float) 7.99));
			System.out.println("Inserted salmon");
			checkSize++;
		}
		if(size>2){
			thing.addItem(itemThree, new StockItem(itemThree, "Just a sack of eggs ... yes a sack",vendorTwo, (float) .30, (float) 1.99));
			System.out.println("Inserted eggs");
			checkSize++;
		}
		if(size>3){
			thing.addItem(itemFour, new StockItem(itemFour, "A little red demon", vendorTwo, (float) .25, (float) 1.49));
			System.out.println("Inserted radish");
			checkSize++;
		}
		
		System.out.println();
		
		if(checkSize==size&&usingHash){
			System.out.println("Full insert check, crash if fucked");
			thing.addItem("test", new StockItem("test","just a test","tester",0,0));
		}
		System.out.println();
	}
	
	public static void test2(int size){//Check get commands
		System.out.println("+++++++++Get functions test+++++++++");
		if(size>0){
			System.out.print(thing.getItem(itemOne).SKU+ ", ");
			System.out.print(thing.getDescription(itemOne)+ ", ");
			System.out.print(thing.getCost(itemOne)+ ", ");
			System.out.println(thing.getRetail(itemOne));
		}
		if(size>1){
			System.out.print(thing.getItem(itemTwo).SKU+ ", ");
			System.out.print(thing.getDescription(itemTwo)+ ", ");
			System.out.print(thing.getCost(itemTwo)+ ", ");
			System.out.println(thing.getRetail(itemTwo));
		}
		if(size>2){
			System.out.print(thing.getItem(itemThree).SKU+ ", ");
			System.out.print(thing.getDescription(itemThree)+ ", ");
			System.out.print(thing.getCost(itemThree)+ ", ");
			System.out.println(thing.getRetail(itemThree));
		}
		if(size>3){
			System.out.print(thing.getItem(itemFour).SKU+ ", ");
			System.out.print(thing.getDescription(itemFour)+ ", ");
			System.out.print(thing.getCost(itemFour)+ ", ");
			System.out.println(thing.getRetail(itemFour));
		}
		System.out.println();
		
		System.out.println("Now get functions if not there, should print out:");
		System.out.println( "null, -0.01, null, -0.01");
		System.out.print(thing.getItem("")+", ");
		System.out.print(thing.getCost("")+", ");
		System.out.print(thing.getDescription("")+", ");
		System.out.print(thing.getRetail(""));
		System.out.println();
	}
	
	public static void test3(int size){//delete all
		System.out.println("+++++++++Deletion test+++++++++");
		if(size>0){
			System.out.println("deleting pickles");
			if(thing.deleteItem(itemOne)) System.out.println("worked");
			else System.out.println("fucked up");
		}
		if(size>1){
			System.out.println("deleting salmon");
			if(thing.deleteItem(itemTwo)) System.out.println("worked");
			else System.out.println("fucked up");
		}
		if(size>2){
			System.out.println("deleting eggs");
			if(thing.deleteItem(itemThree)) System.out.println("worked");
			else System.out.println("fucked up");
		}
		if(size>3){
			System.out.println("deleting radish");
			if(thing.deleteItem(itemFour)) System.out.println("worked");
			else System.out.println("fucked up");
		}
		System.out.println("False delete");
		if(!thing.deleteItem("")) System.out.println("worked");
		else System.out.println("fucked up");
		
		System.out.println();
	}
	
	public static void printAlls(){
		System.out.println("+++++++++Printing All+++++++++");
		System.out.println("Vendor One");
		thing.print(vendorOne);
		System.out.println();
		System.out.println("Vendor Two");
		thing.print(vendorTwo);
		System.out.println("");
		System.out.println("All");
		thing.printAll();
		System.out.println("");
	}
}
