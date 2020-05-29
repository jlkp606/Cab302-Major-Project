package Server;

import Database.JDBCDatabaseSource;
import Database.User;
import Database.Billboard;
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
        //current token
        Token token;
        //get Socket
        ServerSocket serverSocket = getServerSocket();

        //connect to database
        JDBCDatabaseSource dataSource = new JDBCDatabaseSource();

        for ( ; ; ) {
            Socket clientSocket = serverSocket.accept();

            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            HashMap<String, Object> request = (HashMap<String, Object>) objectInputStream.readObject();


            HashMap<String, Object> response = new HashMap<>();

            String requestType = (String) request.get("type");

            if (requestType == "logIn"){
                System.out.println("hi");
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
                token = new Token();
                tokenStore.put(token, username);
                HashMap<String, Object> res = new HashMap<String, Object>();
                res.put("token", token.getToken());
                sendResponse(clientSocket, res);
            }
            else{
                token = (Token) request.get("token");
                if (tokenStore.containsKey(token)){

                    switch (requestType){
                        case "logOut":{
                            tokenStore.remove(token);
                            break;
                        }
                        case "listBillboard":{
//                            ArrayList<Billboard> billboardList = dataSource.getBillboardList();
//                            response.put("billboardList", billboardList);
//                            sendResponse(clientSocket, response);
                            break;
                        }
                        case "getBillboardInfo":{
//                            String billboardName = (String) request.get("billboardName");
//                            Billboard billboard = dataSource.getBillboard(billboardName);
//                            response.put("billboard", billboard);
//                            sendResponse(clientSocket, response);
                            break;
                        }
                        case "createBillboard":{
                            Billboard billboard = (Billboard) request.get("billboard");
                            dataSource.addBillboard(billboard);
                            break;
                        }
                        case "deleteBillboard":{
//                            String billboardName = (String) request.get("billboardName");
//                            dataSource.deleteBillboard(billboardName);
                            break;
                        }
                        case "viewSchedule":{
//                            ArrayList<Schedule> scheduleList = dataSource.getScheduleList();
//                            response.put("scheduleList", scheduleList);
//                            sendResponse(clientSocket, response);
                            break;
                        }
                        case "scheduleBillboard":{
//                            Schedule schedule = request.get("schedule");
//                            dataSource.addSchedule(schedule);
                            break;
                        }
                        case "removeBillboardFromSchedule":{
//                            Schedule schedule = request.get("schedule");
//                            dataSource.deleteSchedule(schedule);
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
                            String username = (String) request.get("username");
                            String password = (String) request.get("password");

                            User user = new User();
                            user.setUsername(username);
                            user.setPassword(Hash.getHash(password + user.getPasswordSalt()));
                            dataSource.addUser(user);
                            //also add permission List
                            break;
                        }
                        case "getUserPermissions": {
//                            String username = (String) request.get("username");
//                            ArrayList<Boolean> permList =  dataSource.getUserPermission(username);
//                            response.put("permissionList", permList);
//                            sendResponse(clientSocket, response);
                            break;
                        }
                        case "setUserPermissions": {
//                            String username = (String) request.get("username");
//                            ArrayList<Boolean> permList =  (ArrayList<Boolean>) request.get("permissionList");
//                            dataSource.setUserPermissions(username, permList);
                            break;
                        }
                        case "setUserPassword":{
        //                    String username = (String) request.get("username");
        //                    String password = (String) request.get("password");
        //                    String salt = dataSource.getSalt(username);
        //                    String dbPassword = Hash.getHash(password + salt);
        //                    dataSource.setUserPassword(username, dbPassword);
        //                    break;
                        }
                        case "modifyUser": {
                            //delete user
                            //make a new one
                        }
                        case "deleteUser": {
        //                    String username = (String) request.get("username");
        //                    dataSource.deleteUser(username);
        //                    break;
                        }
                    }
                } else {
                    System.out.println("Invalid Token");
                }
            }
            clientSocket.close();
        }
    }
}

