package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/12/7
 */

/**
 * // Dynamic和InvokeDynamic是类似的，所以可以用同一个类进行处理
 * CONSTANT_Dynamic_info {
 *     u1 tag;
 *     u2 bootstrap_method_attr_index;
 *     u2 name_and_type_index;
 * }
 *
 * CONSTANT_InvokeDynamic_info {
 *     u1 tag;
 *     u2 bootstrap_method_attr_index;
 *     u2 name_and_type_index;
 * }
 */
public class ConstantDynamic extends ConstantInfo {
    private short bootstrapMethodAttrIndex;
    private short nameAndTypeIndex;

    public ConstantDynamic(byte tag) {
        super(tag);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        bootstrapMethodAttrIndex = BaseReadUtils.U2.read(inputStream);
        nameAndTypeIndex = BaseReadUtils.U2.read(inputStream);
    }
}
