<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ms ="http://microsoft.com/schemas/VisualStudio/TeamTest/2010" >
	<xsl:output method="xml" encoding="UTF-8"/>
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="ms:OrderedTest">

	</xsl:template>

	<xsl:template match="ms:TestRun/ms:TestEntries">
		<xsl:copy>
			<xsl:apply-templates select="@*"/>
			<xsl:for-each select="descendant::ms:TestEntry">
				<xsl:copy>
					<xsl:apply-templates select="@*"/>
				</xsl:copy>
			</xsl:for-each>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>