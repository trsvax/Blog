package com.trsvax.pages.admin.blog;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.trsvax.interfaces.dao.BlogDAO;
import com.trsvax.interfaces.entities.Blog;

public class BlogIndex {
	
	@Property
	private List<Blog> blogs;
	@Property
	private Blog blog;
	
	@Inject
	private BlogDAO dao;
	
	@SetupRender
	void setupRender() {
		blogs = dao.list();
	}

}
