package com;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

public class ReloadMyClass {

	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException,
			NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {

		Class<?> myClass = ClassLoader.getSystemClassLoader().loadClass("com.Simple");
		System.out.println("The timer Started");
		myClass.newInstance().toString();
		URL[] urls = { myClass.getProtectionDomain().getCodeSource().getLocation() };
		ClassLoader delegateParent = myClass.getClassLoader().getParent();
		Date d1 = new Date();
		long time = d1.getTime();
		int repeat = 0;
		for (;;) {
			if (repeat == 1 && ((new Date().getTime() / 1000) - (time / 1000)) != 10) {
				time = new Date().getTime();
				repeat = 0;
			}
			if (((new Date().getTime() / 1000) - (time / 1000)) == 10 && (repeat == 0)) {
				// System.out.println(new Date().getTime()/1000);
				try (URLClassLoader cl = new URLClassLoader(urls, delegateParent)) {
					Class<?> reloaded = cl.loadClass(myClass.getName());
					System.out.println("10 sec passed-Reloading the class");
					reloaded.newInstance().toString();
					
					repeat = 1;
				}
			}
		}
	}
}
