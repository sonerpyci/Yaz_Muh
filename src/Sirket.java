import java.util.ArrayList;

public class Sirket {

	private String companyName;
	private ArrayList<Proje> projeler;
	private ArrayList<Calisan> calisanlar;
	public Sirket(String companyName) {
		super();
		this.companyName = companyName;
		this.projeler = new ArrayList<Proje>();
		this.calisanlar = new ArrayList<Calisan>();
	}
	
	
	
	public void addWorker(Calisan c , Proje p) {
		calisanlar.add(c);
		p.addWorker(c);
	}
	
	public void addWorker(Calisan c ) {
		calisanlar.add(c);
		for(Proje p : projeler) {
			boolean control =p.addWorker(c);
			if(control)
				return;
			
		}
	}
	public void removeWorker(Calisan c) {
		calisanlar.remove(c);
		
		for(Proje p :projeler) {
			if(p.getCalisanlar().contains(c)) {
				p.removeWorker(c);
			}
		}
	}
	
	public void addProject(Proje o) {
		projeler.add(o);
	}
	
	public void removeProject(Proje o) {
		projeler.remove(o);
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public ArrayList<Proje> getProjeler() {
		return projeler;
	}
	public void setProjeler(ArrayList<Proje> projeler) {
		this.projeler = projeler;
	}
	public ArrayList<Calisan> getCalisanlar() {
		return calisanlar;
	}
	public void setCalisanlar(ArrayList<Calisan> calisanlar) {
		this.calisanlar = calisanlar;
	}
	
	
}
