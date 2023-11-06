package lbs.lbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class ItemInfo {

    @Id @Column(name = "item_info_id")
    private Long id;

    private String name;
    private String grade;
    private String grade_effect;

    @Column(length = 1024)
    private String passive;

    private int attack_damage;
    private String attack_speed;
    private int physical_penetration;
    private int magic_penetration;
    private int skill_haste;
    private String critical_chance;
    private String critical_damage;
    private int health;
    private int magic_resistance;
    private int armor;
    private String armor_penetration;
    private int ability_power;
    private int movement_speed;
    private String mana_regeneration;
    private String health_regeneration;
    private String life_steal;
    private String health_regen_and_shield;
    private int mana;
    private String tenacity;
    private String all_damage_life_steal;
    private int gold_per_10_seconds;
    private int base_price;
    private int total_price;
    private int sell_price;
    private String sprite_image;
}
