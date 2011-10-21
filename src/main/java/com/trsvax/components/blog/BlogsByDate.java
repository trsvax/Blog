package com.trsvax.components.blog;

import java.util.List;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.trsvax.interfaces.entities.Blog;

public class BlogsByDate {
	
	@Parameter
	@Property
	private List<Blog> blogs;
	@Property
	private Blog blog;

}
