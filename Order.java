//The OfferOrder and BidOrder extends this class.
//The Order class contains information that Orders would have, namely the person's ID, their price and the volume of their stock
//
public class Order implements anonymous{
	private String id;
	private double price;
	private int volume;
	
	public Order(){
		id = null;
		price = 0;
		volume = 0;
	}
	public Order(String id, double price, int volume){
		this.id = id;
		this.price = price;
		this.volume = volume;
	}
	
	public String getId(){
		return id;
	}
	public double getPrice(){
		return price;
	}
	public int getVolume(){
		return volume;
	}
	public void setId(String id){
		this.id=id;
	}
	public void setPrice(double price){
		this.price=price;
	}
	public void setVolume(int volume){
		this.volume=volume;
	}
	
	public String toString(boolean matchFound){
		if (!matchFound)
			return "\t" + price + "\t" + volume;
		else
			return printFullDetails();
	}
	public String printFullDetails(){
		return "\t" + price + "\t" + volume + "\t" + id;
	}
	public boolean equals(Order other){
		return (this.id.equals(other.getId())
				&& this.price==other.getPrice() 
				&& this.volume==other.getVolume());
	}
	public int compareTo(Order otherOrder) {
		if (this.price > otherOrder.getPrice())
			return 1;
		else if (this.price == otherOrder.getPrice())
			return 0;
		else
			return -1;
	}
}
