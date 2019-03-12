package com.icthh.xm.tmf.ms.resource.cucumber.stepdefs;

import com.icthh.xm.tmf.ms.resource.ResourceApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ResourceApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
