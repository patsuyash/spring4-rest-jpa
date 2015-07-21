package com.suyash586.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suyash586.rest.domain.Office;

public interface OfficeRepository extends JpaRepository<Office, String> {
}
