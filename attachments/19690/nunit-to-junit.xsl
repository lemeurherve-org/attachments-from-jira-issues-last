<?xml version="1.0" encoding="UTF-8"?>
<!-- By Eamon Nerbonne (Rijksuniversiteit Groningen) based on previous work from hudson's nunit plugin (2010-03 version).-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes" />

	<xsl:template match="test-suite" mode="name">
		<xsl:apply-templates select="ancestor::test-suite[1]" mode="nameWithDot" />
		<xsl:value-of select="@name"/>
	</xsl:template>
	<xsl:template match="test-suite" mode="nameWithDot">
		<xsl:if test="not(@type = 'Assembly' or @type = 'Project')">
			<xsl:apply-templates select="." mode="name" />
			<xsl:text>.</xsl:text>
		</xsl:if>
	</xsl:template>
	<xsl:template match="/test-results">
		<testsuites>
			<xsl:for-each select="//test-suite[results/test-case]">
				<xsl:variable name="fulltestsuitename">
					<xsl:apply-templates select="." mode="name"/>
				</xsl:variable>
				<testsuite name="{$fulltestsuitename}"
						  tests="{count(*/test-case)}" time="{@time}"
						  failures="{count(*/test-case/failure)}" errors="0"
						  skipped="{count(*/test-case[@executed='False'])}">
					<xsl:for-each select="*/test-case[@time!='']">
						<xsl:variable name="testcaseName">
							<xsl:choose>
								<xsl:when test="starts-with(./@name, concat($fulltestsuitename,'.'))">
									<xsl:value-of select="substring-after(./@name, concat($fulltestsuitename,'.'))"/>
								</xsl:when>
								<xsl:when test="starts-with(./@name, concat($fulltestsuitename,'('))">
									<xsl:value-of select="concat(ancestor::test-suite[1]/@name,'(', substring-after(./@name, concat($fulltestsuitename,'(')))"/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:value-of select="./@name"/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:variable>

						<testcase classname="{$fulltestsuitename}" name="{$testcaseName}" time="{@time}">
							<xsl:apply-templates select="failure"/>
						</testcase>
					</xsl:for-each>
				</testsuite>
			</xsl:for-each>
		</testsuites>
	</xsl:template>
	<xsl:template match="failure">
		<failure>
			MESSAGE:
			<xsl:value-of select="message" />
			+++++++++++++++++++
			STACK TRACE:
			<xsl:value-of select="stack-trace" />
		</failure>
	</xsl:template>
</xsl:stylesheet>
