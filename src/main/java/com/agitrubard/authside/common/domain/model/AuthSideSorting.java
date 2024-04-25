package com.agitrubard.authside.common.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthSideSorting {

    @NotNull
    public String property;

    @NotNull
    public Sort.Direction direction;

    public static List<AuthSideSorting> of(final Sort sort) {
        return sort.stream()
                .map(order -> AuthSideSorting.builder()
                        .property(order.getProperty())
                        .direction(order.getDirection())
                        .build())
                .toList();
    }

}
