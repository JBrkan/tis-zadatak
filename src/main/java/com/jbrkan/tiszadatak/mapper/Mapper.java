package com.jbrkan.tiszadatak.mapper;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public interface Mapper<S, R> {

    S map(@NonNull R r);

    default List<S> mapList(@NonNull List<R> list) {
        return list.stream().map(this::map).toList();
    }

    default Page<S> mapPage(@NonNull Page<R> page) {
        return new PageImpl<>(mapList(page.getContent()), page.getPageable(), page.getTotalElements());
    }
}
