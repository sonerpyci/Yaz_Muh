
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sirket b = new Sirket("YSOFT");
		Yonetici a = new Yonetici(0,"Mehmet Ali","18000");
		Proje k= new Proje("YSOFT", 0, 0, 0, 2, 3, 4, a);
		Programci p = new Programci(1,"Ahmet Necdet Sezer","12TL");
		k.addWorker(p);
		
		


	}

}
