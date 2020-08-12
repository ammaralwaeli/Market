package com.srit.market.home.ui.home.slider;

import java.io.Serializable;

public class SliderModel implements Serializable {
	private int id;
	private String photo;
	private String createdAt;

	public int getId(){
		return id;
	}

	public String getPhoto(){
		return photo;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	@Override
 	public String toString(){
		return 
			"SliderModel{" + 
			"id = '" + id + '\'' + 
			",photo = '" + photo + '\'' + 
			",createdAt = '" + createdAt + '\'' + 
			"}";
		}
}