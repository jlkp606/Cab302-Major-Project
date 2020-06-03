package Server;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import Database.Billboard;
import Database.Permissions;
import Database.Schedule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static Server.Client.getResponse;
import static Server.Client.sendRequest;
import static Server.Hash.getHash;

public class IntegrationTestBillboardServer {

//    private Socket socket;
//    private HashMap<String, Object> request;

//    @BeforeAll
//    public void SetupServer() throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, IOException {
//        String[] args = new String[]{""};
//        BillboardServer.main(args);
//    }

//    @BeforeEach
//    public void SetupTest() throws IOException {
//        socket = Client.getClientSocket();
//        request = new HashMap<>();
//    }
//    @AfterEach
//    public void CloseTest() throws IOException {
//        socket.close();
//    }

    public static void TestLogin() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("type", "logIn");
        request.put("username","admin");
        String hashedPassword = getHash("josh");
        request.put("password", hashedPassword);
        sendRequest(socket, request);
        HashMap<String, Object> res = Client.getResponse(socket);
        String token = (String) res.get("token");
        System.out.println(token);
        socket.close();
    }
//
//    public void TestCreateUser() throws IOException, NoSuchAlgorithmException {
//        Permissions permission = new Permissions(
//                "itsmeMario12",
//                "true",
//                "true",
//                "true",
//                "true");
//
//
//        String token = "kjryiauznhrjgrxypymj";
//
//        String hashedPassword = getHash("asd123");
//        request.put("type", "createUser");
//        request.put("token", token);
//        request.put("username","itsmeMario12");
//        request.put("password", hashedPassword);
//        request.put("permission", permission);
//        sendRequest(socket, request);
//
//    }
//
//    public static void TestGetUserPermission() throws IOException, ClassNotFoundException {
//        Socket socket = Client.getClientSocket();
//
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
//    }
//
//    public static void TestCreateBillboard() throws IOException, ClassNotFoundException {
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//
//        request.put("type", "createBillboard");
//        Billboard billboard = new Billboard(
//                "1stBillboard",
//                "itsmeMario8",
//                "#0000FF",
//                "Welcome to the ____ Corporation's Annual",
//                "#FFFF00",
//                "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC",
//                "https://example.com/fundraiser_image.jpg",
//                "Be sure to check out https://example.com/ for\n" +
//                        "more information.",
//                "#00FFFF");
//        request.put("billboard", billboard);
//        sendRequest(socket, request);
//        socket.close();
//    }
//
//    public static void TestListBillboard() throws IOException, ClassNotFoundException {
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//        request.put("type", "listBillboard");
//        sendRequest(socket, request);
//
//        HashMap<String, Object> response = getResponse(socket);
//        ArrayList<Billboard> billboards = (ArrayList<Billboard>) response.get("billboardList");
//        System.out.println(billboards.get(0).getbName());
//
//        socket.close();
//    }
//
//    public static void TestDeleteBillboard() throws IOException, ClassNotFoundException {
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//        request.put("type", "deleteBillboard");
//        request.put("billboardName", "2ndBIllboard");
//
//        sendRequest(socket, request);
//        socket.close();
//    }
//
//    public static void TestSetUserPermission() throws IOException, ClassNotFoundException {
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//        request.put("type", "setUserPermissions");
//        request.put("username", "itsmeMario8");
//        Permissions permission = new Permissions(
//                "itsmeMario8",
//                "false",
//                "false",
//                "false",
//                "false");
//        request.put("permission", permission);
//        sendRequest(socket, request);
//        socket.close();
//    }
//
//    public static void TestSetUserPassword() throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//        request.put("type", "setUserPassword");
//        request.put("username", "itsmeMario7");
//
//        String hashedPassword = getHash("123456789");
//        request.put("password", hashedPassword);
//
//        sendRequest(socket, request);
//
//
//        socket.close();
//    }
//
//    public static void TestScheduleBillboard() throws IOException, ClassNotFoundException, NoSuchAlgorithmException{
//
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//        request.put("type", "scheduleBillboard");
//
//        String username = "itsmeMario8";
//        String billboardName = "3rdBIllboard";
//        String startTime = LocalDateTime.now().toString();
//        String endTime = LocalDateTime.now().plusMinutes(15).toString();
//        String day = "Tuesday";
//        String repeat = "daily";
//        Schedule schedule = new Schedule(username,billboardName,startTime,endTime,day,repeat);
//
//        request.put("schedule", schedule);
//
//        sendRequest(socket, request);
//        socket.close();
//    }
//
    public static void TestGetCurrentBillboard() throws IOException, ClassNotFoundException{

        Socket socket = Client.getClientSocket();
        String token = "kjryiauznhrjgrxypymj";
        HashMap<String, Object> request = new HashMap<>();

        request.put("token", token);
        request.put("type", "getCurrentBillboard");
        sendRequest(socket, request);
        HashMap<String, Object> response = getResponse(socket);
        Billboard billboard = (Billboard) response.get("billboard");

        System.out.println(billboard.getMessageColour());
        socket.close();

    }
//
//    @Test
//    public static void TestGetBillboardInfo() throws IOException, ClassNotFoundException{
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//
//        HashMap<String, Object> request = new HashMap<>();
//
//        request.put("token", token);
//        request.put("type", "getBillboardInfo");
//        request.put("billboardName", "1stBillboard");
//        sendRequest(socket, request);
//
//        HashMap<String, Object> response = getResponse(socket);
//        Billboard billboard = (Billboard) response.get("billboard");
//        System.out.println(billboard.getMessageColour());
//        socket.close();
//    }
//
//    public static void TestViewSchedule() throws IOException, ClassNotFoundException{
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//
//        request.put("token", token);
//        request.put("type", "viewSchedule");
//
//        sendRequest(socket, request);
//
//        HashMap<String, Object> response = getResponse(socket);
//        ArrayList<Schedule> scheduleList = (ArrayList<Schedule>) response.get("scheduleList");
//
//        System.out.println(scheduleList.get(0).getBillboardName());
//        socket.close();
//    }
//
//    public static void TestListUser() throws IOException, ClassNotFoundException{
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//
//        request.put("token", token);
//        request.put("type", "listUsers");
//
//        sendRequest(socket, request);
//
//        HashMap<String, Object> response = getResponse(socket);
//        ArrayList<String> userList = (ArrayList<String>) response.get("userList");
//
//        System.out.println(userList);
//        socket.close();
//    }
//
//    public static void TestDeleteUser() throws IOException, ClassNotFoundException{
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//
//        request.put("token", token);
//        request.put("type", "deleteUser");
//        request.put("username", "itsmeMario9");
//        sendRequest(socket, request);
//        socket.close();
//    }
//
//    public static void TestRemoveBillboardFromSchedule() throws IOException, ClassNotFoundException{
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//
//        request.put("token", token);
//        request.put("type", "removeBillboardFromSchedule");
//
//        Schedule schedule = new Schedule();
//        schedule.setBillboardName("3rdBIllboard");
//        schedule.setStartTime("2020-05-31T20:28:47.289059300");
//
//        request.put("schedule", schedule);
//
//        sendRequest(socket, request);
//        socket.close();
//    }
//
//    public static void TestLogOut() throws IOException, ClassNotFoundException{
//        Socket socket = Client.getClientSocket();
//        String token = "kjryiauznhrjgrxypymj";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("token", token);
//        request.put("type", "logOut");
//        sendRequest(socket, request);
//        socket.close();
//    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        TestGetCurrentBillboard();
    }

}