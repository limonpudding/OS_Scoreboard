package psu.lp.scoreboard.client;

import psu.lp.scoreboard.util.GlobalConstants;
import psu.lp.scoreboard.util.ScoreboardAction;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;

public class ActionListener implements Runnable {

    private DatagramSocket socket;



    @Override
    public void run() {
        byte[] recvBuf;
        DatagramPacket packet;
        ByteArrayInputStream byteStream; // = new ByteArrayInputStream(recvBuf);
        ObjectInputStream is;// = new ObjectInputStream(new BufferedInputStream(byteStream));

        try {
            socket = new DatagramSocket(GlobalConstants.APPLICATION_PORT);
            while (true) {
                recvBuf = new byte[6400];
                packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                //int byteCount = packet.getLength();
                byteStream = new ByteArrayInputStream(recvBuf);
                is = new ObjectInputStream(new BufferedInputStream(byteStream));
                ScoreboardAction o = (ScoreboardAction) is.readObject();
                System.out.println(o.toString());
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
