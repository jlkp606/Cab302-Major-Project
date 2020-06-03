import Database.Schedule;
import Server.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static Server.Client.getResponse;
import static Server.Client.sendRequest;

public class viewer_billboard_time extends JFrame {
    private JPanel mainPanel;

    private JTextArea Monday_9;
    private JTextArea Monday_10;
    private JTextArea Monday_11;
    private JTextArea Monday_12;
    private JTextArea Monday_1;
    private JTextArea Monday_2;
    private JTextArea Monday_4;
    private JTextArea Monday_3;
    private JTextArea Monday_5;

    private JTextArea Tuesday_9;
    private JTextArea Tuesday_10;
    private JTextArea Tuesday_11;
    private JTextArea Tuesday_12;
    private JTextArea Tuesday_1;
    private JTextArea Tuesday_2;
    private JTextArea Tuesday_3;
    private JTextArea Tuesday_4;
    private JTextArea Tuesday_5;

    private JTextArea Wednesday_9;
    private JTextArea Wednesday_10;
    private JTextArea Wednesday_11;
    private JTextArea Wednesday_12;
    private JTextArea Wednesday_1;
    private JTextArea Wednesday_2;
    private JTextArea Wednesday_3;
    private JTextArea Wednesday_4;
    private JTextArea Wednesday_5;

    private JTextArea Thursday_9;
    private JTextArea Thursday_10;
    private JTextArea Thursday_11;
    private JTextArea Thursday_12;
    private JTextArea Thursday_1;
    private JTextArea Thursday_2;
    private JTextArea Thursday_3;
    private JTextArea Thursday_4;
    private JTextArea Thursday_5;

    private JTextArea Friday_9;
    private JTextArea Friday_10;
    private JTextArea Friday_11;
    private JTextArea Friday_12;
    private JTextArea Friday_1;
    private JTextArea Friday_2;
    private JTextArea Friday_3;
    private JTextArea Friday_4;
    private JTextArea Friday_5;


    private JButton add;
    private JButton remove;

    public viewer_billboard_time(String title, String token, String user) throws IOException, ClassNotFoundException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        JTextArea[][] textboxArray = new JTextArea[5][9];

        textboxArray[0][0] = Monday_9;
        textboxArray[0][1] = Monday_10;
        textboxArray[0][2] = Monday_11;
        textboxArray[0][3] = Monday_12;
        textboxArray[0][4] = Monday_1;
        textboxArray[0][5] = Monday_2;
        textboxArray[0][6] = Monday_3;
        textboxArray[0][7] = Monday_4;
        textboxArray[0][8] = Monday_5;

        textboxArray[1][0] = Tuesday_9;
        textboxArray[1][1] = Tuesday_10;
        textboxArray[1][2] = Tuesday_11;
        textboxArray[1][3] = Tuesday_12;
        textboxArray[1][4] = Tuesday_1;
        textboxArray[1][5] = Tuesday_2;
        textboxArray[1][6] = Tuesday_3;
        textboxArray[1][7] = Tuesday_4;
        textboxArray[1][8] = Tuesday_5;

        textboxArray[2][0] = Wednesday_9;
        textboxArray[2][1] = Wednesday_10;
        textboxArray[2][2] = Wednesday_11;
        textboxArray[2][3] = Wednesday_12;
        textboxArray[2][4] = Wednesday_1;
        textboxArray[2][5] = Wednesday_2;
        textboxArray[2][6] = Wednesday_3;
        textboxArray[2][7] = Wednesday_4;
        textboxArray[2][8] = Wednesday_5;

        textboxArray[3][0] = Thursday_9;
        textboxArray[3][1] = Thursday_10;
        textboxArray[3][2] = Thursday_11;
        textboxArray[3][3] = Thursday_12;
        textboxArray[3][4] = Thursday_1;
        textboxArray[3][5] = Thursday_2;
        textboxArray[3][6] = Thursday_3;
        textboxArray[3][7] = Thursday_4;
        textboxArray[3][8] = Thursday_5;

