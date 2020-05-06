import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class YazMuhGUI extends JFrame {
    private static final Database database = new Database("YSOFT");
    private static ArrayList<Integer> newRowIdList = new ArrayList<Integer>();
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JButton projectsButton;
    private JButton staffsButton;
    private JPanel rightPanel;
    private JPanel projectsPanel;
    private JPanel welcomePanel;
    private JPanel staffsPanel;
    private JLabel projectsLabel;
    private JLabel welcomeLabel;
    private JTable projectsTable;
    private JScrollPane projectsScrollPane;
    private JTable staffsTable;
    private JButton deleteProjectsButton;
    private JButton saveProjectsButton;
    private JButton refreshProjectsButton;
    private JButton newProjectButton;
    private JButton deleteStaffButton;
    private JButton saveStaffButton;
    private JButton newStaffButton;
    private JButton refreshStaffButton;
    private JScrollPane staffsScrollPane;

    public YazMuhGUI(String title) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.projectsPanel.setVisible(false);
        this.staffsPanel.setVisible(false);


        projectsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                projectsButton.setBackground(Color.ORANGE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                projectsButton.setBackground(UIManager.getColor("control"));
            }
        });

        projectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staffsPanel.setVisible(false);
                welcomePanel.setVisible(false);
                projectsPanel.setVisible(true);
            }
        });

        staffsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                staffsButton.setBackground(Color.ORANGE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                staffsButton.setBackground(UIManager.getColor("control"));
            }
        });

        staffsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staffsPanel.setVisible(true);
                welcomePanel.setVisible(false);
                projectsPanel.setVisible(false);
            }
        });



        projectsPanel.addComponentListener ( new ComponentAdapter()
        {
            public void componentShown ( ComponentEvent e )
            {

                try {
                    DefaultTableModel tableModel = buildTableModel(database.getAndReturnProjectsFromDatabase());
                    tableModel.addTableModelListener(new TableModelListener() {

                        @Override
                        public void tableChanged(TableModelEvent e) {
                            if (/*e.getType()==TableModelEvent.INSERT||*/e.getType()==TableModelEvent.DELETE) {
                                //database.deleteProjectFromDatabase(e.getFirstRow());
                            }
                        }
                    });
                    projectsTable.setModel(tableModel);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            public void componentHidden ( ComponentEvent e )
            {
                System.out.println ( "Component hidden" );
            }
        } );

        staffsPanel.addComponentListener ( new ComponentAdapter()
        {
            public void componentShown ( ComponentEvent e )
            {

                try {
                    DefaultTableModel tableModel = buildTableModel(database.getAndReturnCalisanlarFromDatabase());
                    tableModel.addTableModelListener(new TableModelListener() {

                        @Override
                        public void tableChanged(TableModelEvent e) {
                            if (/*e.getType()==TableModelEvent.INSERT||*/e.getType()==TableModelEvent.DELETE) {
                                //database.deleteCalisanFromDatabase(e.getFirstRow());
                            }
                        }
                    });
                    staffsTable.setModel(tableModel);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            public void componentHidden ( ComponentEvent e )
            {
                System.out.println ( "Component hidden" );
            }
        } );


        refreshProjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    projectsTable.setModel(buildTableModel(database.getAndReturnProjectsFromDatabase()));
                    JOptionPane.showMessageDialog(null, "Projeler Yenilendi !",  "", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        newProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberFormat format = NumberFormat.getInstance();
                NumberFormatter formatter = new NumberFormatter(format);
                formatter.setValueClass(Integer.class);
                formatter.setMinimum(0);
                formatter.setMaximum(Integer.MAX_VALUE);
                formatter.setAllowsInvalid(true);
                // If you want the value to be committed on each keystroke instead of focus lost
                formatter.setCommitsOnValidEdit(true);

                JPanel panel = new JPanel(new GridLayout(0, 2));


                JLabel workerRolesLabel = new JLabel("Pozisyon : ");
                String[] workerRoles = {"yonetici", "programci", "analist", "tasarimci"};
                JComboBox<String> workerRolesCombo = new JComboBox<>(workerRoles);

                JLabel statusLabel = new JLabel("Durum : ");
                String[] statusValues = {"true", "false"};
                JComboBox<String> statusCombo = new JComboBox<>(statusValues);


                JLabel minAnalistLabel = new JLabel("Min. Analist : ");
                JFormattedTextField  minAnalistField = new JFormattedTextField (formatter);

                JLabel minProgramciLabel = new JLabel("Min. Programci : ");
                JFormattedTextField  minProgramciField = new JFormattedTextField (formatter);

                JLabel minTasarimciLabel = new JLabel("Min Tasarimci : ");
                JFormattedTextField  minTasarimciField = new JFormattedTextField (formatter);

                JLabel maxanalistLabel = new JLabel("Max. Analist : ");
                JFormattedTextField  maxAnalistField = new JFormattedTextField (formatter);

                JLabel maxProgramciLabel = new JLabel("Max. Programci : ");
                JFormattedTextField  maxProgramciField = new JFormattedTextField (formatter);

                JLabel maxTasarimciLabel = new JLabel("Max Tasarimci : ");
                JFormattedTextField  maxTasarimciField = new JFormattedTextField (formatter);

                JLabel yoneticiIdLabel = new JLabel("Yonetici Id : ");
                JFormattedTextField  yoneticiIdField = new JFormattedTextField (formatter);

                JLabel projectNameLabel = new JLabel("Proje Ad? : ");
                JTextField projectNameField = new JTextField ("");


                panel.add(projectNameLabel);
                panel.add(projectNameField);

                panel.add(minAnalistLabel);
                panel.add(minAnalistField);

                panel.add(minProgramciLabel);
                panel.add(minProgramciField);

                panel.add(minTasarimciLabel);
                panel.add(minTasarimciField);

                panel.add(maxanalistLabel);
                panel.add(maxAnalistField);

                panel.add(maxProgramciLabel);
                panel.add(maxProgramciField);

                panel.add(maxTasarimciLabel);
                panel.add(maxTasarimciField);

                panel.add(yoneticiIdLabel);
                panel.add(yoneticiIdField);

                /*panel.add(workerRolesLabel);
                panel.add(workerRolesCombo);*/

                panel.add(statusLabel);
                panel.add(statusCombo);

                int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Proje",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {

                        int minAnalist = Integer.parseInt(minAnalistField.getText());
                        int minProgramci = Integer.parseInt(minProgramciField.getText());
                        int minTasarimci = Integer.parseInt(minTasarimciField.getText());
                        int maxAnalist = Integer.parseInt(maxAnalistField.getText());
                        int maxProgramci = Integer.parseInt(maxProgramciField.getText());
                        int maxTasarimci = Integer.parseInt(maxTasarimciField.getText());

                        int yonetici = Integer.parseInt(yoneticiIdField.getText());

                        boolean status = Boolean.parseBoolean(String.valueOf(statusCombo.getSelectedItem()));
                        String projectName = projectNameField.getText();


                        database.getProjectsFromDatabase();
                        database.getCalisanlarFromDatabase();
                        List<Calisan> calisanlar = database.getCalisanlar();
                        for (int i = 0; i < calisanlar.size(); i++){
                            if (yonetici == calisanlar.get(i).getId()){
                                Calisan tempYonetici = calisanlar.get(i);
                                Proje newProject = new Proje(projectName,minAnalist,minProgramci,minTasarimci,maxAnalist,maxProgramci,maxTasarimci, tempYonetici);
                                newProject.setStatus(status);
                                database.updateProjectById(newProject);
                            }

                        }

                        DefaultTableModel tableModel = buildTableModel(database.getAndReturnProjectsFromDatabase());
                        projectsTable.setModel(tableModel);

                    } catch (Exception err) {

                    }
                } else {
                    System.out.println("Cancelled");
                }
                //tableModel.newRowsAdded();
                //tableModel.
                //newRowIdList
            }
        });

        newStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberFormat format = NumberFormat.getInstance();
                NumberFormatter formatter = new NumberFormatter(format);
                formatter.setValueClass(Integer.class);
                formatter.setMinimum(0);
                formatter.setMaximum(Integer.MAX_VALUE);
                formatter.setAllowsInvalid(true);
                // If you want the value to be committed on each keystroke instead of focus lost
                formatter.setCommitsOnValidEdit(true);

                JPanel panel = new JPanel(new GridLayout(0, 2));


                JLabel nameLabel = new JLabel("Ad Soyad : ");
                JTextField nameField = new JTextField ("");

                JLabel salaryLabel = new JLabel("Maas : ");
                JFormattedTextField  salaryField = new JFormattedTextField (formatter);

                JLabel workerRolesLabel = new JLabel("Pozisyon : ");
                String[] workerRoles = {"yonetici", "programci", "analist", "tasarimci"};
                JComboBox<String> workerRolesCombo = new JComboBox<>(workerRoles);


                JLabel projectNameLabel = new JLabel("Proje Adi : ");
                ArrayList<String> projectNames = new ArrayList<String>();
                database.getProjectsFromDatabase();
                database.getCalisanlarFromDatabase();
                ArrayList<Proje> projeler = database.getProjeler();
                for (int i=0; i< projeler.size(); i++) {
                    if (!projectNames.contains(projeler.get(i).getProjectName())) {
                        projectNames.add(projeler.get(i).getProjectName());
                    }

                }
                projectNames.add(null);
                Object [] projectNamesObjArr = projectNames.toArray();
                String[] projectNamesStrArr = Arrays.copyOf(projectNamesObjArr, projectNamesObjArr.length, String[].class);
                JComboBox<String> projectNamesCombo = new JComboBox<String>(projectNamesStrArr);


                JLabel statusLabel = new JLabel("Durum : ");
                String[] statusValues = {"true", "false"};
                JComboBox<String> statusCombo = new JComboBox<>(statusValues);

                panel.add(nameLabel);
                panel.add(nameField);

                panel.add(salaryLabel);
                panel.add(salaryField);

                panel.add(workerRolesLabel);
                panel.add(workerRolesCombo);

                panel.add(projectNameLabel);
                panel.add(projectNamesCombo);

                panel.add(statusLabel);
                panel.add(statusCombo);


                int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Calisan",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {

                        String name = nameField.getText();
                        int salary = Integer.parseInt(salaryField.getText().replaceAll("\\,", ""));
                        String workerRole = String.valueOf(workerRolesCombo.getSelectedItem());
                        String projectName = String.valueOf(projectNamesCombo.getSelectedItem());
                        boolean status = Boolean.parseBoolean(String.valueOf(statusCombo.getSelectedItem()));


                        database.getSirket().setProjeler(new ArrayList<Proje>());
                        database.getSirket().setCalisanlar(new ArrayList<Calisan>());

                        database.getProjectsFromDatabase();
                        database.getCalisanlarFromDatabase();

                        Calisan newCalisan;

                        if (workerRole=="programci") {
                            newCalisan = new Programci(name,salary);
                        } else if (workerRole=="analist") {
                            newCalisan = new Analist(name,salary);
                        } else if (workerRole=="yonetici") {
                            newCalisan = new Yonetici(name,salary);
                        } else if (workerRole=="tasarimci") {
                            newCalisan = new Tasarimci(name,salary);
                        } else {
                            newCalisan = new Calisan(name,salary); // hi� bir bilgi gelmediyse tipini bilmeden �retsin.
                        }


                        newCalisan.setProjectName(projectName);
                        newCalisan.setStatus(status);

                        Proje temp_project=null;
                        for (Proje p : projeler)
                            if(p.getProjectName().compareToIgnoreCase(projectName)==0)
                                temp_project=p;

                        if(temp_project!=null){
                            if( database.getSirket().addWorker(newCalisan,temp_project)){
                                database.getProjectsFromDatabase();
                                database.getCalisanlarFromDatabase();

                                database.updateCalisanById(newCalisan);

                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Bu proje için daha fazla çalışana gerek yok !",  " ", JOptionPane.INFORMATION_MESSAGE);
                            }

                        }
                        else{
                            database.getSirket().getCalisanlar().add(newCalisan);
                            database.getProjectsFromDatabase();
                            database.getCalisanlarFromDatabase();

                            database.updateCalisanById(newCalisan);


                        }
                      /*  if(database.getSirket().addWorker(newCalisan)){
                            database.updateCalisanById(newCalisan);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Bu proje için daha fazla çalışana gerek yok !",  " ", JOptionPane.INFORMATION_MESSAGE);
                        }*/


                        DefaultTableModel tableModel = buildTableModel(database.getAndReturnCalisanlarFromDatabase());
                        staffsTable.setModel(tableModel);

                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                } else {
                    System.out.println("Cancelled");
                }
                //tableModel.newRowsAdded();
                //tableModel.
                //newRowIdList
            }
        });

        refreshStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    staffsTable.setModel(buildTableModel(database.getAndReturnCalisanlarFromDatabase()));
                    JOptionPane.showMessageDialog(null, "Çalışanlar Yenilendi !",  " ", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        deleteStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    DefaultTableModel tableModel = (DefaultTableModel)staffsTable.getModel();

                    System.out.println(staffsTable.getValueAt(staffsTable.getSelectedRow(), 0));
                    database.getProjectsFromDatabase();
                    if(database.deleteCalisanFromDatabase((Integer)staffsTable.getValueAt(staffsTable.getSelectedRow(), 0)))
                        tableModel.removeRow(staffsTable.getSelectedRow());
                    else{
                        JOptionPane.showMessageDialog(null, "Seçilen Eleman İşi Bırakamaz",  "Seçim Hatası", JOptionPane.ERROR_MESSAGE);
                    }
                }


                catch (Exception ex ){
                    JOptionPane.showMessageDialog(null, "Lütfen tablodan silinecek çalışanı seçiniz",  "Seçim Hatası", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deleteProjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    DefaultTableModel tableModel = (DefaultTableModel)projectsTable.getModel();
                    System.out.println(projectsTable.getValueAt(projectsTable.getSelectedRow(), 0));

                    database.deleteProjectFromDatabase((Integer)projectsTable.getValueAt(projectsTable.getSelectedRow(), 0));

                    tableModel.removeRow(projectsTable.getSelectedRow());
                }catch (Exception ex ){
                    JOptionPane.showMessageDialog(null, "Lütfen tablodan silinecek projeyi seçiniz",  "Seçim Hatası", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        this.pack();
        saveProjectsButton.addActionListener(new ActionListener() { //burası
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel tableModel = (DefaultTableModel)projectsTable.getModel();
                    int row = projectsTable.getSelectedRow();
                    String idActual = tableModel.getValueAt(row, 0).toString();
                    String programciActual = tableModel.getValueAt(row, 1).toString();
                    String tasarimciActual = tableModel.getValueAt(row, 2).toString();
                    String analistActual = tableModel.getValueAt(row, 3).toString();
                    String minAnalistActual = tableModel.getValueAt(row, 4).toString();
                    String minProgramciActual = tableModel.getValueAt(row, 5).toString();
                    String minTasarimciActual = tableModel.getValueAt(row, 6).toString();
                    String maxAnalistActual = tableModel.getValueAt(row, 7).toString();
                    String maxProgramciActual = tableModel.getValueAt(row, 8).toString();
                    String maxTasarimciActual = tableModel.getValueAt(row, 9).toString();
                    String yoneticiIdActual = tableModel.getValueAt(row, 10).toString();

                    Boolean statusActual = Boolean.parseBoolean(tableModel.getValueAt(row, 11).toString());
                    String projectNameActual = tableModel.getValueAt(row, 12).toString();


                    /*  update project by id and values above  */
                    NumberFormat format = NumberFormat.getInstance();
                    NumberFormatter formatter = new NumberFormatter(format);
                    formatter.setValueClass(Integer.class);
                    formatter.setMinimum(0);
                    formatter.setMaximum(Integer.MAX_VALUE);
                    formatter.setAllowsInvalid(true);
                    // If you want the value to be committed on each keystroke instead of focus lost
                    formatter.setCommitsOnValidEdit(true);

                    JPanel panel = new JPanel(new GridLayout(0, 2));


                    JLabel statusLabel = new JLabel("Durum : ");
                    String[] statusValues = {"true", "false"};
                    JComboBox<String> statusCombo = new JComboBox<>(statusValues);

                    statusCombo.setSelectedIndex(statusActual ? 0 : 1);

                    JLabel minAnalistLabel = new JLabel("Min. Analist : ");
                    JFormattedTextField  minAnalistField = new JFormattedTextField (formatter);
                    minAnalistField.setText(minAnalistActual);

                    JLabel minProgramciLabel = new JLabel("Min. Programci : ");
                    JFormattedTextField  minProgramciField = new JFormattedTextField (formatter);
                    minProgramciField.setText(minProgramciActual);

                    JLabel minTasarimciLabel = new JLabel("Min Tasarimci : ");
                    JFormattedTextField  minTasarimciField = new JFormattedTextField (formatter);
                    minTasarimciField.setText(minTasarimciActual);

                    JLabel maxanalistLabel = new JLabel("Max. Analist : ");
                    JFormattedTextField  maxAnalistField = new JFormattedTextField (formatter);
                    maxAnalistField.setText(maxAnalistActual);

                    JLabel maxProgramciLabel = new JLabel("Max. Programci : ");
                    JFormattedTextField  maxProgramciField = new JFormattedTextField (formatter);
                    maxProgramciField.setText(maxProgramciActual);

                    JLabel maxTasarimciLabel = new JLabel("Max Tasarimci : ");
                    JFormattedTextField  maxTasarimciField = new JFormattedTextField (formatter);
                    maxTasarimciField.setText(maxTasarimciActual);

                    JLabel yoneticiIdLabel = new JLabel("Yonetici Id : ");
                    JFormattedTextField  yoneticiIdField = new JFormattedTextField (formatter);
                    yoneticiIdField.setText(yoneticiIdActual);

                    JLabel projectNameLabel = new JLabel("Proje Ad? : ");
                    JTextField projectNameField = new JTextField ("");
                    projectNameField.setText(projectNameActual);

                    panel.add(projectNameLabel);
                    panel.add(projectNameField);

                    panel.add(minAnalistLabel);
                    panel.add(minAnalistField);

                    panel.add(minProgramciLabel);
                    panel.add(minProgramciField);

                    panel.add(minTasarimciLabel);
                    panel.add(minTasarimciField);

                    panel.add(maxanalistLabel);
                    panel.add(maxAnalistField);

                    panel.add(maxProgramciLabel);
                    panel.add(maxProgramciField);

                    panel.add(maxTasarimciLabel);
                    panel.add(maxTasarimciField);

                    panel.add(yoneticiIdLabel);
                    panel.add(yoneticiIdField);

                    panel.add(statusLabel);
                    panel.add(statusCombo);

                    int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Proje",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        int minAnalist = Integer.parseInt(minAnalistField.getText());
                        int minProgramci = Integer.parseInt(minProgramciField.getText());
                        int minTasarimci = Integer.parseInt(minTasarimciField.getText());
                        int maxAnalist = Integer.parseInt(maxAnalistField.getText());
                        int maxProgramci = Integer.parseInt(maxProgramciField.getText());
                        int maxTasarimci = Integer.parseInt(maxTasarimciField.getText());

                        int yonetici = Integer.parseInt(yoneticiIdField.getText());

                        boolean status = Boolean.parseBoolean(String.valueOf(statusCombo.getSelectedItem()));
                        String projectName = projectNameField.getText();


                        database.getProjectsFromDatabase();
                        database.getCalisanlarFromDatabase();
                        List<Calisan> calisanlar = database.getCalisanlar();
                        for (int i = 0; i < calisanlar.size(); i++) {
                            if (yonetici == calisanlar.get(i).getId()) {
                                Calisan tempYonetici = calisanlar.get(i);
                                Proje newProject = new Proje(projectName, minAnalist, minProgramci, minTasarimci, maxAnalist, maxProgramci, maxTasarimci, tempYonetici);
                                newProject.setId(Integer.parseInt(idActual));
                                newProject.setStatus(status);

                                database.updateProjectById(newProject);
                            }

                        }

                        tableModel = buildTableModel(database.getAndReturnProjectsFromDatabase());
                        projectsTable.setModel(tableModel);
                        /*  update project by id and values above  */
                    } else {
                        // this line for refresh the view state
                        projectsTable.setModel(buildTableModel(database.getAndReturnProjectsFromDatabase()));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Lütfen tablodan bir proje seçiniz", "InfoBox: " + "Seçim Hatası", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });

        saveStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DefaultTableModel tableModel = (DefaultTableModel) staffsTable.getModel();
                    int row = staffsTable.getSelectedRow();
                    String idActual = tableModel.getValueAt(row, 0).toString();
                    String salaryActual = tableModel.getValueAt(row, 1).toString();
                    Boolean statusActual = Boolean.parseBoolean(tableModel.getValueAt(row, 2).toString());
                    String nameActual = tableModel.getValueAt(row, 3).toString();
                    String workerTypeActual = tableModel.getValueAt(row, 4).toString();
                    String projectNameActual = tableModel.getValueAt(row, 5).toString();


                    NumberFormat format = NumberFormat.getInstance();
                    NumberFormatter formatter = new NumberFormatter(format);
                    formatter.setValueClass(Integer.class);
                    formatter.setMinimum(0);
                    formatter.setMaximum(Integer.MAX_VALUE);
                    formatter.setAllowsInvalid(true);
                    formatter.setCommitsOnValidEdit(true);

                    JPanel panel = new JPanel(new GridLayout(0, 2));

                    JLabel nameLabel = new JLabel("Ad Soyad : ");
                    JTextField nameField = new JTextField(nameActual);

                    JLabel salaryLabel = new JLabel("Maas : ");
                    JFormattedTextField salaryField = new JFormattedTextField(formatter);
                    salaryField.setText(salaryActual);

                    JLabel workerRolesLabel = new JLabel("Pozisyon : ");
                    String[] workerRoles = {"yonetici", "programci", "analist", "tasarimci"};
                    JComboBox<String> workerRolesCombo = new JComboBox<>(workerRoles);
                    switch (workerTypeActual) {
                        case "yonetici":
                            workerRolesCombo.setSelectedIndex(0);
                            break;
                        case "programci":
                            workerRolesCombo.setSelectedIndex(1);
                            break;
                        case "analist":
                            workerRolesCombo.setSelectedIndex(2);
                            break;
                        case "tasarimci":
                            workerRolesCombo.setSelectedIndex(3);
                            break;
                    }

                    JLabel projectNameLabel = new JLabel("Proje Adi : ");

                    ArrayList<String> projectNames = new ArrayList<String>();
                    database.getProjectsFromDatabase();
                    database.getCalisanlarFromDatabase();
                    ArrayList<Proje> projeler = database.getProjeler();
                    for (int i = 0; i < projeler.size(); i++) {
                        if (!projectNames.contains(projeler.get(i).getProjectName())) {
                            projectNames.add(projeler.get(i).getProjectName());
                        }

                    }
                    Object[] projectNamesObjArr = projectNames.toArray();
                    String[] projectNamesStrArr = Arrays.copyOf(projectNamesObjArr, projectNamesObjArr.length, String[].class);
                    JComboBox<String> projectNamesCombo = new JComboBox<String>(projectNamesStrArr);
                    for (int i=0; i < projectNamesStrArr.length; i++){
                        if (projectNamesStrArr[i].equals(projectNameActual))
                            projectNamesCombo.setSelectedItem(i);
                    }



                    JLabel statusLabel = new JLabel("Durum : ");
                    String[] statusValues = {"true", "false"};
                    JComboBox<String> statusCombo = new JComboBox<>(statusValues);
                    statusCombo.setSelectedItem(statusActual ? 1 : 0);

                    panel.add(nameLabel);
                    panel.add(nameField);

                    panel.add(salaryLabel);
                    panel.add(salaryField);

                    panel.add(workerRolesLabel);
                    panel.add(workerRolesCombo);

                    panel.add(projectNameLabel);
                    panel.add(projectNamesCombo);

                    panel.add(statusLabel);
                    panel.add(statusCombo);


                    int result = JOptionPane.showConfirmDialog(null, panel, "Yeni Calisan",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {


                        String name = nameField.getText();

                        int salary = Integer.parseInt(salaryField.getText().replaceAll("\\,", ""));
                        String workerRole = String.valueOf(workerRolesCombo.getSelectedItem());
                        String projectName = String.valueOf(projectNamesCombo.getSelectedItem());
                        boolean status = Boolean.parseBoolean(String.valueOf(statusCombo.getSelectedItem()));


                        database.getSirket().setProjeler(new ArrayList<Proje>());
                        database.getSirket().setCalisanlar(new ArrayList<Calisan>());

                        database.getProjectsFromDatabase();
                        database.getCalisanlarFromDatabase();

                        Calisan newCalisan;

                        if (workerRole == "programci") {
                            newCalisan = new Programci(name, salary);
                        } else if (workerRole == "analist") {
                            newCalisan = new Analist(name, salary);
                        } else if (workerRole == "yonetici") {
                            newCalisan = new Yonetici(name, salary);
                        } else if (workerRole == "tasarimci") {
                            newCalisan = new Tasarimci(name, salary);
                        } else {
                            newCalisan = new Calisan(name, salary); // hi� bir bilgi gelmediyse tipini bilmeden �retsin.
                        }

                        newCalisan.setId(Integer.parseInt(idActual));
                        newCalisan.setProjectName(projectName);
                        newCalisan.setStatus(status);
                        if (database.getSirket().addWorker(newCalisan)) {
                            database.updateCalisanById(newCalisan);
                        } else {
                            JOptionPane.showMessageDialog(null, "Bu proje için daha fazla çalışana gerek yok !", " ", JOptionPane.INFORMATION_MESSAGE);
                        }


                        tableModel = buildTableModel(database.getAndReturnCalisanlarFromDatabase());
                        staffsTable.setModel(tableModel);

                        /*  update project by id and values above  */

                    } else {
                        // this line for refresh the view state
                        staffsTable.setModel(buildTableModel(database.getAndReturnCalisanlarFromDatabase()));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lütfen tablodan bir çalışan seçiniz", "InfoBox: " + "Seçim Hatası", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    public static KeyAdapter myIntValidation(final JLabel label, final JTextField tf){
        return new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (!(ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')) {
                    tf.setText("");
                }
            }
        };
    }

    public static void main (String[] args) {
        YazMuhGUI frame = new YazMuhGUI("Sirket Paneli");
        frame.setSize(1366,768);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.projectsPanel.setVisible(false);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
}
