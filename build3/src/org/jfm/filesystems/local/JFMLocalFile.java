/*
 * Created on Jun 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.filesystems.local;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import org.jfm.filesystems.FSException;
import org.jfm.filesystems.JFMFile;

/**
 * TODO change me!!!
 * @author sergiu
 */
public class JFMLocalFile extends JFMFile {
	
	private File theFile;
	private FileSystemView view=FileSystemView.getFileSystemView();

	/**
	 * Constructor of this file.
	 */
	public JFMLocalFile(Object data) {
		super(data);
		initialize();
	}

	private void initialize(){		
		theFile=view.createFileObject((String)data);		
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getInputStream()
	 */
	public InputStream getInputStream(){
		java.io.FileInputStream in=null;
		try{
			in=new java.io.FileInputStream(theFile);
		}catch(IOException exc){
			exc.printStackTrace();
		}
		return in;
	}


        /**
         * @see org.jfm.filesystems.JFMFile#listFiles()
         */
        public JFMFile[] listFiles(){        	
          File[] files=view.getFiles(theFile,true);
          if(files==null){
            return null;
          }

          JFMFile[] jfmFiles=new JFMFile[files.length];
          for (int i = 0; i < files.length; i++) {
            jfmFiles[i]=new JFMLocalFile(files[i].getAbsolutePath());
          }

          return jfmFiles;
        }

	/**
	 * @see org.jfm.filesystems.JFMFile#getOutputStream()
	 */
	public OutputStream getOutputStream(){
		java.io.FileOutputStream out=null;
		try{
			out=new java.io.FileOutputStream(theFile);
		}catch(IOException exc){
			exc.printStackTrace();
		}
		return out;
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getName()
	 */
	public String getName() {		
		return theFile.getName();
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getParent()
	 */
	public String getParent() {
		return view.getParentDirectory(theFile).getName();
		//return theFile.getParent();
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getMimeType()
	 */
	public String getMimeType(){
		String mime=null;

		//if the file is a directory, return a null mimeType
		if(theFile.isDirectory()){
			return null;
		}

		try{
			mime=java.net.URLConnection.guessContentTypeFromStream(new java.io.FileInputStream(theFile));
		}catch(Exception exc){
			exc.printStackTrace();
		}

		if(mime==null){
			mime=java.net.URLConnection.guessContentTypeFromName(theFile.getName());
		}
		System.out.println(mime);
		return mime;
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getParentFile()
	 */
	public JFMFile getParentFile() {
		if(view.isFileSystemRoot(theFile))
		{
			return null;
		}
		File parentFile=view.getParentDirectory(theFile);
		if(parentFile!=null){
			return new JFMLocalFile(parentFile.getAbsolutePath());
		}
		return null;
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getPath()
	 */
	public String getPath() {
		return theFile.getPath();
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#isAbsolute()
	 */
	public boolean isAbsolute() {
		return theFile.isAbsolute();
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getAbsolutePath()
	 */
	public String getAbsolutePath() {
		return theFile.getAbsolutePath();
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getAbsoluteFile()
	 */
	public JFMFile getAbsoluteFile() {
		return new JFMLocalFile(theFile.getAbsolutePath());
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getCanonicalPath()
	 */
	public String getCanonicalPath() throws FSException {
		try{
			return theFile.getCanonicalPath();
		}catch(IOException ex){
			throw new FSException(ex.getMessage());
		}
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#getCanonicalFile()
	 */
	public JFMFile getCanonicalFile() throws FSException {
		try{
			return new JFMLocalFile(theFile.getCanonicalPath());
		}catch(IOException ex){
			throw new FSException(ex.getMessage());
		}
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#isDirectory()
	 */
	public boolean isDirectory() {
		
		if(view.isFileSystem(theFile)){
			return theFile.isDirectory();
		}else{
			return true;
		}
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#isFile()
	 */
	public boolean isFile() {
		return theFile.isFile();
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#isHidden()
	 */
	public boolean isHidden() {
		return theFile.isHidden();
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#lastModified()
	 */
	public long lastModified() {
		return theFile.lastModified();
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#length()
	 */
	public long length() {
		return theFile.length();
	}

	/**
	 * @see org.jfm.filesystems.JFMFile#compareTo(org.jfm.filesystems.JFMFile)
	 */
	public int compareTo(JFMFile pathname) {
		
		FileSystemView view=FileSystemView.getFileSystemView();
		
		if(view.isDrive(new File(pathname.getAbsolutePath()))){
			//prevent a *thing* in windows, that when the isDirectory method is called for a floppy drive
			//an error pops up from javaw. it probably happens the same for a ZIP drive, but i don't have one, so ...
			return this.toString().compareTo(pathname.toString());
		}else{
			if(this.isDirectory() && !pathname.isDirectory()) return -1;

	    	if(!this.isDirectory() && pathname.isDirectory()) return 1;

	    	return this.toString().compareTo(pathname.toString());
		}
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		boolean equals=theFile.equals(new File(((JFMFile)obj).getAbsolutePath()));
		return equals;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return theFile.hashCode();
	}

        /**
         * @see org.jfm.filesystems.JFMFile#canRead()
         */
        public boolean canRead(){
          return theFile.canRead();
        }

        /**
         * @see org.jfm.filesystems.JFMFile#canWrite()
         */
      public boolean canWrite(){
        return theFile.canWrite();
      }

      /**
       * @see org.jfm.filesystems.JFMFile#exists()
       */
      public boolean exists(){
        return theFile.exists();
      }

      /**
       * @see org.jfm.filesystems.JFMFile#mkdir()
       */
      public JFMFile mkdir(String name) {
        File newdir= new File(theFile,name);
        if(newdir.mkdir()||newdir.exists())
        {
        	return new JFMLocalFile(newdir.getAbsolutePath());
        }
        else
        {
        	return null;
        }
      }

      public JFMFile createFile(String name)
      {
    	 File newfile= new File(theFile,name);
    	 return new JFMLocalFile(newfile.getAbsolutePath());
      }
      /**
       * @see org.jfm.filesystems.JFMFile#delete()
       */
      public boolean delete() {
        return theFile.delete();
      }
      
      /**
       * @see org.jfm.filesystems.JFMFile#getIcon()
       */
      public Icon getIcon(){
      	Icon icon=null;
      	//if we're an UP directory, then return null, otherwise ... ask the filesystem.
      	if(!"..".equals(this.getDisplayName())){     			
	      		icon=view.getSystemIcon(theFile);
      	}
      	return icon;
      }

	public String getSystemDisplayName() {
		return FileSystemView.getFileSystemView().getSystemDisplayName(theFile);
	}
}
