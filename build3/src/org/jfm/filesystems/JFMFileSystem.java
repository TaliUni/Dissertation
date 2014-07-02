/*
 * Created on Jun 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.filesystems;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.jfm.main.JFMClassLoader;
import org.xml.sax.SAXException;




/**
 * This class represents the base class for all filesystems.
 * @author sergiu
 */
public abstract class JFMFileSystem {

	//this is the default filesystem used
	private static final String DEFAULT_FILESYSTEM="org.jfm.filesystems.local.JFMLocalFilesystem";

	//this Hashtable holds the registered filesystems
	private static Hashtable<String,String> registeredFilesystems=new Hashtable<String,String>();

	static{
		//we always have the local filesystem available
		registeredFilesystems.put("Local",DEFAULT_FILESYSTEM);
	}
	
	/**
	 * Empy constructor.
	 */
	public JFMFileSystem() {

	}
	
	public static Hashtable<String,String>  getRegisteredFilesystems()
	{
		return registeredFilesystems; 
	}

  /**
   * This method searches the java path and the plugins directory (a subdirectory of the current directory)
   * for jar's that contain a filesystem plugin.
   * A jar that contains a filesystem plugin, is a jar file that has in its root directory a file named filesystem.xml
   * in which it describes the filesystem class used and its friendly name (to be shown in the menu)
   */
  public static void registerFilesystems()
  {
	  //java.class.path = the system property that specifies the list of jars in the class path
	  String classpath=System.getProperty("java.class.path");
	  if(classpath!=null && classpath.length()>0)
	  {
		  StringTokenizer tokenizer=new StringTokenizer(classpath,System.getProperty("path.separator"));
		  while(tokenizer.hasMoreTokens())
		  {
			  String nextJarFile=tokenizer.nextToken();
			  //we're only interested in this file if it ends with a .jar and if it exists
			  try{
				  java.io.File f=new java.io.File(nextJarFile);
				  if(!(nextJarFile.endsWith("jar") && f.exists() && f.isFile())) continue;
				  registerFilesystem(f);
			  }catch(Exception ignored){
				  ignored.printStackTrace();
			  }
		  }
	  }
	  //no search the plugins directory
	  File dir=new File("plugins/");
	  String full=dir.getAbsolutePath();
	  if(dir.isDirectory() && dir.exists())
	  {
		  File[] files=dir.listFiles();
		  if(files==null) return;
		  for(int i=0;i<files.length;i++)
		  {
			  if(!files[i].isFile() || !files[i].getName().endsWith("jar")) continue;
			  try{
				  registerFilesystem(files[i]);
			  }catch(Exception ignored)
			  {
				  ignored.printStackTrace();
			  }
		  }
	  }
  }
	
  private static void registerFilesystem(File f) throws ParserConfigurationException,SAXException,IOException{
	  java.util.jar.JarFile jarFile=new java.util.jar.JarFile(f,false,java.util.zip.ZipFile.OPEN_READ);
	  ZipEntry entry=jarFile.getEntry("filesystem.xml");
	  if(entry==null)//didnt found it 
	  {
		  return;
	  }
	  //found it
	  
	System.out.println("found "+f+" to be a filesystem plugin");	  
	javax.xml.parsers.SAXParserFactory saxFactory=javax.xml.parsers.SAXParserFactory.newInstance();
	SAXParser parser=saxFactory.newSAXParser();
	FilesystemXMLHandler handler=new FilesystemXMLHandler();
	parser.parse(jarFile.getInputStream(entry), handler);
	String name=handler.getName();
	String theClass=handler.getTheClass();
	registeredFilesystems.put(name,theClass);	
	java.util.List<String> dependencies=handler.getDependencies();
	for(int i=0;i<dependencies.size();i++)
	{
		org.jfm.main.JFMClassLoader.addAditionalJar(dependencies.get(i));
	}
  }

	/**
	 * This method searches through the registered filesystems, loads and returns the requested one.
	 * Fielsystems are represented in the vector as full qualified names of the class that represents them.
	 * @param name The name of the requested filesystem, or null if you accept the default one
	 * @return The requested filesystem instance
	 */
	public static JFMFileSystem createFileSystem(String name){
		JFMFileSystem fs=null;
		if(name==null || name.length()<=0){
			name=DEFAULT_FILESYSTEM;
		}

		Iterator<String> fsCollection=registeredFilesystems.values().iterator();
		
		while(fsCollection.hasNext()){
			String fsClassName=fsCollection.next();
			if(name.equals(fsClassName)){
				try {
					Class fsClass=Class.forName(fsClassName,true,JFMClassLoader.getLoader());
					fs=(JFMFileSystem)fsClass.newInstance();
					return fs;
				} catch (Exception e) {
					e.printStackTrace();
					//ignore the exception
				}
			}
		}

		if(fs==null){
			name=null;
			try {
				Class fsClass=Class.forName(DEFAULT_FILESYSTEM);
				fs=(JFMFileSystem)fsClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				//ignore the exception
			}
		}

		return fs;
	}

