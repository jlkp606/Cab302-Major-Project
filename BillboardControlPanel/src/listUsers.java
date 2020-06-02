import Server.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static Server.Client.getResponse;
import static Server.Client.sendRequest;

public class listUsers extends JFrame {



    private JButton modifyUserButton;
    private JButton createUserButton;
    private JButton deleteUserButton;
//    private  String[] data = new String[100];

    public static ArrayList<String> ListUser(String token) throws IOException, ClassNotFoundException{
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "listUsers");
        sendRequest(socket, request);
        HashMap<String, Object> response = getResponse(socket);
        ArrayList<String> userList = (ArrayList<String>) response.get("userList");
        socket.close();
        return userList;
    }

    public static void DeleteUser(String token,String user) throws IOException, ClassNotFoundException{
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "deleteUser");
        request.put("username", user);
        sendRequest(socket, request);
        socket.close();
    }

    public listUsers(String title, String token,String user) throws IOException, ClassNotFoundException {
        super(title);
        ArrayList<String> users = ListUser( token);
//        ArrayList<String> users = new ArrayList<String>();
//        users.add("Volvo");
//        users.add("BMW");
//        users.add("Ford");
//        users.add("Mazda");
//
        DefaultListModel model = new DefaultListModel();
        JList list= new JList(model) ;

        for (String s : users) {
            model.addElement(s);
        }

        this.getContentPane().setLayout(new BorderLayout());



        modifyUserButton= new JButton("Modify User");
        createUserButton = new JButton("Create User");
        deleteUserButton = new JButton("Delete User");

        JLabel label = new JLabel(" User");
        JPanel panel = new JPanel();
        panel.add(label);

        JPanel panel_2 = new JPanel();
        panel_2.add(modifyUserButton, BorderLayout.LINE_START);
        panel_2.add(createUserButton, BorderLayout.CENTER);
        panel_2.add(deleteUserButton, BorderLayout.LINE_END);

        JFrame frame = new JFrame();
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(list), BorderLayout.CENTER);
        frame.add(panel_2, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(550,550);
        frame.pack();
        frame.setVisible(true);

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();

                String s = (String) list.getSelectedValue();

                if(user.equals(s)){
                    JOptionPane.showMessageDialog(null, "Invalid operation ");
                }
                else {
                    Database.Permissions permissions = null;
                    try {
                        permissions = List_billboard.GetUserPermission(token, user);
                    } catch (IOException | ClassNotFoundException exc) {
                        exc.printStackTrace();
                    }
                    assert permissions != null;
                    if (permissions.getEditUsers().equals("true")) {
                        try {
                            DeleteUser(token, user);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        model.removeElementAt(index);
                    } else {
                        JOptionPane.showMessageDialog(null, "Permission required ");
                    }
                }
            }
        });

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Database.Permissions permissions = null;
                try {
                    permissions = List_billboard.GetUserPermission(token, user);
                } catch (IOException | ClassNotFoundException exc) {
                    exc.printStackTrace();
                }
                assert permissions != null;
                if (permissions.getEditUsers().equals("true")) {
                        JFrame frame = new CreateUser("Create User",token);
                        frame.setLocation(500,300);
                        frame.setSize(550,550);
                        frame.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Permission required ");
                }

            }
        });

        //1. Clicking on Modify Button will take you to Modify User GUI
        modifyUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = (String) list.getSelectedValue();
                Database.Permissions permissions = null;
                try {
                    permissions = List_billboard.GetUserPermission(token, user);
                } catch (IOException | ClassNotFoundException exc) {
                    exc.printStackTrace();
                }
                if (permissions != null) {
                    if (permissions.getEditUsers().equals("true")) {
                        JFrame frame = null;
                        try {
                            frame = new ModifyUserDetails("Modify User Details",token,user,s);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        assert frame != null;
                        frame.setLocation(500,300);
                        frame.setSize(550,550);
                        frame.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Permission required ");
                    }
                }
            }

        });

    }

//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String token = "hsgydfuyigs";
//        String user = "sdgfuiygs";
//        new listUsers(" Users",token,user);
//    }

}