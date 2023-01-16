import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class studentControlPanelPage {
    JPanel studentControlPanelPagePanel;
    private JLabel topTextLabel;
    private JButton activeQuizBtn;
    private JButton testHistoryBtn;
    private JButton quitBtn;

    public studentControlPanelPage(String studentId) {
    activeQuizBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.deleteFrame();
            Main.openFrame(new activeTestPage(studentId).activeTestPagePanel, "Active Tests");
        }
    });

    testHistoryBtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main.deleteFrame();
            Main.openFrame(new testHistoryPage(studentId).testHistoryPagePanel, "Tests History");
        }
    });

        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new registerChecker().registerCheckerMainPanel, "Check Register");
            }
        });
    }
}
