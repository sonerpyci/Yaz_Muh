import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Database {

	Connection conn;
	private final String url = "jdbc:postgresql://localhost:5432/YSOFT";
    private final String user = "postgres";
    private final String password = "1234";
    Statement stmt ;

    private Sirket sirket;
    private ArrayList<Calisan> calisanlar;
    private ArrayList<Proje> projeler;

    public Database(String companyName) {
		super();
		this.calisanlar = new ArrayList<Calisan>();
		this.projeler = new ArrayList<Proje>();
		this.sirket = new Sirket(companyName);

		this.conn = this.connect()  ;



	}

	public void insertProject(Proje proje)
    {

    	String SQL = "INSERT INTO projeler(project_name,programci,tasarimci,analist,min_analist,min_programci,min_tasarimci,max_analist,max_programci,max_tasarimci,yonetici_id,status) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (

                PreparedStatement statement = conn.prepareStatement(SQL);) {

                statement.setString(1, String.valueOf(proje.getProjectName()) );
                statement.setInt(2, proje.getProgramci());
                statement.setInt(3, proje.getTasarimci());
                statement.setInt(4, proje.getAnalist());
                statement.setInt(5, proje.getMinAnalist());
                statement.setInt(6, proje.getMinProgramci());
                statement.setInt(7, proje.getMinTasarimci());
                statement.setInt(8, proje.getMaxAnalist());
                statement.setInt(9, proje.getMaxProgramci());
                statement.setInt(10, proje.getMaxTasarimci());
                statement.setInt(11, proje.getYonetici().getId());
                statement.setBoolean(12, proje.getStatus());
				//statement.setInt(13, proje.getId());
                statement.addBatch();

                statement.executeBatch();

                for ( Calisan c : proje.getCalisanlar()) {
                	updateCalisan(c.getId(),c);
                }


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
	public void insertCalisanWithoutProject(Calisan calisan)
	{

		String SQL = "INSERT INTO calisanlar(salary,status,name,worker_type,project_name) "
				+ "VALUES(?,?,?,?,?)";
		String worker_type = new String();
		try (

				PreparedStatement statement = conn.prepareStatement(SQL);) {

			//statement.setInt(1, calisan.getId() );
			statement.setInt(1, calisan.getSalary());
			statement.setBoolean(2, calisan.getStatus());
			statement.setString(3, calisan.getName());

			if (calisan instanceof Programci) {
				worker_type="programci";
			}
			if (calisan instanceof Analist) {
				worker_type="analist";
			}
			if (calisan instanceof Tasarimci) {
				worker_type="tasarimci";
			}
			if (calisan instanceof Yonetici) {
				worker_type="yonetici";
			}
			statement.setString(4, worker_type);
			statement.setString(5, calisan.getProjectName());

			statement.addBatch();

			statement.executeBatch();


		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}
    public void insertCalisan(Calisan calisan)
    {
    	Proje updater=null;
    	for (Proje temp : sirket.getProjeler()){
    		if(temp.getProjectName().compareToIgnoreCase(calisan.getProjectName())==0){
    			updater=temp;
		temp.addWorker(calisan);

	}
}

    	if(updater!=null)
    		updateProject(updater.getId());
    	String SQL = "INSERT INTO calisanlar(salary,status,name,worker_type,project_name) "
                + "VALUES(?,?,?,?,?)";
    	String worker_type = new String();
        try (

                PreparedStatement statement = conn.prepareStatement(SQL);) {

                //statement.setInt(1, calisan.getId() );
                statement.setInt(1, calisan.getSalary());
                statement.setBoolean(2, calisan.getStatus());
                statement.setString(3, calisan.getName());

                if (calisan instanceof Programci) {
                	worker_type="programci";
                }
                if (calisan instanceof Analist) {
                	worker_type="analist";
                }
                if (calisan instanceof Tasarimci) {
                	worker_type="tasarimci";
                }
                if (calisan instanceof Yonetici) {
                	worker_type="yonetici";
                }
                statement.setString(4, worker_type);
                statement.setString(5, calisan.getProjectName());

                statement.addBatch();

                statement.executeBatch();


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void getProjectsFromDatabase() {
    	projeler=new ArrayList<Proje>();
    	calisanlar=new ArrayList<Calisan>();
    	Proje temp_project ;
    	String sql = "SELECT * FROM projeler";
    	ResultSet rs;
    	Calisan temp= new Yonetici();
		try {
			 Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				 int id = rs.getInt("id");
				 int programci = rs.getInt("programci");
		    	 int tasarimci = rs.getInt("tasarimci");
		    	 int analist = rs.getInt("tasarimci");

		    	 int min_analist = rs.getInt("min_analist");
		    	 int min_programci = rs.getInt("min_programci");
		    	 int min_tasarimci = rs.getInt("min_tasarimci");

		    	 int max_analist = rs.getInt("max_analist");
		    	 int max_programci = rs.getInt("max_programci");
		    	 int max_tasarimci = rs.getInt("max_tasarimci");
		    	 int yonetici_id = rs.getInt("yonetici_id");
		    	 boolean status = rs.getBoolean("status");
		    	 //int id =rs.getInt("id");
		    	 String project_name = rs.getString("project_name");

		    	 calisanlar = sirket.getCalisanlar();
		    	 for (Calisan c : calisanlar) {
		    		 if (c.getId() == yonetici_id) {
		    			 temp = c;
		    		 }
		    	 }
		    	 temp_project = new Proje(/*id,*/ project_name,min_analist,min_programci,min_tasarimci,max_analist,max_programci,max_tasarimci,temp);
		    	 temp_project.setId(id);
		    	 getCalisanlarFromDatabase();

		    	 //calisanlar = sirket.getCalisanlar();
		    	 if (temp_project!=null) {

		    		 for ( Calisan c : calisanlar ) {


						if(c.getProjectName()!=null)
							 if(c.getProjectName().compareToIgnoreCase(temp_project.getProjectName() )==0) {
								 temp_project.addWorker(c);
							 }
			    	 }
			    	// sirket.getProjeler().add(temp_project);
		    	 }
				projeler.add(temp_project);

			}
			sirket.setProjeler(projeler);
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++"+projeler.size());
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    public ResultSet getAndReturnProjectsFromDatabase() {
    	Proje temp_project ;
    	String sql = "SELECT * FROM projeler";
    	ResultSet rs;
    	Calisan temp= new Yonetici();
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

    public void getCalisanlarFromDatabase() {
    	calisanlar=new ArrayList<Calisan>();
    	Calisan temp_calisan;
    	String sql = "SELECT * FROM calisanlar";
    	ResultSet rs;
    	Programci temp_programci;
    	Analist temp_analist;
    	Tasarimci temp_tasarimci;
    	Yonetici temp_yonetici;
    	calisanlar.clear();
		try {
			 Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
		    	 int id = rs.getInt("id");
		    	 int salary = rs.getInt("salary");
		    	 boolean status = rs.getBoolean("status");
		    	 String name = rs.getString("name");
		    	 String worker_type = rs.getString("worker_type");
		    	 String project_name = rs.getString("project_name");

		    	 if (worker_type.compareToIgnoreCase("programci")==0) {
		    		 temp_programci = new Programci(name, salary);
		    		 temp_programci.setId(id);
		    		 temp_programci.setProjectName(project_name);
		    		 calisanlar.add(temp_programci);
		    	 }
		    	 if (worker_type.compareToIgnoreCase("analist")==0) {
		    		 temp_analist = new Analist(name,salary);
		    		 temp_analist.setId(id);
		    		 temp_analist.setProjectName(project_name);
		    		 calisanlar.add(temp_analist);
		    	 }
		    	 if (worker_type.compareToIgnoreCase("tasarimci")==0) {
		    		 temp_tasarimci = new Tasarimci(name,salary);
		    		 temp_tasarimci.setId(id);
		    		 temp_tasarimci.setProjectName(project_name);
		    		 calisanlar.add(temp_tasarimci);
		    	 }
		    	 if (worker_type.compareToIgnoreCase("yonetici")==0) {
		    		 temp_yonetici = new Yonetici(name,salary);
		    		 temp_yonetici.setId(id);
		    		 temp_yonetici.setProjectName(project_name);
		    		 calisanlar.add(temp_yonetici);
		    	 }
	    		 sirket.setCalisanlar(calisanlar);
		    	}
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public ResultSet getAndReturnCalisanlarFromDatabase() {
		Calisan temp_calisan;
		String sql = "SELECT * FROM calisanlar";
		ResultSet rs;
		Programci temp_programci;
		Analist temp_analist;
		Tasarimci temp_tasarimci;
		Yonetici temp_yonetici;
		calisanlar.clear();
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

    public boolean deleteCalisanFromDatabase(int calisan_id) {
    	Calisan temp=null;
    	boolean control=false;
    	//String projeAdi ="?";
    	int projectId = -1;
    	for (Calisan c : sirket.getCalisanlar() ) {
    		if (c.getId()==calisan_id) {
    			//projeAdi=c.getProjectName();
				projectId=c.getProjectId();
    			temp = c;
    		}
    	}
    	if(sirket.removeWorker(temp)){
			String SQL = "DELETE FROM calisanlar where id=?";

			try (
					PreparedStatement pstmt = conn.prepareStatement(SQL)) {
				pstmt.setInt(1, calisan_id);
				pstmt.executeUpdate();
				//updateProject(projeAdi);
				if (projectId > -1)
					updateProject(projectId);

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return control;


    }

    public void deleteProjectFromDatabase(int projeId) {
		sirket.setProjeler(new ArrayList<Proje>());
    	getProjectsFromDatabase();
    	getCalisanlarFromDatabase();
		System.out.println("------------------------------------------------------------");
		System.out.println("PROJE SİL ");
		System.out.println("--------------------------------------------------------");
    	String SQL = "DELETE FROM projeler where id=?";
    	String sql2 = "UPDATE calisanlar set project_name = null where project_name=?";
    	String sql3="delete from calisanlar where project_name is null";
    	Proje temp = null;
		String projeAdi ="?";
    	for (Proje p : sirket.getProjeler()) {
    		if (p.getId() == projeId) {
				temp = p;

				projeAdi = p.getProjectName();

    			break;
    		}
    	}
		try (
			PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, projeId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

		sirket.removeProject(temp);


    	try (
			PreparedStatement pstmt = conn.prepareStatement(sql2)) {

            pstmt.setString(1, projeAdi);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		calisanlar=sirket.getCalisanlar();
		for (Calisan c : calisanlar) {
			System.out.println("Calisan"+ "  "+ c.getName()+ "  "+ c.getProjectName());
		}
    	for(Calisan c:sirket.getCalisanlar()){
    		updateCalisan(c.getId(),c);
		}

    	int i =0;

    	for (Calisan c : calisanlar) {
			System.out.println("Calisan"+ "  "+ c.getName()+ "  "+ c.getProjectName());
    	}

    	for (Proje p : sirket.getProjeler()) {
    		updateProject(p.getId());
    	}
    	ArrayList<Calisan> temp_calisanlar = new ArrayList<Calisan>();
    	for (Calisan c : calisanlar ) {
    		if (c.getProjectName()==null)
    			temp_calisanlar.add(c);
    	}
    	for (Calisan c: temp_calisanlar) {
    		if (c.getProjectName()==null) {

    			deleteCalisanFromDatabase(c.getId()); }
    	}
    	getCalisanlarFromDatabase();
    	getProjectsFromDatabase();
		try (
				PreparedStatement pstmt = conn.prepareStatement(sql2)) {

			pstmt.setString(1, projeAdi);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		try (
				PreparedStatement pstmt = conn.prepareStatement(sql3)) {


			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}


    }


    public void updateProjectById(Proje p){

/* Proje ekleme ekranından verilen değişkenlerle bir Proje nesnesi oluşturulması gerekiyor.Oluşturulan Proje bu methoda gönderilince varsa update yoksa insert işlemi
yapacak.
 */
		int id = p.getId();
		sirket.setProjeler(new ArrayList<Proje>());
		getProjectsFromDatabase();
		ArrayList<Proje> temp=sirket.getProjeler();
		for(Proje s : temp){

			if(s.getId()==id){
				System.out.println("Update Ediliyor");
				updateAllProject(p);

				return;
			}
		}
		System.out.println("İnsert İşlemi");
		insertProject(p);

		}
		public void updateCalisan(int id ,Calisan temp){
			//int id=temp.getId();
			String SQL = "UPDATE calisanlar SET salary=?,status=?,name=?,project_name=? ,project_id=?  WHERE id =?";


			try (

					PreparedStatement statement = conn.prepareStatement(SQL);
			) {
				System.out.println(temp.getName());

				if (temp != null) {
					statement.setInt(1, temp.getSalary());
					statement.setBoolean(2, temp.getStatus());
					statement.setString(3, temp.getName());
					statement.setString(4, temp.getProjectName());
					statement.setInt(5, temp.getProjectId());
					statement.setInt(6,id);
					System.out.println(statement.toString());
					statement.executeUpdate();
				}


			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}

		public void updateCalisanById(Calisan p){
			// Update veya insert işlemi için Calisanin idsi ve Calisan Formundan Elde edilen bilgilerle oluşturulmuş çalışan nesnesi fonksiyona gönderililir verilen id'de çalışan varsa
			//update eder yoksa insert eder
			sirket.setCalisanlar(new ArrayList<Calisan>());
			sirket.setProjeler(new ArrayList<Proje>());
			getProjectsFromDatabase();
			getCalisanlarFromDatabase();
			ArrayList<Calisan> temp=sirket.getCalisanlar();
			for(Calisan s : temp){
				System.out.println("Calisan id "+s.getId());
				if(s.getId()==p.getId()){
					System.out.println("Update Ediliyor");
					updateCalisan(p.getId(), p);

					return;
				}
			}
			System.out.println("İnsert İşlemi");
			insertCalisan(p);
	}


	public void updateAllProject(Proje temp){
			int id=temp.getId();
			String SQL = "UPDATE projeler SET programci = ?, tasarimci = ?, analist = ?,project_name=?  ,min_analist=?, min_programci=?, min_tasarimci=?, max_analist=?,max_programci=?,max_tasarimci=?,yonetici_id=?,status=?  WHERE id =?";


			try (

					PreparedStatement statement = conn.prepareStatement(SQL);
			) {
				System.out.println(temp.getProjectName());

				if (temp != null) {
					statement.setInt(1, temp.getProgramci());
					statement.setInt(2, temp.getTasarimci());
					statement.setInt(3, temp.getAnalist());
					statement.setString(4, temp.getProjectName());
					statement.setInt(5, temp.getMinAnalist());
					statement.setInt(6, temp.getMinProgramci());
					statement.setInt(7, temp.getMinTasarimci());
					statement.setInt(8, temp.getMaxAnalist());
					statement.setInt(9, temp.getMaxProgramci());
					statement.setInt(10, temp.getMaxTasarimci());
					statement.setInt(11,temp.getYonetici().getId());
					statement.setBoolean(12,temp.getStatus());
					statement.setInt(13,id);
					statement.executeUpdate();
				}


			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}

		}

    public void updateProject(int projectId) {
    	String SQL = "UPDATE projeler SET programci = ?, tasarimci = ?, analist = ? WHERE project_name =?";
    	Proje temp = null ;
    	for (Proje p : sirket.getProjeler()) {

			if (p.getId() == projectId) {
				temp = p;
			}
		}
    	try (

                PreparedStatement statement = conn.prepareStatement(SQL);
    			) {

    			if (temp != null) {
                statement.setInt(1, temp.getProgramci());
                statement.setInt(2, temp.getTasarimci());
                statement.setInt(3, temp.getAnalist());
                statement.setString(4, temp.getProjectName());
                statement.executeUpdate();
    			}


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public ArrayList<Calisan> getCalisanlar() {
		return calisanlar;
	}

	public ArrayList<Proje> getProjeler() {
		return projeler;
	}

	public Sirket getSirket() {
		return sirket;
	}

	public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
			stmt=conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return conn;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database app = new Database("company");

        Programci p = new Programci("cayci ahmet",15000);
        Analist a = new Analist("analist",14000);
        Tasarimci t = new Tasarimci("tasarimci",13000);
        Yonetici y = new Yonetici( "oguz", 4500);
        Yonetici y1 = new Yonetici("Hacıbekir Lokumu",3);
        Yonetici y2 = new Yonetici("yonetici",4);
        Proje proje= new Proje(/*3 , */"ytu", 0, 0, 0, 3, 3, 3, y);
        Proje proje2= new Proje(/*1 ,*/ "itu", 0, 0, 0, 3, 3, 3, y1);
        Proje proje3= new Proje(/*2,*/ "odtu", 0, 0, 0, 3, 3, 3, y2);

      	proje.setStatus(true);
        proje2.setStatus(true);
        proje3.setStatus(true);
        app.getSirket().addProject(proje);
        app.getSirket().addProject(proje2);
        app.getSirket().addProject(proje3);
        app.getSirket().addWorker(p);
        app.getSirket().addWorker(a);
        app.getSirket().addWorker(t);
        app.getSirket().addWorker(y);
        app.getSirket().addWorker(y1);
        app.getSirket().addWorker(y2);

        app.insertProject(proje);
        app.insertProject(proje2);
        app.insertProject(proje3);
		Proje temp=new Proje(/*1,*/"hacettepe",1,1,1,5,5,5,y);
		Calisan as=new Calisan("Ahmet The Çay Master",1250);
		app.updateCalisanById(as);


		app.updateProjectById(temp);

    }
}
