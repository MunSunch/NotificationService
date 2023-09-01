package com.munsun.notifications.repository;

import com.munsun.notifications.model.OthersParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OthersParametersRepository extends JpaRepository<OthersParameters, Integer> {}