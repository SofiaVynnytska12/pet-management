package com.mdotm.pets.assembler;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageableAssembler {

    public Pageable buildPageable(int page, int size, List<String> sort) {
        if (sort == null || sort.isEmpty()) {
            return PageRequest.of(page, size, Sort.unsorted());
        }

        Sort.Order[] orders = sort.stream()
                .map(sortStr -> {
                    boolean descending = sortStr.startsWith("-");
                    String field = sortStr.startsWith("-") || sortStr.startsWith("+") ? sortStr.substring(1) : sortStr;
                    Sort.Direction direction = descending ? Sort.Direction.DESC : Sort.Direction.ASC;
                    return new Sort.Order(direction, field);
                })
                .toArray(Sort.Order[]::new);

        return PageRequest.of(page, size, Sort.by(orders));
    }
}
