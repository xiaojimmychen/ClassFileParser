package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/12/7
 */

/**
 * CONSTANT_MethodHandle_info {
 *     u1 tag;
 *     u1 reference_kind;
 *     u2 reference_index;
 * }
 */
public class ConstantMethodHandle extends ConstantInfo {
    private byte refKind;
    private short refIndex;

    public ConstantMethodHandle(byte tag) {
        super(tag);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        refKind = BaseReadUtils.U1.read(inputStream);
        refIndex = BaseReadUtils.U2.read(inputStream);
    }
}
