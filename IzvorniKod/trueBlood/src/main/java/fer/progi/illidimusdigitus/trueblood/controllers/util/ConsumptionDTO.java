package fer.progi.illidimusdigitus.trueblood.controllers.util;

public class ConsumptionDTO {
	
	String bloodType;
	
	String timestamp;
	
	int quantity;
	
	String location;
	
	String employee;

	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public ConsumptionDTO(String bloodType, String timestamp, int quantity, String location, String employee) {
		super();
		this.bloodType = bloodType;
		this.timestamp = timestamp;
		this.quantity = quantity;
		this.location = location;
		this.employee = employee;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
	
	
}
