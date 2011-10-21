package com.trsvax.interfaces.dao;

import java.util.List;

import com.trsvax.interfaces.entities.Blog;

public interface BlogDAO {
	
	
	/**
	 * @return List of all Blogs
	 */
	public List<Blog> list();
	
	
	/**
	 * @return List of published Blogs
	 */
	public List<Blog> published();
	
	/**
	 * @param blog
	 */
	public void save(Blog blog);
	
	/**
	 * @param key
	 * @return Blog for key
	 */
	public Blog fetch(Object key);
	
	
	/**
	 * @return Latest published Blog
	 */
	public Blog latest();

}
