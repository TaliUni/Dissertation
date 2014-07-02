package org.jfm.po;

/**
 * Title:        Java File Manager
 * Description:  
 * Copyright:    Copyright (c) 2001
 * Company:      Home
 * @author Giurgiu Sergiu
 * @version 1.0
 */

 /**
  * This interface is the one that gets passed to the ProgressActionDialog. It is responsibble 
  * of executing the actual operation for every action that needs monitorization.
  */
public interface ActionExecuter {
  
  /**The start method.*/
  public void start();
  
  /**The cancel method. Called when the user presses the cancel button. Is where the start method should clean up (close streams,
   * or anything else), and return.
   */
  public void cancel();
  
}