/**
 * @file ZtreeBean.java
 * @brief 文件简要说明
 *
 *
 * @author
 *    - 2014年8月24日 moonlightbro  创建初始版本
 *
 * @version
 *    - 2014年8月24日  V1.0  简要版本说明
 *
 * @par 版权信息：
 * 		2014 Copyright Moonlight Brother All Rights Reserved.
 */
package com.app.common.bean;

/**
 * @brief ZtreeBean
 * 
 * @author
 *    - 2014年8月24日  moonlightbro  创建初始版本
 *
 */
public class ZtreeBean {
	
	private String id;
	
	private String pId;
	
	private String name;
	
	private boolean open;
	
	private boolean checked;
	
	public ZtreeBean(){
		
	}
	public ZtreeBean(String id, String pId, String name) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
	}
	
	
	public ZtreeBean(String id, String pId, String name, boolean open) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
	}
	
	public ZtreeBean(String id, String pId, String name, boolean open,
			boolean checked) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.checked = checked;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the pId
	 */
	public String getpId() {
		return pId;
	}

	/**
	 * @param pId the pId to set
	 */
	public void setpId(String pId) {
		this.pId = pId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the open
	 */
	public boolean isOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	

}
