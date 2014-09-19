package com.guguluk.sausozluk.dto;

import java.util.List;

@SuppressWarnings("unused")
public class SearchData {
   	private List<Topic> topics;
   	private List<User> users;

 	public List<Topic> getTopics(){
		return this.topics;
	}
	public void setTopics(List<Topic> topics){
		this.topics = topics;
	}
 	public List<User> getUsers(){
		return this.users;
	}
	public void setUsers(List<User> users){
		this.users = users;
	}
}
