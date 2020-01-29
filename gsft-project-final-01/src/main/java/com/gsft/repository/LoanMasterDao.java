package com.gsft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gsft.model.AssetMaster;
import com.gsft.model.LoanCompanyMaster;

public interface LoanMasterDao extends JpaRepository<LoanCompanyMaster, Long> {
	
	
	List<LoanCompanyMaster>  findByAssetIdIn(List<AssetMaster> assetMaster);

}
