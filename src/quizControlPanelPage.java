import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class quizControlPanelPage {
    JPanel quizControlPanelPagePanel;
    private JButton addQuizBtn;
    private JButton quitBtn;
    private JLabel topTextLabel;

    public quizControlPanelPage() {
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new adminControllPanelPage().adminControllPanelPagePanel, "Admin's control panel");
            }
        });
        addQuizBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new quizAddPage().quizAddPagePanel, "Add Quiz");
            }
        });
    }
}
