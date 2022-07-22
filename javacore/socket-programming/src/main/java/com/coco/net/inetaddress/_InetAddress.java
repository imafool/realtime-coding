package com.coco.net.inetaddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * java.net.InetAddress
 * 获取本机/远程服务器的域名和IP信息
 */
public class _InetAddress {
    public static void main(String[] args) throws UnknownHostException {
        //localhost
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);

        //localhost
        InetAddress localhost = InetAddress.getByName("MS-AELEIKMWLHAA");
        System.out.println(localhost);

        //www.oracle.com
        InetAddress oracle = InetAddress.getByName("www.oracle.com");
        System.out.println(oracle);
        String hostip = oracle.getHostAddress();
        String hostName = oracle.getHostName();
        System.out.println(hostip + " " + hostName);
    }
}
