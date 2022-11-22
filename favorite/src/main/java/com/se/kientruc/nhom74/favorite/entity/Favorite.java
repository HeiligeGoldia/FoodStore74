package com.se.kientruc.nhom74.favorite.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Favorite implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int fid;

    @Column
    private String email;

    @Column
    private int product_id;

}
