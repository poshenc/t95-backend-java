package com.t95.t95backend.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.t95.t95backend.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{

    //	@Query("SELECT u FROM users u WHERE u.email = ?1")
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByName(String name);


//    @Query(value = "SELECT * FROM users u WHERE u.name = ?1 AND u.password = ?2", nativeQuery = true)
    User findByNameAndPassword(String name, String password);


}
