package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/11/28
 */

/**
 * CONSTANT_Class_info {
 *     u1 tag;          // tag在基类处理
 *     u2 name_index;
 * }
 */
public class ConstantClass extends ConstantInfo {
    private short nameIndex;

    public ConstantClass(byte tag) {
        super(tag);
    }

    public short getNameIndex() {
        return nameIndex;
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        nameIndex = BaseReadUtils.U2.read(inputStream);
    }
}
