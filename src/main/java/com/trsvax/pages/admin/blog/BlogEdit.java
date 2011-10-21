package com.trsvax.pages.admin.blog;

import java.util.Date;

import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.trsvax.entities.BlogImpl;
import com.trsvax.interfaces.dao.BlogDAO;
import com.trsvax.interfaces.entities.Blog;

public class BlogEdit {
	
	@PageActivationContext
	@Property
	private Blog entry;
	
	@Inject
	private BlogDAO dao;
	
	
	void onActivate() {
		if ( entry == null ) {
			entry = new BlogImpl();
			entry.setCreationDate(new Date());
		}
	}
	
	Object onSuccess() {
		dao.save(entry);
		return BlogIndex.class;
	}

}
