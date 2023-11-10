package lbs.lbs.parse;

import lbs.lbs.entity.ChampionImg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class ChampionImagesParsing {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/lbs_project";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // MySQL에 연결
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            // 이미지 파일이 있는 디렉토리 경로
            String directoryPath = "C:\\Users\\82102\\Desktop\\롤 프로젝트\\dragontail-13.19.1\\img\\champion\\splash";

            // 이미지 파일 삽입
            insertImagesFromDirectory(connection, directoryPath);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 연결 종료
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("MySQL 연결이 닫혔습니다.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void insertImagesFromDirectory(Connection connection, String directoryPath) {
        System.out.println("Inserting images from directory: " + directoryPath);

        File directory = new File(directoryPath);

        // 디렉토리에서 파일 목록 가져오기
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith("_0.jpg")) {
                    // 파일 이름이 "_0"로 끝나는 경우에만 처리
                    // 동적으로 아이디 생성 (여기서는 파일 이름을 사용)
                    String fileName = file.getName().replaceFirst("[.][^.]+$", ""); // 파일 확장자 제거
                    fileName = fileName.replace("_0", "") + ".png";

                    // 이미지 파일 삽입
                    insertImage(connection, fileName, file.toPath());
                }
            }
        }

        System.out.println("Inserting images from directory finished.");
    }


    private static void insertImage(Connection connection, String fileName, Path filePath) {
        try {
            // 이미지 파일 읽기
            byte[] originalImageData = Files.readAllBytes(filePath);


            // MySQL 쿼리 실행
            String query = "INSERT INTO champion_img (champ_name, champ_img) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, fileName);
                preparedStatement.setBytes(2, originalImageData);
                preparedStatement.executeUpdate();
                System.out.println("이미지 파일 " + filePath + "이(가) 성공적으로 삽입되었습니다.");
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}