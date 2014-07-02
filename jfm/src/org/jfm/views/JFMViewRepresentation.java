/*
 * Created on Sep 6, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.jfm.views;

/**
 * This class holds the user friendly and  application friendly information about an view 
 * @author sergiu
 */
public class JFMViewRepresentation {

	/**
	 * The name of the class
	 */
	private String className;
	
	/**
	 * The user firendly name
	 */
	private String name;
	
	/**
	 * Empty constructor 
	 */
	public JFMViewRepresentation() {
		this(null,null);
	}

	/**
	 * Constructor that sets the name and the class name 
	 */
	public JFMViewRepresentation(String name,String className) {
		setName(name);
		setClassName(className);
	}

	/**
	 * @return Returns the className.
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className The className to set.
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * String representation of the object.
	 */
	public String toString(){
		return getName();
	}
	
	/**
	 * Returns true if the passed object is equals with this one. Two objects are considered to be equal
	 * If both the name and the class name's are not null and are equal.
	 */
	public boolean equals(Object obj){
		if(obj==null)return false;
		if(!(obj instanceof JFMViewRepresentation)) return false;
		JFMViewRepresentation viewObj=(JFMViewRepresentation)obj;
		//if any of the members are null, return false
		if(this.getName()==null || this.getClassName()==null) return false;
		if(viewObj.getName()==null || viewObj.getClassName()==null) return false;		
		if(!getName().equals(viewObj.getName())) return false;
		if(!getClassName().equals(viewObj.getClassName())) return false;
		return true;
	}
}
