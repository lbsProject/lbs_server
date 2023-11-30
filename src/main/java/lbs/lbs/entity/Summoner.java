package lbs.lbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Summoner {
    @Id @Column(name = "summoner_id")
    private String id;

    private String profile_icon_id;
    private String puuid;

    @OneToMany(mappedBy = "summoner")
    private List<MatchId> matchIds;

    private String summoner_name;
    private int summoner_level;

    public Summoner(String id, String profile_icon_id, String puuid, String summoner_name, int summoner_level) {
        this.id = id;
        this.profile_icon_id = profile_icon_id;
        this.puuid = puuid;
        this.summoner_name = summoner_name;
        this.summoner_level = summoner_level;
    }


}
