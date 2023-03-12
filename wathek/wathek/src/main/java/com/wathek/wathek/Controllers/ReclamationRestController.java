package com.wathek.wathek.Controllers;


import com.wathek.wathek.Entities.Comment;
import com.wathek.wathek.Entities.Etat;
import com.wathek.wathek.Entities.Reclamation;
import com.wathek.wathek.Repositories.IReclamationRepository;
import com.wathek.wathek.dto.ReclamationRequest;
import com.wathek.wathek.dto.ReclamationStatsResponse;
import com.wathek.wathek.services.IReclamationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Rec")
public class ReclamationRestController {

    @Autowired
    IReclamationServices reclamationServices;
    @Autowired
    IReclamationRepository reclamationRepositorye;


    @PostMapping
    public ResponseEntity<?> addRec(@RequestBody ReclamationRequest reclamation) {

        Reclamation saved = reclamationServices.ajouterReclamation(reclamation);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getIdRec()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamation(@PathVariable long id, @RequestParam(defaultValue = "true") boolean read) {
        return ResponseEntity.ok(reclamationServices.getById(id, read));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "idRec") String sortBy,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "false") boolean all) {

        return ResponseEntity.ok(reclamationServices.findAll(page, size, sortBy, sort, all));

    }

    /*
    @PostMapping("/affe/{idCus}")

    @ResponseBody
    public Reclamation addMedecinToClinique(@RequestBody Reclamation reclamation, @PathVariable("idCus") Integer idCus) {
        return reclamationServices.AddRecAndAssignToCustomer(reclamation, idCus);
    }

    @PostMapping("/affecterRec/{idCus}/{idProduit}")
    @ResponseBody
    public Reclamation addMedecinToClinique(@RequestBody Reclamation reclamation, @PathVariable("idCus") Integer idCus, @PathVariable("idProduit") Long idProduit) {
        return reclamationServices.ajouterf(reclamation, idCus.longValue(), idProduit);
    }

*/
    @PostMapping("/{idRec}/send")
    public void send(@RequestBody String reply, String replays, @PathVariable("idRec") long idRec, @RequestParam("idMod") long idMod) throws InterruptedException {
        reclamationServices.fixReclamation(idRec, reply, replays, idMod);
    }










    /*@PostMapping("/affecter/{idMod}")
    @ResponseBody
    public  Reclamation addRecToMoc(@RequestBody Reclamation ron, @PathVariable("idMod") Integer idMod){
        return reclamationServices.AddRecAndAssignToMod(ron, idMod);
    }*/

   /* @PostMapping("/addRecw/{idCus}/{idMod}")
    @ResponseBody
    public Reclamation AddrecAndAssignToCusandMod (@RequestBody Reclamation reclamation,  @PathVariable("idCus") Integer idCus,@PathVariable("idMod") Integer idMod){
        return reclamationServices.AddrecAndAssignToCusandMod(reclamation,idCus,idMod);
    }*/

    /*
    @PutMapping("/traiter/{idRec}")
    public void dd(@PathVariable("idRec") Long idRec) {
        reclamationServices.dd(idRec);
    }
*/
    @GetMapping("getRecbyuser/{idCus}")
    @ResponseBody
    public long nbrrdvby(@PathVariable("idCus") Integer idCus) {

        return reclamationServices.getnbRecByuser(idCus);
    }


    @GetMapping("reclam/{etat}")
    public List<Reclamation> getall(@PathVariable("etat") Etat etat) {

        return reclamationServices.findByetat(etat);
    }

    @GetMapping("/stats")
    public ResponseEntity<ReclamationStatsResponse> getStats() {

        return ResponseEntity.ok(reclamationServices.getStats());
    }

   /* @Autowired
    private BadWordService badWordService;

    @PostMapping("/Addaaa")
    public void  createComment(@RequestBody Reclamation description) {
        String filteredContent = badWordService.filter(description.getDescription());
        description.setDescription(filteredContent);

        // Save the filtered comment to the database and return it

    }*/


    /*

        @GetMapping("rec/{startDate}")
        @ResponseBody
        public List<Reclamation> getBydate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
            return reclamationServices.getbydate(date);
        }




        @PostMapping("bad")
        public ResponseEntity<?> create(@RequestBody Reclamation reclamation) {
            try {
                reclamationServices.create(reclamation);
                return ResponseEntity.ok().build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        /*@GetMapping("/rechercheDynamique")
        List<User> searchUsers(@RequestParam(required = false) String recherche) {
            return userServices.rechercheDynamique(recherche);
        }


        @GetMapping("/{id}/reclamations")
        public ResponseEntity<Integer> getNombreReclamations(@PathVariable Long id) {
            int nombreReclamations = reclamationServices.getNombreReclamations(id);
            return ResponseEntity.ok(nombreReclamations);
        }*/
    /*@GetMapping("/search")
    public Iterable<Reclamation> search(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long idRec,

            @RequestParam(required = false) Date date,
            @RequestParam(required = false) Etat etat)
    {
        return reclamationServices.search(description,idRec,date,etat);
    }

    @PostMapping("/{id}/comments")
    public  String addCommentToClaim(@PathVariable("id") Long claimId, @ModelAttribute Comment comment) {
        Reclamation claim = reclamationRepositorye.findById(claimId).orElseThrow(EntityNotFoundException::new);
        comment.setTimestamp(LocalDateTime.now());
        comment.setReclamation(claim);
        claim.getComments().add(comment);
         reclamationRepositorye.save(claim);
         return null ;


    }*/



}