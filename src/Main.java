import javax.swing.*;
import java.awt.*;

public class Main {
    public static JFrame mainFrame;
    public static void main(String[] args){
        openFrame(new registerChecker().registerCheckerMainPanel, "Quiz app");
    }
    /*
    ساخت یک عدد صفحه جدید با استفاده از تابع
    محتویات صفحه و عنوان صفحه به عنوان ورودی دریافت می شوند.
     */
    public static void openFrame(Container newJframe, String title){
        mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setContentPane(newJframe);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    /*
    حذف یا تغییر صفحه ایجاد شده
     */
    public static void deleteFrame(){
        mainFrame.dispose();
    }
}

