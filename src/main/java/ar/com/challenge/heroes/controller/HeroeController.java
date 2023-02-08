package ar.com.challenge.heroes.controller;

import ar.com.challenge.heroes.entities.Heroe;
import ar.com.challenge.heroes.services.HeroeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

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
  public Mono<ServerResponse> getHeroeById(ServerRequest req) {
    return this.heroeService.getHeroesById(Long.valueOf(req.pathVariable("id")))
            .flatMap(post -> ok().body(Mono.just(post), Heroe.class))
            .switchIfEmpty(notFound().build());
  }
//
//
//
//  @PutMapping("/{id}")
  public Mono<ServerResponse> updateHeroe(ServerRequest req) {

    var existed = this.heroeService.getHeroesById(Long.valueOf(req.pathVariable("id")));
    return Mono
            .zip(
                    (data) -> {
                      Heroe p = (Heroe) data[0];
                      Heroe p2 = (Heroe) data[1];
                      if (p2 != null && StringUtils.hasText(p2.getNombre())) {
                        p.setNombre(p2.getNombre());
                      }
                      return p;
                    },
                    existed,
                    req.bodyToMono(Heroe.class)
            )
            .cast(Heroe.class)
            .flatMap(this.heroeService::createNewHeroe)
            .flatMap(post -> noContent().build());
  }

  public Mono<ServerResponse> deleteHeroe(ServerRequest req) {
    return this.heroeService.deleteHeroeById(Long.valueOf(req.pathVariable("id")))
            .flatMap(deleted -> noContent().build());
  }

}
