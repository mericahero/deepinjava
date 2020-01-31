import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        //new Thread(new MsgHander()).start();
        try {
            ClassLoader cloader = new MyClassLoader("/Users/meric/Repositories/Study/spare/java/DeepInJava/out/production/rawjava/");
            Class<?> aClass = cloader.loadClass("MyManager");
            Method logic = aClass.getMethod("logic");
            Object o = aClass.newInstance();
            logic.invoke(o);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
