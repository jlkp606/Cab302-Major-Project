import Database.Schedule;
import Server.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static Server.Client.sendRequest;

public class Schedule_billboard extends JFrame {
    private JPanel mainPanel;
    private JCheckBox everyDayCheckBox;
    private JComboBox<Integer> mins_selected;
    private JComboBox<Integer> hours_selected;
    private JComboBox<Integer> duration;
    private String repeat;
    private JCheckBox everyHourcheckbox;

    private JButton Add;
    private JComboBox<String> Select;
    private JComboBox<String> billboardList;

    public Schedule_billboard(String title, String token ,String user) throws IOException, ClassNotFoundException {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        String days[]= {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};

        ArrayList<Database.Billboard> billboards = List_billboard.receiveBillboardList(token);
//        Database.Billboard billboard1 = new Database.Billboard("itsmeMario8Billboard", "itsmeMario8", "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                "more information.", "#00FFFF");
//
//        Database.Billboard billboard2 = new Database.Billboard("SidBillboard", "Sid", "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                "more information.", "#00FFFF");
//
//        Database.Billboard billboard3 = new Database.Billboard("JoshBillboard", "Josh", "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                "more information.", "#00FFFF");
//
//        Database.Billboard billboard4 = new Database.Billboard("LiamBillboard", "Liam", "#0000FF", "Welcome to the ____ Corporation's Annual", "#FFFF00", "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAICAIAAABLbSncAAAALHRFWHRDcmVhdGlvbiBUaW1lAE1vbiAxNiBNYXIgMjAyMCAxMDowNTo0NyArMTAwMNQXthkAAAAHdElNRQfkAxAABh+N6nQI AAAACXBIWXMAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAADVJREFUeNp1jkEKADAIwxr//+duIIhumJMUNUWSbU2AyPROFeVqaIH/T7JeRBd0DY+8SrLVPbTmFQ1iRvw3AAAAAElFTkSuQm CC", "https://example.com/fundraiser_image.jpg", "Be sure to check out https://example.com/ for\n" +
//                "more information.", "#00FFFF");
//        ArrayList<Database.Billboard> billboards = new ArrayList<Database.Billboard>();
//        billboards.add(billboard1);
//        billboards.add(billboard2);
//        billboards.add(billboard3);
//        billboards.add(billboard4);

        for(int i =0;i<billboards.size();i++){
            billboardList.addItem(billboards.get(i).getbName());
        }

        for(int i = 0 ; i< days.length;i++){
            Select.addItem(days[i]);
        }

        for(int i = 0 ; i<=23;i++){
            hours_selected.addItem(i);
        }

        for(int i = 1 ; i<60;i++){
            mins_selected.addItem(i);
        }
        for(int i = 1 ; i<60;i++){
            duration.addItem(i);
        }

        everyDayCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    everyHourcheckbox.setEnabled(false);
                    repeat = "day";
                }

                else{
                    everyHourcheckbox.setEnabled(true);
                    repeat = "";
                }
            }
        });

        everyHourcheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    everyDayCheckBox.setEnabled(false);
                    repeat = "week";
                }

                else{
                    everyDayCheckBox.setEnabled(true);
                    repeat = "";
                }
            }
        });


        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bbname= String.valueOf(billboardList.getSelectedItem());
                String day = String.valueOf(Select.getSelectedItem());
                String selectedDuration = String.valueOf(duration.getSelectedItem());
                String selectedHours = String.valueOf(hours_selected.getSelectedItem());
                String selectedMins = String.valueOf(mins_selected.getSelectedItem());

                LocalDateTime startTime = LocalDate.now().atTime(Integer.parseInt(selectedHours), Integer.parseInt(selectedMins));
                LocalDateTime endTime = startTime.plusMinutes(Long.parseLong(selectedDuration));

                String end = String.valueOf(endTime);
                String start = String.valueOf(startTime);

                Database.Schedule schedule = new Schedule(user,bbname,start,end,day,repeat);
//                System.out.println(schedule);
//                System.out.println(schedule.getBillboardName());
//                System.out.println(schedule.getEndTime());
//                System.out.println(schedule.getStartTime());
//                System.out.println(schedule.getUsername());
//                System.out.println(schedule.getRepeat());
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                try {
                    ScheduleBillboard(token, schedule);
                } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
                    }
                });
            }
        });
    }

//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        String user = "Sid";
//        String token = "2615386751237";
//        JFrame frame =
//                new Schedule_billboard("Add billboard to Schedule",user,token);
//        frame.setLocation(400,200);
//        frame.setSize(450,400);
//        frame.setVisible(true);
//    }
    public static void ScheduleBillboard(String token,Schedule schedule) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {

        Socket socket = Client.getClientSocket();
        HashMap<String, Object> request = new HashMap<>();
        request.put("token", token);
        request.put("type", "scheduleBillboard");
        request.put("schedule", schedule);
        sendRequest(socket, request);
        socket.close();
    }

}
