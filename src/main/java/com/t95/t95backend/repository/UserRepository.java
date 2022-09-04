package com.t95.t95backend.repository;


import com.t95.t95backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

    //	@Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<User> findUserByEmail(String email);

}