	/**
	 * The default root directory in the case of filesystems with more than one root directories (like windows)
	 * @return The default root directory
	 */
	public abstract JFMFile getDefaultRootDirectory();

    /**
     * The system-dependent path-separator character.  This field is
     * initialized to contain the first character of the value of the system
     * property <code>path.separator</code>.  This character is used to
     * separate filenames in a sequence of files given as a <em>path list</em>.
     * On UNIX systems, this character is <code>':'</code>; on Microsoft Windows systems it
     * is <code>';'</code>.
     */
    public abstract char getPathSeparator();

    /**
     * The system-dependent path-separator character, represented as a string
     * for convenience.  This string contains a single character, namely
     * <code>{@link #getPathSeparator()}</code>.
     */
    public String getPathSeparatorString(){
    	return ""+getPathSeparator();
    }


    /**
     * The system-dependent default name-separator character.  This field is
     * initialized to contain the first character of the value of the system
     * property <code>file.separator</code>.  On UNIX systems the value of this
     * field is <code>'/'</code>; on Microsoft Windows systems it is <code>'\'</code>.
     */
    public abstract char getSeparator();

    /**
     * The system-dependent default name-separator character, represented as a
     * string for convenience.  This string contains a single character, namely
     * <code>{@link #getSeparator()}</code>.
     */
    public String getSeparatorString(){
    	return ""+getSeparator();
    }


    /**
     * List the available filesystem roots.
     * @see java.io.File#listRoots()
     */
    public abstract JFMFile[] listRoots();

    /**
     * Returns an array of abstract pathnames denoting the files in the
     * directory denoted by the abstract pathname.
     *
     * <p> If this abstract pathname does not denote a directory, then this
     * method returns <code>null</code>.  Otherwise an array of
     * <code>File</code> objects is returned, one for each file or directory in
     * the directory.  Pathnames denoting the directory itself and the
     * directory's parent directory are not included in the result.
     *
     * <p> There is no guarantee that the name strings in the resulting array
     * will appear in any specific order; they are not, in particular,
     * guaranteed to appear in alphabetical order.
     *
     * @return  An array of abstract pathnames denoting the files and
     *          directories in the directory denoted by this abstract
     *          pathname.  The array will be empty if the directory is
     *          empty.  Returns <code>null</code> if this abstract pathname
     *          does not denote a directory, or if an I/O error occurs.
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the directory
     */
    public abstract JFMFile[] listFiles(JFMFile rootFile);

    /**
     * Returns an array of abstract pathnames denoting the files and
     * directories in the directory denoted by this abstract pathname that
     * satisfy the specified filter.  The behavior of this method is the
     * same as that of the <code>{@link #listFiles()}</code> method, except
     * that the pathnames in the returned array must satisfy the filter.
     * If the given <code>filter</code> is <code>null</code> then all
     * pathnames are accepted.  Otherwise, a pathname satisfies the filter
     * if and only if the value <code>true</code> results when the
     * <code>{@link FilenameFilter#accept}</code> method of the filter is
     * invoked on this abstract pathname and the name of a file or
     * directory in the directory that it denotes.
     *
     * @param  filter  A filename filter
     *
     * @return  An array of abstract pathnames denoting the files and
     *          directories in the directory denoted by this abstract
     *          pathname.  The array will be empty if the directory is
     *          empty.  Returns <code>null</code> if this abstract pathname
     *          does not denote a directory, or if an I/O error occurs.
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the directory
     *
     */
    public abstract JFMFile[] listFiles(JFMFile rootFile,java.io.FilenameFilter filter) ;


    /**
     * Creates the directory named by the abstract pathname denoted by the parameter.
     *
     * @param parent The parent file that denotes the abstract pathname
     * @param name The name of the new dir
     *
     * @return  <code>true</code> if and only if the directory was
     *          created; <code>false</code> otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *          method does not permit the named directory to be created
     */
    public abstract boolean mkdir(JFMFile parent,String name);

    /**
     * Deletes the file or directory denoted by the abstract pathname.  If
     * this pathname denotes a directory, then the directory must be empty in
     * order to be deleted.
     *
     * @param file The file that denotes the abstract pathname
     *
     * @return  <code>true</code> if and only if the file or directory is
     *          successfully deleted; <code>false</code> otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkDelete}</code> method denies
     *          delete access to the file
     */
    public boolean delete(JFMFile file){
      return file.delete();
    }

