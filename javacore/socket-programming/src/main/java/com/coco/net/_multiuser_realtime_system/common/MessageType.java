package com.coco.net._multiuser_realtime_system.common;

/**
 * 消息类型接口：定义一些常量
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCESS = "1"; //用户登录成功
    String MESSAGE_LOGIN_FAILURE = "2"; //登录失败
    String MESSAGE_CUSTOM_MESSAGE = "3"; //普通信息包
    String MESSAGE_GET_ONLINE_USERS = "4"; //获取在线用户列表
    String MESSAGE_RET_ONLINE_USERS = "5"; //返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6"; //客户端退出
    String MESSAGE_CUSTOM_MESSAGE_TO_ALL = "7"; //群发
    String MESSAGE_FILE = "8"; //文件传输
    String MESSAGE_NEWS = MESSAGE_CUSTOM_MESSAGE_TO_ALL; //新闻推送
}
