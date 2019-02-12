package root.demo.controller;

import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import root.demo.dto.FormFieldsDto;
import root.demo.dto.TaskDto;

@RestController
@RequestMapping("/process")
@CrossOrigin
public class ProcessController {

    public static ProcessInstance pi = null;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @GetMapping(path = "/start/{processName}", produces = "application/json")
    public FormFieldsDto startProcess(@PathVariable String processName) {
    	System.out.println(processName);
        pi = runtimeService.startProcessInstanceByKey(processName);
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        System.out.println(task.getName());
        TaskFormData tfd = formService.getTaskFormData(task.getId());

        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @GetMapping(path = "/getNext")
    public TaskDto getNextTask() {
        try{
            Task task =  taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
            if(task!=null){
                System.out.println(task.getName());
                return new TaskDto(task.getId(), task.getName());
            }
        } catch( Exception e) {
            System.out.println("Nema sledeceg taska");
            return new TaskDto(null,"");
        }


        return null;
    }

    /*
    @GetMapping(path = "/getRegisterForm")
    public FormFieldsDTO getRegisterForm() {
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());

        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDTO(task.getId(), pi.getId(), properties);
    }
    */
    
}
