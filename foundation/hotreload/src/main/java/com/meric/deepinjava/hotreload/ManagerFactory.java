package com.meric.deepinjava.hotreload;

import com.sun.javafx.fxml.LoadListener;
import com.sun.org.apache.bcel.internal.generic.LoadClass;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载Manager的工厂
 */
public class ManagerFactory {
    private static final Map<String,LoadInfo> loadTimeMap = new HashMap<String, LoadInfo>();
    public static final String CLASS_PATH ="/Users/meric/Repositories/Study/spare/java/DeepInJava/hotreload/target/classes/";
    public static final String MY_MANAGER = "com.meric.deepinjava.hotreload.MyManager";

    public static BaseManager getManager(String className){
        File file = new File(CLASS_PATH+className.replaceAll("\\.","/" + ".class"));
        long lastModified = file.lastModified();
        if(loadTimeMap.get(className)==null){
            load(className,lastModified);
        }else if(loadTimeMap.get(className).getLoadTime()!=lastModified){
            load(className,lastModified);
        }
        return loadTimeMap.get(className).getManager();
    }

    private static void load(String className, long lastModified) {
        MyClassLoader classLoader = new MyClassLoader(CLASS_PATH);
        Class<?> loadClass = null;
        try {
            loadClass = classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        BaseManager manager = newInstance(loadClass);
        LoadInfo loadInfo = new LoadInfo(classLoader,lastModified);
        loadInfo.setManager(manager);
        loadTimeMap.put(className,loadInfo);

    }

    private static BaseManager newInstance(Class<?> loadClass) {
        try {
            return (BaseManager)loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


}
