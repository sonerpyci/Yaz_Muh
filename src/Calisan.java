
public class Calisan {

private int id;
private String name;
private int salary;
private boolean status;
private String projectName;

public Calisan() {
	super();
	
}


public boolean isStatus() {
	return status;
}


public void setStatus(boolean status) {
	this.status = status;
}


public Calisan( String name, int salary) {
	super();
	this.name = name;
	this.salary = salary;
}


public String getProjectName() {
	return projectName;
}

public boolean getStatus() {
	return status;
}


public void setProjectName(String projectName) {
	this.projectName = projectName;
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
public int getSalary() {
	return salary;
}
public void setSalary(int salary) {
	this.salary = salary;
}

}
