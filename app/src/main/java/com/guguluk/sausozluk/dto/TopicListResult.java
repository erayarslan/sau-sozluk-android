
package com.guguluk.sausozluk.dto;

import java.util.List;

@SuppressWarnings("unused")
public class TopicListResult {
   	private Number count;
   	private List<Topic> data;
   	private String status;

 	public Number getCount(){
		return this.count;
	}
	public void setCount(Number count){
		this.count = count;
	}
 	public List<Topic> getData(){
		return this.data;
	}
	public void setData(List<Topic> data){
		this.data = data;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
