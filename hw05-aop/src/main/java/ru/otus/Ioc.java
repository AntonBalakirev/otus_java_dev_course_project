package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {

    private Ioc() {
    }

    static LoggingInterface createLoggedClass() {
        InvocationHandler handler = new LoggerInvocationHandler(new LoggingImpl());
        return (LoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{LoggingInterface.class}, handler);
    }

    static class LoggerInvocationHandler implements InvocationHandler {
        private final LoggingInterface loggingInterface;

        LoggerInvocationHandler(LoggingInterface loggingInterface) {
            this.loggingInterface = loggingInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Log.class)) {
                System.out.println(
                        "executed method:" + method + ", " +
                                getParamsPlaceholder(args) + ": " + Arrays.toString(args)
                );
            }
            return method.invoke(loggingInterface, args);
        }

        private String getParamsPlaceholder(Object[] args) {
            if (args.length == 1) {
                return "param";
            } else {
                return "params";
            }
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + loggingInterface +
                    '}';
        }
    }
}
