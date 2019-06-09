package base.upp.nc.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import base.upp.nc.domain.User;
import base.upp.nc.service.RESTService;
import base.upp.nc.service.UserService;
import base.upp.nc.web.dto.DTOFormField;
import base.upp.nc.web.dto.FormFieldsDto;
import base.upp.nc.web.dto.TaskDto;

@RestController
@RequestMapping(value = "/api/rest/task")
public class RestTaskController {
	
	private RESTService restProcessService =  new RESTService();
	
	@Autowired
	private UserService userService;

//	@Autowired
//	private RuntimeService runtimeService;
	
	@Autowired
	private RESTService runtimeService;

//	@Autowired
//	TaskService taskService;
//
//	@Autowired
//	FormService formService;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllTasks(Principal principal) {
		User user = userService.findByEmail(principal.getName());

		List<org.camunda.bpm.engine.rest.dto.task.TaskDto> tasks = runtimeService.getTaskAssignee(user.getId().toString());

		List<TaskDto> result = new ArrayList<>();
		for (org.camunda.bpm.engine.rest.dto.task.TaskDto task : tasks) {
			result.add(new TaskDto(task.getId(), task.getName(),
					runtimeService.getVariable(task.getProcessInstanceId(), "magazineId").getValue().toString(),
					runtimeService.getVariable(task.getProcessInstanceId(), "magazineTitle").getValue().toString()));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(path = "/{taskId}")
	public ResponseEntity<?> getTask(@PathVariable String taskId) {
		System.out.println("TaskController.getTask...Getting the form data for the task with id: " + taskId);
		 org.camunda.bpm.engine.rest.dto.task.TaskDto task = runtimeService.getTask(taskId);  
		List<DTOFormField> tfd = runtimeService.getTaskForm(taskId, task);

		return new ResponseEntity<>(new FormFieldsDto(taskId, "publishScientificPaper", tfd), HttpStatus.OK);
	}

	@PostMapping(path = "executeTask/{taskId}")
	public ResponseEntity<?> executeTask(@RequestBody Map<String, Object> form, @PathVariable String taskId) {
		System.out.println("TaskController.executeTask...Executing the task with id: " + taskId);
		System.out.println("TaskController.executeTask...Following form data received:");
		System.out.println(form);

		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		org.camunda.bpm.engine.rest.dto.task.TaskDto task = runtimeService.getTaskAssignee(author.getId().toString()).get(0);
		if (task == null) {
			System.out.println("TaskController.executeTask...Task completed succesfully.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

//		runtimeService.setVariables(task.getProcessInstanceId(), form);
//		taskService.complete(taskId);
		
//		formService.submitTaskForm(taskId, form);
		Map<String, VariableValueDto> variables = new HashMap<>();
		variables = setFormVariables(variables,form);
		this.runtimeService.submitTaskFrom(taskId, variables);


		System.out.println("TaskController.executeTask...Task completed succesfully.");

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(path = "executeTaskReviewers/{taskId}")
	public ResponseEntity<?> executeTaskReviewers(@RequestBody Map<String, Object> form, @PathVariable String taskId) {
		System.out.println("TaskController.executeTaskReviewers...Executing the task with id: " + taskId);
		System.out.println("TaskController.executeTaskReviewers...Following form data received:");
		System.out.println(form);

		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		org.camunda.bpm.engine.rest.dto.task.TaskDto task = runtimeService.getTaskAssignee(author.getId().toString()).get(0);

		if (task == null) {
			System.out.println("TaskController.executeTask...Task completed succesfully.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Map<String, VariableValueDto> variables = new HashMap<>();
		if (form.get("review") != null) {
			System.out.println("Found review");
			List<Map<String, Object>> reviews = (List<Map<String, Object>>) runtimeService
					.getVariable(task.getProcessInstanceId(), "reviews");

			System.out.println("Before adding");
			reviews.add((Map<String, Object>) form.get("review"));
//			runtimeService.setVariable(task.getProcessInstanceId(), "reviews", reviews);
			VariableValueDto variableValue= new VariableValueDto();
			variableValue.setValue(reviews);
			variables.put("reviews", variableValue);
//			this.runtimeService.submitTaskFrom(taskId, variables);
		}
		
//		runtimeService.setVariables(task.getProcessInstanceId(), form);
//		taskService.complete(taskId);
		
		variables = setFormVariables(variables,form);
		
		this.runtimeService.submitTaskFrom(taskId, variables);
		
		System.out.println("TaskController.executeTaskReviewers...Task completed succesfully.");

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private Map<String, VariableValueDto> setFormVariables(Map<String, VariableValueDto> variables,Map<String, Object> form){
		for (Map.Entry<String, Object> entry : form.entrySet()) {
		    System.out.println(entry.getKey() + "/" + entry.getValue());	
			VariableValueDto variableValue= new VariableValueDto();
			variableValue.setValue(entry.getValue());
			variables.put(entry.getKey(), variableValue);
		}
		return variables;
	}
}
