package lbs.lbs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lbs.lbs.dto.SummonerResponseDto;
import lbs.lbs.entity.Summoner;
import lbs.lbs.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class SummonerService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SummonerRepository summonerRepository;
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Value("${riot.api.key}")
    private String myKey;

    public Summoner callRiotAPISummonerByName(String summonerName) {
        try {
            HttpGet request = new HttpGet("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ summonerName + "?api_key=" + myKey);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != 200) {
                    throw new NullPointerException("해당 아이디가 존재하지 않습니다.");
                }
                HttpEntity entity = response.getEntity();
                SummonerResponseDto summonerResponseDto = objectMapper.readValue(entity.getContent(), SummonerResponseDto.class);

                if(summonerRepository.existsById(summonerResponseDto.getId())){
                    return summonerRepository.findById(summonerResponseDto.getId()).orElseThrow(() ->
                            new NullPointerException("해당 아이디가 존재하지 않습니다2."));

                }
                Summoner summoner = new Summoner(summonerResponseDto.getId(), summonerResponseDto.getProfileIconId(),
                        summonerResponseDto.getPuuid(),summonerResponseDto.getName(), summonerResponseDto.getSummonerLevel());

                summonerRepository.save(summoner);
                return summoner;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}