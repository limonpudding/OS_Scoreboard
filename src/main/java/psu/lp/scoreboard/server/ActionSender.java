package psu.lp.scoreboard.server;

import psu.lp.scoreboard.util.GlobalConstants;
import psu.lp.scoreboard.util.ScoreboardAction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Enumeration;

public class ActionSender {

    private static ActionSender instance;

    private static InetAddress broadcast;

    private static DatagramSocket socket;

    public synchronized static ActionSender getInstance() {
        if (instance == null) {
            instance = new ActionSender();
        }
        return instance;
    }
    private ActionSender() {
        try {
            initIP();
            socket = new DatagramSocket();
            socket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Ошибка создания широковещательного сокета!");
        }
    }


    private void initIP() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback())
                    continue;    // Do not want to use the loopback interface.
                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcastAddress = interfaceAddress.getBroadcast();
                    if (broadcastAddress == null)
                        continue;
                    broadcast = broadcastAddress;
                    System.out.println("Broadcast is " + broadcast.toString());
                    return;
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
            System.out.println("Ошибка получения широковещательного адреса!");
        }
    }

    public void sendScoreboardAction(ScoreboardAction action) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
            final ObjectOutputStream oos;
            oos = new ObjectOutputStream(baos);
            oos.writeObject(action);
            final byte[] data = baos.toByteArray();
            DatagramPacket packet = new DatagramPacket(data, data.length, broadcast, GlobalConstants.APPLICATION_PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка отправки состояния табло!");
        }
    }
}
