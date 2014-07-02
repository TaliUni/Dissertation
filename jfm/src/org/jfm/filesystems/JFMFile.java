/*
 * Created on Jun 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.filesystems;

import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.Icon;

/**
 * This class is the base class of an File object. It is supposed to be implemented by every filesystem implementation.
 * The methods implemented by this class are very similar to those offered by the java.io.File class found in the JDK.
 * Even the comments are the same. Why? Because, IMHO, java.io.File represents a very good abstractisation of an file object
 * but I can't use it, because of the way its implemented. Or ... I haven't found a way to use it yet.
 * If somebody will point me to the proper documentation, i will be more than happy to study it, to see how can I improve my skills.
 * @author sergiu
 */
public abstract class JFMFile implements Comparable{

	/**
	 * This object holds the data required for the implementing file to initialize itself.
	 * In the case of an local file, the absolute pathname (String) would be required.
	 * In the case of other implementing filesystems, it may contain other stuff.
	 * Usualy it will be used by the implementing filesystem.
	 */
	protected Object data=null;

        /**
         * This parameter, if setted to a different value than <code>null</code> determines what the toString() method will return
         */
    protected String displayName=null;
    
    /**
    * This parameter is set to true when the file is marked in the view. 
    */
    protected boolean marked=false;

	/**
	 * Constructor.
	 */
	public JFMFile(Object data) {
		this.data=data;
	}

        /**
          * Returns an array of abstract pathnames denoting the files in the
          * directory denoted by this abstract pathname.
          *
          * <p> If this abstract pathname does not denote a directory, then this
          * method returns <code>null</code>.  Otherwise an array of
          * <code>File</code> objects is returned, one for each file or directory in
          * the directory.  Pathnames denoting the directory itself and the
          * directory's parent directory are not included in the result.  Each
          * resulting abstract pathname is constructed from this abstract pathname
          * using the <code>{@link #File(java.io.File, java.lang.String)
          * File(File,&nbsp;String)}</code> constructor.  Therefore if this pathname
          * is absolute then each resulting pathname is absolute; if this pathname
          * is relative then each resulting pathname will be relative to the same
          * directory.
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
        public abstract JFMFile[] listFiles();

       /**
	 * Returns an InputStream to read from this file, or null if it can't be done (the filesystem doesn't support it)
	 * @return an InputStream to read from the file
	 */
	public abstract InputStream getInputStream();

	/**
	 * Returns an OutputStream to write to this file, or null if the write operation isn't supported
	 * (or doesn;t make sense from the filesystem's point of view).
	 * The way that this is implemented in every filesystem can vary.
	 * The call to this method might get the file to be emptied (as in the LocalFileSystem implementation)
	 * You have been warned.
	 * @return An OutputStream to write to this file
	 */
	public abstract OutputStream getOutputStream();

	/**
	 * This string represents the short name of the file (e.g. foo.txt)
	 */
	public abstract String getName();

    /**
     * Returns the pathname string of this abstract pathname's parent, or
     * <code>null</code> if this pathname does not name a parent directory.
     *
     * <p> The <em>parent</em> of an abstract pathname consists of the
     * pathname's prefix, if any, and each name in the pathname's name
     * sequence except for the last.  If the name sequence is empty then
     * the pathname does not name a parent directory.
     *
     * @return  The pathname string of the parent directory named by this
     *          abstract pathname, or <code>null</code> if this pathname
     *          does not name a parent
     */
	public abstract String getParent();

    /**
     * Returns the abstract pathname of this abstract pathname's parent,
     * or <code>null</code> if this pathname does not name a parent
     * directory.
     *
     * <p> The <em>parent</em> of an abstract pathname consists of the
     * pathname's prefix, if any, and each name in the pathname's name
     * sequence except for the last.  If the name sequence is empty then
     * the pathname does not name a parent directory.
     *
     * @return  The abstract pathname of the parent directory named by this
     *          abstract pathname, or <code>null</code> if this pathname
     *          does not name a parent
     */
    public abstract JFMFile getParentFile();

