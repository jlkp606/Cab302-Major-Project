import Database.Schedule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public Schedule_billboard(String title,String user,String token) {
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
                }
            }
        });


        Add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bbname= billboardName.getText();
                String selectedDuration = String.valueOf(duration.getSelectedItem());
                String selectedHours = String.valueOf(hours_selected.getSelectedItem());
                String selectedMins = String.valueOf(mins_selected.getSelectedItem());
                String selectedDay = String.valueOf(Select.getSelectedItem());

                LocalDateTime startTime = LocalDate.now().atTime(Integer.parseInt(selectedHours), Integer.parseInt(selectedMins));
                LocalDateTime endTime = startTime.plusMinutes(Long.parseLong(selectedDuration));

                String end = String.valueOf(endTime);
                String end_bb_time = end.substring(11);

                String start = String.valueOf(startTime);
                String start_bb_time = start.substring(11);
                System.out.println(LocalDateTime.parse(start));
                System.out.println(bbname);
                System.out.println(startTime);
                System.out.println(endTime);
                System.out.println(repeat);
                System.out.println(selectedDay);


                Database.Schedule schedule = new Schedule(user,bbname,start_bb_time,end_bb_time,repeat);

//                Send the schedule to the server
            }
        });
    }

    public static void main(String[] args) {
        String user = "Sid";
        String token = "092408240280";
        JFrame frame = new Schedule_billboard("Add billboard to Schedule",user,token);
        frame.setLocation(400,200);
        frame.setSize(350,350);
        frame.setVisible(true);
    }
}
