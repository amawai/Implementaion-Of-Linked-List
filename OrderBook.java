/**
 *OrderBook is a doubly-linked list containing BidOrder and OfferOrder objects
 *It is sorted from highest-lowest price. If the prices are equal, they will be placed according to whether thay
 *are BidOrders or OfferOrders--newly added equally priced BidOrders are placed after the equally priced BidOrders
 *and newly added equally priced OfferOrders are placed before.  
 * @author Amanda Wai
 * 
 */
public class OrderBook implements anonymous{
	private OrderNode head;
	private OrderNode bestBid;
	private OrderNode bestOffer;
	public OrderBook(){
		head = null;
	}
	
	/**
	 * Inner class of OuterBook--serves as the nodes
	 */
	class OrderNode{
		private Order item;
	    private OrderNode prevLink;
	    private OrderNode nextLink;
	    
	    public OrderNode(){
	    	item = null;
	    	prevLink = null;
	    	nextLink = null;
	    } 
	    public OrderNode(Order newOrder, OrderNode previous, OrderNode next){
	        setData(newOrder);
	        prevLink = previous;
	        nextLink = next;  
	    }
	    public void setData(Order newOrder){
	    	if (newOrder instanceof BidOrder)
	    		item = new BidOrder(newOrder.getId(), newOrder.getPrice(), newOrder.getVolume());	
	    	else if (newOrder instanceof OfferOrder)
	    		item = new OfferOrder(newOrder.getId(), newOrder.getPrice(), newOrder.getVolume());
	    	else
	    		item = new Order(newOrder.getId(), newOrder.getPrice(), newOrder.getVolume());
	    }
	    public void setPrevLink(OrderNode previousLink){
	    	prevLink = previousLink;
	    }
	    public void setNextLink(OrderNode newLink){
	    	nextLink = newLink;
	    }
	    public Order getItem(){
	    	return item;
	    }
	    public Order getDeepCopyItem(){
	    	if (item instanceof BidOrder)
	    		return new BidOrder(item.getId(), item.getPrice(), item.getVolume());	
	    	else if (item instanceof OfferOrder)
	    		return new OfferOrder(item.getId(), item.getPrice(), item.getVolume());
	    	else
	    		return new Order(item.getId(), item.getPrice(), item.getVolume());
	    }
	    
	    public OrderNode getNextLink(){
	    	return nextLink;
	    }
	    public OrderNode getPrevLink(){
	    	return prevLink;
	    }
	    
	    /**
	     * Method that returns a String of the Order type stored in the node.
	     * @return Type of order--i.e. "Bid" or "Offer"
	     */
	    public String getType(){
	    	if (item instanceof BidOrder)
	    		return "Bid";
	    	else if (item instanceof OfferOrder)
	    		return "Offer";
	    	else
	    		return "Order";
	    }
	}
	/**
	 * This method returns the highest priced bid (the "best")
	 * @return Pointer to the best bid
	 */
	public OrderNode getBestBidPointer(){
		getBestBid(); //Getting the latest best bid
		return bestBid;
	}
	/**
	 * This method returns the lowest priced offer (the "best")
	 * @return Pointer to the best offer
	 */
	public OrderNode getBestOfferPointer(){
		getBestOffer(); //Getting the latest best offer
		return bestOffer;
	}
	/**
	 * Prints out the contents of the OrderBook
	 */
	public void outputBook(){
		OrderNode current = head;
		while (current!=null){
			System.out.println(current.getItem().toString(false));
			current = current.getNextLink();
		}
	}
	/**
	 * Prints out the best offer and bid, respectively
	 */
	public void outputBBO(){
		System.out.println("Best Bid & Offer: " + "\n" +
							"Off: " + getBestOffer() + "\n" + 
							"Bid: " + getBestBid() );	
	}
	public String toString(boolean matchFound){
		String bookOrder = "";
		if (!matchFound){
			OrderNode current = head;
			while (current!=null){
				bookOrder += (current.getItem().toString(matchFound) + "\n");
				current = current.getNextLink();
			}
			return bookOrder;
		}
		else
			return printFullDetails();
	}
	public String printFullDetails(){
		String fullDetails = "";
		OrderNode current = head;
		while (current!=null){
			fullDetails += (current.getItem().toString(true) + "\n");
			current = current.getNextLink();
		}
		return fullDetails;
	}

