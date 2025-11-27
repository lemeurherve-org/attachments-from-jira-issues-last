<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="yes" />

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="dependency">
		<xsl:copy>
			<xsl:apply-templates select="@*[name() != 'rev']" />
			<xsl:choose>
				<xsl:when test="exists(@revConstraint)">
					<xsl:attribute name="rev">
                        <xsl:value-of select="@revConstraint" />
                    </xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
                    <xsl:attribute name="rev">
                        <xsl:value-of select="@rev" />
                    </xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:apply-templates select="node()" />
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>

