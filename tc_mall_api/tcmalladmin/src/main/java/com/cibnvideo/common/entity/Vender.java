package com.cibnvideo.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Vender implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long venderId;
	//上级厂商ID，一级厂商为0
	private Long parentId;
	//厂商名称
	private String name;

	private VenderSettlement venderSettlement;

	private List<VenderPayment> venderPayment;
	//排序
	private Integer orderNum;
	//是否删除  -1：已删除  0：正常
	private Integer delFlag;

	public VenderSettlement getVenderSettlement() {
		return venderSettlement;
	}

	public void setVenderSettlement(VenderSettlement venderSettlement) {
		this.venderSettlement = venderSettlement;
	}

	public List<VenderPayment> getVenderPayment() {
		return venderPayment;
	}

	public void setVenderPayment(List<VenderPayment> venderPayment) {
		this.venderPayment = venderPayment;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * 设置：
	 */
	public void setVenderId(Long venderId) {
		this.venderId = venderId;
	}
	/**
	 * 获取：
	 */
	public Long getVenderId() {
		return venderId;
	}
	/**
	 * 设置：上级厂商ID，一级厂商为0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级厂商ID，一级厂商为0
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：厂商名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：厂商名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
	/**
	 * 设置：是否删除  -1：已删除  0：正常
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：是否删除  -1：已删除  0：正常
	 */
	public Integer getDelFlag() {
		return delFlag;
	}

	@Override
	public String toString() {
		return "Vender{" +
				"venderId=" + venderId +
				", parentId=" + parentId +
				", name='" + name + '\'' +
				", orderNum=" + orderNum +
				", delFlag=" + delFlag +
				'}';
	}
}
