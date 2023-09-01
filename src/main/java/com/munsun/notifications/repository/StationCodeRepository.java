package com.munsun.notifications.repository;

import com.munsun.notifications.model.StationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationCodeRepository extends JpaRepository<StationCode, Integer> {}