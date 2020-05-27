package Server;

import Database.DBConnection;
import Token.Token;

import java.sql.Connection;
import java.util.HashMap;

public class ServerMain {
    //mock data
    static String username = "jerry";

    public static void main(String[] Args){

        Connection connection = DBConnection.getInstance();

        HashMap<Token, String> tokenStore = new HashMap<Token, String>();
        Token token = new Token();
        tokenStore.put(token, username);

//        System.out.println(tokenStore.get(token));
//        System.out.println(token.getToken());
//        System.out.println(token.getExpiry());
    }
}
