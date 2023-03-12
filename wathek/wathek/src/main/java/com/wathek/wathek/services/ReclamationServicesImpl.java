package com.wathek.wathek.services;

import com.wathek.wathek.Entities.*;
import com.wathek.wathek.Repositories.*;
import com.wathek.wathek.dto.PageResponse;
import com.wathek.wathek.dto.ReclamationRequest;
import com.wathek.wathek.dto.ReclamationStatsResponse;
import com.wathek.wathek.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class ReclamationServicesImpl implements IReclamationServices {



    @Autowired
    IReclamationRepository reclamationRepository;

    @Autowired
    IProduitRepository produitRepository;


    @Autowired
    NotificationService notificationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BadWordService badWordService;


    /*@Override
    public Reclamation ajouterf(Reclamation reclamation, Long idUser, Long idProduit) {
        User customer = userRepository.findById(idUser).orElse(null);
        Produit produit = produitRepository.findById(idProduit).orElse(null);
        reclamation.setCustomer(customer);
        reclamation.setProduit(produit);

        return reclamationRepository.save(reclamation);
    }
*/
    @Override
    public Reclamation ajouterReclamation(ReclamationRequest reclamationRequest) {

        List<String> errorList = validateReclamation(reclamationRequest);
        if (!errorList.isEmpty()) {
            throw new InputValidationException(errorList);
        }
        Reclamation reclamation = new Reclamation();

        reclamation.setLivreur(userRepository.findById(reclamationRequest.getLivreur()).get());
        reclamation.setAcheteur(userRepository.findById(reclamationRequest.getAcheteur()).get());
        reclamation.setProduit(produitRepository.findById(reclamationRequest.getProduit()).get());

        reclamation.setDescription(reclamationRequest.getText());
        reclamation.setDate(new Date());

        return reclamationRepository.save(reclamation);

    }

    private List<String> validateReclamation(ReclamationRequest reclamationRequest) {
        List<String> errors = new ArrayList<>();

        if (badWordService.isWordForbidden(reclamationRequest.getText())) {
            errors.add("La description contient un mot interdit.");
        }

        Optional<User> optionalUser = userRepository.findById(reclamationRequest.getLivreur());
        if (!optionalUser.isPresent()) {
            errors.add("id Livreur invalide");
        } else {
            User user = optionalUser.get();
            if (!Role.livreur.equals(user.getRole())) {
                errors.add("Role invalid (livreur)");
            }
        }

        Optional<Produit> optionalProduit = produitRepository.findById(reclamationRequest.getProduit());
        if (!optionalProduit.isPresent()) {
            errors.add("id Produit invalide");
        }

        Optional<User> opionalCustomer = userRepository.findById(reclamationRequest.getAcheteur());
        if (!opionalCustomer.isPresent()) {
            errors.add("id Customer invalide");
        } else {
            User user = opionalCustomer.get();
            if (!Role.acheteur.equals(user.getRole())) {
                errors.add("Role invalid (acheteur)");
            }
        }
        return errors;
    }

    @Override
    public Reclamation AddRecAndAssignToCustomer(Reclamation r, Integer idCus) {
        return null;
    }
   /* @Override
    public Reclamation affecterwathe(Reclamation a, Integer idMod) {
        Moderator moderator = moderatorRepository.findById(idMod).get();
        a.setModerators(moderator);
        return reclamationRepository.save(a);

    }*/

    @Override
    public Reclamation AddRecAndAssignToCustomer(Reclamation r, Long idCus) {
        User customer = userRepository.findById(idCus).get();
        r.setAcheteur(customer);

        return reclamationRepository.save(r);
    }
   /* @RestController
    @RequestMapping("/api")
    public class ComplaintController {

        @Autowired
        private BadWordService badWordService;

        @PostMapping("/complaints")
        public ResponseEntity<String> submitComplaint(@RequestBody String complaintText) {
            String filteredText = badWordService.filterBadWords(complaintText);
            // ici, vous pouvez effectuer d'autres opérations sur la réclamation filtrée,
            // comme l'enregistrer dans une base de données
            return new ResponseEntity<>(filtered*/


   /* @Override
    public Reclamation AddRecAndAssignToMod(Reclamation rm, Integer idMod) {
        Moderator moderator = moderatorRepository.findById(idMod).get();
        rm.setModerators(moderator);

        return reclamationRepository.save(rm);
    }*/

   /* @Override
    @Transactional
    public Reclamation AddrecAndAssignToCusandMod(Reclamation rcm, Integer idCus, Integer idMod) {
        Customer customer = customerRepository.findById(idCus).orElse(null);
        Moderator moderator = moderatorRepository.findById(idMod).orElse(null);
        rcm.setCustomers(customer);
        rcm.setModerators(moderator);

        return reclamationRepository.save(rcm);
    }*/


    @Override
    public Reclamation retriveRec(Long idRec) {

        return reclamationRepository.findById(idRec).orElse(null);
    }

    @Override
    public List<Reclamation> findByetat(Etat etat) {
        return reclamationRepository.findByEtat(etat);
    }

    @Override
    public List<Reclamation> getbydate(Date date) {
        return reclamationRepository.getbydate(date);
    }

    @Override
    public void dd(Long idRec) {
        Reclamation rt = reclamationRepository.findById(idRec).orElse(null);
        rt.setEtat(Etat.TRAITER);
        reclamationRepository.save(rt);
    }

    @Override
    public long getnbRecByuser(Integer idCus) {
        return reclamationRepository.countByCustomerId(idCus);
    }

    @Override
    public void fixReclamation(long idRec, String reply, String replays ,long idMod) throws InterruptedException {

        Reclamation reclamation = retriveRec(idRec);

        if (reclamation == null) {
            throw new IllegalArgumentException("Invalid id");

        }

        Optional<User> optionalUser = userRepository.findById(idMod);

        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("Invalid id");

        }

        User moderateur = optionalUser.get();

        if (!Role.moderateur.equals(moderateur.getRole())) {
            throw new InputValidationException(new ArrayList<>(Collections.singleton("Invalid User Role")));
        }

        User customer = reclamation.getAcheteur();
        User livreur = reclamation.getLivreur();

        String emailCustomer = customer.getEmailUser();
        String numLivreur = "+216"+ livreur.getNumTel();
        reclamation.setEtat(Etat.TRAITER);
        reclamation.setReponse(reply);

        reclamation.setModerateur(moderateur);

        reclamationRepository.save(reclamation);

        notificationService.sendMailNotification(emailCustomer,"Update Reclamation", reply);
        notificationService.sendMessageNotification(numLivreur,"+15674065597", replays);
    }

    @Override
    public Reclamation getById(long id, boolean read) {

        Optional<Reclamation> optionalReclamation = reclamationRepository.findById(id);

        if (optionalReclamation.isPresent()) {
            Reclamation reclamation = optionalReclamation.get();
            if (read) {
                reclamation.setReadr(true);
                reclamationRepository.save(reclamation);
            }
            return reclamation;
        }
        throw new EntityNotFoundException("Reclamation NOT FOUND");
    }

    @Override
    public PageResponse<Reclamation> findAll(int page, int size, String sortBy, String sort, boolean all) {
        Pageable pagingSort;
        if (sort.equalsIgnoreCase("desc")) {
            pagingSort = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pagingSort = PageRequest.of(page, size, Sort.by(sortBy));
        }
        Page<Reclamation> reclamationPage;
        if (all) {

            reclamationPage = reclamationRepository.findAll(pagingSort);
        } else {

            reclamationPage = reclamationRepository.findAllByReadr(false ,pagingSort);
        }
        return new PageResponse<>(reclamationPage.getContent(), reclamationPage.getNumber(),
                reclamationPage.getTotalElements(),
                reclamationPage.getTotalPages());
    }

    @Override
    public ReclamationStatsResponse getStats() {
        ReclamationStatsResponse resp = new ReclamationStatsResponse();
        resp.setTotale(reclamationRepository.count());
        resp.setTraiter(reclamationRepository.countByEtat(Etat.TRAITER));
        resp.setNonTraiter(reclamationRepository.countByEtat(Etat.NONTRAITER));
        resp.setLu(reclamationRepository.countByReadr(true));
        resp.setNonLu(reclamationRepository.countByReadr(false));
        return resp;
    }



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
   /* @Override
    public Iterable<Reclamation> search(String description , Long idRec, Date date, Etat etat) {
    Specification<Reclamation> spec = Specification.where(null);
    if (description != null && !description.isEmpty()) {
        spec = spec.and((root, query, builder) ->
                builder.like(root.get("description"), "%" + description + "%"));
    }
    if (idRec != null && !idRec.toString().isEmpty()) {
        spec = spec.and((root, query, builder) ->
                builder.like(root.get("IdRec"), "%" + idRec + "%"));
    }
    if (date != null && !date.toString().isEmpty()) {
        spec = spec.and((root, query, builder) ->
                builder.like(root.get("date"), "%" + date + "%"));
    }
    if (etat != null && !etat.toString().isEmpty()) {
        spec = spec.and((root, query, builder) ->
                builder.like(root.get("etat"), "%" + etat + "%"));
    }
    return reclamationRepository.findAll(spec);*/












}





   /* @Override
    public List<Reclamation> getAllRecbyetat(Reclamation ru String Etat){
        Etat etat1= Etat.NONTRAITER;
         reclamationRepository.findByEtat(etat);
        return null;
    }*/

    /*public void calcNbReclamations(Produit produit,Float Prix ,Long idProduit) {
        Produit produit1 = produitRepository.findById(idProduit).orElse(null);
        int nbReclamations = produit1.getReclamation().size();
        if (nbReclamations > 10) {
            double prix = produit.getPrix();
            prix *= 0.9;  // Applique la réduction de 10%
            produit.setPrix((float) prix);
            produitRepository.save(produit);
        }
    }
}

     */





