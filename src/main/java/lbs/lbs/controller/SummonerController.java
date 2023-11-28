package lbs.lbs.controller;

import lbs.lbs.entity.Summoner;
import lbs.lbs.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/summoner-by-name")
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
}