package psu.lp.scoreboard.server;

import psu.lp.scoreboard.util.GlobalConstants;

import java.io.IOException;
import java.net.*;

public class NewClientListener implements Runnable {

    private static NewClientListener instance;
    private static Thread listener;

    public synchronized static NewClientListener getInstance() throws SocketException, InterruptedException {
        if (instance == null) {
            Thread.sleep(3000);
            instance = new NewClientListener();
            listener = new Thread(instance);
            listener.start();
        }
        return instance;
    }


    private DatagramSocket socket;
    private String localServerIP;
    private ScoreboardServerController controller;

    private NewClientListener() throws SocketException {
        try {
            socket = new DatagramSocket(6000);
            socket.connect(InetAddress.getByName("8.8.8.8"), 6000); // Немного костыльный способ получения адреса в локальной сети
            localServerIP = socket.getLocalAddress().getHostAddress();
            socket.disconnect();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    public void setController(ScoreboardServerController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        DatagramPacket packet;
        byte[] buf;
        String received;

        try {
            while (true) {
                buf = new byte[1024];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                received = new String(packet.getData(), 0, packet.getLength());

                if (received.equals(GlobalConstants.GET_SERVER_INFO)) {
                    controller.sendAll();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
