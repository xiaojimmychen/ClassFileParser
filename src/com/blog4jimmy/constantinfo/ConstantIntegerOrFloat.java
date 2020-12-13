package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/12/7
 */

/**
 * // Integer和Float表示的结构类似，我们也用同一个类进行表示
 * CONSTANT_Integer_info {
 *     u1 tag;
 *     u4 bytes;
 * }
 *
 * CONSTANT_Float_info {
 *     u1 tag;
 *     u4 bytes;
 * }
 */
public class ConstantIntegerOrFloat extends ConstantInfo{
    private int bytes;

    public ConstantIntegerOrFloat(byte tag) {
        super(tag);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        bytes = BaseReadUtils.U4.read(inputStream);
    }
}
