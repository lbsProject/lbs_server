package lbs.lbs.parse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;

public class ChampInfoParsing {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/lbs_project";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        String jsonFilePath = "C:\\Users\\82102\\Desktop\\롤 프로젝트\\dragontail-13.19.1\\13.19.1\\data\\ko_KR\\champion.json"; // JSON 파일 경로
        String jsonData = readJsonFile(jsonFilePath);

        ChampInfoParsing parser = new ChampInfoParsing();
        parser.parseAndStoreChampionData(jsonData);
    }

    public void parseAndStoreChampionData(String jsonData) {
        String dbUrl = "jdbc:mysql://localhost:3306/lbs_project"; // MySQL 데이터베이스 URL
        String dbUser = "root"; // MySQL 사용자 이름
        String dbPassword = "1234"; // MySQL 비밀번호

        try {
            // JSON 파일 읽기
            JsonParser jsonParser = new JsonParser();
            JsonObject championData = jsonParser.parse(jsonData).getAsJsonObject();

            // MySQL 연결
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // 데이터베이스에 데이터 저장
            for (Map.Entry<String, JsonElement> championEntry : championData.getAsJsonObject("data").entrySet()) {

                JsonObject championInfo = championEntry.getValue().getAsJsonObject();

                // 기본 정보 추출
                int championId = championInfo.get("key").getAsInt();
                String enName = championInfo.get("id").getAsString();
                String koName = championInfo.get("name").getAsString();
                String title = championInfo.get("title").getAsString();
                String story = championInfo.get("blurb").getAsString();
                String partype = championInfo.get("partype").getAsString();

                JsonObject images = championInfo.getAsJsonObject("image");
                String image = images.get("full").getAsString();

                // 스탯 정보 추출
                JsonObject stats = championInfo.getAsJsonObject("stats");
                int hp = stats.get("hp").getAsInt();
                int hpPerLevel = stats.get("hpperlevel").getAsInt();
                int mp = stats.get("mp").getAsInt();
                int mpPerLevel = stats.get("mpperlevel").getAsInt();
                int moveSpeed = stats.get("movespeed").getAsInt();
                int armor = stats.get("armor").getAsInt();
                float armorPerLevel = stats.get("armorperlevel").getAsFloat();
                int spellBlock = stats.get("spellblock").getAsInt();
                float spellBlockPerLevel = stats.get("spellblockperlevel").getAsFloat();
                int attackRange = stats.get("attackrange").getAsInt();
                int hpRegen = stats.get("hpregen").getAsInt();
                float hpRegenPerLevel = stats.get("hpregenperlevel").getAsFloat();
                int mpRegen = stats.get("mpregen").getAsInt();
                float mpRegenPerLevel = stats.get("mpregenperlevel").getAsFloat();
                int crit = stats.get("crit").getAsInt();
                int critPerLevel = stats.get("critperlevel").getAsInt();
                int attackDamage = stats.get("attackdamage").getAsInt();
                float attackDamagePerLevel = stats.get("attackdamageperlevel").getAsFloat();
                float attackSpeed = stats.get("attackspeed").getAsFloat();
                int attackSpeedPerLevel = stats.get("attackspeedperlevel").getAsInt();

                // 데이터베이스에 삽입
                String query = "INSERT INTO champion_info " +
                        "(champion_info_id, en_name, ko_name, story, title, champ_img, par_type, hp, hp_per_level, mp, mp_per_level, " +
                        "  move_speed, armor, armor_per_level, spell_block, spell_block_per_level, attack_range, hp_regen, " +
                        "  hp_regen_per_level, mp_regen, mp_regen_per_level, crit, crit_per_level, attack_damage, " +
                        "  attack_damage_per_level, attack_speed, attack_speed_per_level)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, championId);
                preparedStatement.setString(2, enName);
                preparedStatement.setString(3, koName);
                preparedStatement.setString(4, story);
                preparedStatement.setString(5, title);
                preparedStatement.setString(6, image);
                preparedStatement.setString(7, partype);
                preparedStatement.setInt(8, hp);
                preparedStatement.setInt(9, hpPerLevel);
                preparedStatement.setInt(10, mp);
                preparedStatement.setInt(11, mpPerLevel);
                preparedStatement.setInt(12, moveSpeed);
                preparedStatement.setInt(13, armor);
                preparedStatement.setFloat(14, armorPerLevel);
                preparedStatement.setInt(15, spellBlock);
                preparedStatement.setFloat(16, spellBlockPerLevel);
                preparedStatement.setInt(17, attackRange);
                preparedStatement.setInt(18, hpRegen);
                preparedStatement.setFloat(19, hpRegenPerLevel);
                preparedStatement.setInt(20, mpRegen);
                preparedStatement.setFloat(21, mpRegenPerLevel);
                preparedStatement.setInt(22, crit);
                preparedStatement.setInt(23, critPerLevel);
                preparedStatement.setInt(24, attackDamage);
                preparedStatement.setFloat(25, attackDamagePerLevel);
                preparedStatement.setFloat(26, attackSpeed);
                preparedStatement.setInt(27, attackSpeedPerLevel);
                // ... (나머지 스탯 정보 삽입)

                preparedStatement.executeUpdate();
                preparedStatement.close();
            }

            // 연결 종료
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
