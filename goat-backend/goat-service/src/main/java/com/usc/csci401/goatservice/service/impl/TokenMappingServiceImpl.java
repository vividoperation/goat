package com.usc.csci401.goatservice.service.impl;

import com.google.common.base.Preconditions;
import com.usc.csci401.goatcommon.dto.TokenMappingDTO;
import com.usc.csci401.goatcommon.exception.GameException;
import com.usc.csci401.goatcommon.util.BeanConvertUtils;
import com.usc.csci401.goatdao.TokenMappingRepository;
import com.usc.csci401.goatdao.model.TokenMapping;
import com.usc.csci401.goatservice.service.TokenMappingService;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenMappingServiceImpl implements TokenMappingService {

  @Autowired
  private TokenMappingRepository tokenMappingRepository;

  @Override
  public Integer addTokenMapping(String player, Integer gameId){
    Preconditions.checkArgument(Strings.isNotBlank(player), "player cannot be blank");
    Preconditions.checkArgument(gameId != null, "gameId cannot be null");

    TokenMapping tokenMapping = new TokenMapping();
    tokenMapping.setGameid(gameId);
    tokenMapping.setUsername(player);

    return tokenMappingRepository.save(tokenMapping).getToken();
  }

  @Override
  public TokenMappingDTO findByToken(Integer token){
    Preconditions.checkArgument(token != null, "token cannot be null");
    Optional<TokenMapping> tm = tokenMappingRepository.findByToken(token);
    if(!tm.isPresent()){
      throw new GameException("User token does not exist");
    }

    return BeanConvertUtils.convert(tm.get(), TokenMappingDTO.class, true);
  }

  @Override
  @Transactional
  public void deletebyToken(Integer gameId){
    Preconditions.checkArgument(gameId != null, "gameId cannot be null");
    tokenMappingRepository.deleteByGameid(gameId);
  }


}
