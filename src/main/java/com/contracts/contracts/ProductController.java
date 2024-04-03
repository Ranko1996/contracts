package com.contracts.contracts;

import com.contracts.contracts.model.Product;
import com.contracts.contracts.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductRepo repo;

//    @PostMapping("/addProduct")
//    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
//        Product newProduct = repo.save(product);
//        return new ResponseEntity<>(newProduct, HttpStatus.CREATED); // 201 CREATED - uspešno kreiran resurs
//    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = repo.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED); // 201 CREATED - uspešno kreiran resurs
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = repo.findById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 NOT FOUND - proizvod nije pronađen
    }

    @PutMapping("/editProduct/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> optionalProduct = repo.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setNaziv(productDetails.getNaziv());
            existingProduct.setDobavljac(productDetails.getDobavljac());
            existingProduct.setStatus(productDetails.getStatus());

            Product updatedProduct = repo.save(existingProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK); // 200 OK - uspješno ažuriran proizvod
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND - proizvod nije pronađen
        }
    }


    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 NO CONTENT - uspešno obrisan resurs
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND - nije pronađen proizvod za brisanje
        }
    }
}
