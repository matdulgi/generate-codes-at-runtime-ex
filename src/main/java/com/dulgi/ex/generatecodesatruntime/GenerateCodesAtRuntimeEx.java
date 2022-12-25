package com.dulgi.ex.generatecodesatruntime;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

public class GenerateCodesAtRuntimeEx {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, MalformedURLException, URISyntaxException {
        String dirPath = new File("output").getAbsolutePath();
        String className = "TestSource";
        String methodName = "test";
        new Tasks().run(dirPath, className, methodName);
    }
}
