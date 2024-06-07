package com.cash.atm.repository;

import com.cash.atm.entity.ATMEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ATMRepository extends JpaRepository<ATMEntity, Long> {
}
