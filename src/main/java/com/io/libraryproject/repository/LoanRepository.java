package com.io.libraryproject.repository;

import com.io.libraryproject.entity.Loan;
import com.io.libraryproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByUser(User user);

    List<Loan> findAllByUserId(Long id);

    List<Loan> findByBookId(Long id);

    @Query("select l from Loan l where l.expireDate<:now and l.user.id=:id ")
    List<Loan> findLoanExpiredByUser(@Param("id") Long id, @Param("now") LocalDateTime now);

    @Query("select l from Loan l where l.user.id=:id and l.expireDate=:null")
    List<Loan> findLoansByUserIdAndExpireDateIsNull(@Param("id") Long user);
}
