package org.example.tickettoride.model;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Min(1)
    @Column(name = "amount")
    private int amount;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    @Override
    public String toString() {
        return "User{" +
                "amount=" + amount +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
