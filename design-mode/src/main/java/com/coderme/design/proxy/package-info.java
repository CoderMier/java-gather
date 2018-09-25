/**
 * @author qiudm
 * @date 2018/9/25 18:05
 * @desc 代理模式
 */
package com.coderme.design.proxy;
/**
 * 定义：代理模式给某一个对象提供一个代理对象，并由代理对象控制对原对象的引用。通俗的来讲代理模式就是我们生活中常见的中介。
 *
 *
 * 静态代理、动态代理：
 * 静态代理是由程序员创建或特定工具自动生成源代码，在对其编译。在程序员运行之前，代理类.class文件就已经被创建了。
 * 动态代理是在程序运行时通过反射机制动态创建的。
 *
 *
 * JDK动态代理是通过接口中的方法名，在动态生成的代理类中调用业务实现类的同名方法；
 * CGlib动态代理是通过继承业务类，生成的动态代理类是业务类的子类，通过重写业务方法进行代理；1
 * https://www.ibm.com/developerworks/cn/java/j-lo-proxy-pattern/
 *
 *
 */