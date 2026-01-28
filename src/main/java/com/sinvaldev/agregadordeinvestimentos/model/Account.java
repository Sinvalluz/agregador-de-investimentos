package com.sinvaldev.agregadordeinvestimentos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_account")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "account")
    @PrimaryKeyJoinColumn
    private BillingAddress billingAddress;

    @OneToMany(mappedBy = "account")
    private List<AccountStock> accountStocks;


}
