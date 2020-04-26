
public class Calisan {

private int id;
private String name;
private String salary;
private boolean status;

public Calisan() {
	super();
	
}


public boolean isStatus() {
	return status;
}


public void setStatus(boolean status) {
	this.status = status;
}


public Calisan(int id, String name, String salary) {
	super();
	this.id = id;
	this.name = name;
	this.salary = salary;
}






public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSalary() {
	return salary;
}
public void setSalary(String salary) {
	this.salary = salary;
}



}
