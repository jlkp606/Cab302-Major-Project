import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import Server.Client;
import static Server.Hash.getHash;


public class MyGui extends Component implements ActionListener {

    JFrame f;
    JButton bt1;
    JTextField t1, t2;
    JLabel l1, l2;
//Comment it just for testing
    String token ;

    MyGui() {

        f = new JFrame("LOG IN FORM");
        f.setLocation(500, 300);
        f.setSize(450, 300);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        l1 = new JLabel("NAME");
        l1.setBounds(50, 70, 80, 30);

        l2 = new JLabel("PASSWORD");
        l2.setBounds(50, 100, 80, 30);

        t1 = new JTextField();
        t1.setBounds(140, 70, 200, 30);

        t2 = new JPasswordField();
        t2.setBounds(140, 110, 200, 30);

        bt1 = new JButton("LOG IN");
        bt1.setBounds(150, 150, 80, 30);
        bt1.addActionListener(this);


        f.add(l1);
        f.add(l2);
        f.add(t1);
        f.add(t2);
        f.add(bt1);

        f.setVisible(true);

    }
    public static String LoginRequest(String Username, String Password) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("type", "logIn");
        request.put("username", Username);
        request.put("password", Password);
        Client.sendRequest(socket, request);
        HashMap<String, Object> res = Client.getResponse(socket);

        return (String) res.get("token");

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MyGui myGuo = new MyGui();
            }
        });

    }

    public void actionPerformed(ActionEvent ae) {
        String userName = t1.getText();
        String password = t2.getText();
//      UnComment it  for testing
        try {
            String Hashed_password = getHash(password);

            try {
                token = LoginRequest(userName, Hashed_password);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (token.equals("")){

            JOptionPane.showMessageDialog(null, "Incorrect Username or Password ");

        }

        else{

        try {
            JFrame frame = new ControlPanel("Control Panel",token,userName);
            frame.setLocation(500, 300);
            frame.setSize(550, 550);
            frame.setVisible(true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        f.dispose();;
        }

    }

}