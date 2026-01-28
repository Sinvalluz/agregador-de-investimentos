package com.sinvaldev.agregadordeinvestimentos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_stock")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Stock {

    @Id
    @Column(name = "Stock_id")
    private String StockId;

    @Column(name = "description")
    private String description;
}
