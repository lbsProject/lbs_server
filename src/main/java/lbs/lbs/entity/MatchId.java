package lbs.lbs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MatchId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String match_id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Summoner summoner;


    public MatchId(String match_id, Summoner summoner) {
        this.match_id = match_id;
        this.summoner = summoner;
    }
}
