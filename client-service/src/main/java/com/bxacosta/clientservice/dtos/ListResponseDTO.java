package com.bxacosta.clientservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListResponseDTO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 4387735975744375142L;

    public List<T> data;

    public static <T> ListResponseDTO<T> from(List<T> data) {
        return new ListResponseDTO<>(data);
    }
}
