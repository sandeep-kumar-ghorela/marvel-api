package com.marvel.service;


import com.marvel.config.AppConfiguration;
import com.marvel.constants.MarvelConstants;
import com.marvel.dto.ResultsDto;
import com.marvel.service.impl.MarvelServiceImpl;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
@DisplayName("marvel service junits")
public class MarvelServiceImplTest {

  @InjectMocks
  private MarvelServiceImpl marvelService;

  @Mock
  private AppConfiguration appConfiguration;

  private static final String SERVICE_URL = "http://gateway.marvel.com/v1/public/characters";

  @BeforeEach
  public void setup()
  {
    given(appConfiguration.getServiceUrl()).willReturn(SERVICE_URL);
    given(appConfiguration.getHashValue()).willReturn("hash=93169818e97d668892b4849c6ad42fae");
    given(appConfiguration.getPublicKey()).willReturn("?apikey=345934088c7bbb162a21be411c121b66");
    given(appConfiguration.getTimeStamp()).willReturn("ts=123456");
  }

  @Test
  public void testGeCharacterIds() throws Exception
  {
    given(appConfiguration.getLimit()).willReturn("limit=100");
    final Map<Integer, Object> dataMap = marvelService.geCharacterIds();
    final Integer key = dataMap.keySet().stream().findFirst().get();
    assertTrue(key == 200);
    assertTrue(((List<Integer>)dataMap.get(key)).size() == 100);

  }

  @Test
  public void testgetCharacterDetail() throws Exception
  {
    given(appConfiguration.getServiceUrl()).willReturn(SERVICE_URL + MarvelConstants.FORWARD_SLASH);
    final Map<Integer, Object> dataMap = marvelService.getCharacterDetail("1011334");
    final Integer key = dataMap.keySet().stream().findFirst().get();
    final ResultsDto data = (ResultsDto)dataMap.get(key);
    assertTrue(key == 200);
    assertTrue(data.getId() == 1011334);

  }

  @Test
  public void shouldReturnUnauthorizedWhenPublicKeyIsInvalid() throws Exception
  {
    given(appConfiguration.getPublicKey()).willReturn("?apikey=dummy");
    final Map<Integer, Object> dataMap = marvelService.geCharacterIds();
    final Integer key = dataMap.keySet().stream().findFirst().get();
    assertTrue(key == 401);

  }

  @Test
  public void shouldReturnUnauthorizedWhenHashIsInvalid() throws Exception
  {
    given(appConfiguration.getHashValue()).willReturn("hash=dummy");
    final Map<Integer, Object> dataMap = marvelService.geCharacterIds();
    final Integer key = dataMap.keySet().stream().findFirst().get();
    assertTrue(key == 401);

  }

}
