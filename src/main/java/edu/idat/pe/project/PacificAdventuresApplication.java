package edu.idat.pe.project;

import edu.idat.pe.project.persistence.entities.LocationEntity;
import edu.idat.pe.project.persistence.entities.OriginEntity;
import edu.idat.pe.project.persistence.repositories.LocationRepository;
import edu.idat.pe.project.persistence.repositories.OriginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@RequiredArgsConstructor
public class PacificAdventuresApplication implements CommandLineRunner {

	private final OriginRepository originRepository;
	private final LocationRepository locationRepository;

	public static void main(String[] args) {
		SpringApplication.run(PacificAdventuresApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Inserciones en la tabla "destino"
	/*	LocationEntity location1 = new LocationEntity();
		location1.setId(1L);
		location1.setCity("Buenos Aires");
		location1.setCountry("Argentina");
		location1.setAirport("Aeropuerto 1");
		locationRepository.save(location1);

		LocationEntity location2 = new LocationEntity();
		location2.setId(2L);
		location2.setCity("Santiago");
		location2.setCountry("Chile");
		location2.setAirport("Aeropuerto 2");
		locationRepository.save(location2);

		LocationEntity location3 = new LocationEntity();
		location3.setId(3L);
		location3.setCity("Lima");
		location3.setCountry("Perú");
		location3.setAirport("Aeropuerto 3");
		locationRepository.save(location3);

		LocationEntity location4 = new LocationEntity();
		location4.setId(4L);
		location4.setCity("Río de Janeiro");
		location4.setCountry("Brasil");
		location4.setAirport("Aeropuerto 4");
		locationRepository.save(location4);

		LocationEntity location5 = new LocationEntity();
		location5.setId(5L);
		location5.setCity("Montevideo");
		location5.setCountry("Uruguay");
		location5.setAirport("Aeropuerto 5");
		locationRepository.save(location5);

		// Inserciones en la tabla "origen"
		OriginEntity origin1 = new OriginEntity();
		origin1.setId(1L);
		origin1.setCity("Buenos Aires");
		origin1.setCountry("Argentina");
		origin1.setAirport("Aeropuerto 1");
		originRepository.save(origin1);

		OriginEntity origin2 = new OriginEntity();
		origin2.setId(2L);
		origin2.setCity("Santiago");
		origin2.setCountry("Chile");
		origin2.setAirport("Aeropuerto 2");
		originRepository.save(origin2);

		OriginEntity origin3 = new OriginEntity();
		origin3.setId(3L);
		origin3.setCity("Lima");
		origin3.setCountry("Perú");
		origin3.setAirport("Aeropuerto 3");
		originRepository.save(origin3);

		OriginEntity origin4 = new OriginEntity();
		origin4.setId(4L);
		origin4.setCity("Río de Janeiro");
		origin4.setCountry("Brasil");
		origin4.setAirport("Aeropuerto 4");
		originRepository.save(origin4);

		OriginEntity origin5 = new OriginEntity();
		origin5.setId(5L);
		origin5.setCity("Montevideo");
		origin5.setCountry("Uruguay");
		origin5.setAirport("Aeropuerto 5");
		originRepository.save(origin5);*/
	}



	}




