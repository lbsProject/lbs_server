package lbs.lbs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name= "champion_img")
public class ChampionImg {
    @Id @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String champ_name;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] champ_img;

}
