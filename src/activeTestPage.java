import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class activeTestPage {
    JPanel activeTestPagePanel;
    private JTextField quizCodeTextField;
    private JLabel quizCodeLabel;
    private JButton submitBtn;
    private JButton quitBtn;
public activeTestPage(String studentId) {
    quitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.deleteFrame();
            Main.openFrame(new studentControlPanelPage(studentId).studentControlPanelPagePanel, "Student's control panel");
        }
    });
    submitBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String quizCode = quizCodeTextField.getText();
            Main.deleteFrame();
            Main.openFrame(new quizPage(quizCode, studentId).quizPagePanel, "Quiz Page");
        }
    });
}
}
