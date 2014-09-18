package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class User{
   	private String _id;
   	private Number auth;
   	private String clean;
   	private String email;
   	private Number entry_count;
   	private Number generation;
   	private String lastLogin;
   	private String message;
   	private String picture;
   	private Number point;
   	private String registeredAt;
   	private String username;

 	public String get_id(){
		return this._id;
	}
	public void set_id(String _id){
		this._id = _id;
	}
 	public Number getAuth(){
		return this.auth;
	}
	public void setAuth(Number auth){
		this.auth = auth;
	}
 	public String getClean(){
		return this.clean;
	}
	public void setClean(String clean){
		this.clean = clean;
	}
 	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
 	public Number getEntry_count(){
		return this.entry_count;
	}
	public void setEntry_count(Number entry_count){
		this.entry_count = entry_count;
	}
 	public Number getGeneration(){
		return this.generation;
	}
	public void setGeneration(Number generation){
		this.generation = generation;
	}
 	public String getLastLogin(){
		return this.lastLogin;
	}
	public void setLastLogin(String lastLogin){
		this.lastLogin = lastLogin;
	}
 	public String getMessage(){
		return this.message;
	}
	public void setMessage(String message){
		this.message = message;
	}
 	public String getPicture(){
		return this.picture;
	}
	public void setPicture(String picture){
		this.picture = picture;
	}
 	public Number getPoint(){
		return this.point;
	}
	public void setPoint(Number point){
		this.point = point;
	}
 	public String getRegisteredAt(){
		return this.registeredAt;
	}
	public void setRegisteredAt(String registeredAt){
		this.registeredAt = registeredAt;
	}
 	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username = username;
	}
}
