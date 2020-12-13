package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/12/7
 */

/**
 * // Long和Double的结构类似，用同一个类进行表示
 * CONSTANT_Long_info {
 *     u1 tag;
 *     u4 high_bytes;
 *     u4 low_bytes;
 * }
 *
 * CONSTANT_Double_info {
 *     u1 tag;
 *     u4 high_bytes;
 *     u4 low_bytes;
 * }
 */
public class ConstantLongOrDouble extends ConstantInfo {
    private int highValue;
    private int lowValue;

    public ConstantLongOrDouble(byte tag) {
        super(tag);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        highValue = BaseReadUtils.U4.read(inputStream);
        lowValue = BaseReadUtils.U4.read(inputStream);
    }
}
