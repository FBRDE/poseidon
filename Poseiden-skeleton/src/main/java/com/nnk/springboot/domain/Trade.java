package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor @Getter  @Setter
@Entity
@Table(name = "trade")
public class Trade {
    // Map columns in data table TRADE with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trade_id")
    Integer tradeId;
    @NotBlank(message = "Account is mandatory")
    @Column(name="account")
    String account;
    @Column(name="type")
    String type;
    @Column(name="buy_quantity")
    Double buyQuantity;
    @Column(name="sell_quantity")
    Double sellQuantity;
    @Column(name="buy_price")
    Double buyPrice;
    @Column(name="sell_price")
    Double sellPrice;
    @Column(name="benchmark")
    String benchmark;
    @Column(name="trade_date")
    LocalDateTime tradeDate;
    @Column(name="security")
    String security;
    @Column(name="status")
    String status;
    @Column(name="trader")
    String trader;
    @Column(name="book")
    String book;
    @Column(name="creation_name")
    String creationName;
    @Column(name="creation_date")
    LocalDateTime creationDate;
    @Column(name="revision_name")
    String revisionName;
    @Column(name="revision_date")
    LocalDateTime revisionDate;
    @Column(name="deal_name")
    String dealName;
    @Column(name="deal_type")
    String dealType;
    @Column(name="source_list_id")
    String sourceListId;
    @Column(name="side")
    String side;
    public Trade(String trade_account, String typeV,double buyQuantity) {
        this.account=trade_account;
        this.type=typeV;
        this.buyQuantity=buyQuantity;

    }
}
