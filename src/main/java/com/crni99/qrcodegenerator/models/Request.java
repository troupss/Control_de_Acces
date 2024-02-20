package com.crni99.qrcodegenerator.models;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    private Partits partits;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String email;

    @NotNull
    private String productName;

}
