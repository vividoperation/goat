package com.usc.csci401.goatdao;

import com.usc.csci401.goatdao.model.Tournament;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

  Optional<Tournament> findByTournamentid(Integer tournamentid);

  @Modifying
  @Query(value = "update Tournament tournament set tournament.maxplayers = ?2, tournament.currentplayers = ?3, "
      + "tournament.tournamentname = ?4, tournament.gamename = ?5 where tournament.tournamentid=?1")
  void updateById(Integer id, Integer maxplayers, Integer currentplayers, String tournamentname, String gamename);

  @Query(value = "select t from Tournament t where t.currentplayers < t.maxplayers")
  List<Tournament> getJoinable();

}
