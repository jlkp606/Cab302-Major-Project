package DataBase;

import javax.swing.*;

public class addBillboardtoSchedule extends JFrame{
    private JTextField textField1;
    private JCheckBox everyDayCheckBox;
    private JTextField billboardName;
    private JTextField duration;
    private JTextField startTime;
    private JPanel mainPanel;

    public addBillboardtoSchedule(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }

    public static void main(String[] args) {
        JFrame frame = new addBillboardtoSchedule("Add billboard to Schedule");
        frame.setLocation(400,200);
        frame.setSize(300,350);
        frame.setVisible(true);
    }
}
