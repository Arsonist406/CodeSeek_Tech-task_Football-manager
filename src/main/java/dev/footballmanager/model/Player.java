package dev.footballmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "players")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "surname",
            nullable = false)
    private String surname;

    @Column(name = "birth_date",
            nullable = false)
    private LocalDate birthDate;

    @Column(name = "experience_in_months",
            nullable = false)
    private int experienceInMonths;

    @ManyToOne(
            fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @ToString.Exclude
    private Team team;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;
        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
