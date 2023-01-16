import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class lessonAddPage {
    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;

    JPanel lessonAddPagePanel;
    private JLabel topTextLabel;
    private JLabel lessonIdLabel;
    private JLabel teacherIdLabel;
    private JLabel studentIdLabel;
    private JButton submitBtn;
    private JTextField lessonTeacherTextField;
    private JTextField lessonIdTextField;
    private JTextField studentIdTextField;
    private JButton quitBtn;
public lessonAddPage() {
    quitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.deleteFrame();
            Main.openFrame(new lessonControlPanelPage().lessonControlPanelPagePanel, "Lesson's control panel");
        }

    });

    submitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            connect();
            createLessonTable(lessonIdTextField.getText());
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

    public static void createLessonTable(String lessonId){
        try{
            stmt = C.createStatement();
            C.setAutoCommit(false);
            String sql = "CREATE TABLE '" + lessonId +
                    "' (ID INTEGER PRIMARY KEY     AUTOINCREMENT NULL   UNIQUE," +
                    " teacherId           TEXT, " +
                    " quizId           TEXT, " +
                    " quizDate           TEXT, " +
                    " studentId            TEXT)";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
            C.close();

        }catch ( Exception e ){
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "lesson created successfully!");
    }
}
