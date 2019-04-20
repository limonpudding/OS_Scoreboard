package psu.lp.scoreboard.client;

import psu.lp.scoreboard.util.GlobalConstants;
import psu.lp.scoreboard.util.ScoreboardAction;

import java.io.*;
import java.net.*;

public class ActionListener implements Runnable {

    private static ActionListener instance;

    private ScoreboardClientController controller;

    public synchronized static ActionListener getInstance() {
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
        ByteArrayInputStream byteStream; // = new ByteArrayInputStream(recvBuf);
        ObjectInputStream is;// = new ObjectInputStream(new BufferedInputStream(byteStream));

        try {
            socket = new DatagramSocket(GlobalConstants.APPLICATION_PORT);
            while (true) {

                recvBuf = new byte[6400];
                packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                byteStream = new ByteArrayInputStream(recvBuf);
                is = new ObjectInputStream(new BufferedInputStream(byteStream));
                ScoreboardAction action = (ScoreboardAction) is.readObject();
                System.out.println(action.toString());

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
                        controller.stopTime();
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
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setController(ScoreboardClientController controller) {
        this.controller = controller;
    }
}
