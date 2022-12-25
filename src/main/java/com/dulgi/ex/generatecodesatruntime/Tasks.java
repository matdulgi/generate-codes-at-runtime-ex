package com.dulgi.ex.generatecodesatruntime;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;

public class Tasks {
    public void run(String dirPath, String className, String methodName) throws MalformedURLException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        generate(dirPath, className, methodName);
        compile(dirPath + "/" + className + ".java");
        load(dirPath);
        execute(className, methodName);
    }
    private void generate(String dirPath, String className, String methodName){
        String fileName = className + ".java";
        makeDirectory(dirPath);
        String source = genSource(className, methodName);
        writeSource(source, dirPath + "/"+fileName);
    }

    private void makeDirectory(String dirPath){
        File file = new File(dirPath);
        if (file.exists()){
            System.out.println("directory already exists");
            return;
        }
        file.mkdir();
    }

    private void writeSource(String source, String path){
        try(
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        ) {
            bufferedWriter.write(source);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String genSource(String className, String methodName){
        String source = "" +
                "public class "+className+" {\n" +
                "   public void "+methodName+"() {\n" +
                "       System.out.println(\"hello world!\");\n" +
                "   }\n" +
                "}";
        return source;
    }

    private void compile(String path){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, path);
    }


    private void load(String classPath) throws NoSuchMethodException, MalformedURLException, InvocationTargetException, IllegalAccessException, URISyntaxException {
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> clazz = URLClassLoader.class;
        Method method = clazz.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        URI uri = new File(classPath).toURI();
        method.invoke(classLoader, uri.toURL());
    }

    private void execute(String className, String methodName) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class clazz = Class.forName(className);
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod(methodName);
        method.invoke(obj);
    }
}
