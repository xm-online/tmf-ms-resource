package com.icthh.xm.tmf.ms.resource.cucumber.stepdefs;

import com.icthh.xm.tmf.ms.resource.ResourceApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = ResourceApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
