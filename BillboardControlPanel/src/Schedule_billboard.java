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
import java.util.HashMap;

import static Server.Client.sendRequest;

public class Schedule_billboard extends JFrame {
    private JPanel mainPanel;
    private JCheckBox everyDayCheckBox;
    private JTextField billboardName;
    private JComboBox<Integer> mins_selected;
    private JComboBox<Integer> hours_selected;
    private JComboBox<Integer> duration;
    private String repeat;
    private JCheckBox everyHourcheckbox;

    private JButton Add;
    private JLabel Day;
    private JComboBox Select;

    public Schedule_billboard(String title, String token ,String user) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        String days[]={"Monday","Tuesday","Wednesday","Thursday","Friday"};
        for(int i = 0 ; i< days.length;i++){
            Select.addItem(days[i]);
        }

        for(int i = 0 ; i<=23;i++){
            hours_selected.addItem(i);
        }

        for(int i = 0 ; i<=59;i++){
            mins_selected.addItem(i);
            duration.addItem(i);
        }

        everyDayCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    everyHourcheckbox.setEnabled(false);
                    repeat = "Every Day";
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
                    repeat = "Every hour";
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
                String bbname= billboardName.getText();
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
                System.out.println(schedule.getRepeat());

//                try {
//                    ScheduleBillboard( token, schedule);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                } catch (ClassNotFoundException ex) {
//                    ex.printStackTrace();
//                } catch (NoSuchAlgorithmException ex) {
//                    ex.printStackTrace();
//                }

            }
        });
    }

    public static void main(String[] args) {
        String user = "Sid";
        String token = "2615386751237";
        JFrame frame = new Schedule_billboard("Add billboard to Schedule",user,token);
        frame.setLocation(400,200);
        frame.setSize(350,350);
        frame.setVisible(true);
    }
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
