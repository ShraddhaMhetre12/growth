package com.gsft.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsft.AssetCreationDto;
import com.gsft.model.AssetMaster;
import com.gsft.model.CustomerInputDetails;
import com.gsft.model.CustomerInputSummary;
import com.gsft.model.LoanCompanyMaster;
import com.gsft.service.AssetTableService;

@Controller
public class AssetMasterRouteController {

	private Log log = LogFactory.getLog(AssetMasterRouteController.class);

	@Autowired
	AssetTableService service;

	@RequestMapping("/")
	public String asset(Model model) {
		model.addAttribute("assetTable", new AssetCreationDto());
		return "view/submitasset";

	}

	@RequestMapping("/optimizedCal")
	public String submitAsset(Model model) {
		List<CustomerInputDetails> list = service.listAll();
		model.addAttribute("assetTable", list);
		return "view/optimizedCal";
	}

	@RequestMapping(value = "/submitAsset", method = RequestMethod.POST)
	public String submitAsset(Model model, @ModelAttribute AssetCreationDto assetTable) {

		CustomerInputSummary customerSummary = service.saveData(assetTable);
		customerSummary.getCustomerInputDetails();

		Map<String, List<LoanCompanyMaster>> loanCompanyMap = service
				.getLoanCompanyMasterForCustomerInput(customerSummary.getCustomerInputDetails());
		/* System.out.println("l"+loanCompanyMap); */
		model.addAttribute("assetTable", customerSummary.getCustomerInputDetails());
		model.addAttribute("loanCompanyMap", loanCompanyMap);
		model.addAttribute("summaryId", customerSummary.getId());
		return "view/optimizedCal";
	}

// @SuppressWarnings("null")
// @RequestMapping(value = "api/calculate/{id}", method = RequestMethod.POST)
// public @ResponseBody String calculate(@PathVariable Long id) {
// author meeta chauhan 
//
// 
//        Double BankRate =0.0;
//        Double BankLtv = 0.0;
//        Double GsftRate =0.0;
//        Double GsftLtv = 0.0;
//        Double newremainingLoanamt=0.0;
//        Double customerLoanAmount=0.0;
//        Double loanAmtOverLTV = 0.0;
//        Double remainingCustomerLoanAmount = 0.0;
//        Double minRate=0.0;
//        Double requiredLtv=0.0;
//        Double loanAmtOverLTV1=0.0;
//        Double totalLoanamt=0.0;
//        Double lastRate=0.0;
//        Double maxLTV =0.0;
//        Double resultLTV =0.0;
//        Double totalSumAmt =0.0;
//        Double assetValue =0.0;
//        Entry<Double, Double> smallestRateGsft;
//        Entry<Double, Double> smallestRateBank;
//        
//        Map<Double,Double> bankList = new TreeMap<Double,Double>();
//        Map<Double,Double> gsftList = new HashMap<Double,Double>();
//        Map<Double,Double> mergedList = new Map<Double,Double>();
//        MultiValuedMap<String, String> mergedList = new ArrayListValuedHashMap<>();
//
//        Map<Double,Double> usedList = new TreeMap<Double,Double>();
//        Map<Double,Double> assetList = new HashMap<Double,Double>();
//        
//
// CustomerInputSummary customerInputSummary = service.getInputSummaryById(id);
// List<CustomerInputDetails> customerInputDetailsLst = customerInputSummary.getCustomerInputDetails();
//
// Map<String, List<LoanCompanyMaster>> loanCompanyMap = service
// .getLoanCompanyMasterForCustomerInput(customerInputDetailsLst);
//
// List<LoanCompanyMaster> abcdLoanMasterLst = loanCompanyMap.containsKey("ABCD") ? loanCompanyMap.get("ABCD")
// : new ArrayList<LoanCompanyMaster>();
// List<LoanCompanyMaster> gsftLoanMasterLst = loanCompanyMap.containsKey("GSFT") ? loanCompanyMap.get("GSFT")
// : new ArrayList<LoanCompanyMaster>();
//
// // map for GSFT loan master
// Map<Long, CustomerInputDetails> customerInputDetailsMap = customerInputDetailsLst.stream()
// .collect(Collectors.toMap(customerInputDetails -> customerInputDetails.getAssetId().getId(),
// customerInputDetails -> customerInputDetails));
//
// // map for ABCD loan master
// Map<Long, LoanCompanyMaster> abcdLoanMasterMap = abcdLoanMasterLst.stream().collect(Collectors.toMap(
// loanCompanyMaster -> loanCompanyMaster.getAssetId().getId(), loanCompanyMaster -> loanCompanyMaster));
//
// // map for GSFT loan master
// Map<Long, LoanCompanyMaster> gsftLoanMasterMap = gsftLoanMasterLst.stream().collect(Collectors.toMap(
// loanCompanyMaster -> loanCompanyMaster.getAssetId().getId(), loanCompanyMaster -> loanCompanyMaster));
//
// CustomerInputDetails customerInputDetailstmp = null;
// LoanCompanyMaster loanCompanyMasterABCDtmp = null;
// LoanCompanyMaster loanCompanyMasterGSFTtmp = null;
// 
//  
//   
// for (Long assetId : customerInputDetailsMap.keySet()) {
// 
// customerInputDetailstmp = customerInputDetailsMap.get(assetId);
// System.out.println("customerInputDetailstmp =="+customerInputDetailstmp);
// loanCompanyMasterABCDtmp = abcdLoanMasterMap.containsKey(assetId) ? abcdLoanMasterMap.get(assetId) : null;
// System.out.println("loanCompanyMasterABCDtmp =="+loanCompanyMasterABCDtmp);
// loanCompanyMasterGSFTtmp = gsftLoanMasterMap.containsKey(assetId) ? gsftLoanMasterMap.get(assetId) : null;
// System.out.println("loanCompanyMasterGSFTtmp =="+loanCompanyMasterGSFTtmp);
//          BankRate = loanCompanyMasterABCDtmp.getRate(); //get Bank rate into variable.
//          BankLtv = loanCompanyMasterABCDtmp.getLtv(); //get Bank LTV into variable.
//          GsftRate = loanCompanyMasterGSFTtmp.getRate(); //get GSFT rate into variable.
//          GsftLtv = loanCompanyMasterGSFTtmp.getLtv();
//          assetValue = (double) customerInputDetailstmp.getValue();//get GSFT rate into variable.
//          if( BankRate < GsftRate) {
//          mergedList.put(BankRate, BankLtv);
//          assetList.put(BankRate, assetValue);//get GSFT rate and LTV into map list. 
//         
//          }
//          else{
//          mergedList.put(GsftRate,  GsftLtv);
//          assetList.put(GsftRate, assetValue);
////         //get Bank rate and LTV into map list.
//          }
////            
//            
////          mergedList.put(GsftRate, GsftLtv);  //get GSFT rate and LTV into merged list.
////          mergedList.put(BankRate, BankLtv);  //get Bank rate and LTV into merged list.
//          
//          //
//         
//          totalLoanamt+= (customerInputDetailstmp.getValue());
//          System.out.println("assetValue"+assetList);
//          
//          //
//          customerLoanAmount += ((customerInputDetailstmp.getValue()*customerInputDetailstmp.getLtv())/100);
//          
//        
// }
// System.out.println("gsft"+gsftList);
// System.out.println("bank"+bankList);
//// smallestRateGsft = ((HashMap<Double, Double>) gsftList).firstEntry();
//// 
//// smallestRateGsft = ((Map<Double, Double>) bankList).firstEntry();
// System.out.println("sort smallestRateGsft"+ mergedList);
// //System.out.println("sort smallestRatebank"+smallestRateBank);
// 
// logger("value =="+totalLoanamt);
// System.out.println("value =="+customerLoanAmount+"mergedList.equals(assetList))==="+(mergedList.keySet()).equals(assetList.keySet()));
// 
// newremainingLoanamt=customerLoanAmount;
// 
// if((mergedList.keySet()).equals(assetList.keySet())==true){
// System.out.println("ttfhgjgjgj");}
// for(Map.Entry<Double,Double> entry : mergedList.entrySet()) {
// 
// Double mergedListkey = entry.getKey();
// Double mergedListvalue = entry.getValue();
// Double assetAmtValue = 0.0;
// Double assetAmtkey = 0.0;
//
// // assuming your map is Map<String, String>
//    if(assetList.containsKey(mergedListkey)) {
//     gsftList.put(mergedListvalue,(assetList.get(mergedListkey)));
//     System.out.println("Found Duplicate -> " +gsftList );
//    }
//        
// 
// System.out.println("assetAmtValue"+assetAmtValue);
// System.out.println("assetAmtkey"+assetAmtkey);
// 
// loanAmtOverLTV = ((totalLoanamt*mergedListvalue)/100);
// System.out.println("loanAmtOverLTV"+loanAmtOverLTV);
// 
// remainingCustomerLoanAmount = (customerLoanAmount-loanAmtOverLTV);
// System.out.println("remainingCustomerLoanAmount"+remainingCustomerLoanAmount);
// 
// 
//// if(customerLoanAmount == 0 || customerLoanAmount<=loanAmtOverLTV) {
//// if(remainingCustomerLoanAmount<0) {
//// requiredLtv =((customerLoanAmount/totalLoanamt)*100);
//// loanAmtOverLTV1=((totalLoanamt*requiredLtv)/100);
//// System.out.println("loanAmtOverLTV1"+loanAmtOverLTV1);
//// 
//// minRate += ((loanAmtOverLTV1*mergedListkey)/newremainingLoanamt);
//// System.out.println("minRate if ke ander ka if wala"+minRate+"at key=="+mergedListkey);
//// usedList.put(mergedListkey,loanAmtOverLTV1);
//// break;
//// 
//// 
//// }
//// else {
//// requiredLtv =((remainingCustomerLoanAmount/totalLoanamt)*100);
//// System.out.println("requiredLtv"+requiredLtv);
//// loanAmtOverLTV1=((totalLoanamt*requiredLtv)/100);
//// System.out.println("loanAmtOverLTV1"+loanAmtOverLTV1);
//// 
//// minRate += (((loanAmtOverLTV1*mergedListkey)+(loanAmtOverLTV*mergedListkey))/newremainingLoanamt);
//// System.out.println("minRate if ander wala else"+minRate);
//// usedList.put(mergedListkey,loanAmtOverLTV1);
//// break;
//// 
//// }
//// 
//// 
//// }
//// 
//// else {
//// minRate += ((loanAmtOverLTV*mergedListkey)/newremainingLoanamt);
//// System.out.println("minRate esle wala"+minRate);
//// customerLoanAmount=remainingCustomerLoanAmount;
//// System.out.println("customerLoanAmount...."+customerLoanAmount);
//// 
//// }
//// 
// 
// usedList.put(mergedListkey,loanAmtOverLTV);
// 
// }
//  lastRate = ((TreeMap<Double, Double>) usedList).lastKey();
//  System.out.println("minrate=="+minRate);
//  System.out.println(usedList);
//  System.out.println(lastRate);
//  for(Map.Entry<Double,Double> entry : usedList.entrySet()) {
//  Double rate = entry.getKey();
// Double LTV = entry.getValue();
// 
// Double maxLtvAmt =0.0;
// 
// 
// if(rate != lastRate)
// {
// totalSumAmt += (rate*LTV);
// System.out.println("totalSumAmt"+totalSumAmt);
// 
// }
// else {
// 
// maxLtvAmt=(((minRate*totalLoanamt)-(totalSumAmt))/(lastRate+minRate));
// System.out.println("x=="+maxLtvAmt);
// 
// 
// }
// System.out.println("LTV="+LTV);
// resultLTV += (maxLtvAmt+LTV);
// 
//  
//  }
//  System.out.println("resultLTV"+resultLTV);
//  maxLTV=((resultLTV/totalLoanamt)*100);
//  
//  
//  System.out.println("maxLTV==="+maxLTV);
// 
// 
//
// Map<String, String> weightedAvgMap = new HashMap<>();
//
// DecimalFormat df = new DecimalFormat("00.00");
//
// 
// weightedAvgMap.put("minrate", df.format(minRate));
// weightedAvgMap.put("maxltv", df.format(maxLTV));
// 
//
// ObjectMapper objectMapper = new ObjectMapper();
// String json = null;
//
// try {
// json = objectMapper.writeValueAsString(weightedAvgMap);
// 
// } catch (JsonProcessingException e) {
// e.printStackTrace();
// }
//
// return json;
//
// }
	static <K, V> List<K> getAllKeysForValue(Map<Double, AssetMaster> entityList, AssetMaster rateListvalue11) {
		List<K> listOfKeys = null;

// Check if Map contains the given value
		if (entityList.containsValue(rateListvalue11)) {
// Create an Empty List
			listOfKeys = new ArrayList<>();

// Iterate over each entry of map using entrySet
			for (Entry<Double, AssetMaster> entry : entityList.entrySet()) {
// Check if value matches with given value
				if (entry.getValue().equals(rateListvalue11)) {
// Store the key from entry to the list
					listOfKeys.add((K) entry.getKey());
				}
			}
		}
// Return the list of keys whose value matches with given value.
		return listOfKeys;
	}

