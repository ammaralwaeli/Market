package com.srit.market.home.ui.new_order;

import java.util.List;
import java.io.Serializable;

public class OrderPostModel implements Serializable {
	private List<Integer> itemID;
	private List<Integer> itemCounts;
	private int totalPrice;


	public OrderPostModel(List<Integer> itemID, List<Integer> itemCounts, int totalPrice) {
		this.itemID = itemID;
		this.itemCounts = itemCounts;
		this.totalPrice = totalPrice;
	}

	public List<Integer> getItemID(){
		return itemID;
	}

	public List<Integer> getItemCounts(){
		return itemCounts;
	}

	public int getTotalPrice(){
		return totalPrice;
	}

	@Override
 	public String toString(){
		return 
			"OrderPostModel{" + 
			"itemID = '" + itemID + '\'' + 
			",itemCounts = '" + itemCounts + '\'' + 
			",totalPrice = '" + totalPrice + '\'' + 
			"}";
		}
}