package com.marvel.service.impl;

import com.marvel.config.AppConfiguration;
import com.marvel.config.CachingConfig;
import com.marvel.dto.ErrorMessageDto;
import com.marvel.dto.MarvelCharaterDto;

import static com.marvel.constants.MarvelConstants.FORWARD_SLASH;
import static com.marvel.constants.MarvelConstants.AMPERSAND;

import com.marvel.service.MarvelService;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;


/**
 * This class interacts with Marvel apis
 */
@Service
public class MarvelServiceImpl implements MarvelService {

  private static final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1)
      .build();

  @Resource
  private AppConfiguration appConfiguration;

  private Logger LOG = LoggerFactory.getLogger(MarvelServiceImpl.class);

  /**
   * Returns marvel character ids
   */
  @Override
  @Cacheable(value = CachingConfig.REGION_CHARATER_IDS)
  public Map<Integer, Object> geCharacterIds() {
    final String serviceURL = getServiceUrl(appConfiguration.getServiceUrl(),
        appConfiguration.getPublicKey(), AMPERSAND, appConfiguration.getHashValue(),
        AMPERSAND, appConfiguration.getTimeStamp(), AMPERSAND, appConfiguration.getLimit());
    return processData(serviceURL, false);
  }


  /**
   * @param id
   * @return charater detail
   */
  @Override
  public Map<Integer, Object> getCharacterDetail(final String id) {
    final String serviceURL = getServiceUrl(appConfiguration.getServiceUrl(), FORWARD_SLASH, id,
        appConfiguration.getPublicKey(), AMPERSAND, appConfiguration.getHashValue(),
        AMPERSAND, appConfiguration.getTimeStamp());
    return processData(serviceURL, true);
  }

  private Map<Integer, Object> processData(final String serviceURL, final boolean characterIdExist) {

    final HttpResponse<String> response = invokeMarvelService(serviceURL);

    if (!Objects.isNull(response)) {
      if (response.statusCode() == 200) {
        if (characterIdExist) {
          return populateCharacterDetails(response);
        } else {
          return populateCharacterIds(response);
        }

      } else {
        return populateErrorData(response);
      }
    } else {
      return populateErrors();
    }

  }

  private Map<Integer, Object> populateCharacterIds(final HttpResponse<String> response)
  {
    final MarvelCharaterDto data = new Gson().fromJson(response.body(), MarvelCharaterDto.class);
    return getMap(response.statusCode(), data.getData().getResults().stream().map(ar -> ar.getId())
        .collect(Collectors.toList()));
  }

  private Map<Integer, Object> populateCharacterDetails(final HttpResponse<String> response)
  {
    final MarvelCharaterDto data = new Gson().fromJson(response.body(), MarvelCharaterDto.class);
    return getMap(response.statusCode(), data.getData().getResults().get(0));
  }

  private Map<Integer, Object> populateErrorData(final HttpResponse<String> response)
  {
    return getMap(response.statusCode(), new Gson().fromJson(response.body(), ErrorMessageDto.class));
  }

  private Map<Integer, Object> populateErrors()
  {
    ErrorMessageDto data = new ErrorMessageDto();
    data.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
    data.setMessage("Error while calling Marvel service");
    return getMap(500, data);
  }

  private Map<Integer, Object> getMap(int key, Object value)
  {
    final Map<Integer, Object> map = new HashMap<>();
    map.put(key, value);
    return map;
  }

  private String getServiceUrl(final String... params) {
    final StringBuilder urlBuilder = new StringBuilder();
    for (String args : params) {
      urlBuilder.append(args);
    }
    return urlBuilder.toString();
  }

  private HttpResponse<String> invokeMarvelService(final String url) {

    HttpResponse<String> response = null;
    try {

      final HttpRequest request = HttpRequest
          .newBuilder(URI.create(url)).GET()
          .build();

      response = client.send(request, BodyHandlers.ofString());

    } catch (Exception e) {
      LOG.error("Error while calling Marvel service [{}]" + url, e);
    }
    return response;
  }

}