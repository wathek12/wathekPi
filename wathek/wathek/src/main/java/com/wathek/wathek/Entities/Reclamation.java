package com.wathek.wathek.Entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reclamation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idRec;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String description;
    @Enumerated(EnumType.STRING)
    private Etat etat= Etat.NONTRAITER;

    /*@ManyToOne

    private Moderator moderators;*/
    @OneToOne
    private Produit produit;
    @ManyToOne
    @JoinColumn(name="acheteurId")
    private User acheteur;
    @ManyToOne
    @JoinColumn(name="moderateurId")
    private User moderateur;

    private String reponse;
    @ManyToOne
    @JoinColumn(name="livreurId")
    private User livreur;

    private boolean readr;

   /* @OneToMany(mappedBy = "reclamation")
    private List<Comment> comments;*/







}
