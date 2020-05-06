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
	
	
	public boolean addWorker(Calisan c , Proje p) {
		idCounter++;
		c.setId(idCounter);
		c.setProjectName(p.getProjectName());
		calisanlar.add(c);
		boolean control =p.addWorker(c);
		return control;
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
		System.out.println("Boş Eleman Ekleniypor : " +c.getName());
		System.out.println("-------------------------Proje Size : "+this.projeler.size()+" ---------------------------------");
		int ix =0;
		for(Proje p : projeler) {
			System.out.println(ix+ ".   "+p.getProjectName());
			ix++;
				calisanlar.remove(c);
				boolean control = p.addWorker(c);
				if(control ) {
					c.setProjectName(p.getProjectName());
					c.setStatus(true);
					calisanlar.add(c);
					return;
				}
				removeWorker(c);


		}
	}
	
	public boolean removeWorker(Calisan c) {

		calisanlar.remove(c);
		boolean control=false;
		for(int i=0;i<projeler.size();i++) {
			System.out.println("Remove Workere'a girdi");
			if(projeler.get(i).getCalisanlar().contains(c)) {
				control=projeler.get(i).removeWorker(c);

			}
		}
		return  control;
	}
	
	public void addProject(Proje o) {
		for (Proje p:projeler
			 ) {
			if(p.getProjectName().compareToIgnoreCase(o.getProjectName())==0){
				System.out.println("EKLENEMEZ");
				return;
			}

		}


		projeler.add(o);
	}
	
	public void removeProject(Proje o) {
		ArrayList<Calisan> calisanlarr = o.getCalisanlar();
		projeler.remove(o);
		int ix =0;
		System.out.println("---------------------------------Çalışanlar---------------------------------");
		for(Calisan p : calisanlarr) {
			System.out.println(ix + ".   " + p.getName());
			ix++;
		}
		for(int i = 0; i < calisanlarr.size(); i++) {
			System.out.println("************************************************** "+i);

			Calisan c = calisanlarr.get(i);
			addEmptyWorker(c);
			o.removeWorker(c);
		}
		/*for (Calisan c : o.getCalisanlar() ) { // foreach concurrent
			addEmptyWorker(c);
			o.removeWorker(c);
		}*/

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
