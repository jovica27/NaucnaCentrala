package base.upp.nc.web.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.rest.dto.VariableValueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import base.upp.nc.domain.Magazine;
import base.upp.nc.domain.User;
import base.upp.nc.service.MagazineService;
import base.upp.nc.service.RESTService;
import base.upp.nc.service.UserService;

@RestController
@RequestMapping(value="/api/rest/magazine")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RestMagazineController {
	
	@Autowired
	private MagazineService magazineService;

	@Autowired
	private UserService userService;
	

	@Autowired
	private RESTService runtimeService;

//	@Autowired
//	private RuntimeService runtimeService;
	
//	@Autowired
//	TaskService taskService;
//	
//	@Autowired
//	FormService formService;
	
	@RequestMapping(value="/getAll", 
			method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken() throws AuthenticationException {
	
		return new ResponseEntity(magazineService.getAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{magazineId}")
	public ResponseEntity<?> startPublication(Principal principal,
			@PathVariable Long magazineId) {
		Magazine magazine = magazineService.findOne(magazineId);
		if(magazine == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		System.out.println("Trenutno ulogovan:");
		System.out.println(author.getEmail());
		
		
//		Map<String, Object> variables = new HashMap<String,Object>();
//		variables.put("magazineId", magazine.getId());
//		variables.put("authorId", author.getId().toString());
//		variables.put("mainEditorId", magazine.getMainEditor().getId().toString());
//		variables.put("magazineTitle", magazine.getName());
//		runtimeService.startProcess("publishScientificPaper", variables);

		Map<String, VariableValueDto> variables = new HashMap<String,VariableValueDto>();
		VariableValueDto variableValueMagazin= new VariableValueDto();
		variableValueMagazin.setValue( magazine.getId());
		variables.put("magazineId", variableValueMagazin);
		
		VariableValueDto variableValueAuthor= new VariableValueDto();
		variableValueAuthor.setValue(author.getId().toString());
		variables.put("authorId", variableValueAuthor);
		
		VariableValueDto variableValueMainEditor= new VariableValueDto();
		variableValueMainEditor.setValue(magazine.getMainEditor().getId().toString());
		variables.put("mainEditorId", variableValueMainEditor);
		
		VariableValueDto variableValueMagazinTitle= new VariableValueDto();
		variableValueMagazinTitle.setValue(magazine.getName());
		variables.put("magazineTitle", variableValueMagazinTitle);
		
		runtimeService.startProcess("publishProcess", variables);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
