<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:tp="http://data.tp/" 
	xmlns:std="http://standard/">
	
	<xsl:output method="xml" encoding="UTF-8" indent="yes" />
	
	<xsl:template match="tp:demande" >
		<order xmlns="http://standard/">
			<id>
				<xsl:value-of select="tp:id" />
			</id>
			<category>
				<xsl:value-of select="tp:type" />
			</category>
		</order>
	</xsl:template>
	
	
	<!-- laisser le reste tel quel (transformation identite) -->
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>