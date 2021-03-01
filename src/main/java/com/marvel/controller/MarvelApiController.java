package com.marvel.controller;


import com.marvel.dto.MarvelCharaterDto;
import com.marvel.service.MarvelService;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.marvel.constants.MarvelConstants.FORWARD_SLASH;
import static com.marvel.constants.MarvelConstants.URI_CHARACTERS;

/**
 * this class checks whether a given number is prime number or not This implementation only supports
 * maximum number as 2147483647
 */
@RestController
@RequestMapping(value = FORWARD_SLASH)
public class MarvelApiController {

  @Resource
  private MarvelService marvelService;

  @RequestMapping(value = URI_CHARACTERS, method = RequestMethod.GET)
  public ResponseEntity geCharacterIds() {
    return filterResponse(marvelService.geCharacterIds());
  }

  @RequestMapping(value = URI_CHARACTERS + FORWARD_SLASH + "{id}", method = RequestMethod.GET)
  public Object getCharacterDetail(final @PathVariable String id) {
    return filterResponse(marvelService.getCharacterDetail(id));
  }

  private ResponseEntity filterResponse(final Map<Integer, Object> dataMap) {
    final Integer key = dataMap.keySet().stream().findFirst().get();
    return new ResponseEntity<>(dataMap.get(key), HttpStatus.valueOf(key));
  }

}
