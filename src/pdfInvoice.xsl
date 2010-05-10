<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:output indent="yes" />

	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4"
					page-height="29.7cm" page-width="21cm" margin-top="2cm"
					margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body />
				</fo:simple-page-master>
			</fo:layout-master-set>
			<xsl:apply-templates />
		</fo:root>
	</xsl:template>

	<!-- table -->
	<xsl:template match="invoice">
		<fo:page-sequence master-reference="A4">
			<fo:flow flow-name="xsl-region-body">
				<fo:table table-layout="fixed" width="100%">
					<fo:table-column column-width="11cm" />
					<fo:table-column column-width="3cm" />
					<fo:table-column column-width="3cm" />
					<fo:table-body font-size="12pt" line-height="16pt">
						<xsl:apply-templates select="company" />
						<xsl:apply-templates select="customer" />
						<xsl:apply-templates select="date" />
						<xsl:apply-templates select="content" />
						<xsl:apply-templates select="account" />
					</fo:table-body>
				</fo:table>
			</fo:flow>
		</fo:page-sequence>
	</xsl:template>

	<xsl:template match="company">
		<fo:table-row line-height="3cm">
			<fo:table-cell number-columns-spanned="3" font-size="20pt">
				<fo:block>
					<xsl:value-of select="companyname" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell number-columns-spanned="3">
				<fo:block>
					<xsl:value-of select="name" />
				</fo:block>
				<fo:block>
					<xsl:value-of select="address" />
				</fo:block>
				<fo:block>
					<xsl:value-of select="location" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row line-height="0.5cm">
			<fo:table-cell number-columns-spanned="3">
				<fo:block>&#160;</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell number-columns-spanned="3">
				<fo:block>
					<xsl:value-of select="email" />
				</fo:block>
				<fo:block>
					<xsl:value-of select="phone" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="customer">
		<fo:table-row>
			<fo:table-cell>
				<fo:block></fo:block>
			</fo:table-cell>
			<fo:table-cell number-columns-spanned="2">
				<fo:block>
					<xsl:value-of select="name" />
				</fo:block>
				<fo:block>
					<xsl:value-of select="address" />
				</fo:block>
				<fo:block>
					<xsl:value-of select="location" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row line-height="1cm">
			<fo:table-cell number-columns-spanned="3">
				<fo:block>&#160;</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="date">
		<fo:table-row>
			<fo:table-cell>
				<fo:block></fo:block>
			</fo:table-cell>
			<fo:table-cell number-columns-spanned="2">
				<fo:block>
					<xsl:value-of select="datestr" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row line-height="1cm">
			<fo:table-cell number-columns-spanned="3">
				<fo:block>&#160;</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="content">
		<fo:table-row>
			<fo:table-cell font-size="16pt">
				<fo:block>
					<xsl:value-of select="title" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row line-height="0.5cm">
			<fo:table-cell number-columns-spanned="3"
				border-bottom="1pt solid black">
				<fo:block>&#160;</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<xsl:for-each select="service">
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<xsl:value-of select="item" />
					</fo:block>
				</fo:table-cell>
				<fo:table-cell>
					<fo:block></fo:block>
				</fo:table-cell>
				<fo:table-cell text-align="end">
					<fo:block>
						<xsl:value-of select="price" />
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
		</xsl:for-each>
		<fo:table-row line-height="0.1cm">
			<fo:table-cell number-columns-spanned="3" border-top="1pt solid black">
				<fo:block>&#160;</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block></fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
					<xsl:value-of select="total" />
				</fo:block>
			</fo:table-cell>
			<fo:table-cell text-align="end">
				<fo:block>
					<xsl:value-of select="sum" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row>
			<fo:table-cell>
				<fo:block></fo:block>
			</fo:table-cell>
			<fo:table-cell number-columns-spanned="2"
				border-top="2pt double black">
				<fo:block>&#160;</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row line-height="2cm">
			<fo:table-cell number-columns-spanned="3">
				<fo:block>&#160;</fo:block>
			</fo:table-cell>
		</fo:table-row>
	</xsl:template>

	<xsl:template match="account">
		<fo:table-row>
			<fo:table-cell number-columns-spanned="3"
				border-bottom="1pt solid black">
				<fo:block>
					<xsl:value-of select="title" />
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		<xsl:for-each select="line">
			<fo:table-row>
				<fo:table-cell>
					<fo:block>
						<xsl:value-of select="info" />
					</fo:block>
				</fo:table-cell>
			</fo:table-row>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
