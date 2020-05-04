

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sirket b = new Sirket("YSOFT");
		Yonetici a = new Yonetici("Mehmet Ali",18000);
		Proje k= new Proje(/*b.getProjeler().size()+1,*/ "YSOFT", 0, 0, 0, 2, 3, 4, a);
		Programci p = new Programci("Ahmet Necdet Sezer",12);
		k.addWorker(p);
		
		


	}

}
