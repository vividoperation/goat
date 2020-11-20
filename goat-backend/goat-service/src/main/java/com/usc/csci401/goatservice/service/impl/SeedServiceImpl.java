package com.usc.csci401.goatservice.service.impl;

import com.google.common.base.Preconditions;
import com.usc.csci401.goatcommon.exception.TournamentException;
import com.usc.csci401.goatdao.SeedingRepository;
import com.usc.csci401.goatdao.model.Seeding;
import com.usc.csci401.goatservice.service.SeedingService;
import java.util.Optional;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeedServiceImpl implements SeedingService{

  @Autowired
  private SeedingRepository seedingRepository;


  @Override
  public Integer createSeeding(Integer tournamentId, String username, Integer maxPlayer){
    Preconditions.checkArgument(tournamentId != null, "tournament id cannot be null");
    Preconditions.checkArgument(StringUtils.isNotBlank(username), "username cannot be blank");

    Seeding seeding = new Seeding();
    seeding.setTournamentid(tournamentId);
    seeding.setUsername(username);
    seeding.setRound(1);
    Integer maxSeed = seedingRepository.getMaxSeeding(tournamentId);
    if(maxSeed == null || maxSeed > maxPlayer) {
      maxSeed = 1;
    }
    else{
      maxSeed++;
    }
    seeding.setSeed(maxSeed);
    if(maxPlayer == 4 || maxPlayer == 8){
      seeding.setRound(1);
    }

    if(maxPlayer == 5){
      if(maxSeed == 1 || maxSeed == 2 || maxSeed == 3) {
        seeding.setRound(2);
      }
      else{
        seeding.setRound(1);
      }
    }
    else if(maxPlayer == 6){
      if(maxSeed == 1 || maxSeed == 2){
        seeding.setRound(2);
      }
      else{
        seeding.setRound(1);
      }
    }
    else if(maxPlayer == 7){
      if(maxSeed == 1){
        seeding.setRound(2);
      }
      else{
        seeding.setRound(1);
      }
    }

    return seedingRepository.save(seeding).getSeedingid();
  }

  @Override
  public List<Seeding> getByUsername(String username) {
    return seedingRepository.getByUsername(username);
  }

  @Override
  public Seeding getSeed(Integer tournamentId, String username){
    Optional<Seeding> seed = seedingRepository.getByTournamentidAndUsername(tournamentId, username);
    if(!seed.isPresent()){
      throw new TournamentException("Seeding does not exist");
    }

   return seed.get();
  }

  @Override
  public void updateRound(Seeding seeding){
    Preconditions.checkArgument(seeding != null, "Seeding cannot be null");
    seedingRepository.save(seeding);
  }

  @Override
  public String getUsername(Integer tournamentId, Integer seeding){
    Preconditions.checkArgument(tournamentId != null, "tournamentId cannot be null");
    Optional<Seeding> seeds = seedingRepository.getByTournamentidAndSeed(tournamentId, seeding);

    if(!seeds.isPresent()){
      return null;
    }

    return seeds.get().getUsername();
  }

}
