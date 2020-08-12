package com.srit.market.register.register;


import java.io.Serializable;

public class RegisterModel implements Serializable {
	private String username;
	private String password;
	private String adress;
	private String gender;
	private int phone;

	public RegisterModel(String username, String password, String adress, String gender, int phone) {
		this.username = username;
		this.password = password;
		this.adress = adress;
		this.gender = gender;
		this.phone = phone;
	}

	public String getUsername(){
		return username;
	}

	public String getPassword(){
		return password;
	}

	public String getAdress(){
		return adress;
	}

	public String getGender(){
		return gender;
	}

	public int getPhone(){
		return phone;
	}

	@Override
 	public String toString(){
		return 
			"OrderPostModel{" + 
			"username = '" + username + '\'' + 
			",password = '" + password + '\'' + 
			",adress = '" + adress + '\'' + 
			",gender = '" + gender + '\'' + 
			",phone = '" + phone + '\'' + 
			"}";
		}
}