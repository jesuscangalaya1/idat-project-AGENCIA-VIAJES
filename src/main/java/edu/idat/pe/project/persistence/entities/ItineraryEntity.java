package edu.idat.pe.project.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "itinerario")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE itinerario SET deleted = true WHERE id=?")
public class ItineraryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_ida")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String departureDate;

    @Column(name = "fecha_salida")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String arrivalDate;

    @Column(name = "hora")
    private String hour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origen_id", nullable = false, referencedColumnName = "id")
    private OriginEntity origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destino_id", nullable = false, referencedColumnName = "id")
    private LocationEntity location;


    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean deleted;

}
