package com.trsvax.services;

import java.util.Map;

import org.apache.tapestry5.StreamResponse;

public interface XML {

	public StreamResponse streamWithStyle(final Object object, final String mimetype, 
			final String style, final Map<String,String> args);
}
