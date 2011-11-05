<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:template match="/blogs">
		<rss version="2.0">
			<channel>
				<title>Barry's Tapestry Blog</title>
				<link>http://trsvax.com/blog</link>
				<description>A Blog mostly about Tapestry</description>				
				<ttl>1000</ttl>
				<docs>http://blogs.law.harvard.edu/tech/rss</docs>
				<xsl:apply-templates select="blog" />
			</channel>
		</rss>
	</xsl:template>

	<xsl:template match="blog">
		<item>
			<title><xsl:value-of select="title" /></title>
			<link>http://trsvax.com/blog</link>
			<description></description>
			<author>trsvax@gmail.com</author>
			<category>Tapestry</category>
			<guid><xsl:value-of select="key" /></guid>
			<pubDate><xsl:value-of select="creationDate"/></pubDate>
			<source url="http://trsvax.com/Blog/?feed=rss">Barry's Tapestry Blog</source>
		</item>
	</xsl:template>
</xsl:stylesheet>