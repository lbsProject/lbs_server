package lbs.lbs.dto;

import lbs.lbs.entity.MatchId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MatchIdResponseDto {
    List<MatchId> matchIdList = new ArrayList<>();
}
