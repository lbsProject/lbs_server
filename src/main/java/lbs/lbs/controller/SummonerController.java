package lbs.lbs.controller;

import lbs.lbs.entity.MatchId;
import lbs.lbs.entity.Summoner;
import lbs.lbs.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/summoners")
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerService summonerService;

    @GetMapping("/{summonerName}")
    public Summoner callSummonerByName(@PathVariable(name = "summonerName") String summonerName) {
        // URL 인코딩 사용
        summonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8);
        Summoner apiResult = summonerService.callRiotAPISummonerByName(summonerName);
        return apiResult;
    }

    @PostMapping("/match-ids/{puuid}")
    public List<MatchId> getMatchIds(@PathVariable(name = "puuid") String puuid) {
        List<MatchId> matchIds = summonerService.getMatchIds(puuid);

        return matchIds;
    }
}