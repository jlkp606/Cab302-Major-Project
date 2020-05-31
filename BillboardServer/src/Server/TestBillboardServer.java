package Server;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import Database.Billboard;
import Database.Permissions;
import Server.Client;

import static Server.Client.getResponse;
import static Server.Client.sendRequest;
import static Server.Hash.getHash;

public class TestBillboardServer {
    public static void TestLogin() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("type", "logIn");
        request.put("username","itsmeMario8");

        String hashedPassword = getHash("asd123");
        request.put("password", hashedPassword);

        sendRequest(socket, request);
        HashMap<String, Object> res = Client.getResponse(socket);

        System.out.println(res.get("token"));

        socket.close();
    }

    public static void TestCreateUser() throws IOException, NoSuchAlgorithmException {
        Socket socket = Client.getClientSocket();

        HashMap<String, Object> request = new HashMap<>();
        Permissions permission = new Permissions(
                "itsmeMario8",
                "true",
                "false",
                "true",
                "true");


        String token = "kjryiauznhrjgrxypymj";

        String hashedPassword = getHash("asd123");
        request.put("type", "createUser");
        request.put("token", token);
        request.put("username","itsmeMario8");
        request.put("password", hashedPassword);
        request.put("permission", permission);
        sendRequest(socket, request);
        socket.close();

    }
    public static void TestGetUserPermission() throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();

        String token = "kjryiauznhrjgrxypymj";
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "getUserPermissions");
        request.put("username", "itsmeMario6");

        sendRequest(socket, request);

        HashMap<String, Object> response = getResponse(socket);
        Permissions permissions = (Permissions) response.get("permissions");
        System.out.println(permissions.getCreateBillboard());

        socket.close();
    }

    public static void TestCreateBillboard() throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        String token = "kjryiauznhrjgrxypymj";
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);

        request.put("type", "createBillboard");
        Billboard billboard = new Billboard(
                "3rdBIllboard", "123", "ssd",
                "qwe", "Black", "asd",
                "a7d","a1d","a4d");
        request.put("billboard", billboard);
        sendRequest(socket, request);
        socket.close();
    }


    public static void TestListBillboard() throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        String token = "kjryiauznhrjgrxypymj";
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "listBillboard");
        sendRequest(socket, request);

        HashMap<String, Object> response = getResponse(socket);
        ArrayList<Billboard> billboards = (ArrayList<Billboard>) response.get("billboardList");
        System.out.println(billboards.get(0).getbName());

        socket.close();
    }

    public static void TestDeleteBillboard() throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        String token = "kjryiauznhrjgrxypymj";
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "deleteBillboard");
        request.put("billboardName", "2ndBIllboard");

        sendRequest(socket, request);
        socket.close();
    }

    public static void TestSetUserPermission() throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        String token = "kjryiauznhrjgrxypymj";
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "setUserPermissions");
        Permissions permission = new Permissions(
                "itsmeMario8",
                "false",
                "false",
                "false",
                "false");
        request.put("permission", permission);
        sendRequest(socket, request);
        socket.close();
    }
    public static void main(String[] Args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        TestSetUserPermission();
    }

}
