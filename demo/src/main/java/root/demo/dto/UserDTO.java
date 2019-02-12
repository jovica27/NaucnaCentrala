package root.demo.dto;

import java.io.Serializable;

import root.demo.model.User;


public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private String token;
	private TaskDto taskDTO;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public TaskDto getTaskDTO() {
		return taskDTO;
	}
	public void setTaskDTO(TaskDto taskDTO) {
		this.taskDTO = taskDTO;
	}
	
	
	public UserDTO(User user, String token, TaskDto taskDTO) {
		super();
		this.user = user;
		this.token = token;
		this.taskDTO = taskDTO;
	}
	
	
	
	
}
