package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/12/7
 */

/**
 * CONSTANT_String_info {
 *     u1 tag;
 *     u2 string_index;
 * }
 */
public class ConstantString extends ConstantInfo{
    private short stringIndex;

    public ConstantString(byte tag) {
        super(tag);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        stringIndex = BaseReadUtils.U2.read(inputStream);
    }
}
