package com.coco.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter @Setter
public class RedPacket implements Serializable {
	private Long id;
	private Long userId;
	private Double amount;
	private Timestamp sendDate;
	private Integer total;
	private Double unitAmount;
	private Integer stock;
	private Integer version;
	private String note;
	private static final long serialVersionUID = 1049397724701962381L;
}