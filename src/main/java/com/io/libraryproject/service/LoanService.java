package com.io.libraryproject.service;

import com.io.libraryproject.dto.request.LoanRequest;
import com.io.libraryproject.dto.response.LoanResponse;
import com.io.libraryproject.entity.Book;
import com.io.libraryproject.entity.Loan;
import com.io.libraryproject.entity.User;
import com.io.libraryproject.exception.BadRequestException;
import com.io.libraryproject.exception.message.ErrorMessage;
import com.io.libraryproject.mapper.BookMapper;
import com.io.libraryproject.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserService userService;
    private final BookService bookService;
    private final BookMapper bookMapper;

    public LoanService(LoanRepository loanRepository, UserService userService, BookService bookService, BookMapper bookMapper) {
        this.loanRepository = loanRepository;

        this.userService = userService;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }
    public List<Loan> findByBookId(Long id) {
        List<Loan> loans = loanRepository.findByBookId(id);
        return loans;
    }
    public LoanResponse saveLoan(LoanRequest loanRequest) {
        User user = userService.getCurrentUser();
        Book book = bookService.getBook(loanRequest.getBookId());

        LocalDateTime now = LocalDateTime.now();

        List<Loan> loanExpireds = loanRepository.findLoanExpiredByUser(loanRequest.getUserId(),now);
        checkBookIsAvailableAndLoanable(book);

        if (loanExpireds.size()>0) {
            throw new IllegalStateException(ErrorMessage.BOOK_IS_NOT_HAVE_PERMISSON);
        }
        List<Loan> activeLoansOfUser = loanRepository.findLoansByUserIdAndExpireDateIsNull(user.getId());

        Loan loan = new Loan();
        switch (user.getScore()) {
            case -2:
                if (activeLoansOfUser.size()<1){
                    loan.setLoanDate(now);
                    loan.setExpireDate(now.plusDays(3));
                }
                break;
            case -1:
                if (activeLoansOfUser.size()<2){
                    loan.setLoanDate(now);
                    loan.setExpireDate(now.plusDays(6));
                }
                break;
            case 0:
                if (activeLoansOfUser.size()<3){
                    loan.setLoanDate(now);
                    loan.setExpireDate(now.plusDays(10));
                }
                break;
            case 1:
                if (activeLoansOfUser.size()<4){
                    loan.setLoanDate(now);
                    loan.setExpireDate(now.plusDays(15));
                }
                break;
            case 2:
                loan.setLoanDate(now);
                loan.setExpireDate(now.plusDays(20));
                break;
            default:
                throw new BadRequestException(ErrorMessage.SCORE_IS_NOT_ENOUGH);
        }
        loan.setUser(user);
        loan.setBook(book);

        loan.setNotes(loanRequest.getNotes());

        loanRepository.save(loan);

        book.setLoanable(false);

        bookService.save(book);

        LoanResponse loanResponse = new LoanResponse();
        loanResponse.setId(loan.getId());
        loanResponse.setUserId(user.getId());
        loanResponse.setBookId(bookMapper.bookToBookDTO(book));

        return loanResponse;

    }
    private boolean checkBookIsAvailableAndLoanable(Book book) {
        if (!book.isActive()) {
            throw new BadRequestException(ErrorMessage.BOOK_IS_NOT_AVAILABLE_MESSAGE);
        }
        if (!book.isLoanable()) {
            throw new BadRequestException(ErrorMessage.BOOK_IS_NOT_LOANABLE_MESSAGE);
        }
        return true;
    }
}
