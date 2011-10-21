package com.trsvax.services.AWSImpl;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.ValueEncoderFactory;

import com.trsvax.interfaces.dao.BlogDAO;
import com.trsvax.interfaces.entities.Blog;


public class AWSModule {
	
	public static void contributeValueEncoderSource(MappedConfiguration<Class, ValueEncoderFactory> configuration,
			final BlogDAO blogDAO) {
		configuration.add(Blog.class, new ValueEncoderFactory<Blog>() {

			public ValueEncoder<Blog> create(Class<Blog> type) {
				return new ValueEncoder<Blog>() {

					public String toClient(Blog entity) {
						if ( entity.getKey() == null ) {
							return "";
						}
						return entity.getKey().toString();
					}

					public Blog toValue(String key) {
						return blogDAO.fetch(key);
					}
				};
			}
		});
		
	}

}
