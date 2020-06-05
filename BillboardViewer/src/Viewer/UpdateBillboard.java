package Viewer;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.TimerTask;
import Viewer.BillboardGenerator;

class UpdateBillboard extends TimerTask
{
    JLabel BillboardMessage;
    JLabel BillboardInformation;
    JLabel[] labels;
    JFrame BillboardFrame;
    JPanel BillboardElements;
    MouseListener mouseExit;
    KeyListener escapeExit;
    UpdateBillboard(/*JLabel BillboardMessage,JLabel BillboardInformation,*/JLabel[] labels, JFrame BillboardFrame, JPanel BillboardElements, MouseListener mouseExit, KeyListener escapeExit){
//        this.BillboardMessage = BillboardMessage;
//        this.BillboardInformation = BillboardInformation;
        this.labels = labels;
        this.BillboardFrame = BillboardFrame;
        this.BillboardElements = BillboardElements;
        this.mouseExit = mouseExit;
        this.escapeExit = escapeExit;
    }
    public static int i = 0;

    public void run()
    {
        try {
            BillboardGenerator.createBillboardViewer(/*this.BillboardMessage, this.BillboardInformation, */ this.labels, this.BillboardFrame, this.BillboardElements, this.mouseExit, this.escapeExit);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}