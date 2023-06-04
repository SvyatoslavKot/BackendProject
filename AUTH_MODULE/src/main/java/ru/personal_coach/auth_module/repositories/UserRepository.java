package ru.personal_coach.auth_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.personal_coach.auth_module.model.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByMail(String mail);

}
