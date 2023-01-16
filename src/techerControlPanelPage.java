import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class techerControlPanelPage {
    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;

    private JButton quitBtn;
    private JButton deleteTeacherBtn;
    private JLabel teacherManagmentPage;
    JPanel teacherControlPanelPagePanel;
    private JButton addTeacherBtn;

    public techerControlPanelPage() {
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new adminControllPanelPage().adminControllPanelPagePanel, "Admin's control panel");
            }
        });

        addTeacherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new teacherAddPage().teacherAddPagePanel, "Add teacher");
            }
        });

        deleteTeacherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String teacherId = JOptionPane.showInputDialog("Enter Teacher's ID: ");
                connect();
                deleteTeachers(teacherId);
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


    public static void deleteTeachers(String teacherId){
        try {
            C.setAutoCommit(false);
            stmt = C.createStatement();
            sql = "DELETE FROM teacherDetails WHERE teacherID = '" + teacherId +"';";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Teacher is Deleted");
    }
}
