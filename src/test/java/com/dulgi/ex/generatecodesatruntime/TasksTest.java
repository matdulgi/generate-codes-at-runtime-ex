package com.dulgi.ex.generatecodesatruntime;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TasksTest {
    Tasks tasks = new Tasks();
    String className = "GoodDay";
    String fileName = className + ".java";
    String methodName = "goodDay";
    String dirPath = new File("output").getAbsolutePath();
    String sourcePath = dirPath + "/" + fileName;


    @Test
    public void genSourceTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = tasks.getClass().getDeclaredMethod("genSource", String.class, String.class);
        method.setAccessible(true);
        String str = (String) method.invoke(tasks, className, methodName);
        System.out.println(str);
    }

    @Test
    public void makeDirectoryTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = tasks.getClass().getDeclaredMethod("makeDirectory", String.class);
        method.setAccessible(true);
        method.invoke(tasks, dirPath);

    }

    @Test
    public void writeSourceTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = tasks.getClass().getDeclaredMethod("writeSource", String.class, String.class);
        method.setAccessible(true);
        method.invoke(tasks, "goodday", dirPath + "/writeTest");
    }

    @Test
    public void generateTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = tasks.getClass().getDeclaredMethod("generate", String.class, String.class, String.class);
        method.setAccessible(true);
        method.invoke(tasks, dirPath, className, methodName);
    }

    @Test
    public void compileTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = tasks.getClass().getDeclaredMethod("compile", String.class);
        method.setAccessible(true);
        method.invoke(tasks, sourcePath);
    }

    @Test
    public void executeWithoutLoadingTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = tasks.getClass().getDeclaredMethod("execute", String.class, String.class);
        method.setAccessible(true);
        Assert.assertThrows(InvocationTargetException.class, () -> method.invoke(tasks, className, methodName));
    }

    @Test
    public void loadTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        // compile
        String dirPath = new File("sources").getAbsolutePath();
        String className = "LoadTest";
//        Method compile = tasks.getClass().getDeclaredMethod("compile", String.class);
//        compile.setAccessible(true);
//        compile.invoke(tasks, dirPath + "/" + className + ".java" );
        //

        Assert.assertThrows(ClassNotFoundException.class, () -> Class.forName("LoadTest"));
        Method method = tasks.getClass().getDeclaredMethod("load", String.class);
        method.setAccessible(true);
        method.invoke(tasks, dirPath);
        Class clazz = Class.forName("LoadTest");
    }

    @Test
    public void run() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = tasks.getClass().getDeclaredMethod("run", String.class, String.class, String.class);
        method.setAccessible(true);
        method.invoke(tasks, dirPath, className, methodName);
    }


}
