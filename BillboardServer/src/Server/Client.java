
package Server;

import Database.Permissions;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.net.Socket;

public class Client {

    public static Socket getClientSocket() throws IOException {

        Properties props = new Properties();
        FileInputStream in = new FileInputStream("./network.props");
        props.load(in);
        in.close();

        String host = props.getProperty("network.host");
        String port = props.getProperty("network.port");

        Socket socket = new Socket(host, Integer.parseInt(port));
        return socket;
    }

    //sends request to server
    public static void sendRequest(Socket socket, HashMap<String, Object> request) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(request);
        objectOutputStream.flush();
    }

    public static HashMap<String, Object> getResponse(Socket socket) throws IOException, ClassNotFoundException {

        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        HashMap<String, Object> response = (HashMap<String, Object>) objectInputStream.readObject();
        return response;
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = getClientSocket();

//        // test Login request
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("type", "logIn");
//        request.put("username","josho");
//        request.put("password", "asd123");
//
//        sendRequest(socket, request);
//
//        HashMap<String, Object> res = getResponse(socket);
//
//        System.out.println(res.get("token"));

//        //test Create User
//        HashMap<String, Object> request = new HashMap<>();
//        Permissions permission = new Permissions(
//                "itsmeMario6",
//                "true",
//                "false",
//                "true",
//                "true");
//
//        String token = "kjryiauznhrjgrxypymj";
//        request.clear();
//        request.put("type", "createUser");
//        request.put("token", token);
//        request.put("username","itsmeMario6");
//        request.put("password", "asd123");
//        request.put("permissionList", permission);
//        sendRequest(socket, request);

        //getUserPermission Test

//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//        request.put("type", "getUserPermissions");
//        request.put("username", "itsmeMario6");
//
//        sendRequest(socket, request);
//
//        HashMap<String, Object> response = getResponse(socket);
//        Permissions permissions = (Permissions) response.get("permissions");
//        System.out.println(permissions.getCreateBillboard());
//
//        socket.close();

        //LogOut Test

        //send something first
    }
}