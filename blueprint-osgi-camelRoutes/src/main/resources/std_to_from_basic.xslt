<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	xmlns:bas="http://service.tp/" 
	xmlns:std="http://standard/">
	<xsl:template match="std:add">
		<bas:addition>
			<a>
				<xsl:value-of select="x" />
			</a>
			<b>
				<xsl:value-of select="y" />
			</b>
		</bas:addition>
	</xsl:template>
	<!-- <xsl:template match="std:addResponse">
		<bas:additionResponse>
			<return><xsl:value-of select="return" /></return>
		</bas:additionResponse>
	</xsl:template> -->
	<xsl:template match="std:mult">
		<bas:multiplication>
			<a>
				<xsl:value-of select="x" />
			</a>
			<b>
				<xsl:value-of select="y" />
			</b>
		</bas:multiplication>
	</xsl:template>
	<!-- <xsl:template match="std:multResponse">
		<bas:multiplicationResponse>
			<return><xsl:value-of select="return" /></return>
		</bas:multiplicationResponse>
	</xsl:template> -->
	<!-- <xsl:template match="bas:addition">
		<std:add>
			<x>
				<xsl:value-of select="a" />
			</x>
			<y>
				<xsl:value-of select="b" />
			</y>
		</std:add>
	</xsl:template>-->
	<xsl:template match="bas:additionResponse">
		<std:addResponse>
			<return><xsl:value-of select="return" /></return>
		</std:addResponse>
	</xsl:template>
	<!-- <xsl:template match="bas:multiplication">
		<std:mult>
			<x>
				<xsl:value-of select="a" />
			</x>
			<y>
				<xsl:value-of select="b" />
			</y>
		</std:mult>
	</xsl:template>-->
	<xsl:template match="bas:multiplicationResponse">
		<std:multResponse>
			<return><xsl:value-of select="return" /></return>
		</std:multResponse>
	</xsl:template>
	
	<!-- laisser le reste tel quel (transformation identite) -->
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>