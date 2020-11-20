package com.usc.csci401.goatservice.service.impl;

import com.google.common.base.Preconditions;
import com.usc.csci401.goatcommon.dto.TournamentDTO;
import com.usc.csci401.goatcommon.exception.TournamentException;
import com.usc.csci401.goatcommon.util.BeanConvertUtils;
import com.usc.csci401.goatdao.TournamentRepository;
import com.usc.csci401.goatdao.model.Tournament;
import com.usc.csci401.goatservice.param.CreateTournamentParam;
import com.usc.csci401.goatservice.service.TournamentService;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentServiceImpl implements TournamentService {

  @Autowired
  private TournamentRepository tournamentRepository;

  @Override
  public Integer createTournament(CreateTournamentParam param){
    Preconditions.checkArgument(param != null, "tournament params cannot be null");
    Tournament tournament = BeanConvertUtils.convert(param, Tournament.class, true);
    if(tournament == null){
      throw new TournamentException("tournament transform failed");
    }
    tournament.setCurrentplayers(0);

    return tournamentRepository.save(tournament).getTournamentid();
  }

  @Override
  @Transactional
  public void updateCurrentPlayer(Integer id, Integer num){
    Preconditions.checkArgument(num != null, "update num cannot be null");
    Preconditions.checkArgument(id != null, "Torunament id cannot be null");

    Optional<Tournament> tournament = tournamentRepository.findByTournamentid(id);

    if(!tournament.isPresent()){
      throw new TournamentException("Tournament does not exist");
    }

    Tournament tour = tournament.get();
    if(tour.getCurrentplayers().equals(tour.getMaxplayers())){
      throw new TournamentException("Tournament is full, cannot join the tournament");
    }
    tournamentRepository.updateById(id, tour.getMaxplayers(),
        tour.getCurrentplayers() + num, tour.getTournamentname(), tour.getGamename());
  }


  @Override
  public List<TournamentDTO> getAllJoinable(){
    List<Tournament> tournaments = tournamentRepository.getJoinable();

    return BeanConvertUtils.convert(tournaments, TournamentDTO.class, true);
  }

  @Override
  public Tournament getById(Integer id){
    Preconditions.checkArgument(id != null, "Torunament id cannot be null");
    Optional<Tournament> tournament = tournamentRepository.findByTournamentid(id);

    if(!tournament.isPresent()){
      throw new TournamentException("Tournament does not exist");
    }

    return tournament.get();
  }




}
