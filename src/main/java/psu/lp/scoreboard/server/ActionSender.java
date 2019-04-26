package psu.lp.scoreboard.server;

import psu.lp.scoreboard.util.GlobalConstants;
import psu.lp.scoreboard.util.LanUtils;
import psu.lp.scoreboard.util.ScoreboardAction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

class ActionSender {

    private static ActionSender instance;

    private static InetAddress broadcast;

    private static DatagramSocket socket;

    synchronized static ActionSender getInstance() {
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
            broadcast = LanUtils.getBroadcast();
            if (broadcast == null) {
                throw new SocketException("Вы не подключены к локальной сети!");
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
            System.out.println("Ошибка получения широковещательного адреса!");
        }
    }

    void sendScoreboardAction(ScoreboardAction action) {
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
