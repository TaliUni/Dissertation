package org.jfm.views.fview;

import javax.swing.JTextArea;
import org.jfm.filesystems.JFMFile;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public class FView extends JTextArea {
  private JFMFile el;

  public FView() {
    this(null,false);
  }

  public FView(JFMFile el,boolean editable){
    setEl(el);
    setEditable(editable);
  }


  public JFMFile getEl() {
    return this.el;
  }

  private void calculateDirInfo(JFMFile f,DirInfo info){
    if(!f.isDirectory()){
        return;
    }
    JFMFile[] files=f.listFiles();
    if(files==null) return;
    for(int i=0;i<files.length;i++){
        if(files[i].isDirectory()){
          info.addDir();
          calculateDirInfo(files[i],info);
        }else{
           info.addFile(files[i].length());
        }
    }
  }

  public void setEl(JFMFile setEl) {
    this.el = setEl;
    if(el!=null && el.isDirectory()){
      if(isEditable()){
        return;
      }

      DirInfo info=new DirInfo();
      calculateDirInfo(el,info);
      this.setWrapStyleWord(true);
      String dirInfoText="Folder "+el.getName()+" has "+info.getNrOfFiles()+" files and "+info.getNrOfFolders()+" folders and "+
                          " occupies "+info.getTotalSize()+" bytes in total.";
     this.setText(dirInfoText);
      return;
    }

    if(el==null || el.length()<=0) return;
    try{
    StringBuffer text=new StringBuffer((int)el.length());
    java.io.InputStream in=el.getInputStream();
    byte[] data=new byte[(int)el.length()];
    @SuppressWarnings("unused")
    int read=0;
    while((read=in.read(data))>=0){
      text.append(new String(data));
    }
    this.setText(text.toString());
    this.setCaretPosition(0);

    }catch(Exception e){
      javax.swing.JOptionPane.showMessageDialog(this,"Could not open file","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
    }
  }

  private class DirInfo{
      int nrFiles=0;
      int nrFolders=0;
      long totalSize=0;

      public void addFile(long size){
          nrFiles++;
          totalSize+=size;
      }

      public void addDir(){
          nrFolders++;
      }

      public int getNrOfFiles(){
          return nrFiles;
      }

      public long getTotalSize(){
          return totalSize;
      }

      public int getNrOfFolders(){
        return nrFolders;
      }
  }
}
