package com.gsft.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import com.gsft.model.CustomerInputSummary;

public interface CustomerInputSummaryDao extends JpaRepository<CustomerInputSummary, Long> {

	
}
