package com.bxacosta.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResponseDTO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -222900770295570477L;

    public List<T> data;

    public static <T> ListResponseDTO<T> from(List<T> data) {
        return new ListResponseDTO<>(data);
    }

    public static <T> ListResponseDTO<T> empty() {
        return new ListResponseDTO<>(new ArrayList<>());
    }
}
