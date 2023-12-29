package com.safesmart.safesmart.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safesmart.safesmart.dto.TruckChangeRequestDto;
import com.safesmart.safesmart.model.ActionStatus;
import com.safesmart.safesmart.model.ChangeRequest;
import com.safesmart.safesmart.model.TruckChangeRequest;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.ChangeRequestRepository;
import com.safesmart.safesmart.repository.TruckChangeRequestRepository;
import com.safesmart.safesmart.repository.UserInfoRepository;
import com.safesmart.safesmart.repository.ValetDenominationsRepository;
import com.safesmart.safesmart.util.EmailTemplate;

@Service
public class TruckChangeRequestService {

	@Autowired
	private TruckChangeRequestRepository truckChangeRequestRepository;

	@Autowired
	private ChangeRequestRepository changeRequestRepository;

	@Autowired
	private ValetDenominationsRepository valetDenominationsRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private EmailTemplate emailTemplate;

//	public void createTruckChangeRequest(TruckChangeRequestDto truckChangeRequestDto) {
//		TruckChangeRequest truckChangeRequest = new TruckChangeRequest();
//		BeanUtils.copyProperties(truckChangeRequestDto, truckChangeRequest);
//		Optional<UserInfo> optional = userInfoRepository.findById(truckChangeRequestDto.getUpdatedByUser());
//		if (optional.isPresent()) {
//			truckChangeRequest.setActionStatus(ActionStatus.Created);
//
//			truckChangeRequest.setCreatedBy(optional.get());
//			truckChangeRequest.setCreated(LocalDateTime.now());
//		}
//		//ValetDenominations dbValetDenominations = valetDenominationsRepository.findByType(truckChangeRequestDto.getType());
//		// add valetDenomination to truckChange 
//		
//		
//		//updateValetWithTruckchangeRequest(truckChangeRequestDto, dbValetDenominations);
//		//dbValetDenominations.setModifiedBy(optional.get());
//		//dbValetDenominations.setModified(LocalDateTime.now());
//		//valetDenominationsRepository.save(dbValetDenominations);
//		String status="";
//		if(truckChangeRequestDto.getOrderStatus().toString()=="Ordered") {
//			status="Pending";
//		}
//		else if(truckChangeRequestDto.getOrderStatus().toString()=="Delivered") {
//			status="Ordered";
//		}
//		ChangeRequest changeRequest = changeRequestRepository.findByTypeAndOrderStatus(truckChangeRequestDto.getType(), status);
//		changeRequest.setOrderStatus(truckChangeRequestDto.getOrderStatus().toString());
//		changeRequestRepository.save(changeRequest);
//		truckChangeRequestRepository.save(truckChangeRequest);
//		emailTemplate.confirmTruckchangeMail(truckChangeRequest);
//	}

//	public void createTruckChangeRequest(TruckChangeRequestDto truckChangeRequestDto) {
//		TruckChangeRequest truckChangeRequest = new TruckChangeRequest();
//		BeanUtils.copyProperties(truckChangeRequestDto, truckChangeRequest);
//
//		Optional<UserInfo> optional = userInfoRepository.findById(truckChangeRequestDto.getUpdatedByUser());
//		if (optional.isPresent()) {
//			truckChangeRequest.setActionStatus(ActionStatus.Created);
//			truckChangeRequest.setCreatedBy(optional.get());
//			truckChangeRequest.setCreated(LocalDateTime.now());
//		}
//
//		String status = getStatusForOrder(truckChangeRequestDto.getOrderStatus().toString());
//
//		ChangeRequest changeRequest = changeRequestRepository.findByTypeAndOrderStatus(truckChangeRequestDto.getType(),
//				status);
//
//		if (changeRequest != null) {
//			changeRequest.setOrderStatus(truckChangeRequestDto.getOrderStatus().toString());
//			changeRequestRepository.save(changeRequest);
//		} else {
//			// Handle the case when no matching record is found.
//		}
//
//		truckChangeRequestRepository.save(truckChangeRequest);
//	}
//
//	private String getStatusForOrder(String orderStatus) {
//		switch (orderStatus) {
//		case "Ordered":
//			return "Pending";
//		case "Delivered":
//			return "Ordered";
//		default:
//			// Handle unknown order status or provide a default value
//			return "UnknownStatus";
//		}
//	}
	public void createTruckChangeRequest(TruckChangeRequestDto truckChangeRequestDto) {
	    TruckChangeRequest truckChangeRequest = new TruckChangeRequest();
	    BeanUtils.copyProperties(truckChangeRequestDto, truckChangeRequest);

	    Optional<UserInfo> optional = userInfoRepository.findById(truckChangeRequestDto.getUpdatedByUser());
	    if (optional.isPresent()) {
	        truckChangeRequest.setActionStatus(ActionStatus.Created);
	        truckChangeRequest.setCreatedBy(optional.get());
	        truckChangeRequest.setCreated(LocalDateTime.now());
	    }

	    String status = "";
	    if ("Ordered".equals(truckChangeRequestDto.getOrderStatus().toString())) {
	        status = "Pending";
	    } else if ("Delivered".equals(truckChangeRequestDto.getOrderStatus().toString())) {
	        status = "Ordered";
	    }
	    List<ChangeRequest> chr=changeRequestRepository.findByTypeAndOrderStatus(truckChangeRequestDto.getType(), status);
		ChangeRequest changeRequest=chr.stream().findFirst().orElse(null);
		
	    //ChangeRequest changeRequest = changeRequestRepository.findByTypeAndOrderStatus(
	            //truckChangeRequestDto.getType(), status);

	    if (changeRequest != null) {
	        changeRequest.setOrderStatus(truckChangeRequestDto.getOrderStatus().toString());
	        changeRequestRepository.save(changeRequest);
	    }

	    truckChangeRequestRepository.save(truckChangeRequest);
	 //   emailTemplate.confirmTruckchangeMail(truckChangeRequest);
	}

