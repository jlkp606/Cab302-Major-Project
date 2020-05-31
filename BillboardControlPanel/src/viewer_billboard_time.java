import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class viewer_billboard_time extends JFrame{
    private JTextField Friday_9;
    private JTextField Monday_9;
    private JTextField Monday_10;
    private JTextField Monday_11;
    private JTextField Monday_12;
    private JPanel mainPanel;
    private JTextField Friday_4;
    private JTextField Friday_10;
    private JTextField Friday_11;
    private JTextField Friday_12;
    private JTextField Friday_1;
    private JTextField Friday_2;
    private JTextField Friday_3;
    private JTextField Friday_5;
    private JTextField Thursday_9;
    private JTextField Thursday_10;
    private JTextField Thursday_11;
    private JTextField Thursday_12;
    private JTextField Thursday_1;
    private JTextField Thursday_2;
    private JTextField Thursday_3;
    private JTextField Thursday_4;
    private JTextField Thursday_5;
    private JTextField Wednesday_9;
    private JTextField Wednesday_10;
    private JTextField Wednesday_11;
    private JTextField Wednesday_12;
    private JTextField Wednesday_1;
    private JTextField Wednesday_2;
    private JTextField Wednesday_3;
    private JTextField Wednesday_4;
    private JTextField Wednesday_5;
    private JTextField Monday_1;
    private JTextField Monday_2;
    private JTextField Monday_3;
    private JTextField Monday_4;
    private JTextField Monday_5;
    private JTextField Tuesday_9;
    private JTextField Tuesday_10;
    private JTextField Tuesday_11;
    private JTextField Tuesday_12;
    private JTextField Tuesday_1;
    private JTextField Tuesday_2;
    private JTextField Tuesday_3;
    private JTextField Tuesday_4;
    private JTextField Tuesday_5;
    private JButton add;
    private JButton remove;

    public viewer_billboard_time(String title) {
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
        Monday_3.setEnabled(false);
        Monday_4.setEnabled(false);
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

            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new viewer_billboard_time("View Schedule for Billboards");
        frame.setLocation(400,200);
        frame.setSize(850,550);
        frame.setVisible(true);
    }

}
