package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class SearchResult{
   	private SearchData data;
   	private String status;

 	public SearchData getData(){
		return this.data;
	}
	public void setData(SearchData searchData){
		this.data = searchData;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
