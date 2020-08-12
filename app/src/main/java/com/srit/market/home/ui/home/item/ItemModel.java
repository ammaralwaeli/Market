package com.srit.market.home.ui.home.item;

import java.io.Serializable;

public class ItemModel implements Serializable {
	private int price;
	private String photo;
	private String description;
	private int isActive;
	private int categoryID;
	private int id;
	private String createdat;
	private String itemName;
	private int discount;

	public String getPrice(){
		return price+"";
	}

	public String getPhoto(){
		return photo;
	}

	public String getDescription(){
		return description;
	}

	public int getIsActive(){
		return isActive;
	}

	public int getCategoryID(){
		return categoryID;
	}

	public int getId(){
		return id;
	}

	public String getCreatedat(){
		return createdat;
	}

	public String getItemName(){
		return itemName;
	}

	public String getDiscount(){
		return discount+"";
	}

	@Override
 	public String toString(){
		return 
			"ItemModel{" + 
			"price = '" + price + '\'' + 
			",photo = '" + photo + '\'' + 
			",description = '" + description + '\'' + 
			",isActive = '" + isActive + '\'' + 
			",categoryID = '" + categoryID + '\'' + 
			",id = '" + id + '\'' + 
			",createdat = '" + createdat + '\'' + 
			",itemName = '" + itemName + '\'' + 
			",discount = '" + discount + '\'' + 
			"}";
		}
}