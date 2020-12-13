package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/12/7
 */

/**
 * CONSTANT_NameAndType_info {
 *     u1 tag;
 *     u2 name_index;
 *     u2 descriptor_index;
 * }
 */
public class ConstantNameAndType extends ConstantInfo {
    private short nameIndex;
    private short descriptorIndex;

    public ConstantNameAndType(byte tag) {
        super(tag);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        nameIndex = BaseReadUtils.U2.read(inputStream);
        descriptorIndex = BaseReadUtils.U2.read(inputStream);
    }
}
