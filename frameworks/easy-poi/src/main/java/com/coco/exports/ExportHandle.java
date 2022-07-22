package com.coco.exports;

import java.io.*;

public final class ExportHandle {

    private ExportHandle() {}

    public static void write(ByteArrayOutputStream os) throws IOException {
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\app.xlsx"));
            byte[] buff = new byte[10240];

            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (IOException var13) {
            throw var13;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }
}


