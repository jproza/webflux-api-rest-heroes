package ar.com.challenge.heroes.repository;

import ar.com.challenge.heroes.entities.Heroe;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface HeroeRepository extends R2dbcRepository<Heroe, Long> {

    Flux<Heroe> findByNombreContainingIgnoreCase(String nombre);

}
