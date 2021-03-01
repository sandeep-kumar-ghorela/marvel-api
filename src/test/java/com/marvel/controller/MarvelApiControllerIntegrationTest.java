package com.marvel.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.marvel.constants.MarvelConstants.FORWARD_SLASH;
import static com.marvel.constants.MarvelConstants.URI_CHARACTERS;

import com.marvel.config.CachingConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MarvelApiControllerIntegrationTest extends BaseControllerTest {

  @Autowired
  CacheManager cacheManager;

  @Test
  public void shouldReturnStatusOK_GetCharacterIds()throws Exception
  {
    mvc.perform(get( FORWARD_SLASH + URI_CHARACTERS)).andExpect(status().isOk());
  }

  @Test
  public void shouldReturnStatusOK_GetCharacterDetail()throws Exception
  {
    mvc.perform(get( FORWARD_SLASH + URI_CHARACTERS + FORWARD_SLASH + 1011334)).andExpect(status().isOk());
  }

  @Test
  public void cacheShouldNotBeEmpty()throws Exception
  {
    mvc.perform(get( FORWARD_SLASH + URI_CHARACTERS)).andExpect(status().isOk());
    assertNotNull(cacheManager.getCache(CachingConfig.REGION_CHARATER_IDS).getNativeCache());
  }

}
