package com.contracts.contracts.repo;

import com.contracts.contracts.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ContractRepo extends JpaRepository<Contract, Long> {
}
