package com.wathek.wathek.Repositories;

import com.wathek.wathek.Entities.Etat;
import com.wathek.wathek.Entities.Reclamation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface IReclamationRepository extends PagingAndSortingRepository<Reclamation,Long>, JpaSpecificationExecutor<Reclamation> {


    List<Reclamation> findByEtat(@Param("etat") Etat etat);

    long countByEtat(Etat etat);

  @Query("SELECT w FROM  Reclamation w  WHERE w.date = :date ")
     List<Reclamation> getbydate (@Param("date") Date date);


    @Query("select count(r) from Reclamation r where r.acheteur.idUser=:customerId")
    long countByCustomerId(long customerId);

    Page<Reclamation> findAllByReadr(boolean read, Pageable pagingSort);

    long countByReadr(boolean read);

   /* @Query("SELECT u FROM User u WHERE (:search IS NULL OR u.nomUser LIKE %:search% "
            + " OR u.prenomUser LIKE %:search% OR u.username LIKE %:search% OR u.cinUser LIKE %:search% OR u.phoneNumber LIKE %:search% )")
    List<User> rechercheDynamique(@Param("search") String search);
*/



}
