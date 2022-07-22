package com.coco.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter @Setter
public class UserRedPacket implements Serializable {

	private Long id;
	private Long redPacketId;
	private Long userId;
	private Double amount;
	private Timestamp grabTime;
	private String note;
	
	private static final long serialVersionUID = -5617482065991830143L;
}