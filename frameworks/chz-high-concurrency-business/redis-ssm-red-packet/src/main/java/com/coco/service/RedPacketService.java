package com.coco.service;

import com.coco.pojo.RedPacket;

public interface RedPacketService {
	
	RedPacket getRedPacket(Long id);

	int decreaseRedPacket(Long id);
}