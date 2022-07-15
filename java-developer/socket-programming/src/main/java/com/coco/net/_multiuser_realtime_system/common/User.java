package com.coco.net._multiuser_realtime_system.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统用户信息
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String passwd;
}
