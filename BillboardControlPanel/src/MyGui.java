import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//import Server.BillboardServer.*;
//
//import static Server.Hash.getHash;

public class MyGui extends Component implements ActionListener {

    JFrame f;
    JButton bt1 ;
    JTextField t1,t2;
    JLabel l1,l2;

    String token;
    MyGui(){

        f=new JFrame("LOG IN FORM");
        f.setLocation(500,300);
        f.setSize(450,300);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1=new JLabel("NAME");
        l1.setBounds(50,70,80,30);

        l2=new JLabel("PASSWORD");
        l2.setBounds(50,100,80,30);

        t1=new JTextField();
        t1.setBounds(140, 70, 200,30);

        t2=new JPasswordField();
        t2.setBounds(140, 110, 200,30);

        bt1 =new JButton("LOG IN");
        bt1.setBounds(150,150,80,30);
        bt1.addActionListener(this);


        f.add(l1);
        f.add(l2);
        f.add(t1);
        f.add(t2);
        f.add(bt1);

        f.setVisible(true);

    }
    public static void main(String[] args) {
        MyGui myGuo = new MyGui();
    }

    public void actionPerformed(ActionEvent ae) {
        String userName = t1.getText();
        String password = t2.getText();
//        try {
//            String Hashed_password = getHash(password);
//            System.out.println(Hashed_password);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        token = "123456789";

//        send username and password to server
//        recieve its response as token or error
        if (userName.trim().equals("admin") && password.trim().equals("admin")) {

            JFrame frame = null;
            try {
                frame = new Createbillboard("Create Billboard",token,userName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            frame.setLocation(500,300);
            frame.setSize(550,550);
            frame.setVisible(true);
            //System.out.println(System.getProperty("user.dir")+"/xmlFile.xml");
        } else {
            JOptionPane.showMessageDialog(null,"Username or Password incorrect" );

        }
    }
}