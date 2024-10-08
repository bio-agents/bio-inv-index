package uk.ac.ebi.bioinvindex.unloading.bioproperties;

/*
 * __________
 * CREDITS
 * __________
 *
 * Team page: http://isatab.sf.net/
 * - Marco Brandizi (software engineer: ISAvalidator, ISAconverter, BII data management utility, BII model)
 * - Eamonn Maguire (software engineer: ISAcreator, ISAcreator configurator, ISAvalidator, ISAconverter,  BII data management utility, BII web)
 * - Nataliya Sklyar (software engineer: BII web application, BII model,  BII data management utility)
 * - Philippe Rocca-Serra (technical coordinator: user requirements and standards compliance for ISA software, ISA-tab format specification, BII model, ISAcreator wizard, ontology)
 * - Susanna-Assunta Sansone (coordinator: ISA infrastructure design, standards compliance, ISA-tab format specification, BII model, funds raising)
 *
 * Contributors:
 * - Manon Delahaye (ISA team trainee:  BII web services)
 * - Richard Evans (ISA team trainee: rISAtab)
 *
 *
 * ______________________
 * Contacts and Feedback:
 * ______________________
 *
 * Project overview: http://isatab.sourceforge.net/
 *
 * To follow general discussion: isatab-devel@list.sourceforge.net
 * To contact the developers: isaagents@googlegroups.com
 *
 * To report bugs: http://sourceforge.net/tracker/?group_id=215183&atid=1032649
 * To request enhancements:  http://sourceforge.net/tracker/?group_id=215183&atid=1032652
 *
 *
 * __________
 * License:
 * __________
 *
 * Reciprocal Public License 1.5 (RPL1.5)
 * [OSI Approved License]
 *
 * Reciprocal Public License (RPL)
 * Version 1.5, July 15, 2007
 * Copyright (C) 2001-2007
 * Technical Pursuit Inc.,
 * All Rights Reserved.
 *
 * http://www.opensource.org/licenses/rpl1.5.txt
 *
 * __________
 * Sponsors
 * __________
 * This work has been funded mainly by the EU Carcinogenomics (http://www.carcinogenomics.eu) [PL 037712] and in part by the
 * EU NuGO [NoE 503630](http://www.nugo.org/everyone) projects and in part by EMBL-EBI.
 */

import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.term.Parameter;
import uk.ac.ebi.bioinvindex.model.term.Property;
import uk.ac.ebi.bioinvindex.model.term.PropertyValue;
import uk.ac.ebi.bioinvindex.unloading.UnloadManager;
import uk.ac.ebi.bioinvindex.unloading.freetextterms.AbstractFreeTextTermUnloader;

/**
 * Generic unloader definition for {@link PropertyValue}. This class should never be used directly, use
 * {@link PropertyValueUnloader} instead.
 * 
 * @author brandizi
 * <b>date</b>: Oct 27, 2009
 * 
 */
public abstract class AbstractPropertyValueUnloader<PV extends PropertyValue<?>> extends AbstractFreeTextTermUnloader<PV>
{
//	private final static Association [] referringAssociations = new Association [] {
//		new Association ( AssayResult.class, "cascadedPropertyValues" )
//	};  
	
	public AbstractPropertyValueUnloader ( UnloadManager unloadManager ) {
		super ( unloadManager );
	}

	@Override
	public boolean queue ( PV pval ) 
	{
		if ( !super.queue ( pval ) ) return false;

		Property<?> type = pval.getType ();
		if ( type instanceof Parameter )
			unloadManager.queue ( Parameter.class, (Parameter) type );
		else
			unloadManager.queue ( Property.class, type );

		unloadManager.queue ( pval.getUnit () );
		return true;
	}

//	@Override
//	protected Association[] getReferringAssociations () {
//		return referringAssociations;
//	}

}
