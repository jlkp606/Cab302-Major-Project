package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import Database.Permissions;
import Server.Client;

import static Server.Client.getResponse;
import static Server.Client.sendRequest;

public class TestBillboardServer {
    public void TestLogin() throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("type", "logIn");
        request.put("username","josho");
        request.put("password", "asd123");

        sendRequest(socket, request);
        HashMap<String, Object> res = Client.getResponse(socket);
        socket.close();
    }

    public void TestCreateUser() throws IOException {
        Socket socket = Client.getClientSocket();

        HashMap<String, Object> request = new HashMap<>();
        Permissions permission = new Permissions(
                "itsmeMario6",
                "true",
                "false",
                "true",
                "true");

        String token = "kjryiauznhrjgrxypymj";
        request.clear();
        request.put("type", "createUser");
        request.put("token", token);
        request.put("username","itsmeMario6");
        request.put("password", "asd123");
        request.put("permissionList", permission);
        sendRequest(socket, request);
        socket.close();

    }

    public static void main(String[] Args) throws IOException, ClassNotFoundException {

    }

}
