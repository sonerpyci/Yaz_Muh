import java.util.ArrayList;

public class Sirket {

	private String companyName;
	private ArrayList<Proje> projeler;
	private ArrayList<Calisan> calisanlar;
	private int idCounter;
	
	public Sirket(String companyName) {
		super();
		this.companyName = companyName;
		this.projeler = new ArrayList<Proje>();
		this.calisanlar = new ArrayList<Calisan>();
		this.idCounter=0;
	}
	
	
	public void addWorker(Calisan c , Proje p) {
		idCounter++;
		c.setId(idCounter);
		c.setProjectName(p.getProjectName());
		calisanlar.add(c);
		p.addWorker(c);
	}
	
	public boolean addWorker(Calisan c ) {
		idCounter++;
		c.setId(idCounter);
		calisanlar.add(c);
		for(Proje p : projeler) {
			boolean control = p.addWorker(c);
			if(control && c.getStatus()==false) {
				c.setProjectName(p.getProjectName());
				c.setStatus(true);
				return control;
			}
		}
		return false;
	}
	
	public void addEmptyWorker ( Calisan c ) {
		for(Proje p : projeler) {
			boolean control = p.addWorker(c);
			if(control && c.getStatus()==false) {
				c.setProjectName(p.getProjectName());
				c.setStatus(true);
				return;
			}
		}
	}
	
	public void removeWorker(Calisan c) {
		calisanlar.remove(c);
		for(Proje p :projeler) {
			if(p.getCalisanlar().contains(c)) {
				p.removeWorker(c);
				System.out.println("Remove Workere'a girdi");
			}
		}
	}
	
	public void addProject(Proje o) {
		projeler.add(o);
	}
	
	public void removeProject(Proje o) {
		ArrayList<Calisan> calisanlar = o.getCalisanlar();
		for(int i = 0; i < calisanlar.size(); i++) {

			Calisan c = calisanlar.get(i);
			addEmptyWorker(c);
			o.removeWorker(c);
		}
		/*for (Calisan c : o.getCalisanlar() ) { // foreach concurrent
			addEmptyWorker(c);
			o.removeWorker(c);
		}*/
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
