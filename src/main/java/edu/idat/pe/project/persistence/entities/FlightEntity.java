package edu.idat.pe.project.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vuelo")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE vuelo SET deleted = true WHERE id=?")
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacidad")
    private int capacity;

    @Column(name = "duracion")
    @DateTimeFormat(pattern = "HH:mm")
    private String duration;

    @Column(name = "precio")
    private Double price;

    @Column(name = "imagen")
    private String image;

    @Column(name = "hora_salida")
    @DateTimeFormat(pattern = "HH:mm")
    private String departureTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerario_id", nullable = false)
    private ItineraryEntity itinerary;

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean deleted;
}
