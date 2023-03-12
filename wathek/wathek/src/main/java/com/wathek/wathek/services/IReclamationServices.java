package com.wathek.wathek.services;

import com.wathek.wathek.Entities.Etat;
import com.wathek.wathek.Entities.Reclamation;
import com.wathek.wathek.dto.PageResponse;
import com.wathek.wathek.dto.ReclamationRequest;
import com.wathek.wathek.dto.ReclamationStatsResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IReclamationServices {




    Reclamation ajouterReclamation(ReclamationRequest reclamationRequest);

    Reclamation AddRecAndAssignToCustomer(Reclamation r, Integer idCus);


    Reclamation AddRecAndAssignToCustomer(Reclamation r, Long idCus);

    Reclamation retriveRec(Long idRec);


    List<Reclamation> findByetat(Etat etat);

    /*int getbydate(Date startDate);*/

    List<Reclamation> getbydate(Date date);

    void dd(Long idRec);

    long getnbRecByuser(Integer idCus);


    void fixReclamation(long idRec, String reply,String replays , long idMod) throws InterruptedException;

    Reclamation getById(long id, boolean read);

    PageResponse<Reclamation> findAll(int page, int size, String sortBy, String sort, boolean all);

    ReclamationStatsResponse getStats();

    /*
            @Override
            public List<User> rechercheDynamique(String search) {

                return userRepository.rechercheDynamique(search);
            }
              /* @Override
            public int getNombreReclamations(Long id) {

                Produit produit = produitRepository.findById(id).orElse(null);
                if (produit == null) {
                    return 0;
                }
                int nombreReclamations = produit.getReclamation();
                if (nombreReclamations > 10) {
                    double nouveauPrix = produit.getPrix() * 0.9;
                    produit.setPrix((float) nouveauPrix);
                    produitRepository.save(produit);
                }
                return nombreReclamations;
            }*/
    /*Iterable<Reclamation> search(String description, Long idRec, Date date, Etat etat);



    /*
            @Override
            public List<User> rechercheDynamique(String search) {
        
                return userRepository.rechercheDynamique(search);
            }
              /* @Override
            public int getNombreReclamations(Long id) {
        
                Produit produit = produitRepository.findById(id).orElse(null);
                if (produit == null) {
                    return 0;
                }
                int nombreReclamations = produit.getReclamation();
                if (nombreReclamations > 10) {
                    double nouveauPrix = produit.getPrix() * 0.9;
                    produit.setPrix((float) nouveauPrix);
                    produitRepository.save(produit);
                }
                return nombreReclamations;
            }*/
   // Iterable<Reclamation> search(String description, Long idRec, Date date, Etat etat);



    /*
            @Override
            public List<User> rechercheDynamique(String search) {
        
                return userRepository.rechercheDynamique(search);
            }
              /* @Override
            public int getNombreReclamations(Long id) {
        
                Produit produit = produitRepository.findById(id).orElse(null);
                if (produit == null) {
                    return 0;
                }
                int nombreReclamations = produit.getReclamation();
                if (nombreReclamations > 10) {
                    double nouveauPrix = produit.getPrix() * 0.9;
                    produit.setPrix((float) nouveauPrix);
                    produitRepository.save(produit);
                }
                return nombreReclamations;
            }*/

    //List<User> rechercheDynamique (String search);
   // int getNombreReclamations(Long id);


    //List<Reclamation> getAllRecbyetat(String etat);
}
