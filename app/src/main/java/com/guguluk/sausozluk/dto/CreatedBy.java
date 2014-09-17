package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class CreatedBy{
   	private String _id;
   	private String clean;
   	private String username;

 	public String get_id(){
		return this._id;
	}
	public void set_id(String _id){
		this._id = _id;
	}
 	public String getClean(){
		return this.clean;
	}
	public void setClean(String clean){
		this.clean = clean;
	}
 	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username = username;
	}
}
