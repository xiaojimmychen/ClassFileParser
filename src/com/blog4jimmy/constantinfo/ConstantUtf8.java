package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UTFDataFormatException;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/11/28
 */

/**
 * CONSTANT_Utf8_info {
 *     u1 tag;
 *     u2 length;
 *     u1 bytes[length];
 * }
 */
public class ConstantUtf8 extends ConstantInfo{
    private String value;

    public ConstantUtf8(byte tag) {
        super(tag);
    }

    public String getValue() {
        return value;
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        short length = BaseReadUtils.U2.read(inputStream);
        byte[] bytes = new byte[length];
        inputStream.read(bytes);
        value = readUtf8(bytes);
    }

    private String readUtf8(byte[] bytes) throws UTFDataFormatException {
        int c, char2, char3;
        int count = 0;
        int chars_count = 0;
        char[] chars = new char[bytes.length];

        while (count <  bytes.length) {
            c = (int) bytes[count] & 0xff;
            if (c > 127) break;
            count++;
            chars[chars_count++] = (char) c;
        }

        while (count < bytes.length) {
            c = (int) bytes[count] & 0xff;
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    /* 0xxxxxxx*/
                    count++;
                    chars[chars_count++] = (char) c;
                    break;
                case 12:
                case 13:
                    /* 110x xxxx   10xx xxxx*/
                    count += 2;
                    if (count > bytes.length)
                        throw new UTFDataFormatException(
                                "malformed input: partial character at end");
                    char2 = (int) bytes[count - 1];
                    if ((char2 & 0xC0) != 0x80)
                        throw new UTFDataFormatException(
                                "malformed input around byte " + count);
                    chars[chars_count++] = (char) (((c & 0x1F) << 6) |
                            (char2 & 0x3F));
                    break;
                case 14:
                    /* 1110 xxxx  10xx xxxx  10xx xxxx */
                    count += 3;
                    if (count > bytes.length)
                        throw new UTFDataFormatException(
                                "malformed input: partial character at end");
                    char2 = (int) bytes[count - 2];
                    char3 = (int) bytes[count - 1];
                    if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
                        throw new UTFDataFormatException(
                                "malformed input around byte " + (count - 1));
                    chars[chars_count++] = (char) (((c & 0x0F) << 12) |
                            ((char2 & 0x3F) << 6) |
                            ((char3 & 0x3F) << 0));
                    break;
                default:
                    /* 10xx xxxx,  1111 xxxx */
                    throw new UTFDataFormatException(
                            "malformed input around byte " + count);
            }
        }
        return new String(chars, 0, chars_count);
    }
}
