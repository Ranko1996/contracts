package com.contracts.contracts;

import com.contracts.contracts.model.Contract;
import com.contracts.contracts.repo.ContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ContractController {

    @Autowired
    ContractRepo repo;

    @CrossOrigin(origins = "http://localhost:5174")
    @PostMapping("/addContract")
    public ResponseEntity<Contract> addContract(@RequestBody Contract contract) {
        Contract newContract = repo.save(contract);
        return new ResponseEntity<>(newContract, HttpStatus.CREATED); // 201 CREATED - uspešno kreiran resurs
    }

    @CrossOrigin(origins = "http://localhost:5174")
    @GetMapping("/ugovori")
    public List<Contract> getAllContracts() {
        return repo.findAll();
    }

    @CrossOrigin(origins = "http://localhost:5174")
    @GetMapping("/ugovor/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Long id) {
        Optional<Contract> contract = repo.findById(id);
        return contract.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 NOT FOUND - ugovor nije pronađen
    }

    @CrossOrigin(origins = "http://localhost:5174")
    @PutMapping("/editContract/{id}")
    public ResponseEntity<Contract> editContract(@PathVariable Long id, @RequestBody Contract contractDetails) {
        Optional<Contract> optionalContract = repo.findById(id);
        if (optionalContract.isPresent()) {
            Contract existingContract = optionalContract.get();

            // Provjera i ažuriranje polja rokIsporuke ako je prisutno u contractDetails
            if (contractDetails.getRokIsporuke() != null) {
                existingContract.setRokIsporuke(contractDetails.getRokIsporuke());
            }

            // Provjera i ažuriranje polja status ako je prisutno u contractDetails
            if (contractDetails.getStatus() != null) {
                existingContract.setStatus(contractDetails.getStatus());
            }

            Contract updatedContract = repo.save(existingContract);
            return new ResponseEntity<>(updatedContract, HttpStatus.OK); // 200 OK - uspješno ažuriran ugovor
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND - ugovor nije pronađen
        }
    }


    @CrossOrigin(origins = "http://localhost:5174")
    @DeleteMapping("/deleteContract/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 NO CONTENT - uspešno obrisan resurs
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND - nije pronađen ugovor za brisanje
        }
    }
}
