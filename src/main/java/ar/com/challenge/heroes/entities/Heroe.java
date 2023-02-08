package ar.com.challenge.heroes.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
public class Heroe implements Persistable<Long> {

  @Id
  //@GeneratedValue
  private Long id;

  @Column
  private String nombre;


  @Override
  public boolean isNew() {
    return id == null;
  }
}
