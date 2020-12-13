package com.blog4jimmy;

import com.blog4jimmy.constantinfo.ConstantInfo;
import com.blog4jimmy.typeinfo.FieldOrMethodInfo;
import com.blog4jimmy.utils.BaseReadUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/11/27
 */

public class Main {

    public static void main(String[] args) {
        System.out.println("Argument input:");
        for (String arg : args) {
            System.out.print(" ");
            System.out.print(arg);
        }
        System.out.println();

        if (args.length != 1) {
            printUsage();
            return;
        }

        File file = new File(args[0]);
        if (file == null) {
            System.out.println("Open File : " + args[0] + " failed! Please check!");
            return;
        }

        parseClassFile(file);

    }

    private static void parseClassFile(File file) {
        try {
            // 处理class文件的魔术和版本号信息
            FileInputStream inputStream = new FileInputStream(file);
            ClassFile classFile = new ClassFile();
            classFile.magic = BaseReadUtils.U4.read(inputStream);
            classFile.minorVersion = BaseReadUtils.U2.read(inputStream);
            classFile.majorVersion = BaseReadUtils.U2.read(inputStream);

            // 处理class文件的常量池信息
            classFile.constantPoolCount = BaseReadUtils.U2.read(inputStream);
            classFile.cpInfo = new ConstantInfo[classFile.constantPoolCount];
            classFile.parseCpInfo(inputStream);
            classFile.dumpCpInfo();

            // 处理class文件中类的访问标志
            classFile.accessFlags = BaseReadUtils.U2.read(inputStream);
            classFile.dumpAccessFlags();

            // 处理class文件中类的信息和父类信息
            classFile.thisClass = BaseReadUtils.U2.read(inputStream);
            classFile.superClass = BaseReadUtils.U2.read(inputStream);
            classFile.dumpThisAndSuperClass();

            // 处理class文件中的接口信息
            classFile.interfacesCount = BaseReadUtils.U2.read(inputStream);
            classFile.interfaces = new short[classFile.interfacesCount];
            classFile.parseInterfaces(inputStream);
            classFile.dumpInterfaces();

            // 处理class文件中类的字段信息
            classFile.fieldsCount = BaseReadUtils.U2.read(inputStream);
            classFile.fields = new FieldOrMethodInfo[classFile.fieldsCount];
            classFile.parseFields(inputStream);
            classFile.dumpFields();

            // 处理class文件中类的方法信息
            classFile.methodsCount = BaseReadUtils.U2.read(inputStream);
            classFile.methods = new FieldOrMethodInfo[classFile.methodsCount];
            classFile.parseMethods(inputStream);
            classFile.dumpMethods();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java com.blog4jimmy.Main [FILE]");
    }
}
