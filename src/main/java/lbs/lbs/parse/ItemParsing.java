package lbs.lbs.parse;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ItemParsing {
    public static void main(String[] args) {
        String jsonFilePath = "C:\\Users\\82102\\Desktop\\롤 프로젝트\\dragontail-13.19.1\\13.19.1\\data\\ko_KR\\item.json"; // JSON 파일 경로
        String jsonData = readJsonFile(jsonFilePath);

        ItemParsing parser = new ItemParsing();
        parser.parseAndStoreItems(jsonData, jsonFilePath);
    }

    public void parseAndStoreItems(String jsonData, String jsonFilePath) {
        String dbUrl = "jdbc:mysql://localhost:3306/lbs_project"; // MySQL 데이터베이스 URL
        String dbUser = "root"; // MySQL 사용자 이름
        String dbPassword = "1234"; // MySQL 비밀번호

        try {
            // JSON 파일 읽기
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(new FileReader(jsonFilePath)).getAsJsonObject();

            // MySQL 연결
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // 데이터베이스에 데이터 저장
            for (String itemId : jsonObject.keySet()) {
                System.out.println("itemId : " + itemId);
                Long id = Long.parseLong(itemId);
                String grade = "";
                JsonObject itemData;
                try {
                     itemData = jsonObject.getAsJsonObject(itemId);
                }catch (ClassCastException e) {
                    System.err.println("Invalid JSON data for item: " + itemId);
                    continue; // 이 항목은 건너뜁니다.
                }
                String itemName = itemData.get("name").getAsString();
                String itemDescription = itemData.get("description").getAsString();

                boolean isMythic = itemDescription.contains("신화급");
                if(isMythic == true) {
                    grade = "신화급";
                }else grade = "";

                // 스탯 정보 추출
                String grade_effectStr = extractStatValue(itemDescription, "");
                List<String> passiveList = extractPassive(itemDescription);
                String passiveStr = String.join("\n", passiveList);

                String attack_damageStr = extractStatValue(itemDescription, "공격력");
                String attack_speedStr = extractStatValue(itemDescription, "공격 속도");
                String physical_penetrationStr = extractStatValue(itemDescription, "물리 관통력");
                String magic_penetrationStr = extractStatValue(itemDescription, "마법 관통력");
                String skill_hasteStr = extractStatValue(itemDescription, "스킬 가속");
                String critical_chanceStr = extractStatValue(itemDescription, "치명타 확률");
                String critical_damageStr = extractStatValue(itemDescription, "치명타 피해량");
                String healthStr = extractStatValue(itemDescription, "체력");
                String magic_resistanceStr = extractStatValue(itemDescription, "마법 저항력");
                String armorStr = extractStatValue(itemDescription, "방어력");
                String armor_penetrationStr = extractStatValue(itemDescription, "방어구 관통력");
                String ability_powerStr = extractStatValue(itemDescription, "주문력");
                String movement_speedStr = extractStatValue(itemDescription, "이동 속도");
                String mana_regenerationStr = extractStatValue(itemDescription, "기본 마나 재생");
                String health_regenerationStr = extractStatValue(itemDescription, "기본 체력 재생");
                String life_stealStr = extractStatValue(itemDescription, "생명력 흡수");
                String health_regen_and_shieldStr = extractStatValue(itemDescription, "체력 회복 및 보호막");
                String manaStr = extractStatValue(itemDescription, "마나");
                String tenacityStr = extractStatValue(itemDescription, "강인함");
                String all_damage_life_stealStr = extractStatValue(itemDescription, "모든 피해 흡혈");
                String gold_per_10_secondsStr = extractStatValue(itemDescription, "10초당 골드");

                // "gold" 객체 추출
                JsonObject goldObject = itemData.getAsJsonObject("gold");
                // "base," "total," 및 "sell" 값 추출
                int baseValue = goldObject.get("base").getAsInt();
                int totalValue = goldObject.get("total").getAsInt();
                int sellValue = goldObject.get("sell").getAsInt();

                JsonObject imageObject = itemData.getAsJsonObject("image");
                String imageStr = imageObject.get("full").getAsString();

                int attack_damage = parseStatValue(attack_damageStr);
                int physical_penetration = parseStatValue(physical_penetrationStr);
                int magic_penetration = parseStatValue(magic_penetrationStr);
                int skill_haste = parseStatValue(skill_hasteStr);
                int health = parseStatValue(healthStr);
                int magic_resistance = parseStatValue(magic_resistanceStr);
                int armor = parseStatValue(armorStr);
                int ability_power = parseStatValue(ability_powerStr);
                int movement_speed = parseStatValue(movement_speedStr);
                int mana = parseStatValue(manaStr);
                int gold_per_10_seconds = parseStatValue(gold_per_10_secondsStr);


                // 데이터베이스에 삽입
                String query = "INSERT INTO item_info " +
                        "(item_info_id, name, grade, grade_effect, passive, attack_damage, attack_speed, physical_penetration, magic_penetration, " +
                        "skill_haste, critical_chance, critical_damage, health, magic_resistance, armor, armor_penetration, ability_power, " +
                        "movement_speed, mana_regeneration, health_regeneration, life_steal, health_regen_and_shield, mana, tenacity, " +
                        "all_damage_life_steal, gold_per_10_seconds, base_price, total_price, sell_price, image) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, id);
                preparedStatement.setString(2, itemName);
                preparedStatement.setString(3, grade);
                preparedStatement.setString(4, grade_effectStr);
                preparedStatement.setString(5, passiveStr);
                preparedStatement.setInt(6, attack_damage);
                preparedStatement.setString(7, attack_speedStr);
                preparedStatement.setInt(8, physical_penetration);
                preparedStatement.setInt(9, magic_penetration);
                preparedStatement.setInt(10, skill_haste);
                preparedStatement.setString(11, critical_chanceStr);
                preparedStatement.setString(12, critical_damageStr);
                preparedStatement.setInt(13, health);
                preparedStatement.setInt(14, magic_resistance);
                preparedStatement.setInt(15, armor);
                preparedStatement.setString(16, armor_penetrationStr);
                preparedStatement.setInt(17, ability_power);
                preparedStatement.setInt(18, movement_speed);
                preparedStatement.setString(19, mana_regenerationStr);
                preparedStatement.setString(20, health_regenerationStr);
                preparedStatement.setString(21, life_stealStr);
                preparedStatement.setString(22, health_regen_and_shieldStr);
                preparedStatement.setInt(23, mana);
                preparedStatement.setString(24, tenacityStr);
                preparedStatement.setString(25, all_damage_life_stealStr);
                preparedStatement.setInt(26, gold_per_10_seconds);
                preparedStatement.setInt(27, baseValue);
                preparedStatement.setInt(28, totalValue);
                preparedStatement.setInt(29, sellValue);
                preparedStatement.setString(30, imageStr);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }

            // 연결 종료
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 스탯 정보 추출을 위한 도우미 메서드
    private static String extractStatValue(String description, String statName) {
        java.util.regex.Pattern patternAttention = java.util.regex.Pattern.compile(statName + " <attention>([\\d%]+)</attention>");
        java.util.regex.Pattern patternNerfedStat = java.util.regex.Pattern.compile(statName + " <nerfedStat>([\\d%]+)</nerfedStat>");
        java.util.regex.Pattern patternBuffedStat = java.util.regex.Pattern.compile(statName + " <buffedStat>([\\d%]+)</buffedStat>");

        java.util.regex.Matcher matcherAttention = patternAttention.matcher(description);
        java.util.regex.Matcher matcherNerfedStat = patternNerfedStat.matcher(description);
        java.util.regex.Matcher matcherBuffedStat = patternBuffedStat.matcher(description);

        if (matcherAttention.find()) {
            return matcherAttention.group(1);
        } else if (matcherNerfedStat.find()) {
            return matcherNerfedStat.group(1);
        } else if (matcherBuffedStat.find()) {
            return matcherBuffedStat.group(1);
        }

        return "0"; // 스탯 정보가 없는 경우 빈 문자열 반환
    }

    private static List<String> extractPassive(String description) {
        List<String> passiveSections = new ArrayList<>();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<passive>(.*?)</mainText>");
        java.util.regex.Matcher matcher = pattern.matcher(description);

        while (matcher.find()) {
            String passiveSection = matcher.group(1).trim();
            passiveSections.add(passiveSection);
        }

        return passiveSections;
    }

    // 스탯 값을 정수로 파싱하는 도우미 메서드
    private static int parseStatValue(String statValue) {
        if (!statValue.isEmpty()) {
            return Integer.parseInt(statValue);
        }

        return 0;
    }

    private static String readJsonFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}