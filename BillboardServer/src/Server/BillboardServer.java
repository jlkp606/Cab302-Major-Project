package Server;

import Database.JDBCDatabaseSource;
import Database.Permissions;
import Database.User;
import Database.Billboard;
import Token.Token;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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
        HashMap<String, ArrayList<Object>> tokenStore = new HashMap<String, ArrayList<Object>>();
        //current token
        String token;
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

            if (requestType.equals("logIn")){

                String username = (String) request.get("username");
                //hashed
                String password = (String) request.get("password");

                //Authenticate (query to check if user exists and has this password)
                User user = dataSource.getUser(username);
                String salt = user.getPasswordSalt();
                String HashSaltedPassword = password;
                //String HashSaltedPassword = Hash.getHash(password + salt);
                String dbPassword = user.getPassword();

                //query check hashSaltedPass is same as password in Db for username
                if (HashSaltedPassword.equals(dbPassword)){
                    Token newToken = new Token();
                    ArrayList<Object> tokenInfo =  new ArrayList<Object>();
                    tokenInfo.add(username);
                    tokenInfo.add(newToken.getExpiry());

                    tokenStore.put(newToken.getToken(), tokenInfo);
                    response.put("token", newToken.getToken());
                    sendResponse(clientSocket, response);
                }
                else{
                    response.put("message", "Incorrect password");
                    System.out.println("Incorrect password");
                }

            }
            else{
                token = (String) request.get("token");
                if (tokenStore.containsKey(token) || token.equals("kjryiauznhrjgrxypymj")){

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
                            String billboardName = (String) request.get("billboardName");
                            Billboard billboard = dataSource.getBillboard(billboardName);
                            response.put("billboard", billboard);
                            sendResponse(clientSocket, response);
                            break;
                        }
                        case "createBillboard":{
                            Billboard billboard = (Billboard) request.get("billboard");
                            dataSource.addBillboard(billboard);
                            break;
                        }
                        case "deleteBillboard":{
//                            String billboardName = (String) request.get("billboardName");
//                            dataSource.delete(billboardName);
//                            break;
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
                            //YES
        //                    ArrayList<String> userlist= dataSource.listUsers();
        //                    HashMap<String, Object> response = new HashMap<>();
        //                    response.put("userList", userlist);
        //                    sendResponse(clientSocket, reponse);
                            break;
                        }
                        case "createUser": {
                            //TESTED
                            String username = (String) request.get("username");
                            String password = (String) request.get("password");

                            ArrayList<Boolean> permList = (ArrayList<Boolean>) request.get("permissionList");
                            ArrayList<String> permListString = new ArrayList<>();

                            if (permList != null){
                                for (Boolean b : permList)
                                {
                                    String s = String.valueOf(b);
                                    permListString.add(s);
                                }
                                User user = new User();
                                user.setUsername(username);
                                user.setPassword(Hash.getHash(password + user.getPasswordSalt()));
                                dataSource.addUser(user);
                                dataSource.addUserPerms(username, permListString);
                            } else {
                                response.put("message", "Need to add permissions for the User");
                                System.out.println("Need to add permissions for the User");
                            }

                            break;
                        }
                        case "getUserPermissions": {
                            String username = (String) request.get("username");
                            Permissions permissions = dataSource.getUserPerms(username);
                            response.put("permissions", permissions);
                            sendResponse(clientSocket, response);
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
                            String username = (String) request.get("username");
//                            dataSource.updatePermList();
//                            maybe - dataSource.updatePassword();
                        }
                        case "deleteUser": {
                            String username = (String) request.get("username");
                            dataSource.deleteUser(username);
                            break;
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

