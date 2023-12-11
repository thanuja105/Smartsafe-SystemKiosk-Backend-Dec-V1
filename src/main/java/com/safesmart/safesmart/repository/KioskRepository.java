package com.safesmart.safesmart.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.safesmart.safesmart.model.Kiosk;

@Repository
public interface KioskRepository extends PagingAndSortingRepository<Kiosk, Long> {

	Kiosk findByKioskName(String kioskName);

	List<Kiosk> findByActive(boolean active);

	Kiosk findByKioskId(String kioskId);
	
	Kiosk findByIpAddress(String ipAddress);

	List<Kiosk> findByMacAddress(String macAddress);

	List<Kiosk> findByStoreinfok_Id(Long id);

	List<Kiosk> findBySync(boolean sync);

}