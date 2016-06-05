//This object extends Order and contains information from users who want to sell stocks

public class OfferOrder extends Order implements anonymous{
	public OfferOrder(){
		super();
	}
	public OfferOrder(String id, double price, int volume){
		super(id, price, volume);
	}
	public String toString(boolean matchFound){
		if (!matchFound)
			return "Off:\t" + this.getPrice() + "\t" + this.getVolume();
		else
			return printFullDetails();
	}
	public String printFullDetails(){
		return "Off:\t" + this.getPrice() + "\t" + this.getVolume() + "\t" + this.getId();
	}
}
