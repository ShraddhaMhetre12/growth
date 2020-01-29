package com.gsft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsft.model.AssetTable;
import com.gsft.model.CustomerInputDetails;
import com.gsft.model.CustomerInputSummary;

public interface AssetTableDao extends JpaRepository<CustomerInputSummary,Long> {

}
