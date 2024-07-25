package vn.edu.nlu.web.chat.dto.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Builder
public class PageResponse<T> implements Serializable {
    private int page;
    private int size;
    private int total;
    private T items;

    public static <T> PageResponse<T> emptyPageResponse() {
        return PageResponse.<T>builder()
                .size(0)
                .total(0)
                .page(0)
                .build();
    }
}