        textboxArray[4][0] = Friday_9;
        textboxArray[4][1] = Friday_10;
        textboxArray[4][2] = Friday_11;
        textboxArray[4][3] = Friday_12;
        textboxArray[4][4] = Friday_1;
        textboxArray[4][5] = Friday_2;
        textboxArray[4][6] = Friday_3;
        textboxArray[4][7] = Friday_4;
        textboxArray[4][8] = Friday_5;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                textboxArray[i][j].setEnabled(false);
            }
        }
//
//        Database.Schedule schedule1 = new Schedule("Sid", "Billboard1", "2020-06-02T09:40", "2020-06-02T10:25", "FRIDAY", "Every Day");
//        Database.Schedule schedule2 = new Schedule("Liam", "Billboard2", "2020-06-02T12:16", "2020-06-02T12:45", "TUESDAY", "repeat");
//        Database.Schedule schedule3 = new Schedule("Josh", "Billboard3", "2020-06-02T14:00", "2020-06-02T14:05", "MONDAY", "repeat");
//        Database.Schedule schedule4 = new Schedule("Jet", "Billboard4", "2020-06-02T15:25", "2020-06-02T16:50", "THURSDAY", "repeat");
//        Database.Schedule schedule5 = new Schedule("Pratham", "Billboard5", "2020-06-02T13:25", "2020-06-02T13:49", "THURSDAY", "Every Day");
//        Database.Schedule schedule6 = new Schedule("Ram", "Billboard6", "2020-06-02T09:20", "2020-06-02T09:25", "FRIDAY", "repeat");

//      ArrayList<Database.Schedule> scheduleList = new ArrayList<Database.Schedule>();
        ArrayList<Database.Schedule> scheduleList = ViewSchedule( token);
//        scheduleList.add(schedule1);
//        scheduleList.add(schedule2);
//        scheduleList.add(schedule3);
//        scheduleList.add(schedule4);
//        scheduleList.add(schedule5);
//        scheduleList.add(schedule6);

        String[] weekDays = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
        for (int p = 0; p < 5; p++) {
            for (int i = 0; i < scheduleList.size(); i++) {
                String day = scheduleList.get(i).getDay();
                String compareTime = scheduleList.get(i).getStartTime().substring(11, 13);
                String displayStartTime = scheduleList.get(i).getStartTime().substring(11, 16);
                String displayEndTime = scheduleList.get(i).getEndTime().substring(11, 16);
                String billboardScheduled = scheduleList.get(i).getBillboardName();
                String billboardScheduledCreator = scheduleList.get(i).getUsername();
                String repeat = scheduleList.get(i).getRepeat();
                for (int k = 9; k < 18; k++) {
                    String hour = String.format("%02d", k);
                    if (day.equals(weekDays[p])) {
                        if (compareTime.equals(hour)) {

                            if(repeat.equals("Every Day")){
                                for(int m = 0;m<5;m++){
                                textboxArray[m][k - 9].append(billboardScheduledCreator + "\n"+ billboardScheduled + "\n" + displayStartTime + "-" + displayEndTime + "\n");
                                }

                            }

                            else{

                                textboxArray[p][k - 9].append(billboardScheduledCreator + "\n"+ billboardScheduled + "\n" + displayStartTime + "-" + displayEndTime + "\n");

                            }
                        }
                    }
                }

            }
        }

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = new Schedule_billboard("Add billboard to Schedule", user, token);
                    frame.setLocation(400, 200);
                    frame.setSize(350, 350);
                    frame.setVisible(true);
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = new removeScheduledBillboard("Remove Billboard", token, user);
                    frame.setLocation(300, 300);
                    frame.setSize(300, 250);
                    frame.setVisible(true);
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static ArrayList<Schedule> ViewSchedule(String token) throws IOException, ClassNotFoundException {
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "viewSchedule");
        sendRequest(socket, request);
        HashMap<String, Object> response = getResponse(socket);
        ArrayList<Database.Schedule> scheduleList = (ArrayList<Database.Schedule>) response.get("scheduleList");
        socket.close();
        return scheduleList;
    }

    //
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String user = "Sid";
//        String token = "092408240280";
//        JFrame frame = new viewer_billboard_time("View Schedule for Billboards", user, token);
//        frame.setLocation(400, 200);
//        frame.setSize(850, 550);
//        frame.setVisible(true);
//    }

}
