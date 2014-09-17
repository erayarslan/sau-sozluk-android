package com.guguluk.sausozluk.dto;

import java.util.List;

@SuppressWarnings("unused")
public class EntryData {
   	private List<Entry> entry;
   	private String page;
   	private Number pages;
   	private Topic topic;

 	public List<Entry> getEntry(){
		return this.entry;
	}
	public void setEntry(List<Entry> entry){
		this.entry = entry;
	}
 	public String getPage(){
		return this.page;
	}
	public void setPage(String page){
		this.page = page;
	}
 	public Number getPages(){
		return this.pages;
	}
	public void setPages(Number pages){
		this.pages = pages;
	}
 	public Topic getTopic(){
		return this.topic;
	}
	public void setTopic(Topic topic){
		this.topic = topic;
	}
}
