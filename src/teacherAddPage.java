import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class teacherAddPage {
    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;

    JPanel teacherAddPagePanel;
    private JButton SubmitBtn;
    private JLabel topTextLabel;
    private JLabel teacherNameLabel;
    private JLabel teacherLastNameLabel;
    private JLabel teacherUserIdLabel;
    private JLabel teacherPassWordLabel;
    private JLabel teachersPhoneNumberLabel;
    private JLabel teachersLessonIdLabel;
    private JButton QuitBtn;
    private JTextField teacherNameTextField;
    private JTextField teacherLastNameTextField;
    private JTextField teacherUserIdTextField;
    private JTextField teacherPassWordTextField;
    private JTextField teacherPhoneNumberTextField;
    private JTextField teacherLessonIdTextField;

    public teacherAddPage() {
        QuitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new techerControlPanelPage().teacherControlPanelPagePanel, "Teacher's control panel");
            }
        });

        SubmitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
                writeToDB(teacherNameTextField.getText(), teacherLastNameTextField.getText(), teacherUserIdTextField.getText(), teacherPassWordTextField.getText(), teacherPhoneNumberTextField.getText(), teacherLessonIdTextField.getText());
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
            sql = "INSERT INTO teacherDetails (teacherName, teacherLastName, teacherID, teacherPW, teacherPhoneNum, lessonID)" +
                    "VALUES ('"+ firstName + "', '"+ lastName +"', '"+ teacherId +"', '"+ passWord +"', '"+ teacherPhonenum +"', '"+ lessonID +"');";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Records created successfully");
    }
}
