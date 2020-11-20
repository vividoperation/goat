package com.usc.csci401.goatdao;

import com.usc.csci401.goatdao.model.GameRecord;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRecordRepository extends CrudRepository<GameRecord, Long> {

    @Modifying
    @Query(value = "update GameRecord record set record.winner = ?1 where record.gameid = ?2")
    void updateWinnder(String winner, Integer gameId);

    List<GameRecord> findAllByTournamentid(Integer tournamentid);

    GameRecord findByTournamentidAndSeed1AndSeed2(Integer tournamentid, Integer seed1, Integer seed2);

}
