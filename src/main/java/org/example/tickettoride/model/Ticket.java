package org.example.tickettoride.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket {
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

    @Min(1)
    @Column(name = "price")
    private int price;

    @NotBlank
    @Column(name = "currency")
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", segments=" + segments +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}
