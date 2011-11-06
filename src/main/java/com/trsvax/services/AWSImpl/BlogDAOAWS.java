package com.trsvax.services.AWSImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.trsvax.entities.BlogImpl;
import com.trsvax.interfaces.dao.BlogDAO;
import com.trsvax.interfaces.entities.Blog;

public class BlogDAOAWS implements BlogDAO {
	private final Logger logger;
	private final AmazonS3 s3;
	private final String bucketName;
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssZ");
	List<Blog> blogs = new ArrayList<Blog>();
	List<Blog> published = new ArrayList<Blog>();
	
	
	public BlogDAOAWS(Logger logger, AmazonS3 s3, @Symbol("Application.blogBucket") String blogBucket) {
		this.logger = logger;
		this.s3 = s3;
		this.bucketName = blogBucket;
	}

	public synchronized List<Blog> list() {
		if ( blogs.size() > 0 ) {
			return blogs;
		}
		ObjectListing objects = s3.listObjects(bucketName);
		for ( S3ObjectSummary os : objects.getObjectSummaries() ) {
			GetObjectRequest request = new GetObjectRequest(bucketName, os.getKey());			
			blogs.add( from(s3.getObject(request)));			
		}
		Collections.sort(blogs, new Comparator<Blog>() {

			public int compare(Blog o1, Blog o2) {
				return - o1.getCreationDate().compareTo(o2.getCreationDate());
			}
		});
		published.clear();
		for ( Blog blog : blogs ) {
			if ( blog.getPublished() ) {
				published.add(blog);
			}
		}
		return blogs;
	}
	
	public List<Blog> published() {
		list();
		return published;
	}

	public void save(Blog entry) {
		if ( entry.getKey() == null ) {
			entry.setKey(entry.getTitle().replaceAll(" ", ""));
		}
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(entry.getBody().length());
		metadata.setContentType("text/html");
		metadata.addUserMetadata("title", entry.getTitle());
		metadata.addUserMetadata("published", entry.getPublished() ? "t" : "f");
		metadata.addUserMetadata("creationdate", formatter.format(entry.getCreationDate()));

		PutObjectRequest request = new PutObjectRequest(bucketName, entry.getKey().toString(), 
				new ByteArrayInputStream(entry.getBody().getBytes()), metadata);

		s3.putObject(request);
		blogs.clear();
	}

	public Blog fetch(Object key) {
		for ( Blog e : list() ) {
			if ( e.getKey().equals(key) ) {
				return e;
			}
		}
		return null;
	}

	public Blog latest() {
		list();
		return published.size() > 0 ? published.get(0) : new BlogImpl();
	}
	
	Blog from(S3Object o) {
		Map<String,String> data = o.getObjectMetadata().getUserMetadata();
		
		Blog blog = new BlogImpl();
		blog.setKey(o.getKey());
		blog.setTitle(data.get("title"));
		try {
			blog.setCreationDate(formatter.parse(data.get("creationdate")));
		} catch (Exception e1) {
			logger.error("can't format {}",data.get("creationdate"));
		}
		if ( data.get("published") != null && data.get("published").equals("t")) {
			blog.setPublished(true);
		} else {
			blog.setPublished(false);
		}		
		
		try {
			Integer off = 0;
			Integer len = (int) o.getObjectMetadata().getContentLength();
			byte[] b = new byte[len];
						
			while ( off < len )  {				
				off += o.getObjectContent().read(b, off, len - off);
			}

			blog.setBody(new String(b));
		} catch (IOException e) {
			logger.error("Can't read body {}",e);
		} finally {
			try {
				o.getObjectContent().close();
			} catch (IOException e) {
				logger.error("Can't close {}",e);
			}
		}		
		return blog;
	}

}
