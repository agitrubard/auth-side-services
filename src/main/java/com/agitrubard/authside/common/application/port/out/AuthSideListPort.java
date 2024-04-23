package com.agitrubard.authside.common.application.port.out;

import com.agitrubard.authside.common.domain.model.AuthSidePaging;
import com.agitrubard.authside.common.domain.model.AuthSideSorting;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AuthSideListPort {

    default Pageable toPageable(AuthSidePaging pagination, List<AuthSideSorting> sort) {

        if (sort != null) {
            return PageRequest.of(
                    pagination.getPage(),
                    pagination.getPageSize(),
                    Sort.by(sort.stream()
                            .map(sortable -> Sort.Order.by(sortable.getProperty()).with(sortable.getDirection()))
                            .toList())
            );
        }

        return PageRequest.of(
                pagination.getPage(),
                pagination.getPageSize()
        );

    }

}
