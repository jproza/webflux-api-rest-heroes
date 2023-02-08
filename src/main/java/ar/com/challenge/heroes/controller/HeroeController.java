package ar.com.challenge.heroes.controller;

import ar.com.challenge.heroes.entities.Heroe;
import ar.com.challenge.heroes.services.HeroeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class HeroeController {

  @Autowired
  private HeroeService heroeService;

  public Mono<ServerResponse> createNewHeroe(ServerRequest req) {
    return req.bodyToMono(Heroe.class)
            .flatMap(this.heroeService::createNewHeroe)
            .flatMap(post -> created(URI.create("/api/heroe/" + post.getId())).build());
  }

  public Mono<ServerResponse> getAllHeroes(ServerRequest req) {
    return ok().body(this.heroeService.getAllHeroes() , Heroe.class);
  }


//  @GetMapping(value = "/{id}" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//
//  public Mono<Heroe> getHeroeById(@PathVariable("id") Long id) {
//    return heroeService.getHeroesById(id).log();
//  }
//
//
//
//  @PutMapping("/{id}")
//  public Mono<Heroe> updateHeroe(@PathVariable("id") Long id, @Valid @RequestBody HeroeRequest heroeRequest) {
//    return heroeService.updateHeroes(id, heroeRequest).log();
//  }
//
//
//
//  @DeleteMapping("/{id}")
//  public Mono<Heroe> deleteHeroe(@PathVariable("id") Long id) {
//    return heroeService.deleteHeroeById(id).log();
//  }

}
