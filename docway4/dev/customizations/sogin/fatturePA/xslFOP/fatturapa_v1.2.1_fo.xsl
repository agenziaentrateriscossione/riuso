<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:fo="http://www.w3.org/1999/XSL/Format"
		xmlns:p="http://www.fatturapa.gov.it/sdi/fatturapa/v1.1"
		version="1.0">

	<xsl:output encoding="UTF-8" indent="yes" method="xml" standalone="no" omit-xml-declaration="no" />
	
	<xsl:template name="FormatDate">
		<xsl:param name="DateTime" />

		<xsl:variable name="year" select="substring($DateTime,1,4)" />
		<xsl:variable name="month" select="substring($DateTime,6,2)" />
		<xsl:variable name="day" select="substring($DateTime,9,2)" />

		<xsl:value-of select="$day" />
		<xsl:text>/</xsl:text>
		<xsl:value-of select="$month" />
		<xsl:text>/</xsl:text>
		<xsl:value-of select="$year" />
	</xsl:template>
	
	<xsl:template name="FormatDateYYYYMMDD">
		<xsl:param name="DateTime" />

		<xsl:variable name="year" select="substring($DateTime,1,4)" />
		<xsl:variable name="month" select="substring($DateTime,5,2)" />
		<xsl:variable name="day" select="substring($DateTime,7,2)" />

		<xsl:value-of select="$day" />
		<xsl:text>/</xsl:text>
		<xsl:value-of select="$month" />
		<xsl:text>/</xsl:text>
		<xsl:value-of select="$year" />
	</xsl:template>
	
	<xsl:template name="FormatNumProt">
		<xsl:param name="ExtendeNumProt" />

		<xsl:variable name="year" select="substring($ExtendeNumProt,1,4)" />
		<xsl:variable name="number" select="number(substring($ExtendeNumProt,14))" />

		<xsl:value-of select="$number" />
		<xsl:text>/</xsl:text>
		<xsl:value-of select="$year" />
	</xsl:template>
	
	<xsl:attribute-set name="h5">
		<xsl:attribute name="font-size">16pt</xsl:attribute>
		<xsl:attribute name="line-height">19pt</xsl:attribute>
		<xsl:attribute name="space-after">8pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="h6">
		<xsl:attribute name="font-size">14pt</xsl:attribute>
		<xsl:attribute name="line-height">16pt</xsl:attribute>
		<xsl:attribute name="space-after">8pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="section-title">
		<xsl:attribute name="font-size">8pt</xsl:attribute>
		<xsl:attribute name="font-weight">bold</xsl:attribute>
		<xsl:attribute name="space-after">8pt</xsl:attribute>
		<xsl:attribute name="space-before">8pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="padding-top-20">
		<xsl:attribute name="space-before">20pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="padding-top-10">
		<xsl:attribute name="space-before">10pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="padding-top-5">
		<xsl:attribute name="space-before">5pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="font-size-8">
		<xsl:attribute name="font-size">8pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="font-size-10">
		<xsl:attribute name="font-size">10pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="table-content">
		<xsl:attribute name="table-layout">fixed</xsl:attribute>
		<xsl:attribute name="width">100%</xsl:attribute>
		<xsl:attribute name="space-after">8pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="layout-table">
		<xsl:attribute name="table-layout">fixed</xsl:attribute>
		<xsl:attribute name="width">100%</xsl:attribute>
		<xsl:attribute name="space-after">12pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="cell-padding">
		<xsl:attribute name="padding">0.8mm</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="cell-header">
		<xsl:attribute name="background-color">#ccc</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="border">
		<xsl:attribute name="border-color">black</xsl:attribute>
		<xsl:attribute name="border-width">0.1mm</xsl:attribute>
		<xsl:attribute name="border-style">solid</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="border-strong">
		<xsl:attribute name="border-color">black</xsl:attribute>
		<xsl:attribute name="border-width">0.3mm</xsl:attribute>
		<xsl:attribute name="border-style">solid</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="table-cell-label-small">
		<xsl:attribute name="font-size">6pt</xsl:attribute>
		<xsl:attribute name="space-after">6pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="cedente-prestatore">
		<xsl:attribute name="font-size">12pt</xsl:attribute>
		<xsl:attribute name="text-align">right</xsl:attribute>
		<xsl:attribute name="space-after">8pt</xsl:attribute>
		<xsl:attribute name="space-before">20pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="cedente-prestatore-text">
		<xsl:attribute name="font-size">8pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="cessionario-committente">
		<xsl:attribute name="font-size">12pt</xsl:attribute>
		<xsl:attribute name="space-after">8pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="cessionario-committente-text">
		<xsl:attribute name="font-size">8pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="table-logo-protocollo">
		<xsl:attribute name="table-layout">fixed</xsl:attribute>
		<xsl:attribute name="width">100%</xsl:attribute>
		<xsl:attribute name="space-after">25pt</xsl:attribute>
		<xsl:attribute name="font-size">10pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="table-protocollo">
		<xsl:attribute name="table-layout">fixed</xsl:attribute>
		<xsl:attribute name="width">100%</xsl:attribute>
		<xsl:attribute name="font-size">10pt</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:attribute-set name="border-protocollo">
		<xsl:attribute name="border-color">red</xsl:attribute>
		<xsl:attribute name="border-width">0.3mm</xsl:attribute>
	</xsl:attribute-set>

	<xsl:attribute-set name="no-border">
		<xsl:attribute name="border-color">white</xsl:attribute>
		<xsl:attribute name="border-width">0mm</xsl:attribute>
	</xsl:attribute-set>
	
	<xsl:template match="/">
        
        <!-- LOGHI DA VISUALIZZARE SULLA FATTURA -->
        <xsl:param name="logoSogin">url('data:image/png;base64,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')</xsl:param>
        <xsl:param name="logoNucleco">url('data:image/png;base64,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')</xsl:param>
	
	
		<fo:root language="IT">
			
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4-portrail" page-height="297mm" page-width="210mm"
																					margin-top="5mm" margin-bottom="5mm" margin-left="5mm"
																					margin-right="5mm">
					
					<fo:region-body margin-top="25mm" margin-bottom="20mm" />
					<fo:region-before region-name="xsl-region-before" extent="25mm" display-align="before" precedence="true" />
					<fo:region-after region-name="xsl-region-after" extent="15mm" />
				</fo:simple-page-master>
			</fo:layout-master-set>
			
			<fo:page-sequence master-reference="A4-portrail">
				
				<!-- INIZIO HEADER DI OGNI PAGINA DELLA FATTURA -->
				<fo:static-content flow-name="xsl-region-before">
					<fo:table table-layout="fixed" width="100%" border-width="0" font-size="8pt">
						<fo:table-column column-width="proportional-column-width(80)" />
						<fo:table-column column-width="proportional-column-width(20)" />
						
						<fo:table-body>
							<fo:table-row keep-together.within-page="always">
								<fo:table-cell text-align="left">
									<fo:block>
										FATTURA ELETTRONICA
										<xsl:if test="/*[local-name()='FatturaElettronica']/@versione">
											ver <xsl:value-of select="/*[local-name()='FatturaElettronica']/@versione"/>
										</xsl:if>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="right">
									<fo:block>Pagina <fo:page-number /></fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
				</fo:static-content>
				<!-- FINE HEADER DI OGNI PAGINA DELLA FATTURA -->
				
				<!-- INIZIO FOOTER DI OGNI PAGINA DELLA FATTURA -->
				<!--fo:static-content flow-name="xsl-region-after">
					<fo:block font-size="8pt" text-align="center">- <fo:page-number /> -</fo:block>
				</fo:static-content-->
				<!-- FINE FOOTER DI OGNI PAGINA DELLA FATTURA -->
			
				<!-- INZIO CORPO DI OGNI PAGINA DELLA FATTURA -->
				<fo:flow flow-name="xsl-region-body" border-collapse="collapse" reference-orientation="0">
				
                    <!-- EVENTUALI LOGHI E INFO DI SEGNATURA -->
                    <xsl:if test="(/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/DatiTrasmissione/IdTrasmittente/IdCodice = '05779721009') or (/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/DatiTrasmissione/IdTrasmittente/IdCodice = '05081150582') or (/*[local-name()='FatturaElettronica']/DatiSegnatura/NumeroProtocollo and /*[local-name()='FatturaElettronica']/DatiSegnatura/NumeroProtocollo != '')">
                    
                        <fo:table xsl:use-attribute-sets="table-logo-protocollo">
							<fo:table-column column-width="proportional-column-width(35)" />
							<fo:table-column column-width="proportional-column-width(65)" />
							
							<fo:table-body>
								<fo:table-row keep-together.within-page="always">
									<fo:table-cell xsl:use-attribute-sets="no-border">
										
										<!-- LOGO AZIENDALE -->
                                        <fo:block margin-bottom="5mm">
                                            <xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/DatiTrasmissione/IdTrasmittente/IdCodice = '05779721009'">
                                                <fo:external-graphic src="{$logoSogin}" content-height="scale-to-fit" height="1.00in" />
                                            </xsl:if>
                                            <xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/DatiTrasmissione/IdTrasmittente/IdCodice = '05081150582'">
                                                <fo:external-graphic src="{$logoNucleco}" content-height="scale-to-fit" height="1.00in" />
                                            </xsl:if>
                                        </fo:block>
										
									</fo:table-cell>
									<fo:table-cell xsl:use-attribute-sets="no-border">
										
										<!--INIZIO SEGNATURA DI PROTOCOLLO-->
                                        <xsl:if test="/*[local-name()='FatturaElettronica']/DatiSegnatura/NumeroProtocollo and /*[local-name()='FatturaElettronica']/DatiSegnatura/NumeroProtocollo != ''">
                                            <fo:table xsl:use-attribute-sets="table-protocollo">
                                                <fo:table-column column-width="proportional-column-width(34)" />
                                                <fo:table-column column-width="proportional-column-width(33)" />
                                                <fo:table-column column-width="proportional-column-width(33)" />
                                                
                                                <fo:table-body>
                                                    <fo:table-row keep-together.within-page="always">
                                                        <fo:table-cell xsl:use-attribute-sets="cell-padding border border-protocollo">
                                                            <fo:block xsl:use-attribute-sets="table-cell-label-small">
                                                                Numero Protocollo:
                                                            </fo:block>
                                                            <fo:block>
                                                                <xsl:text>N. </xsl:text>
                                                                <xsl:call-template name="FormatNumProt">
                                                                    <xsl:with-param name="ExtendeNumProt" select="/*[local-name()='FatturaElettronica']/DatiSegnatura/NumeroProtocollo" />
                                                                </xsl:call-template>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell xsl:use-attribute-sets="cell-padding border border-protocollo">
                                                            <fo:block xsl:use-attribute-sets="table-cell-label-small">
                                                                Data Protocollo:
                                                            </fo:block>
                                                            <fo:block>
                                                                <xsl:if test="/*[local-name()='FatturaElettronica']/DatiSegnatura/DataProtocollo">
                                                                    <xsl:choose>
                                                                        <xsl:when test="string-length(/*[local-name()='FatturaElettronica']/DatiSegnatura/DataProtocollo) = 8">
                                                                            <xsl:call-template name="FormatDateYYYYMMDD">
                                                                                <xsl:with-param name="DateTime" select="/*[local-name()='FatturaElettronica']/DatiSegnatura/DataProtocollo" />
                                                                            </xsl:call-template>
                                                                        </xsl:when>
                                                                        <xsl:otherwise>
                                                                            <xsl:value-of select="/*[local-name()='FatturaElettronica']/DatiSegnatura/DataProtocollo" />
                                                                        </xsl:otherwise>
                                                                    </xsl:choose>
                                                                </xsl:if>
                                                            </fo:block>
                                                        </fo:table-cell>
                                                        <fo:table-cell xsl:use-attribute-sets="cell-padding border border-protocollo">
                                                            <fo:block xsl:use-attribute-sets="table-cell-label-small">
                                                                Tipo Documento:
                                                            </fo:block>
                                                            <fo:block text-transform="uppercase">
                                                                <xsl:value-of select="/*[local-name()='FatturaElettronica']/DatiSegnatura/TipoDocumento" />
                                                            </fo:block>
                                                        </fo:table-cell>
                                                    </fo:table-row>
                                                    <xsl:if test="/*[local-name()='FatturaElettronica']/DatiSegnatura/ClassificazioneDocumento">
                                                        <fo:table-row keep-together.within-page="always">
                                                            <fo:table-cell xsl:use-attribute-sets="cell-padding border border-protocollo" number-columns-spanned="3">
                                                                <fo:block xsl:use-attribute-sets="table-cell-label-small">
                                                                    Classificazione:
                                                                </fo:block>
                                                                <fo:block>
                                                                    <xsl:value-of select="/*[local-name()='FatturaElettronica']/DatiSegnatura/ClassificazioneDocumento" />
                                                                </fo:block>
                                                            </fo:table-cell>
                                                        </fo:table-row>
                                                    </xsl:if>
                                                </fo:table-body>
                                            </fo:table>
                                        </xsl:if>
                                        <!--FINE SEGNATURA DI PROTOCOLLO-->
										
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
                    
                    </xsl:if>
				    
					<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader">
						
						<!--INIZIO DATI DELLA TRASMISSIONE-->
						<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/DatiTrasmissione">
							<fo:block xsl:use-attribute-sets="h6">
								Dati relativi alla trasmissione
							</fo:block>
							
							<xsl:for-each select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/DatiTrasmissione">
								<fo:table xsl:use-attribute-sets="table-content border font-size-8">
									<fo:table-column column-width="proportional-column-width(100)" />
									<fo:table-body>
										<fo:table-row keep-together.within-page="always">
											<fo:table-cell xsl:use-attribute-sets="cell-padding">
												<xsl:if test="IdTrasmittente">
													<fo:block>
														Identificativo del trasmittente:
														<fo:inline font-weight="bold">
															<xsl:value-of select="IdTrasmittente/IdPaese" />
															<xsl:value-of select="IdTrasmittente/IdCodice" />
														</fo:inline>
													</fo:block>
												</xsl:if>
												<xsl:if test="ProgressivoInvio">
													<fo:block>
														Progressivo di invio:
														<fo:inline font-weight="bold">
															<xsl:value-of select="ProgressivoInvio" />
														</fo:inline>
													</fo:block>
												</xsl:if>
												<xsl:if test="FormatoTrasmissione">
													<fo:block>
														Formato Trasmissione:
														<fo:inline font-weight="bold">
															<xsl:value-of select="FormatoTrasmissione" />
														</fo:inline>
													</fo:block>
												</xsl:if>
												<xsl:if test="CodiceDestinatario">
													<fo:block>
														Codice Amministrazione destinataria:
														<fo:inline font-weight="bold">
															<xsl:value-of select="CodiceDestinatario" />
														</fo:inline>
													</fo:block>
												</xsl:if>
												<xsl:if test="ContattiTrasmittente/Telefono">
													<fo:block>
														Telefono del trasmittente:
														<fo:inline font-weight="bold">
															<xsl:value-of select="ContattiTrasmittente/Telefono" />
														</fo:inline>
													</fo:block>
												</xsl:if>
												<xsl:if test="ContattiTrasmittente/Email">
													<fo:block>
														E-mail del trasmittente:
														<fo:inline font-weight="bold">
															<xsl:value-of select="ContattiTrasmittente/Email" />
														</fo:inline>
													</fo:block>
												</xsl:if>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>	
							</xsl:for-each>
						</xsl:if>
						<!--FINE DATI DELLA TRASMISSIONE-->
						
						<!--INIZIO DATI CEDENTE PRESTATORE-->
						<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore">
							<fo:block xsl:use-attribute-sets="cedente-prestatore">
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/Anagrafica/Denominazione">
									<fo:block font-weight="bold">
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/Anagrafica/Denominazione" />
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/Anagrafica/Cognome">
									<fo:block font-weight="bold">
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/Anagrafica/Cognome" />
										<xsl:text> </xsl:text>
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/Anagrafica/Nome" />
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/IdFiscaleIVA">
									<fo:block xsl:use-attribute-sets="cedente-prestatore-text">
                                        <xsl:if test="not(/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/CodiceFiscale)">C.F. /</xsl:if>
										P.IVA: 
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/IdFiscaleIVA/IdPaese" />
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/IdFiscaleIVA/IdCodice" />
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/CodiceFiscale">
									<fo:block xsl:use-attribute-sets="cedente-prestatore-text">
										C.F.: <xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/DatiAnagrafici/CodiceFiscale" />
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/Indirizzo">
									<fo:block xsl:use-attribute-sets="cedente-prestatore-text">
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/Indirizzo" />
										<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/NumeroCivico">
											<xsl:text> </xsl:text>
											<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/NumeroCivico" />
										</xsl:if>
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/Comune">
									<fo:block xsl:use-attribute-sets="cedente-prestatore-text">
										<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/CAP">
											<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/CAP" />
											<xsl:text> </xsl:text>
										</xsl:if>
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/Comune" />
										<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/Provincia">
											<xsl:text> (</xsl:text>
											<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/Provincia" />
											<xsl:text>)</xsl:text>
										</xsl:if>
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/Nazione">
									<fo:block xsl:use-attribute-sets="cedente-prestatore-text">
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CedentePrestatore/Sede/Nazione" />
									</fo:block>
								</xsl:if>
							</fo:block>
						</xsl:if>
						<!--FINE DATI CEDENTE PRESTATORE-->
						
						<!--INIZIO DATI CESSIONARIO COMMITTENTE-->
						<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente">
							<fo:block xsl:use-attribute-sets="cessionario-committente">
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/Anagrafica/Denominazione">
									<fo:block font-weight="bold">
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/Anagrafica/Denominazione" />
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/Anagrafica/Cognome">
									<fo:block font-weight="bold">
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/Anagrafica/Cognome" />
										<xsl:text> </xsl:text>
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/Anagrafica/Nome" />
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA">
									<fo:block xsl:use-attribute-sets="cessionario-committente-text">
										<xsl:if test="not(/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/CodiceFiscale)">C.F. /</xsl:if>
										P.IVA: 
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA/IdPaese" />
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/IdFiscaleIVA/IdCodice" />
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/CodiceFiscale">
									<fo:block xsl:use-attribute-sets="cessionario-committente-text">
										C.F.: <xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/DatiAnagrafici/CodiceFiscale" />
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/Indirizzo">
									<fo:block xsl:use-attribute-sets="cessionario-committente-text">
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/Indirizzo" />
										<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/NumeroCivico">
											<xsl:text> </xsl:text>
											<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/NumeroCivico" />
										</xsl:if>
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/Comune">
									<fo:block xsl:use-attribute-sets="cessionario-committente-text">
										<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/CAP">
											<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/CAP" />
											<xsl:text> </xsl:text>
										</xsl:if>
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/Comune" />
										<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/Provincia">
											<xsl:text> (</xsl:text>
											<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/Provincia" />
											<xsl:text>)</xsl:text>
										</xsl:if>
									</fo:block>
								</xsl:if>
								<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/Nazione">
									<fo:block xsl:use-attribute-sets="cessionario-committente-text">
										<xsl:value-of select="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/CessionarioCommittente/Sede/Nazione" />
									</fo:block>
								</xsl:if>
							</fo:block>
						</xsl:if>
						<!--FINE DATI CESSIONARIO COMMITTENTE-->
						
						<xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaBody">
							<fo:block xsl:use-attribute-sets="padding-top-20">
								<xsl:if test="count(/*[local-name()='FatturaElettronica']/FatturaElettronicaBody) > 1">
									<fo:block xsl:use-attribute-sets="h5">Lotto di Fatture</fo:block>
								</xsl:if>
								<xsl:for-each select="/*[local-name()='FatturaElettronica']/FatturaElettronicaBody">
									<xsl:if test="count(/*[local-name()='FatturaElettronica']/FatturaElettronicaBody) > 1">
										<fo:block xsl:use-attribute-sets="h6 padding-top-10">Documento num. <xsl:value-of select="position()" /> del Lotto</fo:block>
									</xsl:if>
									
									<!--INIZIO DATI DELL'ORDINE DI ACQUISTO-->
									<xsl:if test="DatiGenerali/DatiOrdineAcquisto">
										<fo:block xsl:use-attribute-sets="section-title">Ordine di acquisto</fo:block>
										
										<fo:table xsl:use-attribute-sets="table-content font-size-8">
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(16)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-header>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea in fattura</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Identificativo ordine</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Data ordine</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea ordine</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Codice commessa/convenzione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CUP</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CIG</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-header>
											<fo:table-body>
												<xsl:for-each select="DatiGenerali/DatiOrdineAcquisto">
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="RiferimentoNumeroLinea">
																	<xsl:for-each select="RiferimentoNumeroLinea">
																		<xsl:if test="(position( )) > 1"><xsl:text>, </xsl:text></xsl:if>
																		<xsl:value-of select="." />
																	</xsl:for-each>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="IdDocumento">
																	<xsl:value-of select="IdDocumento" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="Data">
																	<xsl:call-template name="FormatDate">
																		<xsl:with-param name="DateTime" select="Data" />
																	</xsl:call-template>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="NumItem">
																	<xsl:value-of select="NumItem" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCommessaConvenzione">
																	<xsl:value-of select="CodiceCommessaConvenzione" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCUP">
																	<xsl:value-of select="CodiceCUP" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCIG">
																	<xsl:value-of select="CodiceCIG" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI DELL'ORDINE DI ACQUISTO-->
									
									<!--INIZIO DATI DEL CONTRATTO-->
									<xsl:if test="DatiGenerali/DatiContratto">
										<fo:block xsl:use-attribute-sets="section-title">Contratto</fo:block>
										
										<fo:table xsl:use-attribute-sets="table-content font-size-8">
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(16)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-header>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea in fattura</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Identificativo contratto</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Data contratto</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea contratto</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Codice commessa/convenzione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CUP</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CIG</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-header>
											<fo:table-body>
												<xsl:for-each select="DatiGenerali/DatiContratto">
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="RiferimentoNumeroLinea">
																	<xsl:for-each select="RiferimentoNumeroLinea">
																		<xsl:if test="(position( )) > 1"><xsl:text>, </xsl:text></xsl:if>
																		<xsl:value-of select="." />
																	</xsl:for-each>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="IdDocumento">
																	<xsl:value-of select="IdDocumento" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="Data">
																	<xsl:call-template name="FormatDate">
																		<xsl:with-param name="DateTime" select="Data" />
																	</xsl:call-template>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="NumItem">
																	<xsl:value-of select="NumItem" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCommessaConvenzione">
																	<xsl:value-of select="CodiceCommessaConvenzione" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCUP">
																	<xsl:value-of select="CodiceCUP" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCIG">
																	<xsl:value-of select="CodiceCIG" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI DEL CONTRATTO-->
									
									<!--INIZIO DATI CONVENZIONE-->
									<xsl:if test="DatiGenerali/DatiConvenzione">
										<fo:block xsl:use-attribute-sets="section-title">Convenzione</fo:block>
										
										<fo:table xsl:use-attribute-sets="table-content font-size-8">
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(16)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-header>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea in fattura</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Identificativo convenzione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Data convenzione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea convenzione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Codice commessa/convenzione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CUP</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CIG</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-header>
											<fo:table-body>
												<xsl:for-each select="DatiGenerali/DatiConvenzione">
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="RiferimentoNumeroLinea">
																	<xsl:for-each select="RiferimentoNumeroLinea">
																		<xsl:if test="(position( )) > 1"><xsl:text>, </xsl:text></xsl:if>
																		<xsl:value-of select="." />
																	</xsl:for-each>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="IdDocumento">
																	<xsl:value-of select="IdDocumento" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="Data">
																	<xsl:call-template name="FormatDate">
																		<xsl:with-param name="DateTime" select="Data" />
																	</xsl:call-template>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="NumItem">
																	<xsl:value-of select="NumItem" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCommessaConvenzione">
																	<xsl:value-of select="CodiceCommessaConvenzione" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCUP">
																	<xsl:value-of select="CodiceCUP" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCIG">
																	<xsl:value-of select="CodiceCIG" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI CONVENZIONE-->
									
									<!--INIZIO DATI RICEZIONE-->
									<xsl:if test="DatiGenerali/DatiRicezione">
										<fo:block xsl:use-attribute-sets="section-title">Ricezione</fo:block>
										
										<fo:table xsl:use-attribute-sets="table-content font-size-8">
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(16)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-header>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea in fattura</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Identificativo ricezione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Data ricezione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea ricezione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Codice commessa/convenzione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CUP</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CIG</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-header>
											<fo:table-body>
												<xsl:for-each select="DatiGenerali/DatiRicezione">
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="RiferimentoNumeroLinea">
																	<xsl:for-each select="RiferimentoNumeroLinea">
																		<xsl:if test="(position( )) > 1"><xsl:text>, </xsl:text></xsl:if>
																		<xsl:value-of select="." />
																	</xsl:for-each>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="IdDocumento">
																	<xsl:value-of select="IdDocumento" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="Data">
																	<xsl:call-template name="FormatDate">
																		<xsl:with-param name="DateTime" select="Data" />
																	</xsl:call-template>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="NumItem">
																	<xsl:value-of select="NumItem" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCommessaConvenzione">
																	<xsl:value-of select="CodiceCommessaConvenzione" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCUP">
																	<xsl:value-of select="CodiceCUP" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCIG">
																	<xsl:value-of select="CodiceCIG" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI RICEZIONE-->
									
									<!--INIZIO DATI FATTURE COLLEGATE-->
									<xsl:if test="DatiGenerali/DatiFattureCollegate">
										<fo:block xsl:use-attribute-sets="section-title">Fatture collegate</fo:block>
										
										<fo:table xsl:use-attribute-sets="table-content font-size-8">
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(16)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-column column-width="proportional-column-width(14)" />
											<fo:table-header>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea in fattura</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Identificativo fattura collegata</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Data fattura collegata</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Numero linea fattura collegata</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Codice commessa/convenzione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CUP</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>CIG</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-header>
											<fo:table-body>
												<xsl:for-each select="DatiGenerali/DatiFattureCollegate">
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="RiferimentoNumeroLinea">
																	<xsl:for-each select="RiferimentoNumeroLinea">
																		<xsl:if test="(position( )) > 1"><xsl:text>, </xsl:text></xsl:if>
																		<xsl:value-of select="." />
																	</xsl:for-each>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="IdDocumento">
																	<xsl:value-of select="IdDocumento" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="Data">
																	<xsl:call-template name="FormatDate">
																		<xsl:with-param name="DateTime" select="Data" />
																	</xsl:call-template>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="NumItem">
																	<xsl:value-of select="NumItem" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCommessaConvenzione">
																	<xsl:value-of select="CodiceCommessaConvenzione" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCUP">
																	<xsl:value-of select="CodiceCUP" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="CodiceCIG">
																	<xsl:value-of select="CodiceCIG" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI FATTURE COLLEGATE-->
									
									<!--INIZIO DATI RIFERIMENTO SAL-->
									<xsl:if test="DatiGenerali/DatiSAL">
										<fo:table xsl:use-attribute-sets="table-content border-strong font-size-10">
											<fo:table-column column-width="proportional-column-width(100)" />
											<fo:table-body>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-padding border">
														<fo:block xsl:use-attribute-sets="table-cell-label-small">
															Stato Avanzamento Lavori:
														</fo:block>
														<fo:block>
															<xsl:if test="DatiGenerali/DatiSAL/RiferimentoFase">
																<xsl:for-each select="DatiGenerali/DatiSAL/RiferimentoFase">
																	<xsl:if test="(position( )) > 1"><xsl:text>, </xsl:text></xsl:if>
																	<xsl:value-of select="." />
																</xsl:for-each>
															</xsl:if>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI RIFERIMENTO SAL-->
									
									<!--INIZIO DATI GENERALI DOCUMENTO-->									
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento">
										<fo:table xsl:use-attribute-sets="padding-top-20 table-content font-size-10">
											<fo:table-column column-width="proportional-column-width(30)" />
											<fo:table-column column-width="proportional-column-width(20)" />
											<fo:table-column column-width="proportional-column-width(25)" />
											<fo:table-column column-width="proportional-column-width(25)" />
											<fo:table-body>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-padding border">
														<fo:block xsl:use-attribute-sets="table-cell-label-small">
															Tipologia documento:
														</fo:block>
														<xsl:if test="DatiGenerali/DatiGeneraliDocumento/TipoDocumento">
															<fo:block font-weight="bold">
																<xsl:variable name="TD">
																	<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/TipoDocumento" />
																</xsl:variable>
																<xsl:choose>
																	<xsl:when test="$TD='TD01'">
																		Fattura
																	</xsl:when>
																	<xsl:when test="$TD='TD02'">
																		Acconto/Anticipo su fattura
																	</xsl:when>
																	<xsl:when test="$TD='TD03'">
																		Acconto/Anticipo su parcella
																	</xsl:when>
																	<xsl:when test="$TD='TD04'">
																		Nota di credito
																	</xsl:when>
																	<xsl:when test="$TD='TD05'">
																		Nota di debito
																	</xsl:when>
																	<xsl:when test="$TD='TD06'">
																		Parcella
																	</xsl:when>
																	<xsl:when test="$TD='TD16'">
																		Integrazione fattura reverse charge interno
																	</xsl:when>
																	<xsl:when test="$TD='TD17'">
																		Integrazione/autofattura per acquisto servizi da estero
																	</xsl:when>
																	<xsl:when test="$TD='TD18'">
																		Integrazione per acquisto beni intracomunitari
																	</xsl:when>
																	<xsl:when test="$TD='TD19'">
																		Integrazione/autofattura per acquisto beni ex art.17 c.2 DPR 633/72
																	</xsl:when>
																	<xsl:when test="$TD='TD20'">
																		Autofattura per regolarizzazione e 
																		integrazione delle fatture - 
																		art.6 c.8 d.lgs.471/97 o art.46 c.5 D.L.331/93
																	</xsl:when>
																	<xsl:when test="$TD='TD21'">
																		Autofattura per splafonamento
																	</xsl:when>
																	<xsl:when test="$TD='TD22'">
																		Estrazione beni da Deposito IVA
																	</xsl:when>
																	<xsl:when test="$TD='TD23'">
																		Estrazione beni da Deposito IVA con versamento IVA
																	</xsl:when>
																	<xsl:when test="$TD='TD24'">
																		Fattura differita - art.21 c.4 lett. a
																	</xsl:when>
																	<xsl:when test="$TD='TD25'">
																		Fattura differita - art.21 c.4 terzo periodo lett. b
																	</xsl:when>
																	<xsl:when test="$TD='TD26'">
																		Cessione di beni ammortizzabili e per passaggi interni - art.36 DPR 633/72
																	</xsl:when>
																	<xsl:when test="$TD='TD27'">
																		Fattura per autoconsumo o per cessioni gratuite senza rivalsa
																	</xsl:when>
																</xsl:choose>
															</fo:block>
														</xsl:if>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-padding border">
														<fo:block xsl:use-attribute-sets="table-cell-label-small">
															Valuta:
														</fo:block>
														<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Divisa">
															<fo:block>
																<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Divisa" />
															</fo:block>
														</xsl:if>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-padding border">
														<fo:block xsl:use-attribute-sets="table-cell-label-small">
															Data documento:
														</fo:block>
														<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Data">
															<fo:block font-weight="bold">
																<xsl:call-template name="FormatDate">
																	<xsl:with-param name="DateTime" select="DatiGenerali/DatiGeneraliDocumento/Data" />
																</xsl:call-template>
															</fo:block>
														</xsl:if>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-padding border">
														<fo:block xsl:use-attribute-sets="table-cell-label-small">
															Numero documento:
														</fo:block>
														<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Numero">
															<fo:block font-weight="bold">
																<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Numero" />
															</fo:block>
														</xsl:if>
													</fo:table-cell>
												</fo:table-row>
												<xsl:if test="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento or DatiGenerali/DatiGeneraliDocumento/Arrotondamento">
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Importo totale documento:
															</fo:block>
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento">
																<fo:block font-weight="bold">
																	<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/ImportoTotaleDocumento" />
																</fo:block>
															</xsl:if>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Arrotondamento su Importo totale:
															</fo:block>
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Arrotondamento">
																<fo:block>
																	<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Arrotondamento" />
																</fo:block>
															</xsl:if>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Art. 73 DPR 633/72:
															</fo:block>
															<xsl:if test="DatiGenerali/DatiGeneraliDocumento/Art73">
																<fo:block>
																	<xsl:value-of select="DatiGenerali/DatiGeneraliDocumento/Art73" />
																</fo:block>
															</xsl:if>
														</fo:table-cell>
													</fo:table-row>
												</xsl:if>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="4">
														<fo:block xsl:use-attribute-sets="table-cell-label-small">
															Causale:
														</fo:block>
														<fo:block>
															<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/Causale">
																<xsl:value-of select="current()" />
															</xsl:for-each>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI GENERALI DOCUMENTO-->
									
									<!--INIZIO DATI DELLA RITENUTA-->
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiRitenuta">
										<fo:table xsl:use-attribute-sets="padding-top-20 table-content font-size-10">
											<fo:table-column column-width="proportional-column-width(34)" />
											<fo:table-column column-width="proportional-column-width(33)" />
											<fo:table-column column-width="proportional-column-width(33)" />
											<fo:table-body>
												<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiRitenuta">
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Ritenuta:
															</fo:block>
															<fo:block>
																<xsl:if test="TipoRitenuta">
																	<xsl:variable name="TR">
																		<xsl:value-of select="TipoRitenuta" />
																	</xsl:variable>
																	<xsl:choose>
																		<xsl:when test="$TR='RT01'">
																			Ritenuta persone fisiche
																		</xsl:when>
																		<xsl:when test="$TR='RT02'">
																			Ritenuta persone giuridiche
																		</xsl:when>
																		<xsl:when test="$TR='RT03'">
																			Contributo INPS
																		</xsl:when>
																		<xsl:when test="$TR='RT04'">
																			Contributo ENASARCO
																		</xsl:when>
																		<xsl:when test="$TR='RT05'">
																			Contributo ENPAM
																		</xsl:when>
																		<xsl:when test="$TR='RT06'">
																			Altro contributo previdenziale
																		</xsl:when>
																	</xsl:choose>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Importo ritenuta:
															</fo:block>
															<fo:block>
																<xsl:if test="ImportoRitenuta">
																	<xsl:value-of select="ImportoRitenuta" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Aliquota ritenuta (%):
															</fo:block>
															<fo:block>
																<xsl:if test="AliquotaRitenuta">
																	<xsl:value-of select="AliquotaRitenuta" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
													<xsl:if test="CausalePagamento">
														<fo:table-row keep-together.within-page="always">
															<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="3">
																<fo:block xsl:use-attribute-sets="table-cell-label-small">
																	Causale di pagamento:
																</fo:block>
																<fo:block>
																	<xsl:value-of select="CausalePagamento" />
																	<xsl:if test="CausalePagamento != ''"> (decodifica come da modello CU)</xsl:if>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</xsl:if>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI DELLA RITENUTA-->
									
									<!--INIZIO DATI DEL BOLLO-->
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiBollo">
										<fo:table xsl:use-attribute-sets="padding-top-20 table-content font-size-10">
											<fo:table-column column-width="proportional-column-width(50)" />
											<fo:table-column column-width="proportional-column-width(50)" />
											<fo:table-body>
												<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiBollo">
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Bollo virtuale:
															</fo:block>
															<fo:block>
																<xsl:value-of select="BolloVirtuale" />
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Importo bollo:
															</fo:block>
															<fo:block>
																<xsl:value-of select="ImportoBollo" />
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI DEL BOLLO-->
									
									<!--INIZIO DATI DELLA CASSA PREVIDENZIALE-->
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento/DatiCassaPrevidenziale">
										<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/DatiCassaPrevidenziale">
											<fo:table xsl:use-attribute-sets="table-content border-strong font-size-10">
												<xsl:if test="position() > 0">
													<xsl:attribute name="space-after">0pt</xsl:attribute>
												</xsl:if>
												<xsl:if test="position() != count(DatiGenerali/DatiGeneraliDocumento/DatiCassaPrevidenziale)">
													<xsl:attribute name="space-before">0pt</xsl:attribute>
												</xsl:if>
												<fo:table-column column-width="proportional-column-width(25)" />
												<fo:table-column column-width="proportional-column-width(25)" />
												<fo:table-column column-width="proportional-column-width(25)" />
												<fo:table-column column-width="proportional-column-width(25)" />
												<fo:table-body>
													<fo:table-row keep-together.within-page="always" number-columns-spanned="4">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Cassa previdenziale:
															</fo:block>
															<fo:block>
																<xsl:if test="TipoCassa">
																	<xsl:choose>
																		<xsl:when test="TipoCassa='TC01'">
																			Cassa Nazionale Previdenza e Assistenza Avvocati e Procuratori legali
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC02'">
																			Cassa Previdenza Dottori Commercialisti
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC03'">
																			Cassa Previdenza e Assistenza Geometri
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC04'">
																			Cassa Nazionale Previdenza e Assistenza Ingegneri e Architetti liberi profess.
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC05'">
																			Cassa Nazionale del Notariato
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC06'">
																			Cassa Nazionale Previdenza e Assistenza Ragionieri e Periti commerciali
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC07'">
																			Ente Nazionale Assistenza Agenti e Rappresentanti di Commercio-ENASARCO
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC08'">
																			Ente Nazionale Previdenza e Assistenza Consulenti del Lavoro-ENPACL
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC09'">
																			Ente Nazionale Previdenza e Assistenza Medici-ENPAM
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC10'">
																			Ente Nazionale Previdenza e Assistenza Farmacisti-ENPAF
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC11'">
																			Ente Nazionale Previdenza e Assistenza Veterinari-ENPAV
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC12'">
																			Ente Nazionale Previdenza e Assistenza Impiegati dell'Agricoltura-ENPAIA
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC13'">
																			Fondo Previdenza Impiegati Imprese di Spedizione e Agenzie Marittime
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC14'">
																			Istituto Nazionale Previdenza Giornalisti Italiani-INPGI
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC15'">
																			Opera Nazionale Assistenza Orfani Sanitari Italiani-ONAOSI
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC16'">
																			Cassa Autonoma Assistenza Integrativa Giornalisti Italiani-CASAGIT
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC17'">
																			Ente Previdenza Periti Industriali e Periti Industriali Laureati-EPPI
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC18'">
																			Ente Previdenza e Assistenza Pluricategoriale-EPAP
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC19'">
																			Ente Nazionale Previdenza e Assistenza Biologi-ENPAB
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC20'">
																			Ente Nazionale Previdenza e Assistenza Professione Infermieristica-ENPAPI
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC21'">
																			Ente Nazionale Previdenza e Assistenza Psicologi-ENPAP
																		</xsl:when>
																		<xsl:when test="TipoCassa='TC22'">
																			INPS
																		</xsl:when>
																	</xsl:choose>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Aliquota contributo cassa (%):
															</fo:block>
															<fo:block>
																<xsl:if test="AlCassa">
																	<xsl:value-of select="AlCassa" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Importo contributo cassa:
															</fo:block>
															<fo:block>
																<xsl:if test="ImportoContributoCassa">
																	<xsl:value-of select="ImportoContributoCassa" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Imponibile previdenziale:
															</fo:block>
															<fo:block>
																<xsl:if test="ImponibileCassa">
																	<xsl:value-of select="ImponibileCassa" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Aliquota IVA applicata:
															</fo:block>
															<fo:block>
																<xsl:if test="AliquotaIVA">
																	<xsl:value-of select="AliquotaIVA" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Contributo cassa soggetto a ritenuta:
															</fo:block>
															<fo:block>
																<xsl:if test="Ritenuta">
																	<xsl:value-of select="Ritenuta" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Tipologia di non imponibilità del contributo:
															</fo:block>
															<fo:block>
																<xsl:if test="Natura">
																	<xsl:choose>
																		<xsl:when test="Natura='N1'">
																			Escluse ex art. 15
																		</xsl:when>
																		<xsl:when test="Natura='N2'">
																			Non soggette
																		</xsl:when>
																		<xsl:when test="Natura='N2.1'">
																			Non soggette ad IVA - artt. da 7 a 7-septies del DPR 633/72
																		</xsl:when>
																		<xsl:when test="Natura='N2.2'">
																			Non soggette - altri casi
																		</xsl:when>
																		<xsl:when test="Natura='N3'">
																			Non imponibili
																		</xsl:when>
																		<xsl:when test="Natura='N3.1'">
																			Non imponibili - esportazioni
																		</xsl:when>
																		<xsl:when test="Natura='N3.2'">
																			Non imponibili - cessioni intracomunitarie
																		</xsl:when>
																		<xsl:when test="Natura='N3.3'">
																			Non imponibili - cessioni verso S.Marino
																		</xsl:when>
																		<xsl:when test="Natura='N3.4'">
																			Non imponibili - operazioni assimilate alle 
																			cessioni all'esportazione
																		</xsl:when>
																		<xsl:when test="Natura='N3.5'">
																			Non imponibili - a seguito di dichiarazioni 
																			d'intento
																		</xsl:when>
																		<xsl:when test="Natura='N3.6'">
																			Non imponibili - altre operazioni che non 
																			concorrono alla formazione del plafond
																		</xsl:when>
																		<xsl:when test="Natura='N4'">
																			Esenti
																		</xsl:when>
																		<xsl:when test="Natura='N5'">
																			Regime del margine / IVA non esposta in fattura
																		</xsl:when>
																		<xsl:when test="Natura='N6'">
																			Inversione contabile per le operazioni in reverse 
																			charge ovvero nei casi di autofatturazione per 
																			acquisti extra UE di servizi ovvero per importazioni 
																			di beni nei soli casi previsti
																		</xsl:when>
																		<xsl:when test="Natura='N6.1'">
																			Inversione contabile - cessione di rottami e 
																			altri materiali di recupero
																		</xsl:when>
																		<xsl:when test="Natura='N6.2'">
																			Inversione contabile - cessione di oro e 
																			argento puro
																		</xsl:when>
																		<xsl:when test="Natura='N6.3'">
																			Inversione contabile - subappalto nel settore 
																			edile
																		</xsl:when>
																		<xsl:when test="Natura='N6.4'">
																			Inversione contabile - cessione di fabbricati
																		</xsl:when>
																		<xsl:when test="Natura='N6.5'">
																			Inversione contabile - cessione di telefoni 
																			cellulari
																		</xsl:when>
																		<xsl:when test="Natura='N6.6'">
																			Inversione contabile - cessione di prodotti 
																			elettronici
																		</xsl:when>
																		<xsl:when test="Natura='N6.7'">
																			Inversione contabile - prestazioni comparto 
																			edile e settori connessi
																		</xsl:when>
																		<xsl:when test="Natura='N6.8'">
																			Inversione contabile - operazioni settore 
																			energetico
																		</xsl:when>
																		<xsl:when test="Natura='N6.9'">
																			Inversione contabile - altri casi
																		</xsl:when>
																		<xsl:when test="Natura='N7'">
																			IVA assolta in altro stato UE - vendite a distanza 
																			ex art.40 c.3 e 4 e art.41 c.1 lett. b DL 331/93; 
																			Prestazione di servizi di telecomunicazioni, 
																			tele-radiodiffusione ed elettronici ex art.7-sexies 
																			lett. f, g, e art.74-sexies DPR 633/72
																		</xsl:when>
																	</xsl:choose>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Riferimento amministrativo / contabile:
															</fo:block>
															<fo:block>
																<xsl:if test="RiferimentoAmministrazione">
																	<xsl:value-of select="RiferimentoAmministrazione" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</fo:table-body>
											</fo:table>
										</xsl:for-each>
									</xsl:if>
									<!--FINE DATI DELLA CASSA PREVIDENZIALE-->
									
									<!--INIZIO DATI SCONTO / MAGGIORAZIONE-->
									<xsl:if test="DatiGenerali/DatiGeneraliDocumento/ScontoMaggiorazione">
										<fo:table xsl:use-attribute-sets="padding-top-20 table-content font-size-10">
											<fo:table-column column-width="proportional-column-width(40)" />
											<fo:table-column column-width="proportional-column-width(30)" />
											<fo:table-column column-width="proportional-column-width(30)" />
											<fo:table-body>
												<xsl:for-each select="DatiGenerali/DatiGeneraliDocumento/ScontoMaggiorazione">
													<xsl:if test="Tipo">
														<fo:table-row keep-together.within-page="always">
															<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																<fo:block xsl:use-attribute-sets="table-cell-label-small">
																</fo:block>
																<fo:block>
																	<xsl:choose>
																		<xsl:when test="Tipo = 'SC'">
																			Sconto
																		</xsl:when>
																		<xsl:when test="Tipo = 'MG'">
																			Maggiorazione
																		</xsl:when>
																		<xsl:otherwise>
																			<xsl:value-of select="Tipo" />
																		</xsl:otherwise>
																	</xsl:choose>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																<fo:block xsl:use-attribute-sets="table-cell-label-small">
																	Percentuale:
																</fo:block>
																<fo:block>
																	<xsl:if test="Percentuale">
																		<xsl:value-of select="Percentuale" />
																	</xsl:if>
																</fo:block>
															</fo:table-cell>
															<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																<fo:block xsl:use-attribute-sets="table-cell-label-small">
																	Importo:
																</fo:block>
																<fo:block>
																	<xsl:if test="Importo">
																		<xsl:value-of select="Importo" />
																	</xsl:if>
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</xsl:if>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI SCONTO / MAGGIORAZIONE-->
									
									<!--INIZIO FATTURA PRINCIPALE-->
									<xsl:if test="DatiGenerali/FatturaPrincipale/NumeroFatturaPrincipale">
										<fo:table xsl:use-attribute-sets="padding-top-20 table-content font-size-10">
											<fo:table-column column-width="proportional-column-width(50)" />
											<fo:table-column column-width="proportional-column-width(50)" />
											<fo:table-body>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-padding border">
														<fo:block xsl:use-attribute-sets="table-cell-label-small">
															Numero fattura principale:
														</fo:block>
														<fo:block>
															<xsl:if test="DatiGenerali/FatturaPrincipale/NumeroFatturaPrincipale">
																<xsl:value-of select="DatiGenerali/FatturaPrincipale/NumeroFatturaPrincipale" />
															</xsl:if>
														</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-padding border">
														<fo:block xsl:use-attribute-sets="table-cell-label-small">
															Data fattura principale:
														</fo:block>
														<fo:block>
															<xsl:if test="DatiGenerali/FatturaPrincipale/DataFatturaPrincipale">
																<xsl:call-template name="FormatDate">
																	<xsl:with-param name="DateTime" select="DatiGenerali/FatturaPrincipale/DataFatturaPrincipale" />
																</xsl:call-template>
															</xsl:if>
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE FATTURA PRINCIPALE-->
									
									<!--INIZIO DATI BENI E SERVIZI-->
									<xsl:if test="DatiBeniServizi/DettaglioLinee">
										<fo:table xsl:use-attribute-sets="padding-top-20 table-content font-size-8">
											<fo:table-column column-width="proportional-column-width(5)" /> <!-- nr. linea -->
											<fo:table-column column-width="proportional-column-width(35)" /> <!-- descrizione / codice articolo / periodo riferimento (inzio/fine) -->
											<fo:table-column column-width="proportional-column-width(10)" /> <!-- quantita' / unita' misura -->
											<fo:table-column column-width="proportional-column-width(15)" /> <!-- prezzo unitario -->
											<fo:table-column column-width="proportional-column-width(15)" /> <!-- sconto/maggiorazione -->
											<fo:table-column column-width="proportional-column-width(5)" /> <!-- aliquota iva -->
											<fo:table-column column-width="proportional-column-width(15)" /> <!-- prezzo totale -->
											
											<fo:table-header>
												<fo:table-row keep-together.within-page="always">
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Nr. Linea</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Descrizione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block text-align="right">Qtà</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block text-align="right">P. Unitario</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block>Sconto / Maggiorazione</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block text-align="right">IVA</fo:block>
													</fo:table-cell>
													<fo:table-cell xsl:use-attribute-sets="cell-header cell-padding border">
														<fo:block text-align="right">P. Totale</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-header>
											<fo:table-body>
												<xsl:for-each select="DatiBeniServizi/DettaglioLinee">
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:value-of select="NumeroLinea" />
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="TipoCessionePrestazione and TipoCessionePrestazione != ''">
																	<fo:block font-size="7pt" space-after="5pt">
																		Tipo cessione/prestazione:
																		
																		<fo:inline font-weight="bold">
																			<xsl:choose>
																				<xsl:when test="TipoCessionePrestazione='SC'">
																					Sconto
																				</xsl:when>
																				<xsl:when test="TipoCessionePrestazione='PR'">
																					Premio
																				</xsl:when>
																				<xsl:when test="TipoCessionePrestazione='AB'">
																					Abbuono
																				</xsl:when>
																				<xsl:when test="TipoCessionePrestazione='AC'">
																					Spesa accessoria
																				</xsl:when>
																				<xsl:otherwise>
																					<xsl:value-of select="TipoCessionePrestazione" />
																				</xsl:otherwise>
																			</xsl:choose>
																		</fo:inline>
																	</fo:block>
																</xsl:if>
																<xsl:if test="Descrizione">
																	<fo:block>
																		<xsl:value-of select="Descrizione" />
																	</fo:block>
																</xsl:if>
																<xsl:if test="DataInizioPeriodo or DataFinePeriodo">
																	<fo:block>
																		<xsl:if test="DataInizioPeriodo">
																			<xsl:call-template name="FormatDate">
																				<xsl:with-param name="DateTime" select="DataInizioPeriodo" />
																			</xsl:call-template>
																		</xsl:if>
																		<xsl:if test="DataInizioPeriodo and DataFinePeriodo">
																			<fo:inline> - </fo:inline>
																		</xsl:if>
																		<xsl:if test="DataFinePeriodo">
																			<xsl:call-template name="FormatDate">
																				<xsl:with-param name="DateTime" select="DataFinePeriodo" />
																			</xsl:call-template>
																		</xsl:if>
																	</fo:block>
																</xsl:if>
																<xsl:if test="CodiceArticolo">
																	<fo:block font-size="7pt" space-before="5pt">
																		<xsl:for-each select="CodiceArticolo">
																			<fo:block>
																				<xsl:value-of select="CodiceTipo" />: <xsl:value-of select="CodiceValore" />
																			</fo:block>
																		</xsl:for-each>
																	</fo:block>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block text-align="right">
																<xsl:if test="Quantita">
																	<xsl:value-of select="Quantita" />
																</xsl:if>
																<xsl:if test="Quantita and UnitaMisura">
																	<xsl:text> </xsl:text>
																</xsl:if>
																<xsl:if test="UnitaMisura">
																	<xsl:value-of select="UnitaMisura" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block text-align="right">
																<xsl:if test="PrezzoUnitario">
																	<xsl:value-of select="PrezzoUnitario" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block>
																<xsl:if test="ScontoMaggiorazione">
																	<xsl:for-each select="ScontoMaggiorazione">
																		<fo:block font-size="7pt">
																			<xsl:if test="Importo">
																				<xsl:if test="Tipo">
																					<xsl:choose>
																						<xsl:when test="Tipo='SC'">
																							Sconto:
																						</xsl:when>
																						<xsl:when test="Tipo='MG'">
																							Maggiorazione:
																						</xsl:when>
																					</xsl:choose>
																				</xsl:if>
																				<xsl:value-of select="Importo" />
																				<xsl:if test="Percentuale">
																					<xsl:text> (</xsl:text><xsl:value-of select="Percentuale" /><xsl:text> %)</xsl:text>
																				</xsl:if>
																			</xsl:if>
																		</fo:block>
																	</xsl:for-each>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block text-align="right">
																<xsl:if test="AliquotaIVA">
																	<xsl:value-of select="AliquotaIVA" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block text-align="right">
																<xsl:if test="PrezzoTotale">
																	<xsl:value-of select="PrezzoTotale" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</xsl:if>
									<!--FINE DATI BENI E SERVIZI-->
									
									<!--INIZIO DATI DI RIEPILOGO ALIQUOTE E NATURE-->
									<xsl:if test="DatiBeniServizi/DatiRiepilogo">
										<fo:block xsl:use-attribute-sets="section-title padding-top-20">Riepilogo</fo:block>
										
										<xsl:for-each select="DatiBeniServizi/DatiRiepilogo">
											<fo:table xsl:use-attribute-sets="table-content border-strong font-size-10">
												<xsl:if test="position() > 0">
													<xsl:attribute name="space-after">0pt</xsl:attribute>
												</xsl:if>
												<xsl:if test="position() != count(DatiBeniServizi/DatiRiepilogo)">
													<xsl:attribute name="space-before">0pt</xsl:attribute>
												</xsl:if>
												<fo:table-column column-width="proportional-column-width(25)" />
												<fo:table-column column-width="proportional-column-width(25)" />
												<fo:table-column column-width="proportional-column-width(25)" />
												<fo:table-column column-width="proportional-column-width(25)" />
												<fo:table-body>
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Aliquota IVA (%):
															</fo:block>
															<fo:block>
																<xsl:if test="AliquotaIVA">
																	<xsl:value-of select="AliquotaIVA" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Natura Operazioni:
															</fo:block>
															<fo:block>
																<xsl:if test="Natura">
																	<xsl:variable name="NAT1">
																		<xsl:value-of select="Natura" />
																	</xsl:variable>
																	<xsl:choose>
																		<xsl:when test="$NAT1='N1'">
																			Escluse ex art.15
																		</xsl:when>
																		<xsl:when test="$NAT1='N2'">
																			Non soggette
																		</xsl:when>
																		<xsl:when test="$NAT1='N2.1'">
																			Non soggette ad IVA - artt. da 7 a 7-septies del DPR 633/72
																		</xsl:when>
																		<xsl:when test="$NAT1='N2.2'">
																			Non soggette - altri casi
																		</xsl:when>
																		<xsl:when test="$NAT1='N3'">
																			Non imponibili
																		</xsl:when>
																		<xsl:when test="$NAT1='N3.1'">
																			Non imponibili - esportazioni
																		</xsl:when>
																		<xsl:when test="$NAT1='N3.2'">
																			Non imponibili - cessioni intracomunitarie
																		</xsl:when>
																		<xsl:when test="$NAT1='N3.3'">
																			Non imponibili - cessioni verso S.Marino
																		</xsl:when>
																		<xsl:when test="$NAT1='N3.4'">
																			Non imponibili - operazioni assimilate alle cessioni all'esportazione
																		</xsl:when>
																		<xsl:when test="$NAT1='N3.5'">
																			Non imponibili - a seguito di dichiarazioni d'intento
																		</xsl:when>
																		<xsl:when test="$NAT1='N3.6'">
																			Non imponibili - altre operazioni che non concorrono alla formazione del plafond
																		</xsl:when>
																		<xsl:when test="$NAT1='N4'">
																			Esenti
																		</xsl:when>
																		<xsl:when test="$NAT1='N5'">
																			Regime del margine / IVA non esposta in fattura
																		</xsl:when>
																		<xsl:when test="$NAT1='N6'">
																			Inversione contabile per le operazioni in reverse 
																			charge ovvero nei casi di autofatturazione per 
																			acquisti extra UE di servizi ovvero per importazioni 
																			di beni nei soli casi previsti
																		</xsl:when>
																		<xsl:when test="$NAT1='N6.1'">
																			Inversione contabile - cessione di rottami e altri materiali di recupero
																		</xsl:when>
																		<xsl:when test="$NAT1='N6.2'">
																			Inversione contabile - cessione di oro e argento puro
																		</xsl:when>
																		<xsl:when test="$NAT1='N6.3'">
																			Inversione contabile - subappalto nel settore edile
																		</xsl:when>
																		<xsl:when test="$NAT1='N6.4'">
																			Inversione contabile - cessione di fabbricati
																		</xsl:when>
																		<xsl:when test="$NAT1='N6.5'">
																			Inversione contabile - cessione di telefoni cellulari
																		</xsl:when>
																		<xsl:when test="$NAT1='N6.6'">
																			Inversione contabile - cessione di prodotti elettronici
																		</xsl:when>
																		<xsl:when test="$NAT1='N6.7'">
																			Inversione contabile - prestazioni comparto edile e settori connessi
																		</xsl:when>
																		<xsl:when test="$NAT1='N6.8'">
																			Inversione contabile - operazioni settore energetico
																		</xsl:when>
																		<xsl:when test="$NAT1='N6.9'">
																			Inversione contabile - altri casi
																		</xsl:when>
																		<xsl:when test="$NAT1='N7'">
																			IVA assolta in altro stato UE - vendite a distanza 
																			ex art.40 c.3 e 4 e art.41 c.1 lett. b DL 331/93; 
																			Prestazione di servizi di telecomunicazioni, 
																			tele-radiodiffusione ed elettronici ex art.7-sexies 
																			lett. f, g, e art.74-sexies DPR 633/72
																		</xsl:when>
																	</xsl:choose>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Spese accessorie:
															</fo:block>
															<fo:block>
																<xsl:if test="SpeseAccessorie">
																	<xsl:value-of select="SpeseAccessorie" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Arrotondamento:
															</fo:block>
															<fo:block>
																<xsl:if test="Arrotondamento">
																	<xsl:value-of select="Arrotondamento" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Totale imponibile/importo:
															</fo:block>
															<fo:block>
																<xsl:if test="ImponibileImporto">
																	<xsl:value-of select="ImponibileImporto" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Totale imposta:
															</fo:block>
															<fo:block>
																<xsl:if test="Imposta">
																	<xsl:value-of select="Imposta" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Esigibilità IVA:
															</fo:block>
															<fo:block>
																<xsl:if test="EsigibilitaIVA">
																	<xsl:variable name="EI">
																		<xsl:value-of select="EsigibilitaIVA" />
																	</xsl:variable>
																	<xsl:choose>
																		<xsl:when test="$EI='I'">
																			Esigibilità immediata
																		</xsl:when>
																		<xsl:when test="$EI='D'">
																			Esigibilità differita
																		</xsl:when>
																		<xsl:when test="$EI='S'">
																			Scissione dei pagamenti
																		</xsl:when>
																	</xsl:choose>
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Riferimento normativo:
															</fo:block>
															<fo:block>
																<xsl:if test="RiferimentoNormativo">
																	<xsl:value-of select="RiferimentoNormativo" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</fo:table-body>
											</fo:table>
										</xsl:for-each>
									</xsl:if>
									<!--FINE DATI DI RIEPILOGO ALIQUOTE E NATURE-->
									
									<!--INIZIO DATI PAGAMENTO-->
									<xsl:if test="DatiPagamento">
										<xsl:for-each select="DatiPagamento">
											<fo:block xsl:use-attribute-sets="section-title padding-top-20">
												Pagamento
												<xsl:choose>
													<xsl:when test="CondizioniPagamento='TP01'">
														(pagamento a rate)
													</xsl:when>
													<xsl:when test="CondizioniPagamento='TP02'">
														(pagamento completo)
													</xsl:when>
													<xsl:when test="CondizioniPagamento='TP03'">
														(anticipo)
													</xsl:when>
												</xsl:choose>
											</fo:block>
											
											<xsl:if test="DettaglioPagamento">
												<xsl:for-each select="DettaglioPagamento">					
													<fo:table xsl:use-attribute-sets="table-content border-strong font-size-10">
														<xsl:if test="position() > 0">
															<xsl:attribute name="space-after">0pt</xsl:attribute>
														</xsl:if>
														<xsl:if test="position() != count(DettaglioPagamento)">
															<xsl:attribute name="space-before">0pt</xsl:attribute>
														</xsl:if>
														<fo:table-column column-width="proportional-column-width(25)" />
														<fo:table-column column-width="proportional-column-width(25)" />
														<fo:table-column column-width="proportional-column-width(25)" />
														<fo:table-column column-width="proportional-column-width(25)" />
														
														<fo:table-body>
															<fo:table-row keep-together.within-page="always">
																<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																	<fo:block xsl:use-attribute-sets="table-cell-label-small">
																		Beneficiario del pagamento:
																	</fo:block>
																	<fo:block>
                                                                        <xsl:choose>
                                                                            <xsl:when test="Beneficiario">
                                                                                <xsl:value-of select="Beneficiario" />
                                                                            </xsl:when>
                                                                            <xsl:otherwise>
                                                                                <xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/DatiTrasmissione/IdTrasmittente/IdCodice = '05779721009'">SOGIN S.p.A.</xsl:if>
                                                                                <xsl:if test="/*[local-name()='FatturaElettronica']/FatturaElettronicaHeader/DatiTrasmissione/IdTrasmittente/IdCodice = '05081150582'">NUCLECO S.p.A.</xsl:if>
                                                                            </xsl:otherwise>
                                                                        </xsl:choose>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																	<fo:block xsl:use-attribute-sets="table-cell-label-small">
																		Modalità:
																	</fo:block>
																	<fo:block>
																		<xsl:variable name="MP">
																			<xsl:value-of select="ModalitaPagamento" />
																		</xsl:variable>
																		<xsl:choose>
																			<xsl:when test="$MP='MP01'">
																				Contanti
																			</xsl:when>
																			<xsl:when test="$MP='MP02'">
																				Assegno
																			</xsl:when>
																			<xsl:when test="$MP='MP03'">
																				Assegno circolare
																			</xsl:when>
																			<xsl:when test="$MP='MP04'">
																				Contanti presso Tesoreria
																			</xsl:when>
																			<xsl:when test="$MP='MP05'">
																				Bonifico
																			</xsl:when>
																			<xsl:when test="$MP='MP06'">
																				Vaglia cambiario
																			</xsl:when>
																			<xsl:when test="$MP='MP07'">
																				Bollettino bancario
																			</xsl:when>
																			<xsl:when test="$MP='MP08'">
																				Carta di pagamento
																			</xsl:when>
																			<xsl:when test="$MP='MP09'">
																				RID
																			</xsl:when>
																			<xsl:when test="$MP='MP10'">
																				RID utenze
																			</xsl:when>
																			<xsl:when test="$MP='MP11'">
																				RID veloce
																			</xsl:when>
																			<xsl:when test="$MP='MP12'">
																				RIBA
																			</xsl:when>
																			<xsl:when test="$MP='MP13'">
																				MAV
																			</xsl:when>
																			<xsl:when test="$MP='MP14'">
																				Quietanza erario
																			</xsl:when>
																			<xsl:when test="$MP='MP15'">
																				Giroconto su conti di contabilità speciale
																			</xsl:when>
																			<xsl:when test="$MP='MP16'">
																				Domiciliazione bancaria
																			</xsl:when>
																			<xsl:when test="$MP='MP17'">
																				Domiciliazione postale
																			</xsl:when>
																			<xsl:when test="$MP='MP18'">
																				Bollettino di c/c postale
																			</xsl:when>
																			<xsl:when test="$MP='MP19'">
																				SEPA Direct Debit
																			</xsl:when>
																			<xsl:when test="$MP='MP20'">
																				SEPA Direct Debit CORE
																			</xsl:when>
																			<xsl:when test="$MP='MP21'">
																				SEPA Direct Debit B2B
																			</xsl:when>
																			<xsl:when test="$MP='MP22'">
																				Trattenuta su somme già riscosse
																			</xsl:when>
																			<xsl:when test="$MP='MP23'">
																				PagoPA
																			</xsl:when>
																		</xsl:choose>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																	<fo:block xsl:use-attribute-sets="table-cell-label-small">
																		Codice pagamento:
																	</fo:block>
																	<fo:block>
																		<xsl:attribute name="font-size">
																			<xsl:choose>
																				<xsl:when test="CodicePagamento and string-length(CodicePagamento) > 25">
																					<xsl:text>8pt</xsl:text>
																				</xsl:when>
																				<xsl:otherwise>
																					<xsl:text>10pt</xsl:text>
																				</xsl:otherwise>
																			</xsl:choose>
																		</xsl:attribute>
																		<xsl:if test="CodicePagamento">
																			<xsl:value-of select="CodicePagamento" />
																		</xsl:if>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
															<fo:table-row keep-together.within-page="always">
																<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																	<fo:block xsl:use-attribute-sets="table-cell-label-small">
																		Decorrenza termini di pagamento:
																	</fo:block>
																	<fo:block>
																		<xsl:if test="DataRiferimentoTerminiPagamento">
																			<xsl:call-template name="FormatDate">
																				<xsl:with-param name="DateTime" select="DataRiferimentoTerminiPagamento" />
																			</xsl:call-template>
																		</xsl:if>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																	<fo:block xsl:use-attribute-sets="table-cell-label-small">
																		Termini di pagamento (in giorni):
																	</fo:block>
																	<fo:block>
																		<xsl:if test="GiorniTerminiPagamento">
																			<xsl:value-of select="GiorniTerminiPagamento" />
																		</xsl:if>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																	<fo:block xsl:use-attribute-sets="table-cell-label-small">
																		Data scadenza pagamento:
																	</fo:block>
																	<fo:block>
																		<xsl:if test="DataScadenzaPagamento">
																			<xsl:call-template name="FormatDate">
																				<xsl:with-param name="DateTime" select="DataScadenzaPagamento" />
																			</xsl:call-template>
																		</xsl:if>
																	</fo:block>
																</fo:table-cell>
																<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																	<fo:block xsl:use-attribute-sets="table-cell-label-small">
																		Importo:
																	</fo:block>
																	<fo:block>
																		<xsl:if test="ImportoPagamento">
																			<xsl:value-of select="ImportoPagamento" />
																		</xsl:if>
																	</fo:block>
																</fo:table-cell>
															</fo:table-row>
															<xsl:if test="CodUfficioPostale or CognomeQuietanzante or NomeQuietanzante or CFQuietanzante">
																<fo:table-row keep-together.within-page="always">
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Codice Ufficio Postale:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="CodUfficioPostale">
																				<xsl:value-of select="CodUfficioPostale" />
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Quietanzante:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="CognomeQuietanzante">
																				<xsl:value-of select="CognomeQuietanzante" />
																			</xsl:if>
																			<xsl:if test="CognomeQuietanzante and NomeQuietanzante">
																				<xsl:text> </xsl:text>
																			</xsl:if>
																			<xsl:if test="NomeQuietanzante">
																				<xsl:value-of select="NomeQuietanzante" />
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			CF del quietanzante:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="CFQuietanzante">
																				<xsl:value-of select="CFQuietanzante" />
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																</fo:table-row>
															</xsl:if>
															<xsl:if test="IstitutoFinanziario">
																<fo:table-row keep-together.within-page="always">
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="4">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Istituto finanziario:
																		</fo:block>
																		<fo:block>
																			<xsl:value-of select="IstitutoFinanziario" />
																		</fo:block>
																	</fo:table-cell>
																</fo:table-row>
															</xsl:if>
															<xsl:if test="IBAN or ABI">
																<fo:table-row keep-together.within-page="always">
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Codice IBAN:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="IBAN">
																				<xsl:value-of select="IBAN" />
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Codice ABI:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="ABI">
																				<xsl:value-of select="ABI" />
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																</fo:table-row>
															</xsl:if>
															<xsl:if test="CAB or BIC">
																<fo:table-row keep-together.within-page="always">
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Codice CAB:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="CAB">
																				<xsl:value-of select="CAB" />
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Codice BIC:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="BIC">
																				<xsl:value-of select="BIC" />
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																</fo:table-row>
															</xsl:if>
															<xsl:if test="ScontoPagamentoAnticipato or DataLimitePagamentoAnticipato">
																<fo:table-row keep-together.within-page="always">
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Sconto per pagamento anticipato:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="ScontoPagamentoAnticipato">
																				<xsl:value-of select="ScontoPagamentoAnticipato" />
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Data limite per il pagamento anticipato:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="DataLimitePagamentoAnticipato">
																				<xsl:call-template name="FormatDate">
																					<xsl:with-param name="DateTime" select="DataLimitePagamentoAnticipato" />
																				</xsl:call-template>
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																</fo:table-row>
															</xsl:if>
															<xsl:if test="PenalitaPagamentiRitardati or DataDecorrenzaPenale">
																<fo:table-row keep-together.within-page="always">
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Penale per ritardato pagamento:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="PenalitaPagamentiRitardati">
																				<xsl:value-of select="PenalitaPagamentiRitardati" />
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																	<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="2">
																		<fo:block xsl:use-attribute-sets="table-cell-label-small">
																			Data di decorrenza della penale:
																		</fo:block>
																		<fo:block>
																			<xsl:if test="DataDecorrenzaPenale">
																				<xsl:call-template name="FormatDate">
																					<xsl:with-param name="DateTime" select="DataDecorrenzaPenale" />
																				</xsl:call-template>
																			</xsl:if>
																		</fo:block>
																	</fo:table-cell>
																</fo:table-row>
															</xsl:if>
														</fo:table-body>
													</fo:table>
												</xsl:for-each>
											</xsl:if>
										</xsl:for-each>
									</xsl:if>
									<!--FINE DATI PAGAMENTO-->
									
									<!--INIZIO ALLEGATI-->
									<xsl:if test="Allegati">
										<fo:block xsl:use-attribute-sets="section-title padding-top-20">Allegati</fo:block>
										
										<xsl:for-each select="Allegati">
											<fo:table xsl:use-attribute-sets="table-content border-strong font-size-10">
												<xsl:if test="position() > 0">
													<xsl:attribute name="space-after">0pt</xsl:attribute>
												</xsl:if>
												<xsl:if test="position() != count(Allegati)">
													<xsl:attribute name="space-before">0pt</xsl:attribute>
												</xsl:if>
												<fo:table-column column-width="proportional-column-width(50)" />
												<fo:table-column column-width="proportional-column-width(30)" />
												<fo:table-column column-width="proportional-column-width(20)" />
												<fo:table-body>
													<fo:table-row keep-together.within-page="always">
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Nome dell'allegato:
															</fo:block>
															<fo:block>
																<xsl:if test="NomeAttachment">
																	<xsl:value-of select="NomeAttachment" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Algoritmo di compressione:
															</fo:block>
															<fo:block>
																<xsl:if test="AlgoritmoCompressione">
																	<xsl:value-of select="AlgoritmoCompressione" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
														<fo:table-cell xsl:use-attribute-sets="cell-padding border">
															<fo:block xsl:use-attribute-sets="table-cell-label-small">
																Formato:
															</fo:block>
															<fo:block>
																<xsl:if test="FormatoAttachment">
																	<xsl:value-of select="FormatoAttachment" />
																</xsl:if>
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
													<xsl:if test="DescrizioneAttachment">
														<fo:table-row keep-together.within-page="always">
															<fo:table-cell xsl:use-attribute-sets="cell-padding border" number-columns-spanned="3">
																<fo:block xsl:use-attribute-sets="table-cell-label-small">
																	Descrizione allegato:
																</fo:block>
																<fo:block>
																	<xsl:value-of select="DescrizioneAttachment" />
																</fo:block>
															</fo:table-cell>
														</fo:table-row>
													</xsl:if>
												</fo:table-body>
											</fo:table>
										</xsl:for-each>
									</xsl:if>
									<!--FINE ALLEGATI-->
									
								</xsl:for-each>
							</fo:block>
						</xsl:if>
					</xsl:if>
				
				</fo:flow>
				<!-- FINE CORPO DI OGNI PAGINA DELLA FATTURA -->
				
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	
</xsl:stylesheet>
