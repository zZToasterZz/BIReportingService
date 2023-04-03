package com.reporting.bi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reporting.bi.entity.TemplateRegister;

@Repository
public interface TemplateRegisterRepository extends JpaRepository<TemplateRegister, UUID> {

}
