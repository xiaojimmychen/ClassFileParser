package com.blog4jimmy.typeinfo;

import com.blog4jimmy.constantinfo.ConstantInfo;
import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

public class CodeAttribute extends AttributeInfo {
    private short maxStack;
    private short maxLocals;
    private int codeLength;
    private byte[] code;
    private short exceptionTableLength;
    private ExceptionTable[] exceptionTables;
    private short attributeCount;
    private AttributeInfo[] attributeInfos;

    public CodeAttribute(ConstantInfo[] cpInfo, short attributeNameIndex) {
        super(cpInfo, attributeNameIndex);
    }

    public int getCodeLength() {
        return codeLength;
    }

    public byte[] getCode() {
        return code;
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        attributeLength = BaseReadUtils.U4.read(inputStream);

        maxStack = BaseReadUtils.U2.read(inputStream);
        maxLocals = BaseReadUtils.U2.read(inputStream);
        codeLength = BaseReadUtils.U4.read(inputStream);
        code = new byte[codeLength];
        inputStream.read(code);

        exceptionTableLength = BaseReadUtils.U2.read(inputStream);
        exceptionTables = new ExceptionTable[exceptionTableLength];
        for (int i = 0; i < exceptionTableLength; i++) {
            ExceptionTable table = new ExceptionTable();
            table.read(inputStream);
            exceptionTables[i] = table;
        }

        attributeCount = BaseReadUtils.U2.read(inputStream);
        attributeInfos = new AttributeInfo[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            AttributeInfo attributeInfo = AttributeInfo.getAttribute(cpInfo, inputStream);
            attributeInfo.read(inputStream);
            attributeInfos[i] = attributeInfo;
        }

    }
}
