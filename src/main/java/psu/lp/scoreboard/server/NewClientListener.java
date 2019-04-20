package psu.lp.scoreboard.server;

import psu.lp.scoreboard.util.GlobalConstants;

import java.io.IOException;
import java.net.*;

public class NewClientListener implements Runnable {


    private DatagramSocket socket;
    private String localServerIP;
    private ScoreboardServerController controller;

    public NewClientListener(ScoreboardServerController controller) throws SocketException {
        try {
            socket = new DatagramSocket(6000);
            socket.connect(InetAddress.getByName("8.8.8.8"), 6000); // Немного костыльный способ получения адреса в локальной сети
            localServerIP = socket.getLocalAddress().getHostAddress();
            socket.disconnect();
//            this.setDaemon(true);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
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
                    buf = localServerIP.getBytes();
                    packet = new DatagramPacket(
                            buf,
                            buf.length,
                            packet.getAddress(),
                            packet.getPort());

                    socket.send(packet);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
