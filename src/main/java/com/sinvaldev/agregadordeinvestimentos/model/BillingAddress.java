package com.sinvaldev.agregadordeinvestimentos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_billing_address")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BillingAddress {

    @Id
    @Column(name = "account_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;
}
