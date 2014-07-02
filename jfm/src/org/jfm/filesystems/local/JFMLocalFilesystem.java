/*
 * Created on Jun 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.filesystems.local;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.filechooser.FileSystemView;

import org.jfm.filesystems.FSException;
import org.jfm.filesystems.JFMFile;
import org.jfm.filesystems.JFMFileSystem;

/**
 * TODO change me!!!
 * @author sergiu
 */
public class JFMLocalFilesystem extends JFMFileSystem {
	private FileSystemView view=FileSystemView.getFileSystemView();
		
	/**
	 *
	 */
	public JFMLocalFilesystem() {
		super();
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#getPathSeparator()
	 */
	public char getPathSeparator() {
		return File.pathSeparatorChar;
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#getSeparator()
	 */
	public char getSeparator() {
		return File.separatorChar;
	}

	private JFMFile[] getJFMArrayFromFiles(File[] files){		
		if(files==null) return null;		
		JFMFile[] jfmFiles=new JFMFile[files.length];
		for(int i=0;i<files.length;i++){
			try{
				jfmFiles[i]=new JFMLocalFile(files[i].getAbsolutePath());
			}catch(Exception ignored){
				ignored.printStackTrace();
			}
		}
		Arrays.sort(jfmFiles);
		return jfmFiles;
	}


	/**
	 * @see org.jfm.filesystems.JFMFileSystem#getDefaultRootDirectory()
	 */
	public JFMFile getDefaultRootDirectory(){		
		//return as the users default root dir the root dir of the users home dir				
		return getRootDriveFile(new JFMLocalFile(view.getHomeDirectory()));
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#listRoots()
	 */
	public JFMFile[] listRoots() {
		File[] roots=File.listRoots();
		return getJFMArrayFromFiles(roots);
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#listFiles()
	 */
	public JFMFile[] listFiles(JFMFile root) {
		File f=new File(root.getAbsolutePath());
		File[] localFiles=view.getFiles(f,true);
		return getJFMArrayFromFiles(localFiles);
	}


	/**
	 * @see org.jfm.filesystems.JFMFileSystem#listFiles(java.io.FilenameFilter)
	 */
	public JFMFile[] listFiles(JFMFile rootFile,FilenameFilter filter) {
		File f=new File(rootFile.getAbsolutePath());
		
		File[] localFiles=f.listFiles(filter);
		return getJFMArrayFromFiles(localFiles);
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#mkdir(org.jfm.filesystems.JFMFile)
	 */
	public boolean mkdir(JFMFile file) {
		File f=new File(file.getAbsolutePath());
		return f.mkdir();
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#mkdirs(org.jfm.filesystems.JFMFile)
	 */
	public boolean mkdirs(JFMFile file) {
		File f=new File(file.getAbsolutePath());
		return f.mkdirs();
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#delete(org.jfm.filesystems.JFMFile)
	 */
	public boolean delete(JFMFile file) {
		File f=new File(file.getAbsolutePath());
		return f.delete();
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#createNewFile(org.jfm.filesystems.JFMFile)
	 */
	public boolean createNewFile(JFMFile file) throws FSException {
		File f=new File(file.getAbsolutePath());
		try{
			return f.createNewFile();
		}catch(IOException ex){
			throw new FSException(ex.getMessage());
		}
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#setLastModified(org.jfm.filesystems.JFMFile, long)
	 */
	public boolean setLastModified(JFMFile file, long time) {
		File f=new File(file.getAbsolutePath());
		return f.setLastModified(time);
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#setReadOnly(org.jfm.filesystems.JFMFile)
	 */
	public boolean setReadOnly(JFMFile file) {
		File f=new File(file.getAbsolutePath());
		return f.setReadOnly();
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#canRead(org.jfm.filesystems.JFMFile)
	 */
	public boolean canRead(JFMFile file) {
		File f=new File(file.getAbsolutePath());
		return f.canRead();
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#canWrite(org.jfm.filesystems.JFMFile)
	 */
	public boolean canWrite(JFMFile file) {
		File f=new File(file.getAbsolutePath());
		return f.canWrite();
	}

	/**
	 * @see org.jfm.filesystems.JFMFileSystem#exists(org.jfm.filesystems.JFMFile)
	 */
	public boolean exists(JFMFile file) {
		File f=new File(file.getAbsolutePath());
		return f.exists();
	}

        /**
         * @see org.jfm.filesystems.JFMFileSystem#getStartDirectory()
         */
        public JFMFile getStartDirectory(){
          return getFile(System.getProperty("user.dir"));
        }

        /**
         * @see org.jfm.filesystems.JFMFileSystem#getFile(java.lang.String)
         */
        public JFMFile getFile(String pathName){
          return new JFMLocalFile(pathName);
        }

		@Override
		public boolean mkdir(JFMFile parent, String name) {
			File parentFile=new File(parent.getAbsolutePath(),name);			
			return parentFile.mkdir();
		}

		@Override
		public boolean createNewFile(JFMFile parent, String name) throws FSException {
			File f=new File(parent.getAbsolutePath(),name);
			try{
				return f.createNewFile();
			}catch(IOException ex){
				throw new FSException(ex.getMessage());
			}
		}
		
		@Override
		public boolean isLocal()
		{
			return true;		
		}
}
