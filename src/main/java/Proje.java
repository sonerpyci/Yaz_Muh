import java.util.ArrayList;

public class Proje {
	private String projectName;
	private ArrayList<Calisan> calisanlar;

	private int id;
	private int programci=0;
	private int tasarimci=0;
	private int analist=0;
	
	
	private int minAnalist;
	private int minProgramci;
	private int minTasarimci;
	private int maxAnalist;
	private int maxProgramci;
	private int maxTasarimci;
	private Calisan yonetici;
	private boolean status;//Active True ,
	

	public Proje(/*int id,*/ String projectName, int minAnalist, int minProgramci, int minTasarimci, int maxAnalist,
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
		//this.id=id;
		yonetici.setProjectName(projectName);
		this.yonetici = yonetici;
	}
	
	public Proje(/*int id,*/ String projectName, int minAnalist, int minProgramci, int minTasarimci, int maxAnalist,
			int maxProgramci, int maxTasarimci, Calisan yonetici) {
		super();
		//this.id=id;
		this.calisanlar = new ArrayList<Calisan>();
		calisanlar.add(yonetici);
		this.projectName = projectName;
		this.minAnalist = minAnalist;
		this.minProgramci = minProgramci;
		this.minTasarimci = minTasarimci;
		this.maxAnalist = maxAnalist;
		this.maxProgramci = maxProgramci;
		this.maxTasarimci = maxTasarimci;
		yonetici.setProjectName(projectName);
		this.yonetici = yonetici;

	}



	public boolean addWorker(Calisan calisan) {
		
		if (calisan instanceof Programci) {
			if(programci<maxProgramci) {
				programci++;
				calisan.setProjectName(this.projectName);
				calisan.setProjectId(this.id);
				calisanlar.add(calisan);
				System.out.println("Çalış Başarıyla Eklendi" + calisan.getName() + " " + calisan.getProjectName());
				return true;
			}
			else {
				System.out.println("Projede Maximum Sayıda Programci Bulunmakta");
				return false;
			}
		}

		if (calisan instanceof Analist) {
			if(analist<maxAnalist) {
				analist++;
				calisan.setProjectName(this.projectName);
				calisan.setProjectId(this.id);
				calisanlar.add(calisan);
				System.out.println("�al��an Ba�ar�yla Eklendi");
				return true;
			}
			else {
				System.out.println("Projede Maximum Say�da Analist Bulunmakta");
				return false;
			}
		}
		
		if (calisan instanceof Tasarimci) {
			if(tasarimci<maxTasarimci) {
				tasarimci++;
				calisan.setProjectName(this.projectName);
				calisan.setProjectId(this.id);
				calisanlar.add(calisan);
				System.out.println("�al??an Ba?ar?yla Eklendi");
				return true;
			}
			else {
				System.out.println("Projede Maximum Say?da Tasarimci Bulunmakta");
				return false;
			}
		}
		return false;
	}
	
	
	public boolean removeWorker(Calisan calisan) {
		if (calisan instanceof Programci) {
			System.out.println("Programci Siliniyor");
			if(programci>=minProgramci) {
				programci--;
				calisanlar.remove(calisan);
				return true;
				
			}
			else {
				System.out.println("Projede Minimum Say?da Programci Bulunmakta");
				return false;
				
			}
		}
	
		if (calisan instanceof Analist) {
			if(analist>=minAnalist) {
				analist--;
				calisanlar.remove(calisan);
				return true;
				
			}
			else {
				System.out.println("Projede Minimum Say?da Analist Bulunmakta");
				return false;
			}
		}
		
		if (calisan instanceof Tasarimci) {
			if(tasarimci>=minTasarimci) {
				tasarimci--;
				calisanlar.remove(calisan);
				return true;
				
			}
			else {
				System.out.println("Projede Minimum Say?da Tasarimci Bulunmakta");
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public Calisan getYonetici() {
		return yonetici;
	}
	
	public void setYonetici(Yonetici yonetici) {
		this.yonetici = yonetici;
	}


	public int getProgramci() {
		return programci;
	}
	
	public boolean getStatus() {
		return status;
	}


	public void setProgramci(int programci) {
		this.programci = programci;
	}


	public int getTasarimci() {
		return tasarimci;
	}


	public void setTasarimci(int tasarimci) {
		this.tasarimci = tasarimci;
	}


	public int getAnalist() {
		return analist;
	}


	public void setAnalist(int analist) {
		this.analist = analist;
	}


	public int getMinAnalist() {
		return minAnalist;
	}


	public void setMinAnalist(int minAnalist) {
		this.minAnalist = minAnalist;
	}


	public int getMinProgramci() {
		return minProgramci;
	}


	public void setMinProgramci(int minProgramci) {
		this.minProgramci = minProgramci;
	}


	public int getMinTasarimci() {
		return minTasarimci;
	}


	public void setMinTasarimci(int minTasarimci) {
		this.minTasarimci = minTasarimci;
	}


	public int getMaxAnalist() {
		return maxAnalist;
	}


	public void setMaxAnalist(int maxAnalist) {
		this.maxAnalist = maxAnalist;
	}


	public int getMaxProgramci() {
		return maxProgramci;
	}


	public void setMaxProgramci(int maxProgramci) {
		this.maxProgramci = maxProgramci;
	}


	public int getMaxTasarimci() {
		return maxTasarimci;
	}


	public void setMaxTasarimci(int maxTasarimci) {
		this.maxTasarimci = maxTasarimci;
	}

}
