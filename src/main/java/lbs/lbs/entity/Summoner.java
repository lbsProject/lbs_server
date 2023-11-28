package lbs.lbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Summoner {
    @Id @Column(name = "summoner_id")
    private String id;

    private String profileIconId;
    private String puuid;
    private String name;
    private int summonerLevel;
}
