import com.sun.source.tree.NewArrayTree;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminControllPanelPage {
    JPanel adminControllPanelPagePanel;
    private JButton quizPageBtn;
    private JButton studentPageBtn;
    private JButton teacherPageBtn;
    private JLabel topTextLabel;
    private JButton lessonPageBtn;
    private JButton quitBtn;

    public adminControllPanelPage() {
        teacherPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new techerControlPanelPage().teacherControlPanelPagePanel, "Teacher's management");
            }
        });
        studentPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new studentManagementControlPanelPage().studentManagementControlPanelPagePanel, "Student's management");
            }
        });
        quizPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new quizControlPanelPage().quizControlPanelPagePanel, "Quiz's management");
            }
        });
        lessonPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new lessonControlPanelPage().lessonControlPanelPagePanel, "Lesson'sManagment" );

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
