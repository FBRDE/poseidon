package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor @Getter  @Setter
@Entity
@Table(name = "bidlist")
public class BidList {
    // Map columns in data table BIDLIST with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bid_list_id")
    private int bidListId;
    @Column(name="account")
    @NotBlank(message = "Account is mandatory")
    String account;
    @Column(name="type")
    @NotBlank(message = "Type is mandatory")
    String type;
    @Column(name="bid_quantity")
    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/,message = "Bid quantity must have just 2 digit after '.' and 10 integer before '.'")
    Double bidQuantity;
    @Column(name="ask_quantity")
    Double askQuantity;
    @Column(name="bid")
    Double bid;
    @Column(name="ask")
    Double ask;
    @Column(name="benchmark")
    String benchmark;
    @Column(name="bid_list_date")
    LocalDateTime bidListDate;
    @Column(name="commentary")
    String commentary;
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

    public BidList(String account_test, String type_test, double v) {
        account=account_test;
        type=type_test;
        bidQuantity=v;

    }
}
