package org.jfm.po.copy;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.Vector;
import org.jfm.filesystems.JFMFile;
import java.io.*;

/**
 * Title:        Java File Manager
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class CopyProgressDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private JLabel fileCopyLabel = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JProgressBar fileCopyProgressBar = new JProgressBar();
  private JProgressBar totalCopyProgressBar = new JProgressBar();
  private JLabel statusLabel = new JLabel();
  private JPanel buttonsPanel = new JPanel();
  private JButton cancelButton = new JButton();
  private JPanel progressPanel=new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private Vector filesToCopy=new Vector();
  @SuppressWarnings("unused")
  private JFMFile toDir=null;
  private long totalFilesSizes=0;
  private long totalBytesWritten=0;
  private boolean overwriteAll=false;
  private boolean skipAll=false;
  private boolean cancel=false;
  private Thread copyThread;

  public void addFileToCopy(JFMFile el){
    filesToCopy.addElement(el);
  }

  public void setDirToCopy(JFMFile el){
    toDir=el;
  }

  private long getFilesSize(java.util.List l){
    long totalSizes=0;
    for(int i=0;i<l.size();i++){
      File f=(File)l.get(i);
      if(f.isDirectory()){
        totalSizes+=getFilesSize(java.util.Arrays.asList(f.listFiles()));
      }else{
        totalSizes+=f.length();
      }
    }
    return totalSizes;
  }

  public void startCopy(){
    copyThread=new Thread(new Runnable(){
      public void run(){
        try{
          copyFiles();
        }catch(CopyCancelledException ex){}
      }
    });
    copyThread.start();
    this.setVisible(true);
  }

  private void copyDir(JFMFile dir,JFMFile dest) throws CopyCancelledException{
    JFMFile[] f=dir.listFiles();
    if(f==null) return;
    //File destFile=new File(toDir.getPath()+(toDir.getPath().endsWith(File.separator)?"":File.separator)+el.getName());
    for(int i=0;i<f.length;i++){
      JFMFile destFile=null;//new File(dest.getPath()+(dest.getPath().endsWith(File.separator)?"":File.separator)+f[i].getName());
      if(f[i].isDirectory()){
       // destFile.mkdir();
        copyDir(f[i],destFile);
      }else{
        copyFile(f[i],destFile);
      }
    }
  }

  private void copyFile(JFMFile fin,JFMFile fout) throws CopyCancelledException {
    if(fout.exists() && !overwriteAll && !skipAll){

      java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("EEE, MMM d, yyyy 'at' hh:mm:ss");

      String message="Target file "+fout.getPath()+"  already exists."+System.getProperty("line.separator")+System.getProperty("line.separator")+
                     "Source last modified date: "+format.format(new java.util.Date(fin.lastModified()))+System.getProperty("line.separator")+
                     "Target last modified date: "+format.format(new java.util.Date(fout.lastModified()))+System.getProperty("line.separator")+System.getProperty("line.separator")+
                     "What should I do?";
      String[] buttons=new String[]{"Overwrite","Overwrite all","Skip","Skip all","Append"};
      int result=JOptionPane.showOptionDialog(this,message,"File exists",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,buttons,buttons[2]);
      switch (result){
        case 0:
          break;
        case 1:
          overwriteAll=true;
          break;
        case 2:
          totalBytesWritten+=fin.length();
          int t_percent=(int)((totalBytesWritten*100)/totalFilesSizes);
          totalCopyProgressBar.setValue(t_percent);
          return;
        case 3:
          skipAll=true;
          break;
          case 4:
            break;
      }
    }

    if(fout.exists() && skipAll){
          totalBytesWritten+=fin.length();
          int t_percent=(int)((totalBytesWritten*100)/totalFilesSizes);
          totalCopyProgressBar.setValue(t_percent);
          fileCopyProgressBar.setValue(100);
          return;
    }
      InputStream in=null;
      OutputStream out=null;
      try{
        in=fin.getInputStream();
        out=fout.getOutputStream();
        fileCopyProgressBar.setValue(0);
        byte[] data=new byte[1024];
        int read=0;
        long bytesWrote=0;
        long f_length=fin.length();

        while((read=in.read(data))>=0){
          if(cancel){
            throw new CopyCancelledException();
          }

          out.write(data,0,read);
          bytesWrote+=read;
          totalBytesWritten+=read;
          int f_percent=(int)((bytesWrote*100)/f_length);
          int t_percent=(int)((totalBytesWritten*100)/totalFilesSizes);
          fileCopyProgressBar.setValue(f_percent);
          totalCopyProgressBar.setValue(t_percent);
        }
        fileCopyProgressBar.setValue(100);
      }catch(Exception ex){
        if(ex instanceof CopyCancelledException) throw (CopyCancelledException)ex;
        JOptionPane.showMessageDialog(this,"Error while writing "+fout.getPath(),"Error",JOptionPane.ERROR_MESSAGE);
      }finally{
        try {
          in.close();
          out.close();
        }
        catch (Exception ignored) {}
      }
  }

  private void copyFiles() throws CopyCancelledException{
    totalFilesSizes=getFilesSize(filesToCopy);
    if(filesToCopy.size()==0) this.dispose();
    for(int i=0;i<filesToCopy.size();i++){
      JFMFile el=(JFMFile)filesToCopy.elementAt(i);
      JFMFile destFile=null;//new File(toDir.getPath()+(toDir.getPath().endsWith(File.separator)?"":File.separator)+el.getName());
      if(el.isDirectory()){
       //  if(!destFile.exists()) destFile.mkdirs();
        copyDir(el,destFile);
      }else{
        copyFile(el,destFile);
      }

    }
  }

  public CopyProgressDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public CopyProgressDialog() {
    this(null, "", false);
  }
  void jbInit() throws Exception {
    panel1.setLayout(new BorderLayout());
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    panel1.add(progressPanel,BorderLayout.CENTER);
    panel1.add(buttonsPanel,BorderLayout.SOUTH);
    progressPanel.setLayout(gridBagLayout1);
    fileCopyLabel.setText("File progress:");
    jLabel1.setText("Total progress");
    statusLabel.setText("Copying  file:");
    buttonsPanel.setBounds(new Rectangle(5, 105, 549, 35));
    cancelButton.setText("Cancel");
    totalCopyProgressBar.setStringPainted(true);
    fileCopyProgressBar.setStringPainted(true);
    getContentPane().add(panel1);
    progressPanel.add(totalCopyProgressBar,  new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(17, 8, 11, 10), 294, 9));
    progressPanel.add(jLabel1,  new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(17, 4, 11, 0), 3, 5));
    progressPanel.add(fileCopyProgressBar,  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(9, 8, 0, 10), 294, 9));
    progressPanel.add(fileCopyLabel,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 4, 0, 0), 10, 5));
    progressPanel.add(statusLabel,  new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(7, 4, 0, 10), 458, 4));
    //progressPanel.add(buttonsPanel, null);
    buttonsPanel.add(cancelButton, null);
    totalCopyProgressBar.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        if(totalCopyProgressBar.getValue()>=100){
          cancelButton_actionPerformed(null);
        }
      }
    });
  }

  void cancelButton_actionPerformed(ActionEvent e) {
      cancel=true;
      this.dispose();
  }
}