    /**
     * Atomically creates a new, empty file named by the abstract pathname if
     * and only if a file with that name does not yet exist.  The check for the
     * existence of the file and the creation of the file if it does not exist
     * are a single operation that is atomic with respect to all other
     * filesystem activities that might affect the file.
     *
     * @param file The file that denotes the abstract pathname
     *
     * @return  <code>true</code> if the named file does not exist and was
     *          successfully created; <code>false</code> if the named file
     *          already exists
     *
     * @throws  FSException
     *          If an filesystem error occurred
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *          method denies write access to the file
     *
     */
    public abstract boolean createNewFile(JFMFile parent,String name) throws FSException ;

    /**
     * Sets the last-modified time of the file or directory named by the
     * abstract pathname.
     *
     * <p> All platforms support file-modification times to the nearest second,
     * but some provide more precision.  The argument will be truncated to fit
     * the supported precision.  If the operation succeeds and no intervening
     * operations on the file take place, then the next invocation of the
     * <code>{@link org.jfm.filesystems.JFMFile#lastModified}</code> method will return the (possibly
     * truncated) <code>time</code> argument that was passed to this method.
     *
     * @param file The file that denotes the abstract pathname
     *
     * @param  time  The new last-modified time, measured in milliseconds since
     *               the epoch (00:00:00 GMT, January 1, 1970)
     *
     * @return <code>true</code> if and only if the operation succeeded;
     *          <code>false</code> otherwise
     *
     * @throws  IllegalArgumentException  If the argument is negative
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *          method denies write access to the named file
     */
    public abstract boolean setLastModified(JFMFile file,long time) ;

    /**
     * Marks the file or directory named by the abstract pathname so that
     * only read operations are allowed.  After invoking this method the file
     * or directory is guaranteed not to change until it is either deleted or
     * marked to allow write access.  Whether or not a read-only file or
     * directory may be deleted depends upon the underlying system.
     *
     * @param file The file that denotes the abstract pathname
     *
     * @return <code>true</code> if and only if the operation succeeded;
     *          <code>false</code> otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *          method denies write access to the named file
     */
    public abstract boolean setReadOnly(JFMFile file) ;

    /**
     * Tests whether the application can read the file denoted by the
     * abstract pathname.
     *
     * @param file The file that denotes the abstract pathname
     *
     * @return  <code>true</code> if and only if the file specified by this
     *          abstract pathname exists <em>and</em> can be read by the
     *          application; <code>false</code> otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the file
     */
    public boolean canRead(JFMFile file){
      return file.canRead();
    }

    /**
     * Tests whether the application can modify to the file denoted by the
     * abstract pathname.
     *
     * @param file The file that denotes the abstract pathname
     *
     * @return  <code>true</code> if and only if the file system actually
     *          contains a file denoted by this abstract pathname <em>and</em>
     *          the application is allowed to write to the file;
     *          <code>false</code> otherwise.
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *          method denies write access to the file
     */
    public boolean canWrite(JFMFile file) {
      return file.canWrite();
    }

    /**
     * Tests whether the file or directory denoted by the abstract pathname
     * exists.
     *
     * @param file The file that denotes the abstract pathname
     *
     * @return  <code>true</code> if and only if the file or directory denoted
     *          by this abstract pathname exists; <code>false</code> otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the file or directory
     */
    public boolean exists(JFMFile file){
      return file.exists();
    }


    /**
     * This method returns the root directory of the filesystem of witch the supplied file belongs to.
     * For example:<br>
     *       <p> If the active filesystem is the local filesystem and is an Windows filesystem, this
     * method should return the drive letter of the drive that this file belongs to. On Unix filesystems it will always return
     * '/'. On other filesystems .... it will return whatever the one that imlements it wants to.
     * @param file JFMFile the file  whoses root we want to find out.
     * @return JFMFile the root drive (it cannot be null). It can be the same file that was passed as an argument.
     */
    public JFMFile getRootDriveFile(JFMFile file){
      JFMFile parent=file.getParentFile();
      if(parent==null) return file;

      return getRootDriveFile(parent);
    }

    /**
     * This method returns the start directory for this filesystem
     * @return JFMFile the start directory (for example, on the local filesystem it will be the user directory)
     */
    public abstract JFMFile getStartDirectory();

    /**
     * This method returns an JFMFile object based on the pathname provided
     * @param pathName String
     * @return JFMFile
     */
    public abstract JFMFile getFile(String pathName);
    
    /**
     * Tells us if the filesystem is local. This information may be used for some optimizations in some cases
     * @return true is the filesystem is a local filesystem (hard-disk) false otherwise (a network one)
     */
    public boolean isLocal()
    {
    	//return false by default. let others to override this
    	return false;
    }
    
}
