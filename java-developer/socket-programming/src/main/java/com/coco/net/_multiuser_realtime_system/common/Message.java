package com.coco.net._multiuser_realtime_system.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 通讯消息模型
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sender;
    private String getter;//消息接收者
    private String content;
    private String sendTime;
    private String msgType;
    private byte[] file;
    private String destPath;
}
