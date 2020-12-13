package com.blog4jimmy.typeinfo;

import com.blog4jimmy.utils.BaseReadUtils;

import java.io.IOException;
import java.io.InputStream;

public class ExceptionTable {
    private short startPc;
    private short endPc;
    private short handlerPc;
    private short catchPc;

    public void read(InputStream inputStream) throws IOException {
        startPc = BaseReadUtils.U2.read(inputStream);
        endPc = BaseReadUtils.U2.read(inputStream);
        handlerPc = BaseReadUtils.U2.read(inputStream);
        catchPc = BaseReadUtils.U2.read(inputStream);
    }
}
