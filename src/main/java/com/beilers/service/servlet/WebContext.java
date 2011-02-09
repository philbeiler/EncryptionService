package com.beilers.service.servlet;

import org.springframework.web.context.support.XmlWebApplicationContext;

public class WebContext extends XmlWebApplicationContext {

    @Override
    protected String[] getDefaultConfigLocations() {

        return new String[]{"/WEB-INF/classes/com/beilers/service/resources/contexts/ApplicationContext.xml"};
    }

}
