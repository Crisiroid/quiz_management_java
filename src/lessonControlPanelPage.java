import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class lessonControlPanelPage {

    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;

    JPanel lessonControlPanelPagePanel;
    private JButton deleteLessonButton;
    private JButton addLessonButton;
    private JButton quitButton;

    public lessonControlPanelPage() {
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new adminControllPanelPage().adminControllPanelPagePanel, "Admin's control panel");
            }
        });
        addLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new lessonAddPage().lessonAddPagePanel, "Add Lesson");
            }
        });
        deleteLessonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lessonId = JOptionPane.showInputDialog("Enter Lesson's ID: ");
                connect();
                deleteLesson(lessonId);
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


    public static void deleteLesson(String lessonId){
        try {
            C.setAutoCommit(false);
            stmt = C.createStatement();
            sql = "DROP TABLE " + lessonId +";";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
            C.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Lesson is Deleted");
    }
}
