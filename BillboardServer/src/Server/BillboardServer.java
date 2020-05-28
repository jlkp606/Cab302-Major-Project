package Server;

import DataBase.User;
import Token.Token;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import Server.Hash;

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
    public static void sendResponse(Socket clientSocket, HashMap<String, Object> response) throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {

        //Stores users that have logged in
        HashMap<Token, String> tokenStore = new HashMap<Token, String>();

        //get Socket
        ServerSocket serverSocket = getServerSocket();

        //connect to database
        Database.JDBCDatabaseSource dataSource = new Database.JDBCDatabaseSource();

        for ( ; ; ) {
            Socket clientSocket = serverSocket.accept();

            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            HashMap<String, Object> request = (HashMap<String, Object>) objectInputStream.readObject();

            switch ((String) request.get("type")){
                case "logIn": {
                    String username = (String) request.get("username");
                    //hashed
                    String password = (String) request.get("password");

                    //Authenticate (query to check if user exists and has this password)
                    //query- getSalt for user
                    String salt = "asdasdasd";
                    String dbPassword = Hash.getHash(password + salt);

                    //query check dbPass is same as password in Db for username
                    Boolean query = true;
                    if (query == true){

                    }

                    //Create Token and Store it
                    Token token = new Token();
                    tokenStore.put(token, username);
                    HashMap<String, Object> res = new HashMap<String, Object>();
                    res.put("token", token.getToken());
                    sendResponse(clientSocket, res);
                    break;
                }
                case "logOut":{
                    Token token = (Token) request.get("token");
                    if (tokenStore.containsKey(token)){
                        tokenStore.remove(token);
                    }
                    break;
                }
                case "listBillboard":{

                    break;
                }
                case "getBillboardInfo":{
                    break;
                }
                case "createBillboard":{
                    break;
                }
                case "deleteBillboard":{
                    break;
                }
                case "viewSchedule":{
                    break;
                }
                case "scheduleBillboard":{
                    break;
                }
                case "removeBillboardFromSchedule":{
                    break;
                }
                case "listUsers":{
//                    ArrayList<String> userlist= dataSource.listUsers();
//                    HashMap<String, Object> response = new HashMap<>();
//                    response.put("userList", userlist);
//                    sendResponse(clientSocket, reponse);
                    break;
                }
                case "createUser": {
//                    String username = (String) request.get("username");
//                    String password = (String) request.get("password");
//
//                    Token token = (Token) request.get("token");
//                    if (tokenStore.containsKey(token)){
//                        User user = new User(username);
//                        user.setPassword(Hash.getHash(password + user.getPasswordSalt()));
//                        dataSource.addUser(user);
//                    }
                    break;
                }
                case "getUserPermissions": {
                    break;
                }
                case "setUserPermissions": {
                    break;
                }
                case "setUserPassword":{
                    String username = (String) request.get("username");
                    String password = (String) request.get("password");
                    String salt = dataSource.getSalt(username);
                    String dbPassword = Hash.getHash(password + salt);
                    dataSource.setUserPassword(username, dbPassword);
                    break;
                }
                case "deleteUser": {
                    String username = (String) request.get("username");
                    dataSource.deleteUser(username);
                    break;
                }
            }

            clientSocket.close();
        }
    }
}

