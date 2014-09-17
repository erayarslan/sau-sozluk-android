package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class EntryListResult{
   	private EntryData data;
   	private String status;

 	public EntryData getEntryData(){
		return this.data;
	}
	public void setEntryData(EntryData data){
		this.data = data;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
