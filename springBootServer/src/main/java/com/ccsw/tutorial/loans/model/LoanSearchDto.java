package com.ccsw.tutorial.loans.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

public class LoanSearchDto {

    private PageableRequest pageable;
    private FilterDto filterDto;

    /**
     * @return the pageable
     */
    public PageableRequest getPageable() {
        return pageable;
    }

    /**
     * @param pageable the pageable to set
     */
    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

    /**
     * @return the filterDto
     */
    public FilterDto getFilterDto() {
        return filterDto;
    }

    /**
     * @param filterDto the filterDto to set
     */
    public void setFilterDto(FilterDto filterDto) {
        this.filterDto = filterDto;
    }

}
