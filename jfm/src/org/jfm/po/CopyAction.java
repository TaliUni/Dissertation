package org.jfm.po;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import org.jfm.main.Options;
import org.jfm.po.copy.*;
import org.jfm.event.*;

import org.jfm.filesystems.JFMFile;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class CopyAction implements Action {

  private long totalFilesSizes=0;
  private long totalBytesWritten=0;
  private boolean overwriteAll=false;
  private boolean skipAll=false;
  private boolean cancel=false;
  private ProgressActionDialog progress=null;
  private JFMFile curentlyCopiedFile;

  public CopyAction() {
  }
  private boolean enabled=true;
  public Object getValue(String key) {
    return null;
  }
  public void putValue(String key, Object value) {
  }

  public void setEnabled(boolean b) {
    enabled=b;
  }
  public boolean isEnabled() {
    return enabled;
  }
  public void addPropertyChangeListener(PropertyChangeListener listener) {
  }
  public void removePropertyChangeListener(PropertyChangeListener listener) {
  }

  public void actionPerformed(ActionEvent e) {
     //the vectors should never be null, and should contain at least one element
     //it's the views bussiness to do this and THEY MUST DO THIS,unless it could get ugly

    final JFMFile[] filesToBeCopied =Options.getActivePanel().getSelectedFiles();
    if(filesToBeCopied==null || filesToBeCopied.length==0){
      return;
    }
    
    final JFMFile destinationDir=Options.getInactivePanel().getCurrentWorkingDirectory();


    CopyConfirmDialog d=new CopyConfirmDialog(Options.getMainFrame(),"Copy",true);
    d.setCopyFrom(filesToBeCopied[0].getPath());

    d.setCopyTo(destinationDir.getPath());
    d.setLocationRelativeTo(Options.getMainFrame());
    d.setVisible(true);
    if(d.isCancelled()) return;

//    Vector f=new Vector();
//    for(int i=indexes[0];i<=indexes[1];i++){
//      if(i==0) continue;
//      f.addElement(filesToBeCopied.elementAt(i));
//    }

    //filesToBeCopied=f;

    progress=new ProgressActionDialog(Options.getMainFrame(),"Copy",true);
    progress.setLocationRelativeTo(Options.getMainFrame());

    progress.startAction(new ActionExecuter(){
      public void start(){
        try {
          copyFiles(filesToBeCopied,destinationDir);
        }
        catch (ActionCancelledException ex) {
        	ex.printStackTrace();
          this.cancel();
        }

        ChangeDirectoryEvent ev=new ChangeDirectoryEvent();
        ev.setSource(CopyAction.this);
        ev.setDirectory(destinationDir);
        Broadcaster.notifyChangeDirectoryListeners(ev);
      }

      public void cancel(){
    	//  System.out.println("cancel was called by:");
//    	  Thread.dumpStack();
        cancel=true;
      }
    });

  }

  private long getFilesSize(JFMFile[] files){
    long totalSizes=0;
    for(int i=0;i<files.length;i++){
      JFMFile f=(JFMFile)files[i];
      if(f.isDirectory()){
        totalSizes+=getFilesSize(f.listFiles());
      }else{
        totalSizes+=f.length();
      }
    }
    return totalSizes;
  }

  private void copyDir(JFMFile dir,JFMFile dest) throws ActionCancelledException{
    JFMFile[] f=dir.listFiles();
    if(f==null) return;
    //File destFile=new File(toDir.getPath()+(toDir.getPath().endsWith(File.separator)?"":File.separator)+el.getName());
    for(int i=0;i<f.length;i++){    	      
      if(f[i].isDirectory()){
        JFMFile destFile=dest.mkdir(f[i].getName());
        copyDir(f[i],destFile);
      }else{
    	JFMFile destFile=dest.createFile(f[i].getName());
        copyFile(f[i],destFile);
      }
    }
  }

  private void copyFile(JFMFile fin,JFMFile fout) throws ActionCancelledException {
    if(fout.exists() && !overwriteAll && !skipAll){

      java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("EEE, MMM d, yyyy 'at' hh:mm:ss");

      String message="Target file "+fout.getPath()+"  already exists."+System.getProperty("line.separator")+System.getProperty("line.separator")+
                     "Source last modified date: "+format.format(new java.util.Date(fin.lastModified()))+System.getProperty("line.separator")+
                     "Target last modified date: "+format.format(new java.util.Date(fout.lastModified()))+System.getProperty("line.separator")+System.getProperty("line.separator")+
                     "What should I do?";
      String[] buttons=new String[]{"Overwrite","Overwrite all","Skip","Skip all","Cancel"};

      int result=JOptionPane.showOptionDialog(progress,message,"File exists",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,buttons,buttons[2]);

      switch (result){
        case 0:
          break;
        case 1:
          overwriteAll=true;
          break;
        case 2:
          totalBytesWritten+=fin.length();          
          int t_percent=totalFilesSizes!=0?(int)((totalBytesWritten*100)/totalFilesSizes):0;
          progress.setTotalProgresssValue(t_percent);
          return;
        case 3:
          skipAll=true;
          break;
          case 4:
            throw new ActionCancelledException();
      }
    }

    if(fout.exists() && skipAll){
          totalBytesWritten+=fin.length();
          int t_percent=(int)((totalBytesWritten*100)/totalFilesSizes);
          progress.setTotalProgresssValue(t_percent);
          progress.setFileProgresssValue(100);
          return;
    }
      java.io.InputStream in=null;
      java.io.OutputStream out=null;
      try{
        in=fin.getInputStream();
        //WAS new FileOutputStream(fout.getPath(),append);
        out=fout.getOutputStream();
        progress.setFileProgresssValue(0);
        curentlyCopiedFile=fout;
        byte[] data=new byte[1024];
        int read=0;
        long bytesWrote=0;
        long f_length=fin.length();

        /**@todo Maybe async IO would be nice here**/
        while((read=in.read(data))>=0){
          if(cancel){
            throw new ActionCancelledException();
          }

          out.write(data,0,read);
          bytesWrote+=read;
          totalBytesWritten+=read;
          int f_percent=(int)((bytesWrote*100)/f_length);
          int t_percent=(int)((totalBytesWritten*100)/totalFilesSizes);
          progress.setFileProgresssValue(f_percent);
          progress.setTotalProgresssValue(t_percent);
        }
        progress.setFileProgresssValue(100);
      }catch(ActionCancelledException ex){
    	  ex.printStackTrace();
          curentlyCopiedFile.delete();
          throw ex;
      }catch(Exception ex){
        JOptionPane.showMessageDialog(progress,"Error while writing "+fout.getPath(),"Error",JOptionPane.ERROR_MESSAGE);
        curentlyCopiedFile.delete();
      }finally{
        try {
          in.close();          
        }
        catch (Exception ignored) {}
        try {
            out.close();
        }
        catch (Exception ignored) {}
      }
  }

  private void copyFiles(JFMFile[] filesToBeCopied,JFMFile destinationDir) throws ActionCancelledException{
    totalFilesSizes=getFilesSize(filesToBeCopied);
    if(filesToBeCopied.length==0) progress.dispose();
    for(int i=0;i<filesToBeCopied.length;i++){
      JFMFile el=(JFMFile)filesToBeCopied[i];      
      if(el.isDirectory()){
    	JFMFile destFile=destinationDir.mkdir(el.getName());
        //if(!destFile.exists()) destFile.mkdirs();
        copyDir(el,destFile);
      }else{
    	JFMFile destFile=destinationDir.createFile(el.getName());
        copyFile(el,destFile);
      }

    }
  }
}
