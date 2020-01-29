package com.gsft.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class CustomerInputDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
		
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "summaryId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private CustomerInputSummary summaryId ;
	
	@OneToOne
	@JoinColumn(name = "assetId", nullable = false)
	private AssetMaster assetId;
	private Long value;
	private Double rate;
	private Double ltv;
	private Long tenure;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CustomerInputSummary getSummaryId() {
		return summaryId;
	}
	public void setSummaryId(CustomerInputSummary summaryId) {
		this.summaryId = summaryId;
	}

	public AssetMaster getAssetId() {
		return assetId;
	}
	public void setAssetId(AssetMaster assetId) {
		this.assetId = assetId;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
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
	public void setValue(Long value) {
		this.value = value;
	}

}
