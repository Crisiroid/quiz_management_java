import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class quizPage {
    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;
    static ResultSet res = null;
    static ResultSetMetaData rsmd;
    public int colNo;

    JPanel quizPagePanel;
    private JLabel topTextLabel;
    private JTable quizTable;
    private JButton answerBtn;
    private JScrollPane tableScrollPane;

    public quizPage(String quizCode, String studentId){
        try {
            connect();
            String[] tableColumnsName = {"Question", "Answer One","Answer Two", "Answer Tree", "Answer Four"};
            DefaultTableModel aModel = (DefaultTableModel) quizTable.getModel();
            aModel.setColumnIdentifiers(tableColumnsName);
            Statement stmt = C.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT question, answerOne, answerTwo, answerTree, answerFour FROM 'QUIZNUM0" + quizCode +"';");

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
            quizTable.setModel(aModel);
            C.close();
            stmt.close();
            rs.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        answerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < colNo; i++){
                    String answer = JOptionPane.showInputDialog("Enter answer for Question number " + i + " :");
                    connect();
                    writeToDB(studentId, quizCode, String.valueOf(i), answer);
                }

                connect();
                submitQuiz(studentId, quizCode);
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

    public static void writeToDB(String studentCode, String quizCode, String questionNum, String answer){
        try {
            C.setAutoCommit(false);
            stmt = C.createStatement();
            sql = "INSERT INTO 'QUIZNUM1"+ quizCode + "' (questionNum, answerNum, studentId)" +
                    "VALUES ('"+ questionNum + "', '"+ answer +"', '"+ studentCode +"');";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Your answer is sumbitted!");

    }
    public static void submitQuiz(String studentCode, String quizCode){
        try {
            C.setAutoCommit(false);
            stmt = C.createStatement();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String pending = "Pending";
            sql = "INSERT INTO '"+ studentCode + "' (lessonId, quizCode, quizDate, quizGrade)" +
                    "VALUES ('"+ null + "', '"+ quizCode +"', '"+ dtf.format(now) +"', '"+ pending +"');";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Your answer is sumbitted!");

    }
}
