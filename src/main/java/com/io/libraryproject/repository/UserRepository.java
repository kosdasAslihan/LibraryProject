package com.io.libraryproject.repository;

import com.io.libraryproject.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    User findByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findUserById(Long id);

    @Modifying
    @Query("UPDATE User u SET u.firstName=:firstName,u.lastName=:lastName,u.address=:address,u.phone=:phone,u.birthDate=:birthDate,u.email=:email,u.resetPasswordCode=:resetPasswordCode WHERE u.id=:id")
    void update(@Param("id") Long id,
                @Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("address")String address,
                @Param("phone") String phone,
                @Param("birthDate") String birthDate,
                @Param("email") String email,
                @Param("resetPasswordCode") String resetPasswordCode);
}
