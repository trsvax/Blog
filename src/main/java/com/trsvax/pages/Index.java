package com.trsvax.pages;

import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.trsvax.interfaces.dao.BlogDAO;
import com.trsvax.interfaces.entities.Blog;
import com.trsvax.services.XML;
import com.trsvax.xml.Blogs;

/**
 * Start page of application blog.
 */
@Import(stylesheet="classpath:/com/trsvax/bootstrap/google-code-prettify/prettify.css",
library="classpath:/com/trsvax/bootstrap/google-code-prettify/prettify.js")
public class Index
{
	@PageActivationContext
	@Property
	private Blog blog;
	
	@ActivationRequestParameter
	private String feed;
	
	@Inject
	private BlogDAO dao;
	
	@Inject
	private XML xml;
	
	Object onActivate() {
		if ( feed != null && feed.equals("rss")) {
			return xml.streamWithStyle(new Blogs(dao.published()), "application/rss+xml", "/WEB-INF/xslt/rss.xslt" , null);
		}
		return null;
	}
	
	
	@BeginRender
	void init() {
		if ( blog == null ) {
			blog = dao.latest();
		}
	}

    
}
