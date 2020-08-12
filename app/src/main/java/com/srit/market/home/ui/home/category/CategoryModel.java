package com.srit.market.home.ui.home.category;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class CategoryModel implements Serializable {
	private int categoryID;
	private String name;
	private String photo;

	public int getCategoryID(){
		return categoryID;
	}

	public String getName(){
		return name;
	}

	public String getPhoto(){
		return photo;
	}

	@NotNull
	@Override
 	public String toString(){
		return 
			"CategoryModel{" + 
			"categoryID = '" + categoryID + '\'' + 
			",name = '" + name + '\'' + 
			",photo = '" + photo + '\'' + 
			"}";
		}
}