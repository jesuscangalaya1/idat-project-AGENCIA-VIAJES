package edu.idat.pe.project.controller;

import edu.idat.pe.project.dto.request.PurchaseRequest;
import edu.idat.pe.project.dto.response.PurchaseResponse;
import edu.idat.pe.project.security.dto.Mensaje;
import edu.idat.pe.project.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
@CrossOrigin("*")
public class PurchaseController {

    private final PurchaseService purchaseService;


    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> listCustomerPruchases() {
        return new ResponseEntity<>(purchaseService.getCustomerPurchases(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mensaje> purchaseFlight(@RequestBody PurchaseRequest purchaseRequest) {
        purchaseService.purchaseFlight(purchaseRequest);
        return new ResponseEntity<>(new Mensaje("PURCHASE SUCCESSFULLY"), HttpStatus.CREATED);
    }


}