	/**
	 * A method that returns the best offer from the OrderBook: the lowest-priced offer.
	 * This method also sets the bestOffer pointer
	 * @return The lower price of an OfferOrder, if it's 0 it means that there were no offer
	 */
	public double getBestOffer(){
		OrderNode current = head; 
		double firstOffer = 0;
		double bestOfferPrice = 0;
		boolean offerPresent = false;
		//Getting the price of the first offer 
		if (current.getNextLink() != null){
			while(current != null){
				if (current.getType().equals("Offer")){
					firstOffer = current.getItem().getPrice();
					offerPresent = true;
					break;
				}
				current = current.getNextLink(); 
			}
			bestOfferPrice = firstOffer;
			while (current!=null){ //Go down the list until the lowest offer is encountered
				 if (current.getType().equals("Offer") && current.getItem().getPrice()<=bestOfferPrice){
					 bestOfferPrice = current.getItem().getPrice();
					 bestOffer = current; //Setting the bestOffer pointer
					 offerPresent = true;
				 }
				 current = current.getNextLink();
			}
		}
		else{ //If first item is an instance of OfferOrder
			if (current.getItem() instanceof OfferOrder){
				bestOfferPrice = current.getItem().getPrice();
				bestOffer = current;
				offerPresent = true;
			}
		}
		if (!offerPresent){ //If no offers were found then the bestOffer will point to null
			bestOffer = null;
		}
		return bestOfferPrice; //If it returns 0, there is no best offer.
	}
	/**
	 * A method that returns the best bid from the OrderBook: the highest-priced bid.
	 * This method also sets the bestBid pointer
	 * @return The highest price of a BidOrder, if it's 0 it means that there were no bids
	 */
	public double getBestBid(){
		OrderNode current = head;
		double bestBidPrice = 0;
		boolean bidPresent = false;
		if (current.getNextLink() != null){
			//Getting price of first bid, which should also be the best bid since the first occurence of a bid is the highest priced
			while (current!=null){
				if (current.getType().equals("Bid")){
					bestBidPrice = current.getItem().getPrice();
					bestBid = current; //Setting the bestBid pointer to the bestBid
					bidPresent = true;
					break;
				}
				current = current.getNextLink();
			}
		}
		else{ //If first item is an instance of BidOrder
			if (current.getItem() instanceof BidOrder){
				bestBidPrice = current.getItem().getPrice();
				bestBid = current;
				bidPresent = true;
			}
		}
		if (!bidPresent) //If no bids were found, then sent the bestBid pointer to null
			bestBid = null;
		return bestBidPrice;
	}
	
	public boolean delete(Order orderToDelete){
		boolean deleted = false;
		OrderNode current = head;
		if (orderToDelete instanceof BidOrder){
			if (orderToDelete.getPrice() > getBestBid())
				deleted = false; //BestBid is the highest price, if item to be deleted is higher, it shouldn't exist
			else if (orderToDelete.equals(bestBid.getItem())){
				deleteNode(bestBid);
				getBestBid(); //Getting the new bestBid
				deleted = true;
			}
			//If there are duplicate "bestBid" prices, it can be found before or after the "bestBid"
			else if (orderToDelete.getPrice() == getBestBid()){
				current = bestBid;
				while(current!=null && orderToDelete.getPrice() <= getBestBid()){
					if (orderToDelete.equals(current.getItem())){
						deleteNode(current);
						deleted = true;
						break;
					}
					current = current.getPrevLink();
				}
			}
			else if (orderToDelete.getPrice() <= getBestBid()){
				current = bestBid;
				while (current != null){
					if (orderToDelete.equals(current.getItem())){
						deleteNode(current);
						deleted = true;
						break;
					}
					current = current.getNextLink();
				}
			}
		}
		else if (orderToDelete instanceof OfferOrder){
			if (orderToDelete.getPrice() < getBestOffer())
				deleted = false; //BestOffer is the lowest price, if item to be deleted is higher, it shouldn't exist
			else if (orderToDelete.equals(bestOffer.getItem())){
				deleteNode(bestOffer);
				getBestOffer(); //Getting the new bestOffer
				deleted = true;
			}
			else if (orderToDelete.getPrice() == getBestOffer()){
				current = bestOffer;
				while(current!=null && orderToDelete.getPrice() >= getBestOffer()){
					if (orderToDelete.equals(current.getItem())){
						deleteNode(current);
						deleted = true;
						break;
					}
					current = current.getNextLink();
				}
			}
			else if (orderToDelete.getPrice() >= getBestOffer()){
				current = bestOffer;
				while (current != null){
					if (orderToDelete.equals(current.getItem())){
						deleteNode(current);
						deleted = true;
						break;
					}
					current = current.getPrevLink();
				}
			}
		}
		return deleted;
	}
	
	/**
	 * Helper method to delete a node
	 * @param nodeToDelete This argument represents the node to be deleted
	 */
	private void deleteNode(OrderNode nodeToDelete){
		if (nodeToDelete.getPrevLink()== null){ //If it's the first node
			head = nodeToDelete.getNextLink();
			nodeToDelete.getNextLink().setPrevLink(null);
		}
		else if (nodeToDelete.getNextLink() == null){ //If it's the last node
			nodeToDelete.getPrevLink().setNextLink(null);
		}
		else{
			nodeToDelete.getPrevLink().setNextLink(nodeToDelete.getNextLink());
			nodeToDelete.getNextLink().setPrevLink(nodeToDelete.getPrevLink());
		}
	}

