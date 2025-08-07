package dev.footballmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "teams")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",
            nullable = false)
    private String name;

    @Column(name = "transfer_fee_in_percent",
            nullable = false)
    private double transferFeeInPercent;

    @Column(name = "financial_account_in_double",
            nullable = false)
    private double financialAccountInDouble;

    @OneToMany(
            mappedBy = "team",
            fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Player> players;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;
        return id.equals(team.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
