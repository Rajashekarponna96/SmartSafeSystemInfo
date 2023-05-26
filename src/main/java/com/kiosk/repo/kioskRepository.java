package com.kiosk.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kiosk.model.Kiosk;


@Repository
public interface kioskRepository extends JpaRepository<Kiosk, Integer>{

	Kiosk findByIpAddress(String ipAddress);
}
