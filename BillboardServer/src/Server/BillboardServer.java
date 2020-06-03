package Server;

import Database.*;
import Exceptions.IncorrectPasswordException;
import Exceptions.IncorrectUserException;
import Exceptions.MissingPermissionException;
import Token.Token;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    public static void sendResponse(Socket clientSocket, HashMap<String, Object> response) throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
    }
    /**
     * @param dbUser User from DB
     * @param password password to be authenticated against the DBUser
     *
     * @return Token object
     * */
    public static Token logIn( User dbUser, String password) throws NoSuchAlgorithmException, IncorrectUserException, IncorrectPasswordException {
        String username = dbUser.getUsername();
        if(username != null){
            String dbPassword = dbUser.getPassword();

            String salt = dbUser.getPasswordSalt();
            //String HashSaltedPassword = password;
            String HashSaltedPassword = Hash.getHash(password + salt);

            //query check hashSaltedPass is same as password in Db for username
            if (HashSaltedPassword.equals(dbPassword)){
                Token newToken = new Token();
                return newToken;
            }
            else{
                throw new IncorrectPasswordException("Incorrect Password");
            }
        } else {
            throw new IncorrectUserException("Incorrect Username");
        }
    }

    public static String getCurrentBillboard(ArrayList<Schedule> scheduleList){

        for (Schedule s : scheduleList){
            LocalDateTime ldt = null;
            LocalDate date = ldt.parse(s.getStartTime()).toLocalDate();
            LocalTime startTime = ldt.parse(s.getStartTime()).toLocalTime();
            LocalTime endTime = ldt.parse(s.getEndTime()).toLocalTime();
            String repeat = s.getRepeat();
            Boolean isOn = false;

            if (repeat.equals("never")){

            } else if(repeat.equals("week")){
                if(s.getDay().equals(LocalDate.now().getDayOfWeek())){
                    isOn = (LocalTime.now().isAfter(startTime) &&
                            LocalTime.now().isBefore(endTime));
                };
            } else if(repeat.equals("day")){
                isOn = (LocalTime.now().isAfter(startTime) &&
                        LocalTime.now().isBefore(endTime));
            }
            if (isOn){
                return s.getBillboardName();
            }

        }
        return null;
    }

    public static User setUpUser(Permissions permissions, String username, String password) throws MissingPermissionException, NoSuchAlgorithmException {
        if ((permissions.getCreateBillboard() != null && permissions.getEditAllBillboards() != null) &&
                (permissions.getEditSchedule() != null && permissions.getEditUsers() != null)){
            User user = new User();
            user.setUsername(username);
            user.setPassword(Hash.getHash(password + user.getPasswordSalt()));
            return user;
        } else {
            throw new MissingPermissionException("Missing Permissions for User");
        }
    }

    public static void routeRequest(HashMap<String, Object> request,
                                    HashMap<String, Object> response,
                                    HashMap<String, ArrayList<Object>> tokenStore,
                                    JDBCDatabaseSource dataSource) throws SQLException, NoSuchAlgorithmException {

        String requestType = (String) request.get("type");

        if (requestType.equals("logIn")){

            String username = (String) request.get("username");
            //hashed
            String password = (String) request.get("password");
            User dbUser = dataSource.getUser(username);

            try{
                Token newToken = logIn(dbUser, password);
                ArrayList<Object> tokenInfo =  new ArrayList<Object>();
                tokenInfo.add(username);
                tokenInfo.add(newToken.getExpiry());
                tokenStore.put(newToken.getToken(), tokenInfo);
                response.put("token", newToken.getToken());
                response.put("message", "Successful");
            }
            catch(Exception e){
                response.put("message", e);
            }

        }
        else{
            String token = (String) request.get("token");
            if (tokenStore.containsKey(token) || token.equals("kjryiauznhrjgrxypymj")){

                switch (requestType){
                    case "logOut":{
                        //not tested
                        tokenStore.remove(token);
                        break;
                    }
                    case "listBillboard":{
                        //tested
                        ArrayList<Billboard> billboardList = dataSource.getAllBillboards();
                        response.put("billboardList", billboardList);
                        break;
                    }
                    case "getCurrentBillboard": {
                        ArrayList<Schedule> scheduleList = dataSource.getAllSchedules();
                        String currentBillboardName = getCurrentBillboard(scheduleList);
                        Billboard billboard = dataSource.getBillboard(currentBillboardName);
                        response.put("billboard", billboard);
                        break;

                    }
                    case "getBillboardInfo":{
                        //Tested
                        String billboardName = (String) request.get("billboardName");
                        Billboard billboard = dataSource.getBillboard(billboardName);
                        response.put("billboard", billboard);
                        break;
                    }
                    case "createBillboard":{
                        //tested
                        System.out.println("in server");
                        Billboard billboard = (Billboard) request.get("billboard");
                        dataSource.addBillboard(billboard);
                        break;
                    }
                    case "deleteBillboard":{
                        //tested
                        String billboardName = (String) request.get("billboardName");
                        dataSource.deleteBillboard(billboardName);
                        break;
                    }
                    case "viewSchedule":{
                        //tested
                        ArrayList<Schedule> scheduleList = dataSource.getAllSchedules();
                        response.put("scheduleList", scheduleList);
                        break;
                    }
                    case "scheduleBillboard":{
                        //Tested
                        Schedule schedule = (Schedule) request.get("schedule");
                        dataSource.addSchedule(schedule);
                        break;
                    }
                    case "removeBillboardFromSchedule":{
                        //Tested
                        Schedule schedule = (Schedule) request.get("schedule");
                        dataSource.deleteSchedule(schedule);
                        break;
                    }
                    case "listUsers":{
                        //Tested
                        ArrayList<String> userList = dataSource.getUsernames();
                        response.put("userList", userList);
                        break;
                    }
                    case "createUser": {
                        //TESTED//
                        String username = (String) request.get("username");
                        String password = (String) request.get("password");

                        Permissions permList = (Permissions) request.get("permission");
                        try{
                            User user = setUpUser(permList, username, password);
                            dataSource.addUser(user);
                            dataSource.addUserPerms(username, permList);
                            response.put("message", "Successful");
                        } catch (Exception e){
                            response.put("message", e);
                        }

                        break;
                    }
                    case "getUserPermissions": {
                        //TESTED
                        String username = (String) request.get("username");
                        Permissions permissions = dataSource.getUserPerms(username);
                        response.put("permissions", permissions);
                        break;
                    }
                    case "setUserPermissions": {
                        //tested
                        String username = (String) request.get("username");
                        Permissions userPermissions = (Permissions) request.get("permission");
                        dataSource.updateUserPerms(username, userPermissions);
                        break;
                    }
                    case "setUserPassword":{
                        //tested
                        String username = (String) request.get("username");
                        String password = (String) request.get("password");

                        User user = dataSource.getUser(username);
                        String salt = user.getPasswordSalt();
                        String dbPassword = Hash.getHash(password + salt);
                        dataSource.setUserPassword(username, dbPassword);
                        break;
                    }
                    case "deleteUser": {
                        //tested
                        String username = (String) request.get("username");
                        dataSource.deleteUser(username);
                        break;
                    }
                }
            } else {
                System.out.println("Invalid Token");
                response.put("message", "Invalid Token");
            }
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, SQLException {

        //Stores users that have logged in
        HashMap<String, ArrayList<Object>> tokenStore = new HashMap<String, ArrayList<Object>>();

        //get Socket
        ServerSocket serverSocket = getServerSocket();

        //connect to database
        JDBCDatabaseSource dataSource = new JDBCDatabaseSource();

        for ( ; ; ) {
            //network layer
            Socket clientSocket = serverSocket.accept();

            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            HashMap<String, Object> request = (HashMap<String, Object>) objectInputStream.readObject();

            HashMap<String, Object> response = new HashMap<>();

            //functional

            String requestType = (String) request.get("type");

            routeRequest(request, response,tokenStore,dataSource);

            sendResponse(clientSocket, response);
            clientSocket.close();
        }
    }
}

