package com.ccsw.tutorial.loans;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.common.exception.ConflictException;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loans.model.FilterDto;
import com.ccsw.tutorial.loans.model.Loan;
import com.ccsw.tutorial.loans.model.LoanDto;
import com.ccsw.tutorial.loans.model.LoanSearchDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {
        FilterDto filterParams = dto.getFilterDto();

        Specification<Loan> spec = Specification.where(null);

        if (filterParams != null) {
            if (filterParams.getIdGame() != null) {
                spec = spec.and(new LoanSpecification(new SearchCriteria("game.id", ":", filterParams.getIdGame())));
            }
            if (filterParams.getIdClient() != null) {
                spec = spec
                        .and(new LoanSpecification(new SearchCriteria("client.id", ":", filterParams.getIdClient())));
            }
            LocalDate searchDate = filterParams.getDate();
            if (searchDate != null) {

                spec = spec.and(new LoanSpecification(new SearchCriteria("startDate", ">=", filterParams.getDate())))
                        .and(new LoanSpecification(new SearchCriteria("endDate", "<=", filterParams.getDate())));
            }
        }

        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    @Override
    public void save(Long id, LoanDto dto) throws ConflictException {
        Loan loan;

        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.get(id);
        }

        BeanUtils.copyProperties(dto, loan, "id", "game", "client");

        loan.setGame(gameService.get(dto.getGame().getId()));
        loan.setClient(clientService.get(dto.getClient().getId()));

        LocalDate startDate = loan.getStartDate();
        LocalDate endDate = loan.getEndDate();

        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new ConflictException("La fecha de fin no puede ser anterior a la fecha de comienzo", 410);
        }

        if (ChronoUnit.DAYS.between(startDate, endDate) > 14) {
            throw new ConflictException("El periodo de préstamo máximo es de 14 días", 411);
        }

        List<Loan> loansForClientInDateRange = loanRepository
                .findByClientAndStartDateLessThanEqualAndEndDateGreaterThanEqual(loan.getClient(), endDate, startDate);

        if (loansForClientInDateRange.size() >= 2) {
            throw new ConflictException("El cliente ya tiene 2 préstamos en este rango de fechas", 412);
        }

        List<Loan> loansInDateRange = loanRepository
                .findByGameAndStartDateLessThanEqualAndEndDateGreaterThanEqual(loan.getGame(), endDate, startDate);

        for (Loan existingLoan : loansInDateRange) {
            if (!existingLoan.getId().equals(loan.getId())) {
                throw new ConflictException("El juego ya está prestado a otro cliente en este rango de fechas", 413);
            }
        }

        this.loanRepository.save(loan);

    }

    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }

    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

    @Override
    public Optional<Loan> findOne(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        Optional<Loan> loan = this.loanRepository.findById(id);

        if (loan.isEmpty()) {
            throw new Exception("Not find");
        }

        return loan;
    }

}
