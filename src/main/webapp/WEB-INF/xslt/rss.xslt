<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/blogs">
<rss version="2.0">
<channel>
        <title>Barry's Tapestry Blog</title>
        <description>A Blog mostly about Tapestry</description>
        <link>http://trsvax.com/blog</link>
        <lastBuildDate>Mon, 06 Sep 2010 00:01:00 +0000 </lastBuildDate>
        <pubDate>Mon, 06 Sep 2009 16:45:00 +0000 </pubDate>
        <ttl>1800</ttl>
        
        <xsl:apply-templates select="blog"/>
</channel>
</rss>
</xsl:template>

<xsl:template match="blog">
	<item>
                <title><xsl:value-of select="title"/></title>
                <link>http://trsvax.com/blog</link>
                <guid><xsl:value-of select="key"/></guid>
                <pubDate><xsl:value-of select="creationDate"/></pubDate>
        </item>
</xsl:template>
</xsl:stylesheet>