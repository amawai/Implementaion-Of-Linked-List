//This object extends Order and contains information from users who want to buy stocks

public class BidOrder extends Order implements anonymous{
	public BidOrder(){
		super();
	}
	public BidOrder(String id, double price, int volume){
		super(id, price, volume);
	}
	
	public String toString(boolean matchFound){
		if (!matchFound)
			return "Bid:\t" + this.getPrice() + "\t" + this.getVolume();
		else
			return printFullDetails();
	}
	public String printFullDetails(){
		return "Bid:\t" + this.getPrice() + "\t" + this.getVolume() + "\t" + this.getId();
	}
	
}
