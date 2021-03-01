package com.marvel.service;

import com.marvel.dto.MarvelCharaterDto;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface MarvelService {

  Map<Integer, Object> geCharacterIds();

  Map<Integer, Object> getCharacterDetail(final String id);
}
