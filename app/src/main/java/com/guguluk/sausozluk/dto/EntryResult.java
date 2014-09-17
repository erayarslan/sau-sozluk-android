package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class EntryResult{
   	private Entry data;
   	private String status;

 	public Entry getData(){
		return this.data;
	}
	public void setData(Entry data){
		this.data = data;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
