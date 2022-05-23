package com.mercadolivre.shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlDTO {
    @NotNull(message = "Valor não pode ser null!")
    private String url;
}
