package com.trsvax.components.blog;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.trsvax.interfaces.entities.Blog;

public class BlogView {
	
	
	@Parameter(autoconnect=true)
	@Property
	private Blog blog;

}
