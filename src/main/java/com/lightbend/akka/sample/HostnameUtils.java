package com.lightbend.akka.sample;


import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * @author hung.nt
 */
public class HostnameUtils {
    /**
     * execute command and return result
     *
     * @param cmd: command will be excecute
     * @return result of command
     */
    private static String execReadToString(String cmd) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(cmd);
            InputStream stream = proc.getInputStream();
            Scanner scanner = new Scanner(stream);
            Scanner s = scanner.useDelimiter("\\A");
            String result = s.hasNext() ? s.next().trim() : null;
            scanner.close();
            return result;
        } catch (IOException e) {
            return null;
        }


    }

    /**
     * @param from: min port range
     * @param to    : max port range
     * @return new available port in range
     * @author hung.nt
     */
    public static int generatePort(int from, int to) {
        for (int port = from; port < to; port++) {
            if (available(port)) {
                return port;
            }
        }
        return to;
    }

    /**
     * @param port: port you want to check
     * @return true if port is available
     * @author hung.nt
     * check port is available
     */
    private static boolean available(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    /**
     * @param pattern: partern of ip or domain
     * @return dynamic ip or domain
     * @author hung.nt
     */
    public static String getHostName(String pattern) {
        if ("127.0.0.1".equals(pattern)) {
            return pattern;
        }
        if ("localhost".equals(pattern)) {
            return pattern;
        }
        if (!pattern.contains(".")) {
            return getDnsHostname();
        }
        return getIpAddress(pattern);
    }

    /**
     * @return dns hostname
     * @author hung.nt
     */
    public static String getDnsHostname() {
        String hostname = null;
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("win") >= 0) {
            hostname = System.getenv("COMPUTERNAME");

        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0) {
            if (hostname == null) {
                hostname = execReadToString("cat /etc/hostname");
            }
        }

        if (hostname == null) {
            hostname = execReadToString("hostname");
        }
        return hostname;
    }

    /**
     * @param ip:      ip which want to check
     * @param pattern: partern of ip
     * @return simility value
     * @author hung.nt
     */
    private static int getSimilityIp(String ip, String pattern) {
        int simility = 0;
        if (ip != null && pattern != null) {
            String[] ip_maskes = ip.split("\\.");
            String[] pt_maskes = pattern.split("\\.");
            if (ip_maskes != null && pt_maskes != null && ip_maskes.length == pt_maskes.length) {
                for (int i = 0; i < ip_maskes.length; i++) {
                    if (ip_maskes[i].equals(pt_maskes[i])) {
                        simility++;
                    }
                }
            }
        }
        return simility;
    }

    /**
     * @param pattern: base ip
     * @return : dynamic ip
     * @author hung.nt
     */
    public static String getIpAddress(String pattern) {

        try {
            int maxSimility = 0;
            String ip = null;
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface current = interfaces.nextElement();
                if (!current.isUp() || current.isLoopback() || current.isVirtual())
                    continue;
                Enumeration<InetAddress> addresses = current.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress current_addr = addresses.nextElement();
                    if (current_addr.isLoopbackAddress())
                        continue;
                    if (!(current_addr instanceof Inet4Address))
                        continue;
                    String tempIp = current_addr.getHostAddress();
                    int tempSimility = getSimilityIp(tempIp, pattern);
                    if (tempSimility >= maxSimility) {
                        maxSimility = tempSimility;
                        ip = tempIp;
                    }
                }
            }
            return ip;
        } catch (SocketException e) {
        }
        return null;
    }
}

