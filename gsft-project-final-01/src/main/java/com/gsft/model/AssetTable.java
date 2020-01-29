package com.gsft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class AssetTable {
	
	//@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private int assetid;
	private String assetname;
	private long value;
	private float rate;
	private float ltv;
	private int tenure;
	
	public AssetTable() {
		
	}
	public int getAssetid() {
		return assetid;
	}
	public void setAssetid(int assetid) {
		this.assetid = assetid;
	}
	public String getAssetname() {
		return assetname;
	}
	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public float getLtv() {
		return ltv;
	}
	public void setLtv(float ltv) {
		this.ltv = ltv;
	}
	public int getTenure() {
		return tenure;
	}
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}
	@Override
	public String toString() {
		return "AssetTable [assetid=" + assetid + ", assetname=" + assetname + ", value=" + value + ", rate=" + rate
				+ ", ltv=" + ltv + ", tenure=" + tenure + "]";
	}
	
	
}
