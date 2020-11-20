package com.usc.csci401.goatservice.service;

import com.usc.csci401.goatcommon.dto.TokenMappingDTO;

public interface TokenMappingService {

  Integer addTokenMapping(String player, Integer gameId);

  TokenMappingDTO findByToken(Integer token);

  void deletebyToken(Integer gameId);

}
