package com.safesmart.safesmart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safesmart.safesmart.builder.RoleBuilder;
import com.safesmart.safesmart.common.CommonException;
import com.safesmart.safesmart.common.CommonExceptionMessage;
import com.safesmart.safesmart.dto.LocksRequest;
import com.safesmart.safesmart.dto.RoleDto;
import com.safesmart.safesmart.model.ActionStatus;
import com.safesmart.safesmart.model.Locks;
import com.safesmart.safesmart.model.Role;
import com.safesmart.safesmart.model.UserInfo;
import com.safesmart.safesmart.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleBuilder roleBuilder;

	public void add(RoleDto roleDto) {

		Role role = roleBuilder.toModel(roleDto);
		Role rolename = roleRepository.findByName(roleDto.getName());
		if (rolename != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "Rolename");
		}
		Role roledescription = roleRepository.findByDescription(roleDto.getDescription());
		if (roledescription != null) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "Description");
		}

		role = new Role();
		role.setId(roleDto.getId());
		role.setName(roleDto.getName());
		role.setDescription(roleDto.getDescription());

		role.setActionStatus(ActionStatus.Created);
		roleRepository.save(role);
	}
	public void toDelete(Long id) {
		roleRepository.deleteById(id);
	}
	public void upDate(RoleDto roleDto) {
		
		Long id = roleDto.getId();
		
		Role role = roleRepository.findById(id).get();
		Role role2 = roleRepository.findByName(roleDto.getName());
		if(role2!=null && !roleDto.getId().equals(role2.getId())) {
			throw CommonException.CreateException(CommonExceptionMessage.ALREADY_EXISTS, "Role Name");
		}
		role = roleBuilder.toUpdate(roleDto);

		roleRepository.save(role);
	}

	@JsonIgnore
	public List<RoleDto> findAll() {
		List<Role> roles = (List<Role>) roleRepository.findAll();

		return roleBuilder.toDtoList(roles);
	}
	
	public void addWebModule(RoleDto roleDto) {

		Role role = roleRepository.findByName(roleDto.getName());

		if (role != null) {
		role.setWebModule(roleDto.getWebModule());
		role.setFeatures(roleDto.getFeatures());

		roleRepository.save(role);
		}
			

	}

}
