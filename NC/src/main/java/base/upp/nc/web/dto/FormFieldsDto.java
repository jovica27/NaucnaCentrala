package base.upp.nc.web.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.ArrayList;
import java.util.List;

public class FormFieldsDto {
    String taskId;
    List<DTOFormField> formFields;
    String processInstanceId;

    public FormFieldsDto(String taskId, String processInstanceId, List<DTOFormField> formFields) {
        super();
        this.taskId = taskId;
        this.formFields = formFields;
        this.processInstanceId = processInstanceId;
    }

    public FormFieldsDto() {}

    public void CreateFormFieldsDto(String taskId, String processInstanceId, List<FormField> properties) {
         this.taskId = taskId;
         this.formFields = new ArrayList<DTOFormField>();
         for (FormField formField : properties) {
        	 DTOFormField dtoFormField = new DTOFormField(formField.getId(), formField.getLabel(), formField.getTypeName());
        	 this.formFields.add(dtoFormField);
         }
         this.processInstanceId = processInstanceId;
	}

	public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<DTOFormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<DTOFormField> formFields) {
        this.formFields = formFields;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

}
