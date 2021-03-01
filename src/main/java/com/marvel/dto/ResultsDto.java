package com.marvel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultsDto {

  private String name;
  private String description;
  private ThumbnailDto thumbnail;
  private int id;

}
