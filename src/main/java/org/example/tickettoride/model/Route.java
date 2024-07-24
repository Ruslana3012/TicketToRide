package org.example.tickettoride.model;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "departure")
    private String departure;

    @NotBlank
    @Column(name = "arrival")
    private String arrival;

    @Min(1)
    @Column(name = "segments")
    private int segments;
}
