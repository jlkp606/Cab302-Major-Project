import Server.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static Server.Client.sendRequest;

public class removeScheduledBillboard extends JFrame {
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JButton button1;

    public removeScheduledBillboard(String title,String token,String user) throws IOException, ClassNotFoundException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // GUI window will close after Exist button on the top is pressed
        this.setContentPane(mainPanel);
        this.pack();


//        Database.Schedule schedule1 = new Database.Schedule("Sid", "Billboard1", "2020-06-02T09:40", "2020-06-02T10:25", "FRIDAY", "Every Day");
//        Database.Schedule schedule2 = new Database.Schedule("Liam", "Billboard2", "2020-06-02T12:16", "2020-06-02T12:45", "TUESDAY", "repeat");
//        Database.Schedule schedule3 = new Database.Schedule("Josh", "Billboard3", "2020-06-02T14:00", "2020-06-02T14:05", "MONDAY", "repeat");
//        Database.Schedule schedule4 = new Database.Schedule("Jet", "Billboard4", "2020-06-02T15:25", "2020-06-02T16:50", "THURSDAY", "repeat");
//        Database.Schedule schedule5 = new Database.Schedule("Pratham", "Billboard5", "2020-06-02T13:25", "2020-06-02T13:49", "THURSDAY", "Every Day");
//        Database.Schedule schedule6 = new Database.Schedule("Ram", "Billboard6", "2020-06-02T09:20", "2020-06-02T09:25", "FRIDAY", "repeat");

//      ArrayList<Database.Schedule> scheduleList = new ArrayList<Database.Schedule>();
        ArrayList<Database.Schedule> scheduleList = viewer_billboard_time.ViewSchedule( token);
//        scheduleList.add(schedule1);
//        scheduleList.add(schedule2);
//        scheduleList.add(schedule3);
//        scheduleList.add(schedule4);
//        scheduleList.add(schedule5);
//        scheduleList.add(schedule6);

        for(int i =0;i<scheduleList.size();i++){
            comboBox1.addItem(scheduleList.get(i).getBillboardName());
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user;
                String displayStartTime;
                String displayEndTime;
                String day;
                String repeat;
                String billboardName;

                String bbname= String.valueOf(comboBox1.getSelectedItem());
                for (int i = 0; i < scheduleList.size(); i++) {
                    billboardName = scheduleList.get(i).getBillboardName();
                    if(billboardName.equals(bbname)){
                        user = scheduleList.get(i).getUsername();
                        displayStartTime = scheduleList.get(i).getStartTime();
                        displayEndTime = scheduleList.get(i).getEndTime();
                        day = scheduleList.get(i).getDay();
                        repeat = scheduleList.get(i).getRepeat();
                        Database.Schedule schedule = new Database.Schedule(user, billboardName, displayStartTime, displayEndTime, day, repeat);
//
//                        System.out.println(schedule.getUsername());
//                        System.out.println(schedule.getBillboardName());
//                        System.out.println(schedule.getStartTime());
//                        System.out.println(schedule.getEndTime());
//                        System.out.println(schedule.getDay());
//                        System.out.println(schedule.getRepeat());

                        try {
                            RemoveBillboardFromSchedule(token,schedule);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }
                }


            }
        });
    }

    public static void RemoveBillboardFromSchedule(String token, Database.Schedule schedule) throws IOException, ClassNotFoundException{
        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "removeBillboardFromSchedule");
        request.put("schedule", schedule);
        sendRequest(socket, request);
        socket.close();
    }

//    public static void main(String[] args) {
//        String token = "htdyrd";
//        String user = "htdyrd";
//        JFrame frame = new  removeScheduledBillboard ("String title", token, user);
//        frame.setLocation(500, 300);
//        frame.setSize(300, 250);
//        frame.setVisible(true);
//    }
}
