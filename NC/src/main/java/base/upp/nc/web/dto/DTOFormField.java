package base.upp.nc.web.dto;

public class DTOFormField {

	String id;
	String label;
	FormFieldTypeDto type;
	
	public DTOFormField() {}

	public DTOFormField(String id, String label, String type) {
		super();
		this.id = id;
		this.label = label;
		this.type = new FormFieldTypeDto(type.toLowerCase());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public FormFieldTypeDto getType() {
		return type;
	}

	public void setType(String type) {
		this.type = new FormFieldTypeDto(type.toLowerCase());
	}
	
}