    /**
     * Converts this abstract pathname into a pathname string.  The resulting
     * string uses the {@link #getSeparator() default name-separator character} to
     * separate the names in the name sequence.
     *
     * @return  The string form of this abstract pathname
     */
    public abstract String getPath();

    /**
     * Tests whether this abstract pathname is absolute.  The definition of
     * absolute pathname is system dependent.  On UNIX systems, a pathname is
     * absolute if its prefix is <code>"/"</code>.  On Microsoft Windows systems, a
     * pathname is absolute if its prefix is a drive specifier followed by
     * <code>"\\"</code>, or if its prefix is <code>"\\"</code>.
     * Some other filesystems implementations (FTP, NFS, SMB, etc.) can have their own definition
     * of the <i>absolute pathname</i>, ven though I would recommend sticking to the UNIX definition.
     *
     * @return  <code>true</code> if this abstract pathname is absolute,
     *          <code>false</code> otherwise
     */
    public abstract boolean isAbsolute();

    /**
     * Returns the absolute pathname string of this abstract pathname.
     *
     * <p> If this abstract pathname is already absolute, then the pathname
     * string is simply returned as if by the <code>{@link #getPath}</code>
     * method.  If this abstract pathname is the empty abstract pathname then
     * the pathname string of the current user directory, which is named by the
     * system property <code>user.dir</code>, is returned.  Otherwise this
     * pathname is resolved in a system-dependent way.  On UNIX systems, a
     * relative pathname is made absolute by resolving it against the current
     * user directory.  On Microsoft Windows systems, a relative pathname is made absolute
     * by resolving it against the current directory of the drive named by the
     * pathname, if any; if not, it is resolved against the current user
     * directory.
     *
     * @return  The absolute pathname string denoting the same file or
     *          directory as this abstract pathname
     *
     * @throws  SecurityException
     *          If a required system property value cannot be accessed.
     *
     * @see     org.jfm.filesystems.JFMFile#isAbsolute()
     */
    public abstract String getAbsolutePath();

    /**
     * Returns the absolute form of this abstract pathname.
     *
     * @return  The absolute abstract pathname denoting the same file or
     *          directory as this abstract pathname
     *
     * @throws  SecurityException
     *          If a required system property value cannot be accessed.
     */
    public abstract JFMFile getAbsoluteFile();

    /**
     * Returns the canonical pathname string of this abstract pathname.
     *
     * <p> A canonical pathname is both absolute and unique.  The precise
     * definition of canonical form is system-dependent.  This method first
     * converts this pathname to absolute form if necessary, as if by invoking the
     * {@link #getAbsolutePath} method, and then maps it to its unique form in a
     * system-dependent way.  This typically involves removing redundant names
     * such as <tt>"."</tt> and <tt>".."</tt> from the pathname, resolving
     * symbolic links (on UNIX platforms), and converting drive letters to a
     * standard case (on Microsoft Windows platforms).
     *
     * <p> Every pathname that denotes an existing file or directory has a
     * unique canonical form.  Every pathname that denotes a nonexistent file
     * or directory also has a unique canonical form.  The canonical form of
     * the pathname of a nonexistent file or directory may be different from
     * the canonical form of the same pathname after the file or directory is
     * created.  Similarly, the canonical form of the pathname of an existing
     * file or directory may be different from the canonical form of the same
     * pathname after the file or directory is deleted.
     *
     * @return  The canonical pathname string denoting the same file or
     *          directory as this abstract pathname
     *
     * @throws  FSException
     *          If an filesystem error occurs, which is possible because the
     *          construction of the canonical pathname may require
     *          filesystem queries
     *
     * @throws  SecurityException
     *          If a required system property value cannot be accessed.
     */
    public abstract String getCanonicalPath() throws FSException ;

    /**
     * Returns the canonical form of this abstract pathname.
     *
     * @return  The canonical pathname string denoting the same file or
     *          directory as this abstract pathname
     *
     * @throws  FSException
     *          If an filesystem error occurs, which is possible because the
     *          construction of the canonical pathname may require
     *          filesystem queries
     *
     * @throws  SecurityException
     *          If a required system property value cannot be accessed.
     */
    public abstract JFMFile getCanonicalFile() throws FSException;


