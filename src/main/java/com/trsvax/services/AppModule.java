package com.trsvax.services;

import java.io.File;

import org.apache.shiro.realm.Realm;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.ServiceBuilder;
import org.apache.tapestry5.ioc.ServiceResources;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.impl.SecurityFilterChain;
import org.tynamo.shiro.extension.realm.text.ExtendedPropertiesRealm;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.trsvax.bootstrap.services.TwitterBootstrapModule;
import com.trsvax.interfaces.dao.BlogDAO;
import com.trsvax.interfaces.entities.Blog;
import com.trsvax.services.AWSImpl.BlogDAOAWS;
import com.trsvax.tapestry.aws.core.services.AWSCoreModule;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@SubModule({AWSCoreModule.class,TwitterBootstrapModule.class})
public class AppModule 
{
    public static void bind(ServiceBinder binder)
    {
       binder.bind(BlogDAO.class,BlogDAOAWS.class);
       binder.bind(XML.class,XMLImpl.class);
       binder.bind(AWSCredentials.class, new ServiceBuilder<AWSCredentials>() {
			public AWSCredentials buildService(ServiceResources serviceResources) {
				try {
					return new PropertiesCredentials( new File(System.getenv("HOME") + "/.aws/keys.ini"));
				} catch (Exception e) {
					e.printStackTrace();
				} 
				return null;
			}
		});

    }

    public static void contributeFactoryDefaults(
            MappedConfiguration<String, Object> configuration)
    {
        configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
    }

    public static void contributeApplicationDefaults(
            MappedConfiguration<String, Object> configuration)
    {
    	//configuration.add(SymbolConstants.PRODUCTION_MODE, true);
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add("Application.name", "Blog");
        configuration.add("Application.blogBucket", "com.trsvax.blog");
        configuration.add("Application.awsKeys", System.getenv("HOME") + "/.aws/keys.ini");
    }


    public static void contributeSecurityConfiguration(Configuration<SecurityFilterChain> configuration,
            SecurityFilterChainFactory factory) {
    	configuration.add(factory.createChain("/admin/**").add(factory.roles(), "admin").build());
    }
    

    public static void contributeWebSecurityManager(Configuration<Realm> configuration) {
        ExtendedPropertiesRealm realm = new ExtendedPropertiesRealm(System.getenv("HOME") + "/.shiro/blog.ini");
        configuration.add(realm);
    }
    
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
