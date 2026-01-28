package com.sinvaldev.agregadordeinvestimentos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_account_Stock")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AccountStock {

    @EmbeddedId
    private AccountStockId id;

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("stockId")
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "quantity")
    private Integer quantity;
}