	public List<TruckChangeRequest> findAll() {
		return truckChangeRequestRepository.findAll();
	}

	public void cancelTruckChangeRequest(TruckChangeRequestDto truckChangeRequestDto) {
		TruckChangeRequest truckChangeRequest = new TruckChangeRequest();
		BeanUtils.copyProperties(truckChangeRequestDto, truckChangeRequest);
		Optional<UserInfo> optional = userInfoRepository.findById(truckChangeRequestDto.getUpdatedByUser());
		if (optional.isPresent()) {
			truckChangeRequest.setCreatedBy(optional.get());
			truckChangeRequest.setCreated(LocalDateTime.now());
		}
		// ValetDenominations dbValetDenominations =
		// valetDenominationsRepository.findByType(truckChangeRequestDto.getType());
		// add valetDenomination to truckChange

		// updateValetWithTruckchangeRequest(truckChangeRequestDto,
		// dbValetDenominations);
		// dbValetDenominations.setModifiedBy(optional.get());
		// dbValetDenominations.setModified(LocalDateTime.now());
		// valetDenominationsRepository.save(dbValetDenominations);

		//comme
//		ChangeRequest changeRequest = changeRequestRepository.findByTypeAndOrderStatus(truckChangeRequestDto.getType(),
//				"Ordered");
//		changeRequest.setOrderStatus("Cancelled");
//		changeRequestRepository.save(changeRequest);
//		truckChangeRequestRepository.save(truckChangeRequest);
//		emailTemplate.cancelTruckchangeMail(truckChangeRequest);
//	}
		ChangeRequest changeRequest = (ChangeRequest) changeRequestRepository.findByTypeAndOrderStatus(truckChangeRequestDto.getType(),
				"Ordered");
		changeRequest.setOrderStatus("Cancelled");
		changeRequestRepository.save(changeRequest);
		truckChangeRequestRepository.save(truckChangeRequest);
		emailTemplate.cancelTruckchangeMail(truckChangeRequest);
	}

}