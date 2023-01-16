import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class testHistoryPage {
    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;
    static ResultSet res = null;
    static ResultSetMetaData rsmd;
    public int colNo;

    JPanel testHistoryPagePanel;
    private JLabel topTextLabel;
    private JTable historyTable;
    private JScrollPane tableScrollPane;
    private JButton quitBtn;

    public testHistoryPage(String studentId){
        try{
            connect();
            String[] tableColumnsName = {"Lesson Id", "Quiz Code","Quiz Date", "Grade"};
            DefaultTableModel aModel = (DefaultTableModel) historyTable.getModel();
            aModel.setColumnIdentifiers(tableColumnsName);
            Statement stmt = C.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT lessonId, quizCode, quizDate, quizGrade FROM '" + studentId +"';");


            System.out.printf(studentId);

            rsmd = rs.getMetaData();
            colNo = rsmd.getColumnCount();
            while (rs.next()) {
                Object[] objects = new Object[colNo];
                for (int i = 0; i < colNo; i++) {
                    objects[i] = rs.getObject(i + 1);
                }
                aModel.addRow(objects);
            }
            System.out.println(colNo);
            historyTable.setModel(aModel);
            C.close();
            stmt.close();
            rs.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new studentControlPanelPage(studentId).studentControlPanelPagePanel, "Student's control panel");
            }
        });
    }
    public static void connect() {
        //Opening connection to Sqlite using JDBC driver
        try {
            Class.forName("org.sqlite.JDBC");
            C = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
