package org.jfm.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * This class loader is used to load the classes that are requested by plugins's additional libraries
 * @author sergiu
 */
public class JFMClassLoader extends ClassLoader {

	private static List<String> jarsList=new ArrayList<String>();
	private static JFMClassLoader theLoader=null;
	
	public static JFMClassLoader getLoader()
	{
		if(theLoader==null) theLoader=new JFMClassLoader();
		return theLoader;
	}
	
	public static void addAditionalJar(String jarFile)
	{
		File f=new File(jarFile);
		if(!(f.exists() && f.isFile() && f.canRead()))
		{
			return;
		}
		if(jarsList.contains(jarFile)) return;
		jarsList.add(jarFile);
	}
	
	/*protected synchronized Class<?> loadClass(String name, boolean resolve) 
    throws ClassNotFoundException
	{
		Class loadedClass=null;
		loadedClass = findLoadedClass (name);
		if (loadedClass == null ) {
		  try {
		    loadedClass = findSystemClass (name);
		  } catch (Exception e) {
		    // Ignore these
		  }
		}		
		
		if(loadedClass!=null) return loadedClass;
		
		System.out.println("Searching for "+name);

		//the name gets passed to us in the form: java.lang.String, we have to make it: java/lang/String
		String className=name.replace('.','/')+".class";
		//System.out.println(className);
		for(int i=0;i<jarsList.size();i++)
		{
			try {
				JarFile jarFile=new JarFile(jarsList.get(i));
				ZipEntry entry=jarFile.getEntry(className);
				if(entry==null) continue;
				InputStream classStream=jarFile.getInputStream(entry);
				ByteArrayOutputStream outStr=new ByteArrayOutputStream();
				byte[] data=new byte[1024];
				int read=0;
				while((read=classStream.read(data))>=0){
					outStr.write(data,0,read);
				}
				byte[] theClass=outStr.toByteArray();
				loadedClass=defineClass(name, theClass, 0, theClass.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(loadedClass==null){
			//if we are STILL null, then ... Houston, we've got a problem
			throw new ClassNotFoundException("Can't find "+name);
		}
		
		if (resolve)
			resolveClass (loadedClass);		
		return loadedClass;		
	}*/
	
	/* (non-Javadoc)
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
	public Class<?> findClass(String name) throws ClassNotFoundException {
		Class loadedClass=null;
		//System.out.println("Searching for "+name);

		//the name gets passed to us in the form: java.lang.String, we have to make it: java/lang/String
		String className=name.replace('.','/')+".class";
		//System.out.println(className);
		for(int i=0;i<jarsList.size();i++)
		{
			try {
				JarFile jarFile=new JarFile(jarsList.get(i));
				ZipEntry entry=jarFile.getEntry(className);
				if(entry==null) continue;
				InputStream classStream=jarFile.getInputStream(entry);
				ByteArrayOutputStream outStr=new ByteArrayOutputStream();
				byte[] data=new byte[1024];
				int read=0;
				while((read=classStream.read(data))>=0){
					outStr.write(data,0,read);
				}
				byte[] theClass=outStr.toByteArray();
				loadedClass=defineClass(name, theClass, 0, theClass.length);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(loadedClass==null){
			//if we are STILL null, then ... Houston, we've got a problem
			throw new ClassNotFoundException("Can't find "+name);
		}
		return loadedClass;
	}
		
}
