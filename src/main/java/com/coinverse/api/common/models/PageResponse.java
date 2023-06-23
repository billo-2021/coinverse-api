package com.coinverse.api.common.models;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponse <T> {
    private final Integer count;
    private final Long total;
    private final List<T> data;

    public PageResponse(final Integer count, final Long total, final List<T> data) {
        this.count = count;
        this.total = total;
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getData() {
        return data;
    }

    public static <U> PageResponse<U> of(Page<U> page) {
        return new PageResponse<>(
                page.getNumberOfElements(),
                page.getTotalElements(),
               page.toList()
        );
    }
}
