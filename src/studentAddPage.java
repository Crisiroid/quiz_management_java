import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class studentAddPage {
    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;

    JPanel studentAddPagePanel;
    private JTextField studentNameTextField;
    private JTextField studentLastNameTextField;
    private JTextField studentUserIdTextField;
    private JTextField studentPassWordTextField;
    private JTextField studentPhoneNumberTextField;
    private JTextField studentLessonsCodeTextField;
    private JButton submitBtn;
    private JButton quitBtn;
    private JLabel studentNameLabel;
    private JLabel studentLastNameLabel;
    private JLabel studentIdLabel;
    private JLabel studentPwLabel;
    private JLabel studentPhoneNumLabel;
    private JLabel studentLessonLabel;
    private JLabel topTextLabel;

public studentAddPage() {
    quitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.deleteFrame();
            Main.openFrame(new studentManagementControlPanelPage().studentManagementControlPanelPagePanel, "Student's control panel");
        }
    });

    submitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            connect();
            writeToDB(studentNameTextField.getText(), studentLastNameTextField.getText(), studentUserIdTextField.getText(), studentPassWordTextField.getText(), studentPhoneNumberTextField.getText(), studentLessonsCodeTextField.getText());
            connect();
            createStudentTable(studentUserIdTextField.getText());
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
    //Writing into Database
    public static void writeToDB(String firstName, String lastName, String teacherId, String passWord, String teacherPhonenum, String lessonID){
        try {
            C.setAutoCommit(false);
            stmt = C.createStatement();
            sql = "INSERT INTO studentDetails (studentName, studentLastName, studentID, studentPW, studentPhoneNum, studentLessonId)" +
                    "VALUES ('"+ firstName + "', '"+ lastName +"', '"+ teacherId +"', '"+ passWord +"', '"+ teacherPhonenum +"', '"+ lessonID +"');";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Records created successfully");
    }

    public static void createStudentTable(String userId){
        try{
            C.setAutoCommit(false);
            stmt = C.createStatement();
            String sql = "CREATE TABLE '" + userId +
                    "' (ID INTEGER PRIMARY KEY     AUTOINCREMENT NULL   UNIQUE," +
                    " lessonId           TEXT, " +
                    " quizCode            TEXT, " +
                    " quizDate        TEXT, " +
                    " quizGrade         TEXT)";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
            C.close();

        }catch ( Exception e ){
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "user created successfully!");
    }
}
