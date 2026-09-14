package com.vishu.project.corebank.repository;

import com.vishu.project.corebank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {}