package edu.idat.pe.project.service;

import edu.idat.pe.project.dto.request.PurchaseRequest;
import edu.idat.pe.project.dto.response.PurchaseResponse;

import java.util.List;

public interface PurchaseService {

    void purchaseFlight(PurchaseRequest purchaseRequest);

    List<PurchaseResponse> getCustomerPurchases();

}
