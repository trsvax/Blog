package com.trsvax.interfaces.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.apache.tapestry5.beaneditor.Validate;

import com.trsvax.entities.BlogImpl;


public interface Blog {
	
	@NonVisual
	public Object getKey();
	public void setKey(Object key);
	
	@NonVisual
	public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	
	@Validate("required")
	public String getTitle();
	public void setTitle(String title);
	
	public String getBody();
	public void setBody(String body);
	
	public Boolean getPublished();
	public void setPublished(Boolean published);
	

}
