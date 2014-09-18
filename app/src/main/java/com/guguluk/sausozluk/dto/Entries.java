package com.guguluk.sausozluk.dto;

@SuppressWarnings("unused")
public class Entries{
   	private Number __v;
   	private String _id;
   	private String createdAt;
   	private String createdAtStr;
   	private CreatedByDetail createdBy;
   	private String createdOn;
   	private Number point;
   	private Number status;
   	private String text;
   	private Topic topic;
   	private String updatedAt;

 	public Number get__v(){
		return this.__v;
	}
	public void set__v(Number __v){
		this.__v = __v;
	}
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
 	public String getCreatedAtStr(){
		return this.createdAtStr;
	}
	public void setCreatedAtStr(String createdAtStr){
		this.createdAtStr = createdAtStr;
	}
 	public CreatedByDetail getCreatedBy(){
		return this.createdBy;
	}
	public void setCreatedBy(CreatedByDetail createdBy){
		this.createdBy = createdBy;
	}
 	public String getCreatedOn(){
		return this.createdOn;
	}
	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}
 	public Number getPoint(){
		return this.point;
	}
	public void setPoint(Number point){
		this.point = point;
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
