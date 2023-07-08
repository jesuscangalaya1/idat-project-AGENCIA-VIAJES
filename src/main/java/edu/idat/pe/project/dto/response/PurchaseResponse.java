package edu.idat.pe.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponse {

    private Long id;
    private Integer amount;
    private Double price;
    private Double total;
    private LocalDate purchaseDate;

    private List<FlightResponse> flights = new ArrayList<>();
}
