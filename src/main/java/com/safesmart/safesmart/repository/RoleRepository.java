package com.safesmart.safesmart.repository;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.safesmart.safesmart.model.Role;


@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

	Role findByName(String role);

	Role findByname(String name);

	Role findByDescription(String description);

	Role findByIdentifier(String identifier);
	
	List<Role> findBySync(Boolean sync);

	// Role findByDescription(String role);

}
