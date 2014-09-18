package com.guguluk.sausozluk.dto;

import java.util.List;

@SuppressWarnings("unused")
public class UserResult{
   	private User data;
   	private List<Entries> entries;
   	private String status;

 	public User getData(){
		return this.data;
	}
	public void setData(User data){
		this.data = data;
	}
 	public List<Entries> getEntries(){
		return this.entries;
	}
	public void setEntries(List<Entries> entries){
		this.entries = entries;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
