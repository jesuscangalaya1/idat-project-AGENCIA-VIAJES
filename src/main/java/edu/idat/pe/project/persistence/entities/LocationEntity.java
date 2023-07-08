package edu.idat.pe.project.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "destino")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE destino SET deleted = true WHERE id=?")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ciudad")
    private String city;

    @Column(name = "pais")
    private String country;

    @Column(name = "aeropuerto")
    private String airport;
/*

    @OneToMany(mappedBy = "location", cascade = CascadeType.REMOVE)
    private List<ItineraryEntity> itineraries = new ArrayList<>();
*/


    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean deleted;


    public LocationEntity(Long id, String city, String country, String airport) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.airport = airport;
    }

    public LocationEntity(int i, String santiago, String chile, String airport) {
    }
}
