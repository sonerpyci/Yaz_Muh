import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        newProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = (DefaultTableModel)projectsTable.getModel();
                tableModel.addRow(new Object[]{});
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
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });


        deleteStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = (DefaultTableModel)staffsTable.getModel();

                System.out.println(staffsTable.getValueAt(staffsTable.getSelectedRow(), 0));
                database.deleteCalisanFromDatabase((Integer)staffsTable.getValueAt(staffsTable.getSelectedRow(), 0));
                tableModel.removeRow(staffsTable.getSelectedRow());
            }
        });
        deleteProjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = (DefaultTableModel)projectsTable.getModel();

                System.out.println(projectsTable.getValueAt(projectsTable.getSelectedRow(), 0));
                database.deleteProjectFromDatabase((Integer)projectsTable.getValueAt(projectsTable.getSelectedRow(), 0));
                tableModel.removeRow(projectsTable.getSelectedRow());
            }
        });


        this.pack();
        saveProjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = (DefaultTableModel)projectsTable.getModel();
                //projectsTable.get




            }
        });
        saveStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
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
