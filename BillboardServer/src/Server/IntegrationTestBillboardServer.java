package Server;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static Server.Client.sendRequest;
import static Server.Hash.getHash;

public class IntegrationTestBillboardServer {
    @Test
    public void TestLogin() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("type", "logIn");
        request.put("username","admin");
        String hashedPassword = getHash("admin");
        request.put("password", hashedPassword);
        sendRequest(socket, request);
        HashMap<String, Object> res = Client.getResponse(socket);
        String token = (String) res.get("token");
        socket.close();

        assert(token != null);
        assert(token.length() == 20);
    }
}
