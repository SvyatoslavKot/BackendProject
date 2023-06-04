package com.example.users_module.repository;

import com.example.users_module.model.AppUserClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserClientRepository extends JpaRepository<AppUserClient, Long> {

    Optional<AppUserClient> findAppUserClientByMail (String mail);
}
