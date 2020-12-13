package com.blog4jimmy.typeinfo;

import com.blog4jimmy.constantinfo.ConstantInfo;
import com.blog4jimmy.constantinfo.ConstantUtf8;
import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/11/28
 */
public class AttributeInfo extends BasicTypeInfo{
    final ConstantInfo[] cpInfo;
    protected short attributeNameIndex;
    protected int attributeLength;
    protected byte[] info;

    public AttributeInfo(ConstantInfo[] cpInfo, short attributeNameIndex) {
        this.cpInfo = cpInfo;
        this.attributeNameIndex = attributeNameIndex;
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        attributeLength = BaseReadUtils.U4.read(inputStream);
        info = new byte[attributeLength];
        inputStream.read(info);
    }

    public static AttributeInfo getAttribute(ConstantInfo[] cpInfo, InputStream inputStream) throws IOException {
        short nameIndex = BaseReadUtils.U2.read(inputStream);
        String name = ((ConstantUtf8) cpInfo[nameIndex]).getValue();
        if (name.compareToIgnoreCase("Code") == 0) {
            return new CodeAttribute(cpInfo, nameIndex);
        }
        return new AttributeInfo(cpInfo, nameIndex);

    }
}
