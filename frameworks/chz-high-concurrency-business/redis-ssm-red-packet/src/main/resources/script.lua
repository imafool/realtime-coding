---------------------------------------------------------------------------------------------------
--Lua脚本：使用方法：第一运行，现在Redis中编译和缓存，获得一个SHA1字符串，之后通过该字符串和参数就能调用Lua脚本了
---------------------------------------------------------------------------------------------------
--使用Lua脚本保存红包信息

--缓存抢红包列表
local listKey = 'red_packet_list'..KEYS[1]
--当前被抢红包key
local redPacket = 'red_packet_'..KEYS[1]
--当前红包库存
local stock = tonumber(redis.call('hget', redPacket, 'stock'))
--没有库存，返回0
if stock <= 0 then return 0 end
--库存-1
stock = stock - 1
--保存当前库存
redis.call('hset', redPacket, 'stock', tostring(stock))
--链表中加入当前红包信息
redis.call('rpush', listKey, ARGV[1])
--如果是最后一个红包，返回2，表示红包已经抢完，需要持久化到DB
if stock == 0 then return 2 end
--返回1，抢红包成功
return 1