import java.util.ArrayList;

public class Proje {
	private String projectName;
	private ArrayList<Calisan> calisanlar;

	private int programci=0;
	private int tasarimci=0;
	private int analist=0;
	
	
	private int minAnalist;
	private int minProgramci;
	private int minTasarimci;
	private int maxAnalist;
	private int maxProgramci;
	private int maxTasarimci;
	private Yonetici yonetici;
	private boolean status;//Active True ,
	
	

	


	public Proje(String projectName, int minAnalist, int minProgramci, int minTasarimci, int maxAnalist,
			int maxProgramci, int maxTasarimci, Yonetici yonetici) {
		super();
		this.calisanlar = new ArrayList<Calisan>();
		calisanlar.add(yonetici);
		this.projectName = projectName;
		this.minAnalist = minAnalist;
		this.minProgramci = minProgramci;
		this.minTasarimci = minTasarimci;
		this.maxAnalist = maxAnalist;
		this.maxProgramci = maxProgramci;
		this.maxTasarimci = maxTasarimci;
		this.yonetici = yonetici;
	}


	public boolean addWorker(Calisan calisan) {
		
		if (calisan instanceof Programci) {
			if(programci<maxProgramci) {
				programci++;
				calisanlar.add(calisan);
				System.out.println("Çalýþan Baþarýyla Eklendi");
				return true;
			}
			else {
				System.out.println("Projede Maximum Sayýda Programci Bulunmakta");
				return false;
			}
		}
		
		
		
		if (calisan instanceof Analist) {
			if(analist<maxAnalist) {
				analist++;
				calisanlar.add(calisan);
				System.out.println("Çalýþan Baþarýyla Eklendi");
				return true;
			}
			else {
				System.out.println("Projede Maximum Sayýda Analist Bulunmakta");
				return false;
			}
		}
		
		if (calisan instanceof Tasarimci) {
			if(tasarimci<maxTasarimci) {
				tasarimci++;
				calisanlar.add(calisan);
				System.out.println("Çalýþan Baþarýyla Eklendi");
				return true;
			}
			else {
				System.out.println("Projede Maximum Sayýda Tasarimci Bulunmakta");
				return false;
			}
		}
		
		
		return false;
		
	}
	
	
	public boolean removeWorker(Calisan calisan) {
		if (calisan instanceof Programci) {
			if(programci>minProgramci) {
				programci--;
				calisanlar.remove(calisan);
				return true;
				
			}
			else {
				System.out.println("Projede Minimum Sayýda Programci Bulunmakta");
				return false;
				
			}
		}
		
		
		
		if (calisan instanceof Analist) {
			if(analist>minAnalist) {
				analist--;
				calisanlar.remove(calisan);
				return true;
				
			}
			else {
				System.out.println("Projede Minimum Sayýda Analist Bulunmakta");
				return false;
			}
		}
		
		if (calisan instanceof Tasarimci) {
			if(tasarimci>minTasarimci) {
				tasarimci--;
				calisanlar.remove(calisan);
				return true;
				
			}
			else {
				System.out.println("Projede Minimum Sayýda Tasarimci Bulunmakta");
				return false;
				
			}
		}
		return false;
	}
	
	
	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
		if(status==false) {
			for(Calisan c :calisanlar) {
				this.removeWorker(c);
			}
		}
		
	}

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public ArrayList<Calisan> getCalisanlar() {
		return calisanlar;
	}
	public void setCalisanlar(ArrayList<Calisan> calisanlar) {
		this.calisanlar = calisanlar;
	}

	public Yonetici getYonetici() {
		return yonetici;
	}
	public void setYonetici(Yonetici yonetici) {
		this.yonetici = yonetici;
	}

	
	
}
