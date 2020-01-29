package com.gsft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class LoanCompanyMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Double id;
	
	@OneToOne
	@JoinColumn(name = "assetId", nullable = false)
	private AssetMaster assetId;
	private String entity;
	private Double rate;
	private Double ltv;
	private Long tenure;
	

	public Double getId() {
		return id;
	}
	public void setId(Double id) {
		this.id = id;
	}
	public AssetMaster getAssetId() {
		return assetId;
	}
	public void setAssetId(AssetMaster assetId) {
		this.assetId = assetId;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getLtv() {
		return ltv;
	}
	public void setLtv(Double ltv) {
		this.ltv = ltv;
	}
	public Long getTenure() {
		return tenure;
	}
	public void setTenure(Long tenure) {
		this.tenure = tenure;
	}
	@Override
	public String toString() {
		return "LoanCompanyMaster [id=" + id + ", assetId=" + assetId + ", entity=" + entity + ", rate=" + rate
				+ ", ltv=" + ltv + ", tenure=" + tenure + "]";
	}	
	
}
