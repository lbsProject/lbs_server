package lbs.lbs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lbs.lbs.dto.MatchIdResponseDto;
import lbs.lbs.dto.SummonerResponseDto;
import lbs.lbs.entity.MatchId;
import lbs.lbs.entity.Summoner;
import lbs.lbs.repository.MatchIdRepository;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummonerService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SummonerRepository summonerRepository;

    private final MatchIdRepository matchIdRepository;
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Value("${riot.api.key}")
    private String myKey;

    public Summoner callRiotAPISummonerByName(String summonerName) {
        try {
            HttpGet request = new HttpGet("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ summonerName + "?api_key=" + myKey);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != 200) {
                    throw new NullPointerException("해당 아이디는 존재하지 않습니다.");
                }
                HttpEntity entity = response.getEntity();
                SummonerResponseDto summonerResponseDto = objectMapper.readValue(entity.getContent(), SummonerResponseDto.class);

                if(summonerRepository.existsById(summonerResponseDto.getId())){
                    return summonerRepository.findById(summonerResponseDto.getId()).orElseThrow(() ->
                            new NullPointerException("해당 아이디는 존재하지 않습니다."));

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

    public List<MatchId> getMatchIds(String puuid) {
        try {
            HttpGet request = new HttpGet("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=20" + "&api_key=" + myKey);
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != 200) {
                    throw new NullPointerException("해당 puuid가 존재하지 않습니다..");
                }

                HttpEntity entity = response.getEntity();
                List<String> matchIdList = objectMapper.readValue(entity.getContent(), List.class);

                Summoner summoner = summonerRepository.findByPuuid(puuid).orElseThrow(() ->
                        new NullPointerException("해당 아이디는 존재하지 않습니다."));

                // matchIds를 설정할 때 builder() 대신에 set 메서드를 사용하여 id를 할당
                summoner.setMatchIds(matchIdList.stream()
                        .map(matchId -> new MatchId(matchId, summoner))
                        .collect(Collectors.toList()));

                summonerRepository.save(summoner);

                // MatchId 저장 부분도 변경
                List<MatchId> savedMatchIds = matchIdRepository.saveAll(
                        matchIdList.stream()
                                .map(matchIdStr -> new MatchId(matchIdStr, summoner))
                                .collect(Collectors.toList())
                );

                return savedMatchIds;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}