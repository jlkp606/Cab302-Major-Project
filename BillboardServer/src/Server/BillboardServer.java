package Server;

import DataBase.User;
import Token.Token;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;

public class BillboardServer {
    public static ServerSocket getServerSocket() throws IOException {
        Properties props = new Properties();
        FileInputStream in = new FileInputStream("./network.props");
        props.load(in);
        in.close();

        String port = props.getProperty("network.port");

        ServerSocket socket = new ServerSocket(Integer.parseInt(port));
        return socket;
    }
    public void sendResponse(){

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //Stores users that have logged in
        HashMap<Token, String> tokenStore = new HashMap<Token, String>();

        //get Socket
        ServerSocket serverSocket = getServerSocket();

        //connect to database
        JDBCBillboardDataSource dataSource = new JDBCBillboardDataSource();

        for ( ; ; ) {
            Socket clientSocket = serverSocket.accept();

            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            HashMap<String, Object> request = (HashMap<String, Object>) objectInputStream.readObject();

            switch ((String) request.get("type")){
                case "log in":
                    System.out.println("hi");
                    Token token = new Token();
                    tokenStore.put(token, (String) request.get("username"));
                    HashMap<String, Object> res = new HashMap<String, Object>();
                    res.put("token", token.getToken());

                    OutputStream out = clientSocket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                    objectOutputStream.writeObject(res);
                    objectOutputStream.flush();
                    break;

                case "log out":

                    break;
                case "create user":
                    User user =
                            new User((String) request.get("username"),
                            (String) request.get("password"));
                    dataSource.addUser(user);
                    break;

            }

            clientSocket.close();
        }
    }
}

