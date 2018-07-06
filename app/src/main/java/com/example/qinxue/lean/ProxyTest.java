package com.example.qinxue.lean;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by qinxue on 2017/10/26.
 */

public class ProxyTest {
    public interface Subject {
        void doSomething();
    }

    static class RealSubject implements Subject {
        public void doSomething() {
            Log.i("qinxue", "doSomething()");
        }
    }

    static class ProxyHandler implements InvocationHandler {
        private Object proxied;

        public ProxyHandler(Object proxied) {
            this.proxied = proxied;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //在转调具体目标对象之前，可以执行一些功能处理

            //转调具体目标对象的方法
            Log.i("qinxue", "动态代理执行前");
            Object result = method.invoke(proxied, args);
            Log.i("qinxue", "动态代理执行后");
            return result;

            //在转调具体目标对象之后，可以执行一些功能处理
        }
    }
}
