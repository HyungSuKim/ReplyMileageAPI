package com.triple.replymileageapi.useracct;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserAcctRepo extends JpaRepository<UserAcct, String> {
    List<UserAcct> findByUserId(String userId);
}
