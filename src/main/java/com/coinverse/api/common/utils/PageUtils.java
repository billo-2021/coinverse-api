package com.coinverse.api.common.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {
    public static Pageable getPageable(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
