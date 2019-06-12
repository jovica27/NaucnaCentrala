package base.upp.nc.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import base.upp.nc.domain.ScientificPaper;
import base.upp.nc.domain.User;
import base.upp.nc.service.StorageService;
import base.upp.nc.service.UserService;

import static base.upp.nc.service.StorageServiceImpl.rootLocation;
import org.springframework.http.MediaType;

@RestController
@CrossOrigin(origins ="*")
@RequestMapping("/paper")
public class UploadController {

	@Autowired
	StorageService storageService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	FormService formService;
	
	
	private String caumndaVariableFileName= "paperFileName";
	
	List<String> files = new ArrayList<String>();
//	@ModelAttribute ScientificPaper sciencePaper,@RequestParam("textPDF") MultipartFile multipartFile,

	@PostMapping("/postUpload/{taskId}")
	public ResponseEntity<String> handleFileUpload(@RequestParam("textPDF") MultipartFile multipartFile, @PathVariable String taskId) {
		System.out.println("\n paper con");

//			storageService.store(sciencePaper.getTextPDF()[0]);
		
//		runtimeService.getVariable(executionId, "", value);
		// stavljanje variable
		String fileName = multipartFile.getOriginalFilename();

		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

//		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list()
//				.get(0);
		Task task = taskService.createTaskQuery().taskId(taskId).list().get(0);
		runtimeService.setVariable(task.getProcessInstanceId(),caumndaVariableFileName,fileName);

		storageService.delete(fileName);
		storageService.store(multipartFile);
		System.out.println("\n\nLocation ::" + rootLocation);

		return ResponseEntity.status(HttpStatus.OK).body("Success!");
	}
	

	@RequestMapping(
			value = "/download/{taskId}",
			method = RequestMethod.GET            
	)
	public ResponseEntity<Resource>downloadPaper(@PathVariable String taskId){
		//SciencePaperDownload paper = sciencePaperService.findByName(paperName);

//		String extension = paper.getMimetype().split("/")[1];
//		System.out.println("EKSTENZIJA JE :::::::::::::" + extension);

		//User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		Task task = taskService.createTaskQuery().taskId(taskId).list().get(0);
		String paperName=runtimeService.getVariable(task.getProcessInstanceId(), caumndaVariableFileName).toString();
		Resource resource = storageService.loadFile(paperName);
		
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
			//.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + paperName +".pdf" + "" + "\"")
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + paperName  + "" + "\"")
			.body(resource);
				
	}
	
	
//	@PostMapping("/post/{id}")
//	public ResponseEntity<String> handleFileUpload(@ModelAttribute("currentUser") CurrentUser currentUser, @ModelAttribute SciencePaper sciencePaper, @PathVariable Long id) {
//		System.out.println("\n upload con");
//
//		Magazine magazine = magazineService.findById(id);
//		User user = currentUser.getUser();
//		ScientificPaper newSciencePaper = new SciencePaper(sciencePaper.getName(), sciencePaper.getKeywords(), sciencePaper.getAbbstract(), sciencePaper.getScentificField(), sciencePaper.getTextPDF());
//
//		newSciencePaper.setScienceMagazine(magazine);
//		newSciencePaper.setNameMagazine(newSciencePaper.getScienceMagazine().getName());
//		newSciencePaper.setNameScientifiField(newSciencePaper.getScienceMagazine().getScientificField().getName());
//		System.out.println("\n\t\tnaucna oblast kojoj pripada rad: " + newSciencePaper.getScienceMagazine().getScientificField().getName());
//
//
//		newSciencePaper.setAuthor(user);
//
//		newSciencePaper.setCoAuthor(new ArrayList<User>());
//		newSciencePaper.setScentificField(newSciencePaper.getScienceMagazine().getScientificField());
//		newSciencePaper.setLocationOnDrive(sciencePaper.getTextPDF()[0].getOriginalFilename());
//
//		sciencePaperService.save(newSciencePaper);
//		storageService.store(sciencePaper.getTextPDF()[0]);
//
//		System.out.println("\n\nLOCATION::::::::::" + rootLocation);
//
//		return ResponseEntity.status(HttpStatus.OK).body("Success!");
//	}
//	@GetMapping("/files/{filename:.+}")
//	@ResponseBody
//	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//		Resource file = storageService.loadFile(filename);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//				.body(file);
//	}
//	
//	@GetMapping("/getallfiles")
//	public ResponseEntity<List<String>> getListFiles(Model model) {
//		List<String> fileNames = files
//				.stream().map(fileName -> MvcUriComponentsBuilder
//						.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
//				.collect(Collectors.toList());
//
//		return ResponseEntity.ok().body(fileNames);
//	}
//
//	@GetMapping("/files/{filename:.+}")
//	@ResponseBody
//	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//		Resource file = storageService.loadFile(filename);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//				.body(file);
//	}
//	
}
