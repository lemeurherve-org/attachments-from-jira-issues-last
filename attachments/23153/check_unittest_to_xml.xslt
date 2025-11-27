<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0" xmlns:c="http://check.sourceforge.net/ns">

<!--

bart@vankuik.nl, 13-MAR-2006

You can use this xslt on the commandline as follows:
$ xsltproc check_unittest.xslt check_output.xml > check_output.html

-->

<xsl:output method="xml" indent="yes" />

<xsl:variable name="suite_timestamp" select="//c:testsuites/c:datetime" />
<xsl:variable name="suite_duration" select="//c:testsuites/c:duration * 1000" />

<xsl:template match="c:testsuites">
	<testsuites>
		<xsl:attribute name="time">
			<xsl:value-of select="$suite_duration" />
		</xsl:attribute>
		<xsl:apply-templates />
	</testsuites>
</xsl:template>

<xsl:template match="c:suite">
	<testsuite>
		<xsl:attribute name="disabled">
			<xsl:value-of select="0" />
		</xsl:attribute>
		<xsl:attribute name="errors">
			<xsl:value-of select="count(./c:test[@result='error'])" />
		</xsl:attribute>
		<xsl:attribute name="failures">
			<xsl:value-of select="count(./c:test[@result='failure'])" />
		</xsl:attribute>
		<xsl:attribute name="name">
			<xsl:value-of select="./c:title" />
		</xsl:attribute>
		<xsl:attribute name="skipped">
			<xsl:value-of select="0" />
		</xsl:attribute>
		<xsl:attribute name="tests">
			<xsl:value-of select="count(./c:test)" />
		</xsl:attribute>
		<xsl:attribute name="timestamp">
			<xsl:value-of select="$suite_timestamp" />
		</xsl:attribute>
		<xsl:attribute name="time">
			<xsl:value-of select="$suite_duration" />
		</xsl:attribute>
		<xsl:apply-templates />
	</testsuite>
</xsl:template>

<xsl:template match="c:test">
	<testcase>
		<xsl:attribute name="name">
			<xsl:value-of select="./c:id" />
		</xsl:attribute>
		<xsl:attribute name="time">
			<xsl:value-of select="./c:duration * 1000" />
		</xsl:attribute>
		<xsl:attribute name="classname">
			<xsl:value-of select="./c:description" />
		</xsl:attribute>
		<xsl:if test="@result='failure'">
			<failure>
				<xsl:attribute name="message">
					<xsl:value-of select="./c:message" />
				</xsl:attribute>
				<xsl:attribute name="type">
					<xsl:value-of select="failure" />
				</xsl:attribute>
			</failure>
		</xsl:if>
		<xsl:if test="@result='error'">
			<error>
				<xsl:attribute name="message">
					<xsl:value-of select="./c:message" />
				</xsl:attribute>
				<xsl:attribute name="type">
					<xsl:value-of select="error" />
				</xsl:attribute>
			</error>
		</xsl:if>
	</testcase>
</xsl:template>

<!-- this swallows all unmatched text -->
<xsl:template match="text()|@*" />

</xsl:stylesheet>