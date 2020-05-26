package Server;

import Token.Token;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class BillboardServer {
    // object stream https://coderanch.com/t/209228/java/Passing-object-Client-Server
    //
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HashMap<Token, String> tokenStore = new HashMap<Token, String>();


        int portNumber = 4444;

        ServerSocket serverSocket = new ServerSocket(portNumber);
        for (; ; ) {
            Socket clientSocket = serverSocket.accept();

            InputStream in = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            HashMap<String, Object> request = (HashMap<String, Object>) objectInputStream.readObject();

            switch ((String) request.get("name")){
                case "log in":
                    Token token = new Token();
                    tokenStore.put(token, (String) request.get("username"));

                    OutputStream out = clientSocket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                    objectOutputStream.writeObject(token);
                    objectOutputStream.flush();
                    break;

                case "log out":

                    break;

            }

            clientSocket.close();
        }
    }
}

