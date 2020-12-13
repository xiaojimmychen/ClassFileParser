package com.blog4jimmy;

import com.blog4jimmy.constantinfo.*;
import com.blog4jimmy.typeinfo.CodeAttribute;
import com.blog4jimmy.typeinfo.FieldOrMethodInfo;
import com.blog4jimmy.typeinfo.AttributeInfo;
import com.blog4jimmy.utils.BaseReadUtils;
import com.blog4jimmy.utils.InstructionUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.blog4jimmy.constantinfo.ConstantInfo.*;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/11/28
 */
public class ClassFile {
    public int magic;
    public short minorVersion;
    public short majorVersion;
    public short constantPoolCount;
    public ConstantInfo[] cpInfo;
    public short accessFlags;
    public short thisClass;
    public short superClass;
    public short interfacesCount;
    public short[] interfaces;
    public short fieldsCount;
    public FieldOrMethodInfo[] fields;
    public short methodsCount;
    public FieldOrMethodInfo[] methods;
    public short attributesCount;
    public AttributeInfo[] attributes;

    public void parseCpInfo(InputStream inputStream) throws IOException {
        for (int i = 1; i < constantPoolCount; i++) {
            byte tag = BaseReadUtils.U1.read(inputStream);

            switch (tag) {
                case CONSTANT_UTF8: {
                    ConstantInfo constantInfo = new ConstantUtf8(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }

                case CONSTANT_CLASS: {
                    ConstantInfo constantInfo = new ConstantClass(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }

                case CONSTANT_FIELDREF:
                case CONSTANT_METHODREF:
                case CONSTANT_INTERFACEMETHODREF: {
                    ConstantInfo constantInfo = new ConstantRef(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }

                case CONSTANT_DOUBLE:
                case CONSTANT_LONG: {
                    ConstantInfo constantInfo = new ConstantLongOrDouble(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    // jvm规定：
                    // If a CONSTANT_Long_info or CONSTANT_Double_info structure is the entry at index n in the constant_pool table,
                    // then the next usable entry in the table is located at index n+2. The constant_pool index n+1 must be valid but is considered unusable.
                    i++;
                    break;
                }

                case CONSTANT_INTEGER:
                case CONSTANT_FLOAT: {
                    ConstantInfo constantInfo = new ConstantIntegerOrFloat(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }

                case CONSTANT_STRING: {
                    ConstantInfo constantInfo = new ConstantString(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }

                case CONSTANT_NAMEANDTYPE: {
                    ConstantInfo constantInfo = new ConstantNameAndType(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }

                case CONSTANT_METHODHANDLE: {
                    ConstantInfo constantInfo = new ConstantMethodHandle(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }

                case CONSTANT_METHODTYPE: {
                    ConstantInfo constantInfo = new ConstantMethodType(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }

                case CONSTANT_DYNAMIC:
                case CONSTANT_INVOKEDYNAMIC: {
                    ConstantInfo constantInfo = new ConstantDynamic(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }

                case CONSTANT_MODULE:
                case CONSTANT_PACKAGE: {
                    ConstantInfo constantInfo = new ConstantModuleOrPackage(tag);
                    constantInfo.read(inputStream);
                    cpInfo[i] = constantInfo;
                    break;
                }
            }
        }
    }

    public void dumpCpInfo() {
        System.out.println("constantPoolCount: " + constantPoolCount);
        if (cpInfo != null) {
            for (int i = 0; i < constantPoolCount; i++) {
                ConstantInfo constantInfo = cpInfo[i];
                if (constantInfo == null) {
                    continue;
                }
                switch (constantInfo.getTag()) {
                    case CONSTANT_UTF8: {
                        System.out.println("[+]" + i + " UTF-8\t\t" + ((ConstantUtf8) constantInfo).getValue());
                        break;
                    }

                    case CONSTANT_CLASS: {
                        short index = ((ConstantClass) constantInfo).getNameIndex();
                        System.out.println("[+]" + i + " CLASS\t\t" + ((ConstantUtf8)cpInfo[index]).getValue());
                    }
                }
            }
        }
        System.out.println();
    }

    public void dumpAccessFlags() {
        System.out.printf("accessFlags: 0x%x\n", accessFlags);
        System.out.print("[+] access_flag\t\t");
        System.out.println(getAccessFlagsString(accessFlags));
        System.out.println();
    }

    public String getAccessFlagsString(short flag) {
        boolean or = false;
        StringBuilder builder = new StringBuilder();

        if ((flag & 0x0001) != 0) {
            builder.append("ACC_PUBLIC");
            or = true;
        }

        if ((flag & 0x0010) != 0) {
            if (or) {
                builder.append(" | ");
            }
            builder.append("ACC_FINAL");
        }

        if ((flag & 0x0020) != 0) {
            if (or) {
                builder.append(" | ");
            }
            builder.append("ACC_SUPER");
        }

        if ((flag & 0x0200) != 0) {
            if (or) {
                builder.append(" | ");
            }
            builder.append("ACC_INTERFACE");
        }

        if ((flag & 0x0400) != 0) {
            if (or) {
                builder.append(" | ");
            }
            builder.append("ACC_ABSTRACT");
        }

        if ((flag & 0x1000) != 0) {
            if (or) {
                builder.append(" | ");
            }
            builder.append("ACC_SYNTHETIC");
        }

        if ((flag & 0x2000) != 0) {
            if (or) {
                builder.append(" | ");
            }
            builder.append("ACC_ANNOTATION");
        }

        if ((flag & 0x4000) != 0) {
            if (or) {
                builder.append(" | ");
            }
            builder.append("ACC_ENUM");
        }

        if ((flag & 0x8000) != 0) {
            if (or) {
                builder.append(" | ");
            }
            builder.append("ACC_MODULE");
        }
        return builder.toString();
    }

    public void dumpThisAndSuperClass() {
        System.out.printf("thisClassIndex = %d, superClassIndex = %d\n", thisClass, superClass);

        ConstantClass thisConstantClass = (ConstantClass)cpInfo[thisClass];
        ConstantClass superConstantClass = (ConstantClass)cpInfo[superClass];
        System.out.println("[+] this_class\t\t" + ((ConstantUtf8)cpInfo[thisConstantClass.getNameIndex()]).getValue());
        System.out.println("[+] super_class\t\t" + ((ConstantUtf8)cpInfo[superConstantClass.getNameIndex()]).getValue());
        System.out.println();
    }

    public void parseInterfaces(FileInputStream inputStream) throws IOException {
        for (int i = 0; i < interfacesCount; i++) {
            interfaces[i] = BaseReadUtils.U2.read(inputStream);
        }
    }

    public void dumpInterfaces() {
        System.out.println("interfacesCount = " + interfacesCount);
        for (int i = 0; i < interfacesCount; i++) {
            ConstantClass interfaceConstantClass = (ConstantClass) cpInfo[interfaces[i]];
            ConstantUtf8 interfaceUtf8 = (ConstantUtf8)(cpInfo[interfaceConstantClass.getNameIndex()]);
            System.out.println("[+] interface\t\t" + interfaceUtf8.getValue());
        }
        System.out.println();
    }

    public void parseFields(FileInputStream inputStream) throws IOException {
        for (int i = 0; i < fieldsCount; i++) {
            FieldOrMethodInfo fieldInfo = new FieldOrMethodInfo(cpInfo);
            fieldInfo.read(inputStream);
            fields[i] = fieldInfo;
        }
    }

    public void dumpFields() {
        System.out.println("fieldsCount = " + fieldsCount);
        for (int i = 0; i < fieldsCount; i++) {
            FieldOrMethodInfo fieldInfo = fields[i];
            System.out.println("[+] " + i + " field\t\t" + ((ConstantUtf8)cpInfo[fieldInfo.getNameIndex()]).getValue());
            System.out.println("[+] " + i + " desc\t\t" + ((ConstantUtf8)cpInfo[fieldInfo.getDescriptorIndex()]).getValue());
            // 属性部分这里我们就不进行dump了
        }
        System.out.println();
    }

    public void parseMethods(FileInputStream inputStream) throws IOException {
        for (int i = 0; i < methodsCount; i++) {
            FieldOrMethodInfo method = new FieldOrMethodInfo(cpInfo);
            method.read(inputStream);
            methods[i] = method;
        }
    }

    public void dumpMethods() {
        System.out.println("methodsCount = " + methodsCount);
        for (int i = 0; i < methodsCount; i++) {
            FieldOrMethodInfo methodInfo = methods[i];
            System.out.print("[+] " + i + " method: " + ((ConstantUtf8)cpInfo[methodInfo.getNameIndex()]).getValue());
            System.out.print("() " + " desc: " + ((ConstantUtf8)cpInfo[methodInfo.getDescriptorIndex()]).getValue());
            System.out.println(" accessFlag: " + getAccessFlagsString(methodInfo.getAccessFlags()));
            for (int j = 0; j < methodInfo.getAttributesCount(); j++) {
                AttributeInfo attributeInfo = methodInfo.getAttributes()[j];
                if (attributeInfo instanceof CodeAttribute) {
                    attributeInfo = (CodeAttribute)methodInfo.getAttributes()[j];
                    for (int k = 0; k < ((CodeAttribute) attributeInfo).getCodeLength(); k++) {
                        byte byteCode = ((CodeAttribute) attributeInfo).getCode()[k];
                        System.out.printf("\t\t0x%x:%s\n", byteCode, InstructionUtils.getInstruction(Math.abs(byteCode)));
                    }
                }
            }


            System.out.println();
        }
    }
}
