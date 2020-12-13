package com.blog4jimmy.constantinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/11/28
 */

// tag在基类中进行处理，其余的常量池类型继承该基类
public abstract class ConstantInfo {
    public static final byte CONSTANT_UTF8 = 1;
    public static final byte CONSTANT_INTEGER = 3;
    public static final byte CONSTANT_FLOAT = 4;
    public static final byte CONSTANT_LONG = 5;
    public static final byte CONSTANT_DOUBLE = 6;
    public static final byte CONSTANT_CLASS = 7;
    public static final byte CONSTANT_STRING = 8;
    public static final byte CONSTANT_FIELDREF = 9;
    public static final byte CONSTANT_METHODREF = 10;
    public static final byte CONSTANT_INTERFACEMETHODREF = 11;
    public static final byte CONSTANT_NAMEANDTYPE = 12;
    public static final byte CONSTANT_METHODHANDLE = 15;
    public static final byte CONSTANT_METHODTYPE = 16;
    public static final byte CONSTANT_DYNAMIC = 17;
    public static final byte CONSTANT_INVOKEDYNAMIC = 18;
    public static final byte CONSTANT_MODULE = 19;
    public static final byte CONSTANT_PACKAGE = 20;

    private byte tag;

    public ConstantInfo(byte tag) {
        this.tag = tag;
    }

    public byte getTag() {
        return tag;
    }

    public abstract void read(InputStream inputStream) throws IOException;

}
