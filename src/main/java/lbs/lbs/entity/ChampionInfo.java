package lbs.lbs.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ChampionInfo {
    @Id @Column(name = "champion_info_id")
    private int id;

    private String en_name;
    private String ko_name;
    private String story;

    @Lob
    private byte[] champ_img;

    private String title;
    private String par_type;
    private int hp;
    private int hp_per_level;
    private int mp;
    private int mp_per_level;
    private int move_speed;
    private int armor;
    private float armor_per_level;
    private int spell_block;
    private float spell_block_per_level;
    private int attack_range;
    private int hp_regen;
    private float hp_regen_per_level;
    private int mp_regen;
    private float mp_regen_per_level;
    private int crit;
    private int crit_per_level;
    private int attack_damage;
    private float attack_damage_per_level;
    private float attack_speed;
    private int attack_speed_per_level;


}
