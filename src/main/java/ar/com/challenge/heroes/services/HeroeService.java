package ar.com.challenge.heroes.services;

import ar.com.challenge.heroes.entities.Heroe;
import ar.com.challenge.heroes.repository.HeroeRepository;
import ar.com.challenge.heroes.reqres.HeroeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@CacheConfig(cacheNames = "product")
@Service
public class HeroeService {

  @Autowired
  private HeroeRepository heroeRepository;

  @Caching(evict = {@CacheEvict(value = "getAllHeroescache", allEntries = true),
          @CacheEvict(value = "heroecache", key = "#heroeRequest.nombre")
  })
  public Mono<Heroe> createNewHeroe(Heroe heroe) {
    return  heroeRepository.save(heroe).log();
  }

  @Cacheable(value = "getAllHeroescache")
  public Flux<Heroe> getAllHeroes() {
    Flux<Heroe> hf = heroeRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
    //return hf.limitRate(2);
    return hf;
  }

  public Flux<Heroe> findByNombreContainingIgnoreCase(String nombre) {
    return heroeRepository.findByNombreContainingIgnoreCase(nombre).log();
  }


  public Mono<Heroe> getHeroesById(Long id) {
    return heroeRepository.findById(id).defaultIfEmpty(new Heroe());

  }

  @Caching(evict = {@CacheEvict(value = "getAllHeroescache", allEntries = true),
          @CacheEvict(value = "heroecache", key = "#heroeToUpdateRequest.nombre")
  })
  @Transactional
  public Mono<Heroe> updateHeroes(Long id, HeroeRequest heroeToUpdateRequest) {

   return heroeRepository.findById(id).flatMap(oldHeroe -> {
      oldHeroe.setNombre(heroeToUpdateRequest.getNombre());
      return heroeRepository.save(oldHeroe).log();})
            .map(updateHeroe -> updateHeroe)
            .defaultIfEmpty(new Heroe());

  }

  public Mono<Heroe> deleteHeroeById(Long id) {
    return heroeRepository.findById(id)
            .flatMap(deleteHeroe ->
      heroeRepository.delete(deleteHeroe)
    .then(Mono.just(deleteHeroe))).log();
  }


}
