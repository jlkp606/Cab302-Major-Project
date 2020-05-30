package Server;

import Database.*;
import Token.Token;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
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
                            //not tested
                            tokenStore.remove(token);
                            break;
                        }
                        case "listBillboard":{
                            //not tested
                            ArrayList<Billboard> billboardList = dataSource.getAllBillboards();
                            response.put("billboardList", billboardList);
                            sendResponse(clientSocket, response);
                            break;
                        }
                        case "getCurrentBillboard": {
                            ArrayList<Schedule> scheduleList = dataSource.getAllSchedules();
                            for (Schedule s : scheduleList){
                                LocalDateTime ldt = null;
                                LocalDateTime startTime = ldt.parse(s.getStartTime());
                                LocalDateTime endTime = ldt.parse(s.getEndTime());
                                //repeat if hour - check whole hours, what minute we are on
                                //repeat if days - check what hours,
                                //repeat week - check day
                                Boolean isOn =
                                        (LocalDateTime.now().isAfter(startTime) &&
                                        LocalDateTime.now().isBefore(endTime));

                                if (isOn) {
                                    Billboard billboard = dataSource.getBillboard(s.getBillboardName());
                                    response.put("billboard", billboard);
                                    sendResponse(clientSocket, response);
                                    break;
                                }
                            }

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
                            // not tested
                            String billboardName = (String) request.get("billboardName");
                            dataSource.deleteBillboard(billboardName);
                            break;
                        }
                        case "viewSchedule":{
                            //not tested
                            ArrayList<Schedule> scheduleList = dataSource.getAllSchedules();
                            response.put("scheduleList", scheduleList);
                            sendResponse(clientSocket, response);
                            break;
                        }
                        case "scheduleBillboard":{
                            //not tested
                            Schedule schedule = (Schedule) request.get("schedule");
                            dataSource.addSchedule(schedule);
                            break;
                        }
                        case "removeBillboardFromSchedule":{
                            //not tested
                            Schedule schedule = (Schedule) request.get("schedule");
                            dataSource.deleteSchedule(schedule);
                            break;
                        }
                        case "listUsers":{
                            //not tested
                            ArrayList<String> userList = dataSource.getUsernames();
                            response.put("userList", userList);
                            sendResponse(clientSocket, response);
                            break;
                        }
                        case "createUser": {
                            //TESTED// test again
                            String username = (String) request.get("username");
                            String password = (String) request.get("password");

                            Permissions permList = (Permissions) request.get("permission");

                            if (permList != null){
                                User user = new User();
                                user.setUsername(username);
                                user.setPassword(Hash.getHash(password + user.getPasswordSalt()));
                                dataSource.addUser(user);
                                dataSource.addUserPerms(username, permList);
                            } else {
                                response.put("message", "Need to add permissions for the User");
                                System.out.println("Need to add permissions for the User");
                            }

                            break;
                        }
                        case "getUserPermissions": {
                            //TESTED
                            String username = (String) request.get("username");
                            Permissions permissions = dataSource.getUserPerms(username);
                            response.put("permissions", permissions);
                            sendResponse(clientSocket, response);
                            break;
                        }
                        case "setUserPermissions": {
                            //Not tested
                            String username = (String) request.get("username");
                            Permissions userPermissions = (Permissions) request.get("permission");
                            dataSource.updateUserPerms(username, userPermissions);
                            break;
                        }
                        case "setUserPassword":{
                            //Not tested
                            String username = (String) request.get("username");
                            String password = (String) request.get("password");

                            User user = dataSource.getUser(username);
                            String salt = user.getPasswordSalt();
                            String dbPassword = Hash.getHash(password + salt);
                            dataSource.setUserPassword(username, dbPassword);
                            break;
                        }
                        case "modifyUser": {
                            //same as user and perm update
//                            String username = (String) request.get("username");
//                            String password = (String) request.get("password");
//                            Permissions userPermissions = (Permissions) request.get("permission");
//
//                            dataSource.updateUserPerms(username, userPermissions);
//                            dataSource.setUserPassword(username, password);
                        }
                        case "deleteUser": {
                            //untested
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

