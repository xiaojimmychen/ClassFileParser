package com.blog4jimmy.typeinfo;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Jimmy.Chen@blog4jimmy.com
 * @date 2020/12/8
 */
public abstract class BasicTypeInfo {
    public abstract void read(InputStream inputStream) throws IOException;
}
