package com.ccsw.tutorial.loans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.loans.model.Loan;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LoanSpecification implements Specification<Loan> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LoanSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Predicate toPredicate(Root<Loan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            Path<String> path = (Path<String>) getPath(root);
            if (path.getJavaType() == String.class) {
                return builder.like(path, "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(path, criteria.getValue());
            }
        }

        if (criteria.getOperation().equalsIgnoreCase("<=") && criteria.getValue() != null) {
            Path<LocalDate> path = (Path<LocalDate>) getPath(root);

            LocalDate date;
            date = parseStringToLocalDate(criteria.getValue().toString());
            return builder.greaterThanOrEqualTo(path, date);

        } else if (criteria.getOperation().equalsIgnoreCase(">=") && criteria.getValue() != null) {
            Path<LocalDate> path = (Path<LocalDate>) getPath(root);

            LocalDate date;
            date = parseStringToLocalDate(criteria.getValue().toString());
            return builder.lessThanOrEqualTo(path, date);
        }

        return null;
    }

    private LocalDate parseStringToLocalDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(string, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Error al analizar la cadena en un objeto LocalDate: " + e.getMessage());
            return null;
        }
    }

    private Path<?> getPath(Root<Loan> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

}