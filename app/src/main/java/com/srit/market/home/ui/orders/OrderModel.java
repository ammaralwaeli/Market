package com.srit.market.home.ui.orders;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderModel implements Serializable {
	private int OrderID;
	private String createdAt;
	private int userID;
	private String state;
	private int totalPrice;

	public int getOrderID(){
		return OrderID;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getDate(){
		String strDate;
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(createdAt);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			strDate = dateFormat.format(date1);
		}catch (Exception e){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			strDate = dateFormat.format(new Date());
		}
		return strDate;
	}

	public int getUserID(){
		return userID;
	}


	public String getState(){
		return state;
	}

	public int getTotalPrice(){
		return totalPrice;
	}

	@Override
 	public String toString(){
		return 
			"OrderModel{" + 
			"orderID = '" + OrderID + '\'' +
			",createdAt = '" + createdAt + '\'' + 
			",userID = '" + userID + '\'' + 
			",state = '" + state + '\'' + 
			",totalPrice = '" + totalPrice + '\'' + 
			"}";
		}
}