package com.usc.csci401.goatservice.service;

import com.usc.csci401.goatcommon.dto.TournamentDTO;
import com.usc.csci401.goatdao.model.Tournament;
import com.usc.csci401.goatservice.param.CreateTournamentParam;
import java.util.List;

public interface TournamentService {

  Integer createTournament(CreateTournamentParam param);

  void updateCurrentPlayer(Integer id, Integer num);

  List<TournamentDTO> getAllJoinable();

  Tournament getById(Integer id);

}
