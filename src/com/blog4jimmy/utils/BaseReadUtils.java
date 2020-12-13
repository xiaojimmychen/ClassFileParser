package com.blog4jimmy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/11/27
 */
// U1、U2、U4 分别从文件中读取1、2、4个字节并分别组合成byte、short和int型值
public class BaseReadUtils {

    public static class U1 {
        public static byte read(InputStream inputStream) throws IOException {
            // 此处我们不处理异常，异常统一由调用者处理好了
            byte[] bytes = new byte[1];
            inputStream.read(bytes);
            return bytes[0];
        }
    }

    public static class U2 {
        public static short read(InputStream inputStream) throws IOException {
            // 此处我们不处理异常，异常统一由调用者处理好了
            byte[] bytes = new byte[2];
            inputStream.read(bytes);
            return ByteBuffer.wrap(bytes).getShort();
        }
    }

    public static class U4 {
        public static int read(InputStream inputStream) throws IOException {
            // 此处我们不处理异常，异常统一由调用者处理好了
            byte[] bytes = new byte[4];
            inputStream.read(bytes);
            return ByteBuffer.wrap(bytes).getInt();
        }
    }

}
