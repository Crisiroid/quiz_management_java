import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class quizAddPage {
    static Connection C = null;
    static String sql = null;
    static Statement stmt = null;

    JPanel quizAddPagePanel;
    private JLabel lessonIdLabel;
    private JLabel teacherIdLabel;
    private JLabel questionNumLabel;
    private JButton submitBtn;
    private JTextField lessonIdTextField;
    private JTextField teacherIdTextFeild;
    private JTextField qustionNumTextField;
    private JButton quitBtn;
    private JTextField quizCodeTextField;
    private JLabel quizCodeLabel;

    public quizAddPage() {

    quitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.deleteFrame();
            Main.openFrame(new quizControlPanelPage().quizControlPanelPagePanel, "Quiz Control panel");
        }
    });
    submitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String[] questionList = new String[Integer.parseInt(qustionNumTextField.getText())];
            String[] answerOne = new String[Integer.parseInt(qustionNumTextField.getText())];
            String[] answerTwo = new String[Integer.parseInt(qustionNumTextField.getText())];
            String[] answerTree = new String[Integer.parseInt(qustionNumTextField.getText())];
            String[] answerFour = new String[Integer.parseInt(qustionNumTextField.getText())];
            connect();
            createQuizTable(quizCodeTextField.getText());
            System.out.println("Level one is done");

            connect();
            createAnswerTable(quizCodeTextField.getText());

            connect();
            quizAndLesson(lessonIdTextField.getText(), quizCodeTextField.getText());
            System.out.println("Level two is done");

            for(int i = 0; i < Integer.parseInt(qustionNumTextField.getText()); i++){
                connect();
                questionList[i] = JOptionPane.showInputDialog("Enter question number: " + i);
                answerOne[i] = JOptionPane.showInputDialog("Enter Answer One for question number: " + i);
                answerTwo[i] = JOptionPane.showInputDialog("Enter Answer Two for question number: " + i);
                answerTree[i] = JOptionPane.showInputDialog("Enter Answer Tree for question number: " + i);
                answerFour[i] = JOptionPane.showInputDialog("Enter Answer Four for question number: " + i);
                writeToDB(quizCodeTextField.getText(), teacherIdTextFeild.getText(), questionList[i], answerOne[i], answerTwo[i], answerTree[i], answerFour[i]);
            }
            System.out.println("Level tree is done");

            JOptionPane.showMessageDialog(null, "This is your quiz code, give it to the teachers and students " + quizCodeTextField.getText());
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

    public static void createQuizTable(String quizId){
        try{
            stmt = C.createStatement();
            C.setAutoCommit(false);
            String sql = "CREATE TABLE 'QUIZNUM0" + quizId +
                    "' (ID INTEGER PRIMARY KEY     AUTOINCREMENT NULL   UNIQUE," +
                    " teacherId           TEXT, " +
                    " question           TEXT, " +
                    " answerOne           TEXT, " +
                    " answerTwo           TEXT, " +
                    " answerTree           TEXT, " +
                    " answerFour           TEXT, " +
                    " studentId            TEXT)";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
            C.close();

        }catch ( Exception e ){
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static void createAnswerTable(String quizId){
        try{
            stmt = C.createStatement();
            C.setAutoCommit(false);
            String sql = "CREATE TABLE 'QUIZNUM1" + quizId +
                    "' (ID INTEGER PRIMARY KEY     AUTOINCREMENT NULL   UNIQUE," +
                    " questionNum           TEXT, " +
                    " answerNum           TEXT, " +
                    " studentId            TEXT)";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
            C.close();

        }catch ( Exception e ){
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
    }
    public static void quizAndLesson(String lessonId, String quizId){
        try {
            C.setAutoCommit(false);
            stmt = C.createStatement();
            sql = "INSERT INTO quizAndLesson (lessonId, quizId)" +
                    "VALUES ('"+ lessonId + "', '"+ quizId +"');";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Records created successfully");
    }

    public static void writeToDB(String lessonId, String teacherId, String question, String answerOne, String answerTwo, String answerTree, String answerFour){
        try {
            C.setAutoCommit(false);
            stmt = C.createStatement();
            sql = "INSERT INTO 'QUIZNUM0"+ lessonId +"' (teacherId, question, answerOne, answerTwo, answerTree, answerFour)" +
                    "VALUES ('"+ teacherId + "', '"+ question +"', '"+ answerOne +"', '"+ answerTwo +"', '"+ answerTree +"', '"+ answerFour +"');";
            stmt.executeUpdate(sql);
            C.commit();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getClass().getName() + ": " + e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Records created successfully");
    }
}
