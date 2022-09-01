package com.t95.t95backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.t95.t95backend.entity.UserAccount;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long>{

    //	@Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<UserAccount> findUserByEmail(String email);

}
