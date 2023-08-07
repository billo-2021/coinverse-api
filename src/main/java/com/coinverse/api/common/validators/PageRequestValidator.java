package com.coinverse.api.common.validators;

import com.coinverse.api.common.exceptions.ValidationException;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequestValidator {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer MAX_PAGE_SIZE = 100;

    public static Pageable validate(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        if (pageNumber < DEFAULT_PAGE_NUMBER) {
            throw new ValidationException("pageNumber must be greater than or equal to 0");
        }

        if (pageSize < 1) {
            throw new ValidationException("pageSize must be greater than or equal to 1");
        }

        if (pageSize > MAX_PAGE_SIZE) {
            throw new ValidationException("pageSize must be less than or equal to " + MAX_PAGE_SIZE);
        }

        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
