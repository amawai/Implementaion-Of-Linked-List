//Interface that prevents the name of Order objects from printing unless a transaction has occured
//
public interface anonymous {
	//advises toString() that it shouldn't print out name until trade is executed
	public String toString(boolean matchFound);
	
	public String printFullDetails();
	
}
