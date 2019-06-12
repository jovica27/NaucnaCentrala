package base.upp.nc.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
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

import base.upp.nc.domain.*;
import base.upp.nc.repository.MagazineRepository;
import base.upp.nc.service.UserService;
import base.upp.nc.web.dto.DTOFormField;
import base.upp.nc.web.dto.FormFieldsDto;
import base.upp.nc.web.dto.TaskDto;


@RestController
@RequestMapping(value = "/api/task")
public class TaskController {

	@Autowired
	private UserService userService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	FormService formService;

//	@Autowired
//	MagazineService
	
	@Autowired
    private MagazineRepository magazineRepository;
	
	private String uploadType = "upload";
	private String downloadType = "download";
	private String infoType = "info";
	private String infoReviewerType = "reviewerType";

	
	@GetMapping("/getAll")
	public ResponseEntity<?> getAllTasks(Principal principal) {
		User user = userService.findByEmail(principal.getName());

		List<Task> tasks = taskService.createTaskQuery().active().taskAssignee(user.getId().toString()).list();

		List<TaskDto> result = new ArrayList<>();
		for (Task task : tasks) {
			result.add(new TaskDto(task.getId(), task.getName(),
					runtimeService.getVariable(task.getProcessInstanceId(), "magazineId").toString(),
					(String) runtimeService.getVariable(task.getProcessInstanceId(), "magazineTitle")));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(path = "/{taskId}")
	public ResponseEntity<?> getTask(@PathVariable String taskId) {
		System.out.println("TaskController.getTask...Getting the form data for the task with id: " + taskId);
		TaskFormData tfd = formService.getTaskFormData(taskId);

		List<FormField> properties = tfd.getFormFields();
		FormFieldsDto res = new FormFieldsDto();
		res.CreateFormFieldsDto(taskId, "publishScientificPaper", properties);
		res = getAdditionalInfo(res, taskId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}


	@PostMapping(path = "executeTask/{taskId}")
	public ResponseEntity<?> executeTask(@RequestBody Map<String, Object> form, @PathVariable String taskId) {
		System.out.println("TaskController.executeTask...Executing the task with id: " + taskId);
		System.out.println("TaskController.executeTask...Following form data received:");
		System.out.println(form);

		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list()
				.get(0);
		if (task == null) {
			System.out.println("TaskController.executeTask...Task completed succesfully.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		//ako je multi instance dodavanje review-a radi posebno
		if(task.getName().equals("Recenziranje rada")){
			addReview(task,form,author.getId().toString());
		}else {
		runtimeService.setVariables(task.getProcessInstanceId(), form);
		}
		taskService.complete(taskId);

		System.out.println("TaskController.executeTask...Task completed succesfully.");

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void addReview(Task task, Map<String, Object> form, String userId) {
		Map<String, String> reviews = (Map<String, String>) runtimeService
				.getVariable(task.getProcessInstanceId(), "reviews");
		if(reviews == null)
			reviews = new HashMap<String, String>();
		reviews.put(userId, form.get("review").toString());
		runtimeService.setVariable(task.getProcessInstanceId(),"reviews", reviews);
	}
	
	@PostMapping(path = "executeTaskReviewers/{taskId}")
	public ResponseEntity<?> executeTaskReviewers(@RequestBody Map<String, Object> form, @PathVariable String taskId) {
		System.out.println("TaskController.executeTaskReviewers...Executing the task with id: " + taskId);
		System.out.println("TaskController.executeTaskReviewers...Following form data received:");
		System.out.println(form);

		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list()
				.get(0);
		if (task == null) {
			System.out.println("TaskController.executeTask...Task completed succesfully.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
//
//		if (form.get("review") != null) {
//			System.out.println("Found review");
//			List<Map<String, Object>> reviews = (List<Map<String, Object>>) runtimeService
//					.getVariable(task.getProcessInstanceId(), "reviews");
//
//			System.out.println("Before adding");
//			reviews.add((Map<String, Object>) form.get("review"));
//			runtimeService.setVariable(task.getProcessInstanceId(), "reviews", reviews);
//		}
		
		runtimeService.setVariables(task.getProcessInstanceId(), form);
		taskService.complete(taskId);
		System.out.println("TaskController.executeTaskReviewers...Task completed succesfully.");

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private FormFieldsDto getAdditionalInfo(FormFieldsDto ffd,String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).list().get(0);
		String taskName= task.getName();
		if(taskName.equals("Objava rada")){
			DTOFormField p = new DTOFormField("upload","Paper upload",uploadType);
			ffd.getFormFields().add(p);
		}else if( taskName.equals("Urednik pregleda rad tematski")) {
			String title=runtimeService.getVariable(task.getProcessInstanceId(), "title").toString();
			DTOFormField titleField = new DTOFormField("upload","Paper title",infoType,title);
			ffd.getFormFields().add(titleField);
			
			String abstractDescription=runtimeService.getVariable(task.getProcessInstanceId(), "abstractDescription").toString();
			DTOFormField abstractDescriptionField = new DTOFormField("upload","Paper abstract description",infoType,abstractDescription);
			ffd.getFormFields().add(abstractDescriptionField);
			
			String scientificArea=runtimeService.getVariable(task.getProcessInstanceId(), "scientificArea").toString();
			DTOFormField scientificAreaField = new DTOFormField("upload","Paper scientific area",infoType,scientificArea);
			ffd.getFormFields().add(scientificAreaField);
			
			String keywords=runtimeService.getVariable(task.getProcessInstanceId(), "keywords").toString();
			DTOFormField keywordsField = new DTOFormField("upload","Paper keywords",infoType,keywords);
			ffd.getFormFields().add(keywordsField);		
		}
		else if(taskName.equals("Pregleda se pdf")) {
			DTOFormField p = new DTOFormField("download","Paper download",downloadType);
			ffd.getFormFields().add(p);
		}else if(taskName.equals("Autor treba da ispravi rad")) {
			DTOFormField p = new DTOFormField("upload", "Paper upload", uploadType);
			ffd.getFormFields().add(p);
		}else if(taskName.equals("Odabir recezenata")) {
//			List<String> currentReviewers = (List<String>) runtimeService.getVariable(task.getProcessInstanceId(), "reviewers");
			// oni koji mogu da se odaberu su oni koji nisu dali svoju recenziju
			List<String> currentReviewers = new ArrayList<String>();
			Map<String, String> reviews = (Map<String, String>) runtimeService
					.getVariable(task.getProcessInstanceId(), "reviews");
			if(reviews != null) {
				for ( String key : reviews.keySet() ) {
				currentReviewers.add(key);
				}
			}
			 final Long magazineId = Long.valueOf(runtimeService.getVariable(task.getProcessInstanceId(),"magazineId").toString());
		     final Magazine magazine = magazineRepository.findById(magazineId).get();
		     List<User> magazineReviewers = new ArrayList<User>(magazine.getReviewers());
		     List<User> availableReviewers = new ArrayList<User>();
			if(currentReviewers.isEmpty()) {
				availableReviewers= magazineReviewers;
			}else {
				for (User user : magazineReviewers) {
					boolean isAlreadyReviewer= false;
					for(String currentReviewerId: currentReviewers) {
						if(user.getId().equals(Long.valueOf(currentReviewerId))) {
							isAlreadyReviewer=true;
						}
					}
					if(!isAlreadyReviewer) {
						availableReviewers.add(user);
					}
				}
			}
			for (User user : availableReviewers) {
				DTOFormField p = new DTOFormField(user.getId().toString(),user.getFirstName() +" "+user.getLastName(),infoReviewerType);
				ffd.getFormFields().add(p);
			}
		}else if(taskName.equals("Recenziranje rada")) {
			DTOFormField p = new DTOFormField("download","Paper download",downloadType);
			ffd.getFormFields().add(p);
		}else if(taskName.equals("Urednik odlucuje sta dalje sa radom")) {
			Map<String, String> reviews = (Map<String, String>) runtimeService
					.getVariable(task.getProcessInstanceId(), "reviews");
			if(reviews != null) {
				for ( Map.Entry<String,String> entry : reviews.entrySet() ) {
					User reviewer= userService.findById(Long.valueOf(entry.getKey()));
					DTOFormField p = new DTOFormField("rezenczija","Review by " +reviewer.getFirstName() +" "+reviewer.getLastName(),infoType,entry.getValue());
					ffd.getFormFields().add(p);
				}
			}
			DTOFormField p = new DTOFormField("download","Paper download",downloadType);
			ffd.getFormFields().add(p);
		}else if(taskName.equals("Autor popravlja rad")) {
			Map<String, String> reviews = (Map<String, String>) runtimeService
					.getVariable(task.getProcessInstanceId(), "reviews");
			if(reviews != null) {
				for ( Map.Entry<String,String> entry : reviews.entrySet() ) {
					User reviewer= userService.findById(Long.valueOf(entry.getKey()));
					DTOFormField p = new DTOFormField("review","Review",infoType,entry.getValue());
					ffd.getFormFields().add(p);
				}
			}
			DTOFormField p = new DTOFormField("download","Paper download",downloadType);
			ffd.getFormFields().add(p);
			DTOFormField u = new DTOFormField("upload", "Paper upload", uploadType);
			ffd.getFormFields().add(u);
		}else if(taskName.equals("Urednik proverava da li je autor popravio rad")) {
			Map<String, String> reviews = (Map<String, String>) runtimeService
					.getVariable(task.getProcessInstanceId(), "reviews");
			if(reviews != null) {
				for ( Map.Entry<String,String> entry : reviews.entrySet() ) {
					User reviewer= userService.findById(Long.valueOf(entry.getKey()));
					DTOFormField p = new DTOFormField("review","Review by " +reviewer.getFirstName() +" "+reviewer.getLastName(),infoType,entry.getValue());
					ffd.getFormFields().add(p);
				}
			}
			final String changes = runtimeService.getVariable(task.getProcessInstanceId(),"changes").toString();
			DTOFormField ch = new DTOFormField("changes","Author made changes",infoType,changes);
			ffd.getFormFields().add(ch);
			
			final String replays = runtimeService.getVariable(task.getProcessInstanceId(),"Reply").toString();
			DTOFormField rp = new DTOFormField("replay","Author replayed to reviews",infoType,replays);
			ffd.getFormFields().add(rp);
			
			DTOFormField p = new DTOFormField("download","Paper download",downloadType);
			ffd.getFormFields().add(p);
		}
		
		return ffd;
	}
	
}