	@SuppressWarnings({ "null", "unchecked" })
	@RequestMapping(value = "api/calculate/{id}", method = RequestMethod.POST)
	public @ResponseBody String calculate(@PathVariable Long id) {

		Double BankRate = 0.0;
		Double BankLtv = 0.0;
		Double GsftRate = 0.0;
		Double GsftLtv = 0.0;
		Double rateValue = 0.0;
		Double newremainingLoanamt = 0.0;
		Double customerLoanAmount = 0.0;
		Double loanAmtOverLTV = 0.0;
		Double remainingCustomerLoanAmount = 0.0;
		Double minRate = 0.0;
		Double requiredLtv = 0.0;
		Double loanAmtOverLTV1 = 0.0;
		Double totalLoanamt = 0.0;
		Double lastRate = 0.0;
		Double maxLTV = 0.0;
		Double resultLTV = 0.0;
		Double totalSumAmt = 0.0;
		Double assetValue = 0.0;
		Double idGsft = 0.0;
		Double idBank = 0.0;
		AssetMaster entityListLtvValue = null;
		Double totalAssetValue = 0.0;
		Double assetListValueAssetValue = 0.0;
		Double ltvListLtvValue = 0.0;
		Double loanAmtOverAsset = 0.0;
		Double totalLtv = 0.0;
		Double value = 0.0;
		Double rateVaule1 = 0.0;
		Double rateVaule2 = 0.0;
		Double ltvVaule1 = 0.0;
		Double ltvVaule2 = 0.0;
		Double ltvfinalList = 0.0;
		Double loanAmtOverAssetFinalSum = 0.0;
		Double minRateCal = 0.0;
		Double rateUsed = 0.0;
		Double loanUsed = 0.0;
		Double finalLoanAmt = 0.0;
		int finalSortListsize=0;

		Double rateListvalue1 = 0.0;
		AssetMaster entityGsft = null;
		AssetMaster entityBank = null;

		Entry<Double, Double> smallestRateGsft;
		Entry<Double, Double> smallestRateBank;

		Map<Double, Double> rateList = new HashMap<Double, Double>();
		Map<Double, Double> ltvList = new HashMap<Double, Double>();
		Map<Double, AssetMaster> entityList = new HashMap<Double, AssetMaster>();
		Map<Double, Double> rateAssetList = new HashMap<Double, Double>();

		Map<Double, AssetMaster> mergedList = new HashMap<Double, AssetMaster>();

		Map<Double, Double> usedList = new TreeMap<Double, Double>();
		Map<Double, Double> assetList = new HashMap<Double, Double>();
		Map<Double, Double> finalLTVList = new HashMap<Double, Double>();
		Map<Double, Double> finalRateList = new HashMap<Double, Double>();

		CustomerInputSummary customerInputSummary = service.getInputSummaryById(id);
		List<CustomerInputDetails> customerInputDetailsLst = customerInputSummary.getCustomerInputDetails();

		Map<String, List<LoanCompanyMaster>> loanCompanyMap = service
				.getLoanCompanyMasterForCustomerInput(customerInputDetailsLst);

		List<LoanCompanyMaster> abcdLoanMasterLst = loanCompanyMap.containsKey("ABCD") ? loanCompanyMap.get("ABCD")
				: new ArrayList<LoanCompanyMaster>();
		List<LoanCompanyMaster> gsftLoanMasterLst = loanCompanyMap.containsKey("GSFT") ? loanCompanyMap.get("GSFT")
				: new ArrayList<LoanCompanyMaster>();

// map for GSFT loan master
		Map<Long, CustomerInputDetails> customerInputDetailsMap = customerInputDetailsLst.stream()
				.collect(Collectors.toMap(customerInputDetails -> customerInputDetails.getAssetId().getId(),
						customerInputDetails -> customerInputDetails));

// map for ABCD loan master
		Map<Long, LoanCompanyMaster> abcdLoanMasterMap = abcdLoanMasterLst.stream().collect(Collectors.toMap(
				loanCompanyMaster -> loanCompanyMaster.getAssetId().getId(), loanCompanyMaster -> loanCompanyMaster));

// map for GSFT loan master
		Map<Long, LoanCompanyMaster> gsftLoanMasterMap = gsftLoanMasterLst.stream().collect(Collectors.toMap(
				loanCompanyMaster -> loanCompanyMaster.getAssetId().getId(), loanCompanyMaster -> loanCompanyMaster));

		CustomerInputDetails customerInputDetailstmp = null;
		LoanCompanyMaster loanCompanyMasterABCDtmp = null;
		LoanCompanyMaster loanCompanyMasterGSFTtmp = null;

		for (Long assetId : customerInputDetailsMap.keySet()) {

			customerInputDetailstmp = customerInputDetailsMap.get(assetId);
			// System.out.println("customerInputDetailstmp ==" + customerInputDetailstmp);
			loanCompanyMasterABCDtmp = abcdLoanMasterMap.containsKey(assetId) ? abcdLoanMasterMap.get(assetId) : null;
			// System.out.println("loanCompanyMasterABCDtmp ==" + loanCompanyMasterABCDtmp);
			loanCompanyMasterGSFTtmp = gsftLoanMasterMap.containsKey(assetId) ? gsftLoanMasterMap.get(assetId) : null;
			// System.out.println("loanCompanyMasterGSFTtmp ==" + loanCompanyMasterGSFTtmp);
			BankRate = loanCompanyMasterABCDtmp.getRate(); // get Bank rate into variable.
			BankLtv = loanCompanyMasterABCDtmp.getLtv(); // get Bank LTV into variable.
			GsftRate = loanCompanyMasterGSFTtmp.getRate(); // get GSFT rate into variable.
			GsftLtv = loanCompanyMasterGSFTtmp.getLtv();
			idGsft = loanCompanyMasterGSFTtmp.getId();
			idBank = loanCompanyMasterABCDtmp.getId();
			entityGsft = loanCompanyMasterGSFTtmp.getAssetId();
			entityBank = loanCompanyMasterABCDtmp.getAssetId();
			assetValue = (double) customerInputDetailstmp.getValue();// get GSFT rate into variable.
		
			
				rateList.put(idBank, BankRate);
				ltvList.put(idBank, BankLtv);
				assetList.put(idBank, assetValue);// get GSFT rate and LTV into map list.
				entityList.put(idBank, entityBank);
				rateList.put(idGsft, GsftRate);
				ltvList.put(idGsft, GsftLtv);
				assetList.put(idGsft, assetValue);
				entityList.put(idGsft, entityGsft);
				

				System.out.println("");
//				System.out.println("rate List==" + rateList + "\n ltv List" + ltvList + "\n asset List =" + assetList
//						+ "\n entity List" + entityList);

				totalAssetValue += (customerInputDetailstmp.getValue());
				System.out.println("total Asset Value =" + totalAssetValue);
				customerLoanAmount += ((customerInputDetailstmp.getValue() * customerInputDetailstmp.getLtv()) / 100);
				System.out.println("customerLoanAmount=" + customerLoanAmount);
				System.out.println("");
			
			

		}

		System.out.println("");
		ArrayList<Object> listOfKeys1 = new ArrayList<>();

		for (Entry<Double, AssetMaster> entry : entityList.entrySet()) {
			AssetMaster rateListvalue11 = entry.getValue();

			List<String> listOfKeys = AssetMasterRouteController.getAllKeysForValue(entityList, rateListvalue11);

			if (!listOfKeys1.contains(listOfKeys)) {

				listOfKeys1.add(listOfKeys);

			}

		}

		int lengthData = ((ArrayList<Object>) listOfKeys1).size();
		

		for (int i = 0; i < lengthData; i++) {

			Double value1 = (Double) ((ArrayList<Object>) listOfKeys1.get(i)).get(0);
			Double value2 = (Double) ((ArrayList<Object>) listOfKeys1.get(i)).get(1);
//			System.out.println(value1);
//			System.out.println(value2);
			if (rateList.containsKey(value1)) {
				rateVaule1 = (rateList.get(value1));
				rateVaule2 = (rateList.get(value2));
			}
			if (ltvList.containsKey(value1)) {
				ltvVaule1 = (ltvList.get(value1));
				ltvVaule2 = (ltvList.get(value2));
			}

//			System.out.println("asset nn rateVaule1 -> " + rateVaule1);
//			System.out.println("asset nn rateVaule2 -> " + rateVaule2);
//			System.out.println(" nn ltvVaule1 -> " + ltvVaule1);
//			System.out.println(" nn ltvVaule2 -> " + ltvVaule2);
			if(rateVaule1 != 0 && rateVaule2 != 0 && ltvVaule1 !=0 && ltvVaule2 !=0 ) {
				if (rateVaule1 < rateVaule2) {

					if (ltvVaule1 < ltvVaule2) {
						finalLTVList.put(value1, ltvVaule1);
						finalLTVList.put(value2, ltvVaule2);
						finalRateList.put(value1, rateVaule1);
						finalRateList.put(value2, rateVaule2);
					} else {
						finalLTVList.put(value1, ltvVaule1);
						finalRateList.put(value1, rateVaule1);
					}

				} else if (rateVaule1 == rateVaule2) {

					if (ltvVaule1 < ltvVaule2) {
						finalLTVList.put(value1, ltvVaule1);
						finalLTVList.put(value2, ltvVaule2);
						finalRateList.put(value1, rateVaule1);
						finalRateList.put(value2, rateVaule2);
					} else {
						finalLTVList.put(value1, ltvVaule1);
						finalRateList.put(value1, rateVaule1);
					}

				} else {
					if (ltvVaule2 < ltvVaule1) {
						finalLTVList.put(value1, ltvVaule1);
						finalRateList.put(value1, rateVaule1);
					} else {

						// finalLTVList.put(value1, ltvVaule1);
						finalLTVList.put(value2, ltvVaule2);
						// finalRateList.put(value1, rateVaule1);
						finalRateList.put(value2, rateVaule2);
					}

			}
				
			
			}

		}

// if(assetListValueAssetValue)

//		System.out.println("finalLTVList" + finalLTVList);
//		System.out.println("finalRateList" + finalRateList);

// listOfKeys1.forEach(sublist -> sub.forEach(element ->
// System.out.println(element)));
		System.out.println("");
		Map<Double, Double> finalRateSortedList = finalRateList.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		System.out.println("final Rate Sorted List =" + finalRateSortedList);
		finalSortListsize=finalRateSortedList.size();
		
		for (Map.Entry<Double, Double> entry : finalRateSortedList.entrySet()) {
			Double rateListvalue = entry.getValue();
			Double rateListkey = entry.getKey();

			if (assetList.containsKey(rateListkey)) {
				assetListValueAssetValue = (assetList.get(rateListkey));
				System.out.println("asset value -> " + assetListValueAssetValue);
			}
			if (finalLTVList.containsKey(rateListkey)) {
				ltvfinalList = (finalLTVList.get(rateListkey));
				System.out.println("ltv value -> " + ltvfinalList);
			}

			loanAmtOverAsset = ((ltvfinalList * assetListValueAssetValue) / 100);
			
			remainingCustomerLoanAmount = (customerLoanAmount - loanAmtOverAsset);
			
			
			
			if (customerLoanAmount == 0 || customerLoanAmount <= loanAmtOverAsset) {
				if (remainingCustomerLoanAmount < 0) {

					requiredLtv = ((customerLoanAmount / assetListValueAssetValue) * 100);
					loanAmtOverAssetFinalSum += loanAmtOverAsset;
					minRateCal += (loanAmtOverAsset * rateListvalue);
					//System.out.println("minRate if ke ander ka if wala" + minRate + loanAmtOverAssetFinalSum+ "at key==" + rateListvalue);
					usedList.put(loanAmtOverAsset,rateListvalue);
					break;
				} else {
					requiredLtv = ((remainingCustomerLoanAmount / assetListValueAssetValue) * 100);
					//System.out.println("requiredLtv" + requiredLtv);
					loanAmtOverLTV1 = ((assetListValueAssetValue * requiredLtv) / 100);
					//System.out.println("loanAmtOverLTV1" + loanAmtOverLTV1);
					loanAmtOverAssetFinalSum += loanAmtOverAsset;
					//System.out.println("Loan over each asset else ->" + loanAmtOverAssetFinalSum);
					minRateCal += (loanAmtOverAsset * rateListvalue);
					//System.out.println("minRate if ander wala else" + minRate);
					usedList.put(loanAmtOverAsset,rateListvalue);
					break;
				}
			}

			else {
				loanAmtOverAssetFinalSum += loanAmtOverAsset;
				System.out.println("Loan over each asset ->" + loanAmtOverAssetFinalSum);

				minRateCal += (loanAmtOverAsset * rateListvalue);
				//System.out.println("minRate esle wala" + minRate);
				//System.out.println("Loan over each asset ->" + loanAmtOverAssetFinalSum);

				customerLoanAmount = remainingCustomerLoanAmount;
				

			}
			
			usedList.put(loanAmtOverAsset,rateListvalue);
			System.out.println("");
			System.out.println("remainingCustomerLoanAmount" + remainingCustomerLoanAmount);
		}
		if(finalSortListsize==0) {
			minRate=0.0;
		}
		else {
			minRate += (minRateCal / loanAmtOverAssetFinalSum);
		}
		

//		for (Map.Entry<Double, Double> entry1 : usedList.entrySet()) {
//		rateUsed = entry1.getKey();
//		loanUsed = entry1.getValue();
//		System.out.println(rateUsed + loanUsed);
//		finalLoanAmt += loanUsed;
//		minRate += ((rateUsed * loanUsed)/finalLoanAmt);
//		System.out.println("Hii used");
//		}
//			

		//System.out.println("jjhjhhj" + minRate);
		System.out.println(""); 
		System.out.println("used List" + usedList);
//		System.out.println("rateValue" + totalLtv);
		maxLTV = ((loanAmtOverAssetFinalSum / totalAssetValue) * 100);
		System.out.println("");
		System.out.println("Mazimum LTV = " + maxLTV);
		System.out.println("Minimum Rate ="+ minRate);
		
		Map<String, String> weightedAvgMap = new HashMap<>();

		DecimalFormat df = new DecimalFormat("00.00");

		weightedAvgMap.put("minrate", df.format(minRate));
		weightedAvgMap.put("maxltv", df.format(maxLTV));

		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;

		try {
			json = objectMapper.writeValueAsString(weightedAvgMap);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;

	}

	private static List<String> getAllKeysForValue(Map<Double, Double> ltvList, Double rateListvalue) {
// TODO Auto-generated method stub
		return null;
	}

	private void logger(String string) {
// TODO Auto-generated method stub

	}

	@RequestMapping(value = "api/asset/", method = RequestMethod.POST)
	public @ResponseBody String getAssetDetails() {
		List<AssetMaster> assetMaster = service.getAssetMasterDetails();
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;

		try {
			json = objectMapper.writeValueAsString(assetMaster);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;

	}

}