    /**
     * Tests whether the file denoted by this abstract pathname is a
     * directory.
     *
     * @return <code>true</code> if and only if the file denoted by this
     *          abstract pathname exists <em>and</em> is a directory;
     *          <code>false</code> otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the file
     */
    public abstract boolean isDirectory() ;

    /**
     * Tests whether the file denoted by this abstract pathname is a normal
     * file.  A file is <em>normal</em> if it is not a directory and, in
     * addition, satisfies other system-dependent criteria.  Any non-directory
     * file created by a Java application is guaranteed to be a normal file.
     *
     * @return  <code>true</code> if and only if the file denoted by this
     *          abstract pathname exists <em>and</em> is a normal file;
     *          <code>false</code> otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the file
     */
    public abstract boolean isFile() ;

    /**
     * Tests whether the file named by this abstract pathname is a hidden
     * file.  The exact definition of <em>hidden</em> is system-dependent.  On
     * UNIX systems, a file is considered to be hidden if its name begins with
     * a period character (<code>'.'</code>).  On Microsoft Windows systems, a file is
     * considered to be hidden if it has been marked as such in the filesystem.
     *
     * @return  <code>true</code> if and only if the file denoted by this
     *          abstract pathname is hidden according to the conventions of the
     *          underlying platform
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the file
     */
    public abstract boolean isHidden() ;

    /**
     * Returns the time that the file denoted by this abstract pathname was
     * last modified.
     *
     * @return  A <code>long</code> value representing the time the file was
     *          last modified, measured in milliseconds since the epoch
     *          (00:00:00 GMT, January 1, 1970), or <code>0L</code> if the
     *          file does not exist or if an I/O error occurs
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the file
     */
    public abstract long lastModified() ;

    /**
     * Returns the length of the file denoted by this abstract pathname.
     * The return value is unspecified if this pathname denotes a directory.
     *
     * @return  The length, in bytes, of the file denoted by this abstract
     *          pathname, or <code>0L</code> if the file does not exist
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkRead(java.lang.String)}</code>
     *          method denies read access to the file
     */
    public abstract long length() ;

    /**
     * Compares two abstract pathnames lexicographically.  The ordering
     * defined by this method depends upon the underlying system.  On UNIX
     * systems, alphabetic case is significant in comparing pathnames; on Microsoft Windows
     * systems it is not.
     *
     * @param   pathname  The abstract pathname to be compared to this abstract
     *                    pathname
     *
     * @return  Zero if the argument is equal to this abstract pathname, a
     *		value less than zero if this abstract pathname is
     *		lexicographically less than the argument, or a value greater
     *		than zero if this abstract pathname is lexicographically
     *		greater than the argument
     */
    public abstract int compareTo(JFMFile pathname) ;

    /**
     * Compares this abstract pathname to another object.  If the other object
     * is an abstract pathname, then this function behaves like <code>{@link
     * #compareTo(JFMFile)}</code>.  Otherwise, it throws a
     * <code>ClassCastException</code>, since abstract pathnames can only be
     * compared to abstract pathnames.
     *
     * @param   o  The <code>Object</code> to be compared to this abstract
     *             pathname
     *
     * @return  If the argument is an abstract pathname, returns zero
     *          if the argument is equal to this abstract pathname, a value
     *          less than zero if this abstract pathname is lexicographically
     *          less than the argument, or a value greater than zero if this
     *          abstract pathname is lexicographically greater than the
     *          argument
     *
     * @throws  <code>ClassCastException</code> if the argument is not an
     *		abstract pathname
     *
     * @see     java.lang.Comparable
     */
    public int compareTo(Object o) {
    	return compareTo((JFMFile)o);
    }

    /**
     * Tests this abstract pathname for equality with the given object.
     * Returns <code>true</code> if and only if the argument is not
     * <code>null</code> and is an abstract pathname that denotes the same file
     * or directory as this abstract pathname.  Whether or not two abstract
     * pathnames are equal depends upon the underlying system.  On UNIX
     * systems, alphabetic case is significant in comparing pathnames; on Microsoft Windows
     * systems it is not. With other filesystems implementation ... it's their responsability
     * to decide wether it should be case sensitive or not.
     *
     * @param   obj   The object to be compared with this abstract pathname
     *
     * @return  <code>true</code> if and only if the objects are the same;
     *          <code>false</code> otherwise
     */
    public abstract boolean equals(Object obj) ;

