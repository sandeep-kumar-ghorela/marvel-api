package com.marvel.controller;

import com.marvel.ApplicationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(classes = ApplicationController.class)
public class BaseControllerTest
{
  @Autowired
  protected MockMvc mvc;
}