package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Role;
import com.cybertek.accounting.entity.SPTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SPTableRepository extends JpaRepository<SPTable,Long> {
}
