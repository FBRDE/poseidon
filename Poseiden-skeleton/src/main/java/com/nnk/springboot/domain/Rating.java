package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor @AllArgsConstructor @Getter  @Setter
@Entity
@Table(name = "rating")
public class Rating {
    // Map columns in data table RATING with corresponding java fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Integer id;
    @NotBlank
    @Column(name="moodys_rating")
    String moodysRating;
    @NotBlank
    @Column(name="sand_p_rating")
    String sandPRating;
    @NotBlank
    @Column(name="fitch_rating")
    String fitchRating;

    @Column(name="order_number")
    Integer orderNumber;
    public Rating(String moodys_rating, String sand_pRating, String fitch_rating, int order) {
        moodysRating=moodys_rating;
        sandPRating=sand_pRating;
        fitchRating=fitch_rating;
        orderNumber=order;
    }
}
