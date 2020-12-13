package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/12/7
 */

/**
 * CONSTANT_Module_info {
 *     u1 tag;
 *     u2 name_index;
 * }
 *
 * CONSTANT_Package_info {
 *     u1 tag;
 *     u2 name_index;
 * }
 */
public class ConstantModuleOrPackage extends ConstantInfo {
    private short nameIndex;

    public ConstantModuleOrPackage(byte tag) {
        super(tag);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        nameIndex = BaseReadUtils.U2.read(inputStream);
    }
}
