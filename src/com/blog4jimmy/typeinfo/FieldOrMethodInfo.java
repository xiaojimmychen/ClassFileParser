package com.blog4jimmy.typeinfo;

import com.blog4jimmy.constantinfo.ConstantInfo;
import com.blog4jimmy.constantinfo.ConstantUtf8;
import com.blog4jimmy.utils.BaseReadUtils;

import javax.management.Attribute;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/11/28
 */
public class FieldOrMethodInfo extends BasicTypeInfo {
    private final ConstantInfo[] cpInfo;

    private short accessFlags;
    private short nameIndex;
    private short descriptorIndex;
    private short attributesCount;
    private AttributeInfo[] attributes;

    public FieldOrMethodInfo(final ConstantInfo[] cpInfo) {
        this.cpInfo = cpInfo;
    }

    public short getAccessFlags() {
        return accessFlags;
    }

    public short getNameIndex() {
        return nameIndex;
    }

    public short getDescriptorIndex() {
        return descriptorIndex;
    }

    public short getAttributesCount() {
        return attributesCount;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public void read(InputStream inputStream) throws IOException {
        accessFlags = BaseReadUtils.U2.read(inputStream);
        nameIndex = BaseReadUtils.U2.read(inputStream);
        descriptorIndex = BaseReadUtils.U2.read(inputStream);
        attributesCount = BaseReadUtils.U2.read(inputStream);
        attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            short attributeNameIndex = BaseReadUtils.U2.read(inputStream);
            String name = ((ConstantUtf8)cpInfo[attributeNameIndex]).getValue();
            if (name.compareToIgnoreCase("Code") == 0) {
                CodeAttribute attribute = new CodeAttribute(cpInfo, attributeNameIndex);
                attribute.read(inputStream);
                attributes[i] = attribute;
                continue;
            }
            AttributeInfo attributeInfo = new AttributeInfo(cpInfo, attributeNameIndex);
            attributeInfo.read(inputStream);
            attributes[i] = attributeInfo;
        }
    }
}
