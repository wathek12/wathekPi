package com.wathek.wathek.dto;

import com.wathek.wathek.Entities.Etat;
import com.wathek.wathek.Entities.Produit;
import com.wathek.wathek.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReclamationRequest implements Serializable {


    private String text;

    private long produit;

    private long livreur;

    private long acheteur;
}