    /**
     * Computes a hash code for this abstract pathname.  Because equality of
     * abstract pathnames is inherently system-dependent, so is the computation
     * of their hash codes.  On UNIX systems, the hash code of an abstract
     * pathname is equal to the exclusive <em>or</em> of its pathname string
     * and the decimal value <code>1234321</code>.  On Microsoft Windows systems, the hash
     * code is equal to the exclusive <em>or</em> of its pathname string,
     * convered to lower case, and the decimal value <code>1234321</code>.
     *
     * @return  A hash code for this abstract pathname
     */
    public abstract int hashCode() ;

    /**
     * If <code>getDisplayName()</code> return null it  returns the pathname string of this abstract pathname.
     * This is just the string returned by the <code>{@link #getPath}</code> method.
     * Otherwise it just returns the displayName.
     * @return  The string form of this abstract pathname
     */
    public String toString() {
      if(getDisplayName()==null){
        return getName();
      }else{
        return getDisplayName();
      }
    }
    
    /**
     * Returns the system display name as returned by the filesystem viewer, or null
     * @return
     */
    public abstract String getSystemDisplayName();

    /**
     * This method returns the string that represents the mime-type of this file, or null if it can't be determined.
     * @return the mime type of this file
     */
    public abstract String getMimeType();


    /**
     * This method returns the root directory of the filesystem of witch the file belongs to.
     * For example:<br>
     *       <p> If the active filesystem is the local filesystem and is an Windows filesystem, this
     * method should return the drive letter of the drive that this file belongs to. On Unix filesystems it will always return
     * '/'. On other filesystems .... it will return whatever the one that implements it wants to.
     * @return JFMFile the root drive (it cannot be null). It can be the same file that was passed as an argument.
     */
    public JFMFile getRootDriveFile(){
      JFMFile parent=getParentFile();
      if(parent==null) return this;

      return parent.getRootDriveFile();

    }

    /**
     * Returns the display name
     * @return String the display name
     */
    public String getDisplayName() {
      return displayName;
    }

    /**
     * Sets the display name
     * @param displayName String the new display name
     */
    public void setDisplayName(String displayName) {
      this.displayName = displayName;
    }

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
    public abstract boolean canRead();

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
    public abstract boolean canWrite() ;

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
    public abstract boolean exists();

    /**
     * Creates the directory named by the abstract pathname denoted by the parameter.
     *
     * @param file The file that denotes the abstract pathname
     *
     * @return  The new file object created or null otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *          method does not permit the named directory to be created
     */
    public abstract JFMFile mkdir(String name) ;
    
    
    /**
     * Creates the file named by the abstract pathname denoted by the parameter.
     *
     * @param file The file that denotes the abstract pathname
     *
     * @return  The new file object created or null otherwise
     *
     * @throws  SecurityException
     *          If a security manager exists and its <code>{@link
     *          java.lang.SecurityManager#checkWrite(java.lang.String)}</code>
     *          method does not permit the named directory to be created
     */
    public abstract JFMFile createFile(String name);

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
    public abstract boolean delete() ;

    /**
     * Returns the filesystem's icon associated with this file, or null if it can't be determined.
     * @return The filesystem's icon associated with this file, or null if it can't be determined.
     */
    public abstract Icon getIcon();
    
    /**
     * Returns true if the file has been marked in the view, false otherwise
     * @return true if the file has been marked in the view, false otherwise
     */
    public boolean isMarked(){
    	return marked;
    }
    
    /**
     * Sets the marked flag. This method is usually called by the UI View to signal that the file 
     * has been marked for selection. It helps to draw the file name with another color, and to 
     * perform operations on the file.
     * @param flag 
     */
    public void setMarked(boolean flag){
    	marked=flag;
    }
}
