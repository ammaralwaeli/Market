package com.srit.market.home.ui.more;

import java.io.Serializable;

public class ProfileModel implements Serializable {
	private String username;
	private int userId;
	private String role;
	private String location;
	private int iat;
	private int exp;

	public String getUsername(){
		return username;
	}

	public int getUserId(){
		return userId;
	}

	public String getRole(){
		return role;
	}

	public String getLocation(){
		return location;
	}

	public int getIat(){
		return iat;
	}

	public int getExp(){
		return exp;
	}

	@Override
 	public String toString(){
		return 
			"ProfileModel{" + 
			"username = '" + username + '\'' + 
			",user_id = '" + userId + '\'' + 
			",role = '" + role + '\'' + 
			",location = '" + location + '\'' + 
			",iat = '" + iat + '\'' + 
			",exp = '" + exp + '\'' + 
			"}";
		}
}