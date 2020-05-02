
public class Yonetici extends Calisan {

	private Proje proje;
	
	
	public Yonetici(String name,int salary) {
		super(name,salary);		
	}
	
	public Yonetici() {
		super();
	}


	public Yonetici(Proje proje) {
		super();
		this.proje = proje;
	}


	public void projeBitir() {
		
	proje.setStatus(false);
	
	
		
	}
	
}
