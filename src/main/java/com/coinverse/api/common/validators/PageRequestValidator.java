package com.coinverse.api.common.validators;

import com.coinverse.api.common.constants.ErrorMessage;

import com.coinverse.api.common.utils.ErrorMessageUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageRequestValidator {
    private static final int MIN_PAGE_NUMBER = 0;
    private static final int MIN_PAGE_SIZE = 1;
    private static final int MAX_PAGE_SIZE = 100;

    public static Pageable validate(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        if (pageNumber < MIN_PAGE_NUMBER) {
            throw ErrorMessageUtils.getValidationException(ErrorMessage.MIN_PAGE_NUMBER_VALIDATION);
        }

        if (pageSize < MIN_PAGE_SIZE) {
            throw ErrorMessageUtils.getValidationException(ErrorMessage.MIN_PAGE_SIZE_VALIDATION);
        }

        if (pageSize > MAX_PAGE_SIZE) {
            throw ErrorMessageUtils.getValidationException(ErrorMessage.MAX_PAGE_SIZE_VALIDATION);
        }

        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
