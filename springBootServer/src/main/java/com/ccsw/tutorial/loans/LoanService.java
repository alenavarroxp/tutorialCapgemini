package com.ccsw.tutorial.loans;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.loans.model.Loan;
import com.ccsw.tutorial.loans.model.LoanDto;
import com.ccsw.tutorial.loans.model.LoanSearchDto;

public interface LoanService {
    /**
     * @param id
     * @return
     */
    Loan get(Long id);

    /**
     * @param dto
     * @return
     */
    Page<Loan> findPage(LoanSearchDto dto);

    /**
     * @param id
     * @param dto
     */
    void save(Long id, LoanDto dto) throws Exception;

    /**
     * @param id
     * @throws Exception
     */
    void delete(Long id) throws Exception;

    /**
     * @return
     */
    List<Loan> findAll();

    /**
     * @param id
     * @return
     * @throws Exception
     */
    Optional<Loan> findOne(Long id) throws Exception;
}
