import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class loginPage {
    public static String accessType;

    //متغیر های اولیه پایگاه داده
    public static Connection C = null;
    public static Statement stmt = null;
    static ResultSet res = null;
    JPanel loginPagePanel;
    private JButton loginBtn;
    private JLabel login;
    private JLabel userNameLabel;
    private JLabel passWordLabel;
    private JTextField passWordTextField;
    private JTextField userNameTextField;

    public static String userName;

    public loginPage() {

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connect();
                authenticate(userNameTextField.getText(), passWordTextField.getText());
                userName = userNameTextField.getText();
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

    public static void authenticate(String userName, String passWord){
        //searching in database for username and password
        try{
            C.setAutoCommit(false);
            stmt = C.createStatement();
            res = stmt.executeQuery("SELECT EXISTS(SELECT 1 FROM userDetails where userName='"+userName+"' and password='"+passWord+"');");

            if(res.next()){
                boolean found = res.getBoolean(1);
                if (found) {
                    accessCheck(userName);

                } else {
                    JOptionPane.showMessageDialog(null, "We Don't have You on record. Please Call Administration");
                }

                C.commit();
                stmt.close();
                C.close();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }

    }
    public static void accessCheck(String userName){
        //searching in database for username and password
        try{
            C.setAutoCommit(false);
            stmt = C.createStatement();
            res = stmt.executeQuery("select * from userDetails where userName = '" + userName +"';");
            res.next();
            System.out.println("this is working");
            accessType = res.getString("rank");


            if (accessType.equals("Student")) {
                Main.deleteFrame();
                Main.openFrame(new studentControlPanelPage(userName).studentControlPanelPagePanel, "Student's control panel");
                System.out.println("111");
            }

            if(accessType.equals("Admin")){
                Main.deleteFrame();
                Main.openFrame(new adminControllPanelPage().adminControllPanelPagePanel, "Admin's control panel");
                System.out.println("222");
            }

            C.commit();
            stmt.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
