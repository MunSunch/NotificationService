package com.munsun.notifications.repository;

import com.munsun.notifications.model.StationPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationPathRepository extends JpaRepository<StationPath, Integer> {}