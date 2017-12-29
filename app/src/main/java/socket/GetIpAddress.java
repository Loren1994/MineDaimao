package socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Copyright © 28/12/2017 by loren
 */

public class GetIpAddress {

    private static String IP;
    private static int PORT;

    public static String getIP() {
        return IP;
    }

    public static int getPort() {
        return PORT;
    }

    public static void getLocalIpAddress(ServerSocket serverSocket) {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    String mIP = inetAddress.getHostAddress().substring(0, 3);
                    if (mIP.equals("192")) {
                        IP = inetAddress.getHostAddress();    //获取本地IP
                        PORT = serverSocket.getLocalPort();    //获取本地的PORT
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
