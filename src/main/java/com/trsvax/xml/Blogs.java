package com.trsvax.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.trsvax.entities.BlogImpl;
import com.trsvax.interfaces.entities.Blog;

@SuppressWarnings("restriction")
@XmlRootElement
public class Blogs {
	private List<Blog> blogs;
	
	public Blogs() {
		
	}
	
	public Blogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	
	@XmlElement(name="blog",type=BlogImpl.class)
	public List<Blog> getBlogs() {
		return blogs;
	}
	
	
}
