package lbs.lbs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lbs.lbs.entity.Summoner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SummonerService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Value("${riot.api.key}")
    private String myKey;

    public Summoner callRiotAPISummonerByName(String summonerName) {
        try {
            HttpGet request = new HttpGet("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ summonerName + "?api_key=" + myKey);
            System.out.println("myKey : " + myKey);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("HTTP Status Code: " + statusCode);

                if (statusCode != 200) {
                    return null;
                }

                HttpEntity entity = response.getEntity();
                Summoner summoner = objectMapper.readValue(entity.getContent(), Summoner.class);
                System.out.println("Summoner Data: " + summoner);

                return summoner;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}