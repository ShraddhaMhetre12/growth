package com.gsft.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsft.AssetCreationDto;
import com.gsft.model.AssetMaster;
import com.gsft.model.CustomerInputDetails;
import com.gsft.model.CustomerInputSummary;
import com.gsft.model.LoanCompanyMaster;
import com.gsft.repository.AssetMasterDao;
import com.gsft.repository.AssetTableDao;
import com.gsft.repository.CustomerInputDatailsDao;
import com.gsft.repository.CustomerInputSummaryDao;
import com.gsft.repository.LoanMasterDao;

@Service
public class AssetTableService {

	@Autowired
	private AssetTableDao dao;

	@Autowired
	private AssetMasterDao assetMasterDao;
	
	@Autowired
	private CustomerInputDatailsDao customerInputDatailsDao;
	
	@Autowired
	private LoanMasterDao loanMasterDao;
	
	@Autowired
	private CustomerInputSummaryDao customerInputSummaryDao;

	//Insert the multiple recorders into db
	@Transactional
	public CustomerInputSummary saveData(AssetCreationDto assetTableBo) {
		
		CustomerInputSummary customerInputSummary = new CustomerInputSummary();
		customerInputSummary.setDate(LocalDate.now());
		dao.save(customerInputSummary);
		List<CustomerInputDetails> customerInputDetailLst = Optional.ofNullable(assetTableBo.getAsset())
				.orElse(Collections.emptyList()).stream().filter(Objects::nonNull).map(assetui -> {
					CustomerInputDetails customerInputDetails = new CustomerInputDetails();
					customerInputDetails.setLtv(assetui.getLtv());
					customerInputDetails.setRate(assetui.getRate());
					customerInputDetails.setValue(assetui.getValue());
					customerInputDetails.setTenure(assetui.getTenure());

					Optional<AssetMaster> assetMaster = assetMasterDao.findById(Long.valueOf(assetui.getAssetname()));

					customerInputDetails.setAssetId(assetMaster.get());
					customerInputDetails.setSummaryId(customerInputSummary);

					return customerInputDetails;
				}).collect(Collectors.toList());

		
		customerInputSummary.setCustomerInputDetails(customerInputDetailLst);

		dao.save(customerInputSummary);
		
		return customerInputSummary;
	}

	public List<CustomerInputDetails> listAll() {
		// return dao.findAll();
		return null;
	}
	
	
	
	public List<AssetMaster> getAssetMaster(){
		return assetMasterDao.findAll();
	}
	
	public Map<String, List<LoanCompanyMaster>> getLoanCompanyMaster()
	{
		List<LoanCompanyMaster> loanCompanyMasterList=loanMasterDao.findAll();;
		
		Map<String, List<LoanCompanyMaster>> result =loanCompanyMasterList.stream().collect(Collectors.groupingBy(LoanCompanyMaster::getEntity));

		return result;
		
	}
	
	
	public Map<String, List<LoanCompanyMaster>> getLoanCompanyMasterForAssets(List<AssetMaster> assetMasterList)
	{
		List<LoanCompanyMaster> loanCompanyMasterList=loanMasterDao.findByAssetIdIn(assetMasterList);
		
		Map<String, List<LoanCompanyMaster>> result =
				loanCompanyMasterList.stream().collect(
                        Collectors.groupingBy(
                        		LoanCompanyMaster::getEntity
                        )
                );

		return result;
		
	}
	
	public Map<String, List<LoanCompanyMaster>> getLoanCompanyMasterForCustomerInput(List<CustomerInputDetails> customerInputDetailsList)
	{
		
		
		List<AssetMaster> assetMasterList = new ArrayList<>();
		for (CustomerInputDetails customerInputDetails : customerInputDetailsList) {
			assetMasterList.add(customerInputDetails.getAssetId());
		}

		List<LoanCompanyMaster> loanCompanyMasterList=loanMasterDao.findByAssetIdIn(assetMasterList);
		
		Map<String, List<LoanCompanyMaster>> result =
				loanCompanyMasterList.stream().collect(
                        Collectors.groupingBy(
                        		LoanCompanyMaster::getEntity
                        )
                );

		return result;
		
	}
	
	public CustomerInputSummary getInputSummaryById(Long summaryId)
	{
		Optional<CustomerInputSummary> customerInputSummary=customerInputSummaryDao.findById(summaryId);
		return customerInputSummary.get();
		
	}
	
	public List<CustomerInputDetails> getInputDetailsBySummaryId(Long summaryId)
	{
		List<CustomerInputDetails> customerInputDetailsLst=customerInputDatailsDao.findBySummaryIdOrderByAssetIdAsc(summaryId);
		
		return customerInputDetailsLst;
	}
	
	
	
	//AssetMaster Name fetch the list from database.
	public List<AssetMaster> getAssetMasterDetails() {
		List<AssetMaster> assetList = assetMasterDao.findAll();
		return assetList;
		
	}
}
