package com.safesmart.safesmart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safesmart.safesmart.builder.ChangeRquestBuilder;
import com.safesmart.safesmart.dto.ChangeValetDenominationsDto;
import com.safesmart.safesmart.model.ActionStatus;
import com.safesmart.safesmart.model.ChangeValetDenominations;
import com.safesmart.safesmart.model.InsertBill;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.model.ValetDenominations;
import com.safesmart.safesmart.remoterepository.Remote_ValetDenominationsRepository;
import com.safesmart.safesmart.repository.ChangeRquestDenominationsRepository;
import com.safesmart.safesmart.repository.UserInfoRepository;
import com.safesmart.safesmart.repository.ValetDenominationsRepository;

@Service
public class ChangeRquestDenominationsService {

	@Autowired
	private ChangeRquestDenominationsRepository changeRquestDenominationsRepository;

	@Autowired
	private ChangeRquestBuilder changeRquestBuilder;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private ValetDenominationsRepository valetDenominationsRepository;
	
	@Autowired
	private Remote_ValetDenominationsRepository remote_ValetDenominations;
	
	@Autowired
	private DataMigrationService dataMigrationService;

	public void createDenominations(ChangeValetDenominationsDto changeValetDenominationsDto) {

		ChangeValetDenominations changeValetDenominations = changeRquestBuilder
				.convertToModel(changeValetDenominationsDto);

		changeValetDenominations.setUpdatedOn(LocalDateTime.now());
		Optional<UserInfo> optional = userInfoRepository.findById(changeValetDenominationsDto.getUpdatedByUser());

		if (optional.isPresent()) {
			changeValetDenominations.setActionStatus(ActionStatus.Created);
			changeValetDenominations.setCreatedBy(optional.get());
			changeValetDenominations.setCreated(LocalDateTime.now());
		}

		Optional<ValetDenominations> optional2 = valetDenominationsRepository
				.findById(changeValetDenominationsDto.getValetDenominationsId());
		if (optional2.isPresent()) {
			ValetDenominations valetDenominations = optional2.get();
			valetDenominations.setCents(changeValetDenominationsDto.getValetDenominationsDto().getCents());
			valetDenominations.setDen_1$(changeValetDenominationsDto.getValetDenominationsDto().getDen_1$());
			valetDenominations.setDen_10$(changeValetDenominationsDto.getValetDenominationsDto().getDen_10$());
			valetDenominations.setDen_100$(changeValetDenominationsDto.getValetDenominationsDto().getDen_100$());
			valetDenominations.setDen_20$(changeValetDenominationsDto.getValetDenominationsDto().getDen_20$());
			valetDenominations.setDen_5$(changeValetDenominationsDto.getValetDenominationsDto().getDen_5$());
			valetDenominations.setDen_50$(changeValetDenominationsDto.getValetDenominationsDto().getDen_50$());
			valetDenominations.setDimes(changeValetDenominationsDto.getValetDenominationsDto().getDimes());
			valetDenominations.setNickels(changeValetDenominationsDto.getValetDenominationsDto().getNickels());
			valetDenominations.setQuarters(changeValetDenominationsDto.getValetDenominationsDto().getQuarters());
			valetDenominations.setModified(LocalDateTime.now());
			valetDenominations.setModifiedBy(optional.get());
			valetDenominations.setSync(true);
			valetDenominations.setActionStatus(ActionStatus.Updated);
			
			ValetDenominations dbValetDenominations = remote_ValetDenominations.findByIdentifier(valetDenominations.getIdentifier());
			Hibernate.initialize(dbValetDenominations.getCreatedBy());
            Hibernate.initialize(dbValetDenominations.getModifiedBy());
			if (dbValetDenominations == null) {
				dbValetDenominations.setSync(true);
			}

			try {
				if(dbValetDenominations != null) {
				ValetDenominations upadtedvaletDenominations = dataMigrationService.convertToValetDenominations(valetDenominations,true);
				Hibernate.initialize(valetDenominations.getCreatedBy());
	            Hibernate.initialize(valetDenominations.getModifiedBy());

	            
				upadtedvaletDenominations.setSync(true);
				upadtedvaletDenominations.setId(dbValetDenominations.getId());
				upadtedvaletDenominations.setCreatedBy(dbValetDenominations.getCreatedBy());
				remote_ValetDenominations.save(upadtedvaletDenominations);
				}
				
			} catch (Exception e) {
				valetDenominations.setSync(false);
			}

			
			
			valetDenominationsRepository.save(valetDenominations);
			changeValetDenominations.setValetDenominations(valetDenominations);
		}
		//changeValetDenominations.setCreatedBy(changeValetDenominationsDto.getUpdatedByUser());
		changeRquestDenominationsRepository.save(changeValetDenominations);
	}

	public List<ChangeValetDenominationsDto> findAllDenominations(Long valetDenominationId) {

		List<ChangeValetDenominationsDto> response = new ArrayList<ChangeValetDenominationsDto>();

		List<ChangeValetDenominations> list = changeRquestDenominationsRepository
				.findByValetDenominations_Id(valetDenominationId);
		if (list != null && list.size() > 0) {
			response = changeRquestBuilder.convertToDtosList(list);
		}

		return response;
	}

}
