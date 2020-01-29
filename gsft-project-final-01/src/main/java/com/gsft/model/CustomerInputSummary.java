package com.gsft.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class CustomerInputSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "summaryId",cascade = CascadeType.ALL)
	private List<CustomerInputDetails> customerInputDetails;
	
	public List<CustomerInputDetails> getCustomerInputDetails() {
			return customerInputDetails;
		}

	public void setCustomerInputDetails(List<CustomerInputDetails> customerInputDetails) {
		this.customerInputDetails = customerInputDetails;
	}

	public Long getId() {
		return id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "CustomerInputSummary [id=" + id + ", date=" + date + "]";
	}
	
	
	
}
