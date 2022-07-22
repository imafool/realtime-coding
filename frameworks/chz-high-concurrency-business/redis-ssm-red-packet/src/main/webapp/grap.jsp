<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>测试程序</title>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                //模拟30000个异步请求，进行并发
                var max = 30000;
                // var max = 3;//测试服务器连接使用
                for (var i = 1; i <= max; i++) {
                    $.post({
                        //jQuery的post异步请求
                        // url: "./userRedPacket/grapRedPacket.do?redPacketId=1&userId=" + i,
                        // url: "./userRedPacket/grapRedPacketForUpdate.do?redPacketId=1&userId=" + i,
                        // url: "./userRedPacket/grapRedPacketForVersion.do?redPacketId=1&userId=" + i,
                        // url: "./userRedPacket/grapRedPacketForVersionByTimestamp.do?redPacketId=1&userId=" + i,
                        // url: "./userRedPacket/grapRedPacketForVersionByRetries.do?redPacketId=1&userId=" + i,
                        url: "./userRedPacket/grapRedPacketForRedis.do?redPacketId=1&userId=" + i,
                        success: function (result) {

                        }
                    });
                }
            });
        </script>
    </head>
    <body>
    </body>
</html>