package com.coco.net._multiuser_realtime_system.client.view;

import com.coco.net._multiuser_realtime_system.client.service.ChatService;
import com.coco.net._multiuser_realtime_system.client.service.FileService;
import com.coco.net._multiuser_realtime_system.client.service.UserService;
import com.coco.net._multiuser_realtime_system.client.utils.InputUtils;

/**
 * 客户端菜单页面
 */
public class ClientView {

    private boolean loop = true; //是否显示菜单
    private String key = ""; //键盘输入
    private UserService userService = new UserService();
    private ChatService chatService = new ChatService();
    private FileService fileService = new FileService();

    public static void main(String[] args) {
        new ClientView().mainMenu();
        System.out.println("退出系统");//在main线程给服务器发送退出系统的消息，调用System.exit(0)退出，服务端收到消息，结束与之关联的线程持有的socket和线程
    }

    private void mainMenu(){
        while(loop){
            System.out.println("==================欢迎登录网络通信系统===================");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.println("请输入你的选择：");

            key = InputUtils.readString(1);

            if ("1".equals(key)) {

                System.out.println("请输入用户名：");
                String userId = InputUtils.readString(50);
                System.out.println("请输入密码：");
                String pwd = InputUtils.readString(50);

                //登录验证
                boolean isSuccess = userService.checkUser(userId, pwd);

                if (isSuccess){
                    System.out.println("==================用户(" + userId + ")登录成功===================");
                    //进入二级菜单
                    while(loop){
                        System.out.println("==================系统二级菜单(" + userId + ")==================");
                        System.out.println("\t\t 1 显示在线用户列表");
                        System.out.println("\t\t 2 群发消息");
                        System.out.println("\t\t 3 私聊消息");
                        System.out.println("\t\t 4 发送文件");
                        System.out.println("\t\t 9 退出系统");
                        System.out.println("请输入你的选择：");
                        key = InputUtils.readString(1);
                        if ("1".equals(key)) {
                            System.out.println("==============用户列表=============");
                            userService.onlineUserList();
                            // System.out.println("获取在线用户列表");
                        } else if ("2".equals(key)) {
                            // System.out.println("群发消息");
                            System.out.println("请输入群发消息：");
                            String content = InputUtils.readString(1000);
                            chatService.sendAll(userId, content);
                        } else if ("3".equals(key)) {
                            // System.out.println("私聊消息");
                            System.out.println("请输入欲聊天的编号：");
                            String getter = InputUtils.readString(50);
                            System.out.println("请输入欲聊天的内容：");
                            String content = InputUtils.readString(1000);
                            chatService.sendToSomebody(userId, getter, content);
                        } else if ("4".equals(key)) {
                            String sourPath = "C:\\Users\\Administrator\\Desktop\\upload.txt";
                            String destPath = "C:\\Users\\Administrator\\Desktop\\upload_download.txt";
                            System.out.println("即将传输文件：" + "[从本地路径："+ sourPath +"] 到 [对方路径：" + destPath + "]");
                            System.out.println("请输入欲要发送的人的编号：");
                            String getter = InputUtils.readString(50);
                            fileService.sendFile(userId, getter, sourPath, destPath);
                        } else if ("9".equals(key)) {
                            loop = false;
                            userService.logout();
                        }
                    }
                }else{
                    System.out.println("登录失败");
                }

                System.out.println("登录系统");
            } else if ("9".equals(key)) {
                loop = false;
            }
        }
    }
}
