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
@Table(name = "origen")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE origen SET deleted = true WHERE id=?")
public class OriginEntity {

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

    @OneToMany(mappedBy = "origin", cascade = CascadeType.REMOVE)
    private List<ItineraryEntity> itineraries = new ArrayList<>();
*/

    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean deleted;


}
