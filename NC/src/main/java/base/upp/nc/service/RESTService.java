package base.upp.nc.service;

import java.util.Map;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.runtime.StartProcessInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.CompleteTaskDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import base.upp.nc.web.dto.DTOFormField;


@Service
public class RESTService {

	private RestTemplate restTemplate = new RestTemplate();
	
	public ProcessInstanceDto startProcess(String key, Map<String, VariableValueDto> variables) {
		StartProcessInstanceDto spi = new StartProcessInstanceDto();
		spi.setVariables(variables);
		
		ProcessInstanceDto pi = this.restTemplate.postForObject("http://localhost:8080/rest/process-definition/key/" + key + "/start", 
				spi, ProcessInstanceDto.class);
		return pi;
	}
	
	public TaskDto getNextTask(String processId) {
		ResponseEntity<List<TaskDto>> response = this.restTemplate.exchange("http://localhost:8080/rest/task?processInstanceId=" + processId, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<TaskDto>>(){});
		List<TaskDto> tasks = response.getBody();
		return tasks.get(0);
	}
	
	public TaskDto getTask(String taskId) {
		/*ResponseEntity<TaskDto> response = restTemplate.exchange("http://localhost:8080/rest/task/" + taskId, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<TaskDto>(){});
		TaskDto task = response.getBody();*/
		TaskDto task = restTemplate.getForObject("http://localhost:8080/rest/task/" + taskId, 
				TaskDto.class);
		return task;
	}
	
	public List<TaskDto> getTaskAssignee(String assignee) {
		ResponseEntity<List<TaskDto>> response = this.restTemplate.exchange("http://localhost:8080/rest/task?assignee=" + assignee, 
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<TaskDto>>(){});
		List<TaskDto> tasks = response.getBody();
		return tasks;
	}
	
	public VariableValueDto getVariable(String processId, String name) {
		System.out.println("VARIABLE NAME: " + name);
		VariableValueDto vv = this.restTemplate.getForObject("http://localhost:8080/rest/process-instance/" + processId + "/variables/" + name, 
				VariableValueDto.class);
		return vv;
	}
	
	public List<DTOFormField> getTaskForm(String taskId,org.camunda.bpm.engine.rest.dto.task.TaskDto task) {
		String processId= task.getProcessInstanceId();
		String taskName= task.getName();
		ResponseEntity<LinkedHashMap<String, VariableValueDto>> responseAllVariables = this.restTemplate.exchange("http://localhost:8080/rest/process-instance/" + processId + "/variables/",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<LinkedHashMap<String, VariableValueDto>>() {});
		LinkedHashMap<String, VariableValueDto> mapVariables =responseAllVariables.getBody();
		List<DTOFormField> Variables = new ArrayList<>();
		for (String key : mapVariables.keySet()) {
			DTOFormField ff = new DTOFormField();
			ff.setId(key);
			ff.setType(mapVariables.get(key).getType());
			ff.setLabel(capitalize(key));
			Variables.add(ff);
		}
		
		ResponseEntity<LinkedHashMap<String, VariableValueDto>> response = this.restTemplate.exchange("http://localhost:8080/rest/task/" + taskId + "/form-variables",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<LinkedHashMap<String, VariableValueDto>>() {});
		LinkedHashMap<String, VariableValueDto> map = response.getBody();
		List<DTOFormField> formFields = new ArrayList<>();
		for (String key : map.keySet()) {
			boolean isVariable= false;
			for (DTOFormField variable : Variables) {
				if(variable.getId().equals(key)) {
					if(taskName.equals("Scientific paper evalutation")) {
						if(!key.equals("bigCorrection") 
						&& !key.equals("smallCorrection")
						&& !key.equals("refused")
						&& !key.equals("accepted")
						&& !key.equals("anotherReview")) {
							isVariable=true;
							break;
						}
					}
					else if(taskName.equals("Urednik odlucuje sta dalje sa radom")) {
						if(!key.equals("isPaperAccepted") 
						&& !key.equals("smallChangesRequired")
						&& !key.equals("addNewReviewer")
						&& !key.equals("hugeChangesRequired")) {
							isVariable=true;
							break;
						}
					}else if(taskName.equals("Reviewing process")) {
						if(!key.equals("review")) {
							isVariable=true;
							break;
						}
					}else if(taskName.equals("Correction")) {
						if(!key.equals("content") && !key.equals("correctionComment")) {
							isVariable=true;
							break;
						}
					}else if(taskName.equals("Check scientific paper")) {
						if(!key.equals("paperApproved") && !key.equals("notRelevant") && !key.equals("badFormating") ) {
							isVariable=true;
							break;
						}
					}else if(taskName.equals("Submit paper again")) {
						if(!key.equals("pdfContent") ) {
							isVariable=true;
							break;
						}
					}else {
						isVariable=true;
						break;
					}
				}
					
			}
			if(!isVariable) {
				DTOFormField ff = new DTOFormField();
				ff.setId(key);
				ff.setType(map.get(key).getType());
				ff.setLabel(capitalize(key));
				formFields.add(ff);
			}
		}
		

		return formFields;
	}
	
	public void submitTaskFrom(String taskId, Map<String, VariableValueDto> variables) {
		CompleteTaskDto completeDto = new CompleteTaskDto();
		completeDto.setVariables(variables);
		@SuppressWarnings("unused")
		ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8080/rest/task/" + taskId + "/submit-form", completeDto, Void.class);
	}
	
	public void completeTask(String taskId) {
		CompleteTaskDto completeDto = new CompleteTaskDto();
		@SuppressWarnings("unused")
		ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:8080/rest/task/" + taskId + "/complete", completeDto, Void.class);
	}
	
	private String capitalize(final String line) {
		   return Character.toUpperCase(line.charAt(0)) + line.substring(1);
		}
}

