package com.trsvax.components;

import java.util.List;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;

import com.trsvax.interfaces.dao.BlogDAO;
import com.trsvax.interfaces.entities.Blog;

/**
 * Layout component for pages of application blog.
 */
//@Import(stylesheet="context:layout/layout.css")
public class Layout
{
    /**
     * The page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;
    
    @Property
    @Parameter
    private String tagline;

    @Property
    private String pageName;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;
    
    @Property
    @Inject
    @Symbol(SymbolConstants.TAPESTRY_VERSION)
    private String tapestryVersion;
    
    @Property
    @Inject
    @Symbol("Application.name")
    private String appName;
    
    @Property
    private List<Blog> blogs;
    
    @Inject
    private BlogDAO dao;
    
    @BeginRender
    void beginRender() {
    	blogs = dao.published();
    }


    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "current_page_item"
                : null;
    }

    public String[] getPageNames()
    {
        return new String[]{"Index", "About", "Contact"};
    }
}
