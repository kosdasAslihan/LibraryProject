package com.io.libraryproject.repository;

import com.io.libraryproject.entity.Loan;
import com.io.libraryproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByUser(User user);

    List<Loan> findAllByUserId(Long id);
}
