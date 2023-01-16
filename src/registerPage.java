import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class registerPage {
    // متغیر های پایگاه داده
    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;
    JPanel registerPagePanel;
    private JTextField nameTextField;
    private JTextField userNameTextField;
    private JTextField lastNameTextField;
    private JButton submitBtn;
    private JLabel nameLabel;
    private JLabel lastNameLabel;
    private JLabel userNameLabel;
    private JLabel passWordLabel;
    private JTextField passWordTextField;
    private JComboBox accessTypeBox;
    private JLabel rankLabel;

    public registerPage(){
        accessTypeBox.addItem("Student");
        accessTypeBox.addItem("Admin");
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
                writeToDB(nameTextField.getText(), lastNameTextField.getText(), userNameTextField.getText(), passWordTextField.getText().toString(), accessTypeBox.getSelectedItem().toString());
                if(accessTypeBox.getSelectedItem() == "Student"){
                    connect();
                    createstudentTable(userNameTextField.getText());
                }

                Main.deleteFrame();
                Main.openFrame(new loginPage().loginPagePanel, "Login page");
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
    public static void writeToDB(String firstName, String lastName, String userName, String passWord, String rank){
        try {
            C.setAutoCommit(false);
            stmt = C.createStatement();
            sql = "INSERT INTO userDetails (firstName, lastName, userName, password, rank)" +
                    "VALUES ('"+ firstName + "', '"+ lastName +"', '"+ userName +"', '"+ passWord +"', '"+ rank +"');";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Records created successfully");

    }
    public static void createstudentTable(String userId){
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