	/**
	 * Adds a new Order object to the OrderBook from highest to lowest price.
	 * Bids of equal price go after the previous equally-priced bids in a FIFO manner
	 * Offers of equal price go before previous equally-priced offers in a LIFO manner
	 * @param orderAdded The newest Order to be added to the OrderBook
	 */
	public void add(Order orderAdded){
		boolean done = false;
		//If newOrder is the first item in the OrderBook, the head will point to it
		if (head == null){
			head = new OrderNode(orderAdded, null, null);
		}
		else{ //Put in order from highest to lowest price
			OrderNode currentNode = head;
			if (currentNode.getNextLink() == null){ //Applicable if first node is initialized but not the second
				if(!addInOrder(orderAdded, currentNode))
					head.setNextLink(new OrderNode(orderAdded, head, null));
			}
			//Either the last node is reached or the newOrder gets added to the OrderBook
			else{
				while (currentNode.getNextLink() != null){
					if(!addInOrder(orderAdded, currentNode)){
						currentNode = currentNode.getNextLink(); //If no conditions were met, then the current node is now the next node
					}else{
						done = true;
						break;
					}
				}
				//If none of the conditions previously have been met, then the newOrder will be placed at the end of the OrderBook
				if (!done)
					currentNode.setNextLink(new OrderNode(orderAdded,currentNode, null));
			}
		}
		//Updates the bestOffer and bestBid pointers
		getBestOffer();
		getBestBid();
	}
	/**
	 * This method determines whether the added order is able to be placed before or after the argument node
	 * @param orderAdded Order object to be added to the OrderBook
	 * @param currentNode The current node that the orderAdded will be compared to, pricewise
	 * @return True if orderAdded was succesfully placed, false if it wasn't added (if the price of orderAdded was lower than currentNode, in which case a new "currentNode" value is required)
	 */
	private boolean addInOrder(Order orderAdded, OrderNode currentNode){
		boolean done = false;
		//If the price is equal
		if (orderAdded.compareTo(currentNode.getItem()) == 0){
			//For "BidOrders", the newOrder will be placed after the last equally priced "Bid".
			if (orderAdded instanceof BidOrder && currentNode.getType().equals("Bid") && (currentNode.getNextLink()!=null) && (currentNode.getNextLink().getItem().compareTo(currentNode.getItem())!=0)){
				//if the next link isn't empty, otherwise it will be added to the end of the OrderBook (outside of this method)
				if (currentNode.getNextLink()!=null){
					placeOrderAfterCurrentNode(orderAdded, currentNode);
					done = true;
				}
			}
			else if (orderAdded instanceof OfferOrder){
				//If previous link isn't empty
				if (currentNode.getPrevLink() != null)
					placeOrderBeforeCurrentNode(orderAdded, currentNode);
				else 
					placeAtStart(orderAdded, currentNode);
				done = true;
			}
		}
		else if(orderAdded.compareTo(currentNode.getItem()) > 0){
			//If the newOrder has the highest price, it will be placed at the head of the OrderBook
			if(currentNode.getPrevLink()== null)
				placeAtStart(orderAdded, currentNode);
			else
				placeOrderBeforeCurrentNode(orderAdded, currentNode);
			done = true;
		}
		return done;
	}
	
	/**
	 * Method that places an OrderNode at the start of the OrderBook
	 * @param orderAtStart Order to be placed at the start of the OrderBook
	 * @param formerFirstNode The second node
	 */
	private void placeAtStart(Order orderAtStart, OrderNode formerFirstNode){
		head = new OrderNode(orderAtStart, null, formerFirstNode);
		formerFirstNode.setPrevLink(head);
	}
	/**
	 * Private helper method for the "add" method, places a new order before a specific order
	 * @param orderBeforeNode The new order to be placed before a node whose next link will point to currentNode
	 * @param currentNode The current node where the previous link will point to the "orderBeforeNode"
	 */
	private void placeOrderBeforeCurrentNode(Order orderBeforeNode, OrderNode currentNode){
		OrderNode newOrder = new OrderNode(orderBeforeNode, currentNode.getPrevLink(), currentNode);
		currentNode.getPrevLink().setNextLink(newOrder); //the current node's previous node's next link is the orderBeforeNode
		currentNode.setPrevLink(newOrder); //the current node's previous link is the orderBeforeNode
	}
	/**
	 * Private helper method for the "add" method places a new order after a specific order
	 * @param orderAfterNode The new order to be placed after a node whose previous node will point to currentNode
	 * @param currentNode The current node where the next link will point to the "orderAfterNode"
	 */
	private void placeOrderAfterCurrentNode(Order orderAfterNode, OrderNode currentNode){
		OrderNode newOrder = new OrderNode(orderAfterNode, currentNode, currentNode.getNextLink());
		currentNode.getNextLink().setPrevLink(newOrder); //Points the node after the currentNode's previous link to the orderAfterNode
		currentNode.setNextLink(newOrder); //Point the currentNode's next link to the orderAfterNode
	}	
}
