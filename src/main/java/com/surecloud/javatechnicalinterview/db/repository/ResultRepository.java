package com.surecloud.javatechnicalinterview.db.repository;

import com.surecloud.javatechnicalinterview.db.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, UUID> {
}
