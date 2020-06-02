import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class viewer_billboard_time extends JFrame{
    private JTextArea Friday_9;
    private JTextArea Monday_9;
    private JTextArea Monday_10;
    private JTextArea Monday_11;
    private JTextArea Monday_12;
    private JPanel mainPanel;
    private JTextArea Friday_4;
    private JTextArea Friday_10;
    private JTextArea Friday_11;
    private JTextArea Friday_12;
    private JTextArea Friday_1;
    private JTextArea Friday_2;
    private JTextArea Friday_3;
    private JTextArea Friday_5;
    private JTextArea Thursday_9;
    private JTextArea Thursday_10;
    private JTextArea Thursday_11;
    private JTextArea Thursday_12;
    private JTextArea Thursday_1;
    private JTextArea Thursday_2;
    private JTextArea Thursday_3;
    private JTextArea Thursday_4;
    private JTextArea Thursday_5;
    private JTextArea Wednesday_9;
    private JTextArea Wednesday_10;
    private JTextArea Wednesday_11;
    private JTextArea Wednesday_12;
    private JTextArea Wednesday_1;
    private JTextArea Wednesday_2;
    private JTextArea Wednesday_3;
    private JTextArea Wednesday_4;
    private JTextArea Wednesday_5;
    private JTextArea Monday_1;
    private JTextArea Monday_2;
    private JTextArea Monday_4;
    private JTextArea Monday_3;
    private JTextArea Monday_5;
    private JTextArea Tuesday_9;
    private JTextArea Tuesday_10;
    private JTextArea Tuesday_11;
    private JTextArea Tuesday_12;
    private JTextArea Tuesday_1;
    private JTextArea Tuesday_2;
    private JTextArea Tuesday_3;
    private JTextArea Tuesday_4;
    private JTextArea Tuesday_5;
    private JButton add;
    private JButton remove;

    public viewer_billboard_time(String title, String token ,String user) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        Monday_9.setEnabled(false);
        Monday_10.setEnabled(false);
        Monday_11.setEnabled(false);
        Monday_12.setEnabled(false);
        Monday_1.setEnabled(false);
        Monday_2.setEnabled(false);
        Monday_4.setEnabled(false);
        Monday_3.setEnabled(false);
        Monday_5.setEnabled(false);

        Tuesday_9.setEnabled(false);
        Tuesday_10.setEnabled(false);
        Tuesday_11.setEnabled(false);
        Tuesday_12.setEnabled(false);
        Tuesday_1.setEnabled(false);
        Tuesday_2.setEnabled(false);
        Tuesday_3.setEnabled(false);
        Tuesday_4.setEnabled(false);
        Tuesday_5.setEnabled(false);

        Wednesday_9.setEnabled(false);
        Wednesday_10.setEnabled(false);
        Wednesday_11.setEnabled(false);
        Wednesday_12.setEnabled(false);
        Wednesday_1.setEnabled(false);
        Wednesday_2.setEnabled(false);
        Wednesday_3.setEnabled(false);
        Wednesday_4.setEnabled(false);
        Wednesday_5.setEnabled(false);

        Thursday_9.setEnabled(false);
        Thursday_10.setEnabled(false);
        Thursday_11.setEnabled(false);
        Thursday_12.setEnabled(false);
        Thursday_1.setEnabled(false);
        Thursday_2.setEnabled(false);
        Thursday_3.setEnabled(false);
        Thursday_4.setEnabled(false);
        Thursday_5.setEnabled(false);

        Friday_9.setEnabled(false);
        Friday_10.setEnabled(false);
        Friday_11.setEnabled(false);
        Friday_12.setEnabled(false);
        Friday_1.setEnabled(false);
        Friday_2.setEnabled(false);
        Friday_3.setEnabled(false);
        Friday_4.setEnabled(false);
        Friday_5.setEnabled(false);



        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                JFrame frame = new Schedule_billboard("Add billboard to Schedule",user,token);
                frame.setLocation(400,200);
                frame.setSize(350,350);
                frame.setVisible(true);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        String user = "Sid";
        String token = "092408240280";
        JFrame frame = new viewer_billboard_time("View Schedule for Billboards",user,token);
        frame.setLocation(400,200);
        frame.setSize(850,550);
        frame.setVisible(true);
    }

}
