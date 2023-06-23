package com.coinverse.api.common.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageRequestValidator {
    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer MAX_PAGE_SIZE = 100;

    public PageRequest validatePageRequest(final Integer pageNumber, final Integer pageSize) {
        Integer parsedPageNumber = pageNumber == null || pageNumber < DEFAULT_PAGE_NUMBER ?
                DEFAULT_PAGE_NUMBER :
                pageNumber;

        Integer parsedPageSize = (pageSize == null || pageSize >= MAX_PAGE_SIZE) ?
                MAX_PAGE_SIZE :
                pageSize;

        return PageRequest.of(parsedPageNumber - 1, parsedPageSize);
    }
}
