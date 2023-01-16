import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class studentManagementControlPanelPage {
    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;

    private JButton quitBtn;
    private JButton deleteStudentBtn;
    JPanel studentManagementControlPanelPagePanel;
    private JButton addstudentBtn;

    public studentManagementControlPanelPage() {
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new adminControllPanelPage().adminControllPanelPagePanel, "Admin's control panel");
            }
        });

        addstudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new studentAddPage().studentAddPagePanel, "Add Student");
            }
        });

        deleteStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String  studentId = JOptionPane.showInputDialog("Enter Lesson's ID: ");
                connect();
                deleteStudent(studentId);
            }
        });
    }

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            C = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void deleteStudent(String studentId){
        try {
            C.setAutoCommit(false);
            stmt = C.createStatement();
            sql = "DROP TABLE " + studentId +";";
            stmt.executeUpdate(sql);

            sql = "DELETE FROM studentDetails WHERE studentID = '" + studentId +"';";
            stmt.executeUpdate(sql);

            C.commit();
            stmt.close();
            C.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Student is Deleted");
    }

}
