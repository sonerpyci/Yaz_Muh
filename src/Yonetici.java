
public class Yonetici extends Calisan {

	private Proje proje;
	
	
	public Yonetici(int id ,String name,String salary) {
		super(id,name,salary);
		
	}


	public Yonetici(Proje proje) {
		super();
		this.proje = proje;
	}


	public void projeBitir() {
		
	proje.setStatus(false);
	
	
		
	}
	
}
