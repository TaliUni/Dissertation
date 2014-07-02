package org.jfm.views.fview;

/**
 * <p>Title: Java File Manager</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Home</p>
 * @author Giurgiu Sergiu
 * @version 1.0
 */

public interface FindListener {
    public void find(String findText,String replaceText,boolean caseSensitive,boolean fileStart,boolean wholeWords,boolean regexp,int count);
    public void all(String findText,String replaceText,boolean caseSensitive,boolean fileStart,boolean wholeWords,boolean regexp);
}