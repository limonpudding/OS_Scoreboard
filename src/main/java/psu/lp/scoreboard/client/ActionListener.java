package psu.lp.scoreboard.client;

import psu.lp.scoreboard.util.GlobalConstants;
import psu.lp.scoreboard.util.LanUtils;
import psu.lp.scoreboard.util.ScoreboardAction;

import java.io.*;
import java.net.*;

public class ActionListener implements Runnable {

    private static ActionListener instance;

    private ScoreboardClientController controller;

    synchronized static ActionListener getInstance() {
        if (instance == null) {
            instance = new ActionListener();
        }
        return instance;
    }

    private DatagramSocket socket;

    private ActionListener() {

    }

    @Override
    public void run() {
        byte[] recvBuf;
        DatagramPacket packet;
        ByteArrayInputStream byteStream;
        ObjectInputStream is;

        try {
            socket = new DatagramSocket(GlobalConstants.APPLICATION_PORT);
            while (true) {

                recvBuf = new byte[6400];
                packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                byteStream = new ByteArrayInputStream(recvBuf);
                is = new ObjectInputStream(new BufferedInputStream(byteStream));
                ScoreboardAction action = (ScoreboardAction) is.readObject();
                System.out.println(action.getActionType().getInfo());
                switch (action.getActionType()) {
                    case SET_TEAM_NAMES:
                        controller.setTeamNames(action);
                        break;
                    case INCREASE_SCORE:
                        controller.setScore(action);
                        break;
                    case TIMER_START:
                        controller.setTime(action);
                        break;
                    case TIMER_PAUSE:
                        controller.stopTime(action);
                        break;
                    case NEW_HALF:
                        controller.setHalf(action);
                        break;
                    case RESET_HALF:
                        controller.resetHalfAndTime();
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void setController(ScoreboardClientController controller) {
        this.controller = controller;
    }

    void getAllInfo() throws IOException {
        byte[] buf = GlobalConstants.GET_SERVER_INFO.getBytes();
        DatagramSocket socketUDP = new DatagramSocket();
        InetAddress address = LanUtils.getBroadcast();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 6000);
        socketUDP.send(packet);
    }
}
