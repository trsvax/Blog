package com.trsvax.pages;

import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.trsvax.interfaces.dao.BlogDAO;
import com.trsvax.interfaces.entities.Blog;

/**
 * Start page of application blog.
 */
public class Index
{
	@PageActivationContext
	@Property
	private Blog blog;
	
	@Inject
	private BlogDAO dao;
	
	
	@BeginRender
	void init() {
		if ( blog == null ) {
			blog = dao.latest();
		}
	}

    
}
