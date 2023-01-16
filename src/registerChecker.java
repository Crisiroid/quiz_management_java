import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class registerChecker {
    public JPanel registerCheckerMainPanel;
    private JButton registerBtn;
    private JButton loginBtn;
    public registerChecker(){

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new registerPage().registerPagePanel, "Register in application");
            }
        });
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.deleteFrame();
                Main.openFrame(new loginPage().loginPagePanel, "Login to application");
            }
        });
    }
}

