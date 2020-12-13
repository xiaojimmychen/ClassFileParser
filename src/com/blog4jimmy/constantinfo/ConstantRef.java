package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/11/28
 */

/**
 * // 因为结构类似，这里使用ConstantRef来表示下面三种类型的常量
 * CONSTANT_Fieldref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 *
 * CONSTANT_Methodref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 *
 * CONSTANT_InterfaceMethodref_info {
 *     u1 tag;
 *     u2 class_index;
 *     u2 name_and_type_index;
 * }
 */
public class ConstantRef extends ConstantInfo {
    private short classIndex;
    private short nameAndTypeIndex;

    public ConstantRef(byte tag) {
        super(tag);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        classIndex = BaseReadUtils.U2.read(inputStream);
        nameAndTypeIndex = BaseReadUtils.U2.read(inputStream);
    }
}
