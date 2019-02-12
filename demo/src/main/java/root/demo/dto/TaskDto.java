package root.demo.dto;

public class TaskDto {
	
	String taskId;
	String taskName;
	String taskAssignee;
	
	public TaskDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	   public TaskDto(String taskId, String taskName){
	        this.taskId = taskId;
	        this.taskName = taskName;
	    }
	   
	public TaskDto(String taskId, String name, String assignee) {
		super();
		this.taskId = taskId;
		this.taskName = name;
		this.taskAssignee = assignee;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return taskName;
	}

	public void setName(String name) {
		this.taskName = name;
	}

	public String getAssignee() {
		return taskAssignee;
	}

	public void setAssignee(String assignee) {
		this.taskAssignee = assignee;
	}

}
