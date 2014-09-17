package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class Entry{
   	private String _id;
   	private String createdAt;
   	private CreatedBy createdBy;
   	private Number status;
   	private String text;
   	private Topic topic;
   	private String updatedAt;

 	public String get_id(){
		return this._id;
	}
	public void set_id(String _id){
		this._id = _id;
	}
 	public String getCreatedAt(){
		return this.createdAt;
	}
	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}
 	public CreatedBy getCreatedBy(){
		return this.createdBy;
	}
	public void setCreatedBy(CreatedBy createdBy){
		this.createdBy = createdBy;
	}
 	public Number getStatus(){
		return this.status;
	}
	public void setStatus(Number status){
		this.status = status;
	}
 	public String getText(){
		return this.text;
	}
	public void setText(String text){
		this.text = text;
	}
 	public Topic getTopic(){
		return this.topic;
	}
	public void setTopic(Topic topic){
		this.topic = topic;
	}
 	public String getUpdatedAt(){
		return this.updatedAt;
	}
	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}
}
