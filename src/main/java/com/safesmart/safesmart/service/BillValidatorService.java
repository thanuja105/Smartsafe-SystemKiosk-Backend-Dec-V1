package com.safesmart.safesmart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safesmart.safesmart.common.CommonException;
import com.safesmart.safesmart.common.CommonExceptionMessage;
import com.safesmart.safesmart.dto.BillValidatorRequest;
import com.safesmart.safesmart.dto.BillValidatorResponse;
import com.safesmart.safesmart.dto.KioskRequest;
import com.safesmart.safesmart.dto.KioskResponse;
import com.safesmart.safesmart.dto.LocksResponse;
import com.safesmart.safesmart.model.ActionStatus;
import com.safesmart.safesmart.model.BillValidator;
import com.safesmart.safesmart.model.Kiosk;
import com.safesmart.safesmart.model.Locks;
import com.safesmart.safesmart.repository.BillValidatorRepository;

@Service
@Transactional
public class BillValidatorService {

	@Autowired
	private BillValidatorRepository billValidatorRepository;
	
	public void add(BillValidatorRequest billValidatorRequest) {

		BillValidator billValidator = billValidatorRepository.findByBillAcceptorName(billValidatorRequest.getBillAcceptorName());
		if (billValidator != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "BillAcceptorName");
		}
		
		//validation for billvalidatorNo
		BillValidator billValidatorNo = billValidatorRepository.findByBillAcceptorNo(billValidatorRequest.getBillAcceptorNo());
		if (billValidatorNo != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "BillAcceptorNo");
		}
	

		billValidator = new BillValidator();
		billValidator.setBillAcceptorNo(billValidatorRequest.getBillAcceptorNo());
		billValidator.setBillAcceptorName(billValidatorRequest.getBillAcceptorName());
		billValidator.setBrandName(billValidatorRequest.getBrandName());
		billValidator.setModelName(billValidatorRequest.getModelName());
		billValidator.setMachineType(billValidatorRequest.getMachineType());
		billValidator.setStorageCapacity(billValidatorRequest.getStorageCapacity());
	
		billValidator.setActive(billValidatorRequest.isActive());
		billValidator.setActionStatus(ActionStatus.Created);

	
		billValidatorRepository.save(billValidator);
	}
	
	public List<BillValidatorResponse> findAllUser() {
		// TODO Auto-generated method stub
		List<BillValidator> billValidators = (List<BillValidator>) billValidatorRepository.findAll();

		List<BillValidatorResponse> billValidatorResponses = new ArrayList<BillValidatorResponse>();

		for (BillValidator billValidator : billValidators) {
			billValidatorResponses.add(new BillValidatorResponse(billValidator.getId(),billValidator.getBillAcceptorNo(), billValidator.getBillAcceptorName(), billValidator.getBrandName(),
					billValidator.getModelName(), billValidator.getMachineType(), billValidator.getStorageCapacity(), billValidator.isActive()));
		}
		return billValidatorResponses;
	}
	
	public void deleteByBillValidator(Long Id) {
		billValidatorRepository.deleteById(Id);
	}
	
	public void updateBillValidator(BillValidatorRequest billValidatorRequest) {


		BillValidator billValidator = billValidatorRepository.findById(billValidatorRequest.getId()).get();

		billValidator.setBillAcceptorNo(billValidatorRequest.getBillAcceptorNo());
		billValidator.setBillAcceptorName(billValidatorRequest.getBillAcceptorName());
		billValidator.setBrandName(billValidatorRequest.getBrandName());
		billValidator.setModelName(billValidatorRequest.getModelName());
		billValidator.setMachineType(billValidatorRequest.getMachineType());
		billValidator.setStorageCapacity(billValidatorRequest.getStorageCapacity());
	
		billValidator.setActive(billValidatorRequest.isActive());
		
		billValidatorRepository.save(billValidator);

	}
	
	public List<BillValidatorResponse> findUnassignedBillValidator() {
		List<BillValidator> billValidators = (List<BillValidator>) billValidatorRepository.findByActive(true);
		List<BillValidatorResponse> infoResponses = new ArrayList<BillValidatorResponse>();
		for (BillValidator billValidator : billValidators) {
			if (billValidator != null && billValidator.getStoreinfob() == null) {
				infoResponses.add(new BillValidatorResponse(billValidator.getId(),billValidator.getBillAcceptorNo(), billValidator.getBillAcceptorName(), billValidator.getBrandName(),
						billValidator.getModelName(), billValidator.getMachineType(), billValidator.getStorageCapacity(), billValidator.isActive()));
			}
		}
		return infoResponses;
	}

	public BillValidator getBillValidatorInfo(Long billid) {
		Optional<BillValidator> billValidator = billValidatorRepository.findById(billid);
		BillValidator billValidatorInfo = new BillValidator();
		if(billValidator.isPresent()) {
			billValidatorInfo=billValidator.get();
		}
		return billValidatorInfo;
	}
	
}
