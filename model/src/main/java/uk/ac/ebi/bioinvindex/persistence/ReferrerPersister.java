package uk.ac.ebi.bioinvindex.persistence;

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
 * This work is licenced under the Creative Commons Attribution-Share Alike 2.0 UK: England & Wales License. To view a copy of this licence, visit http://creativecommons.org/licenses/by-sa/2.0/uk/ or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California 94105, USA.
 *
 * __________
 * Sponsors
 * __________
 * This work has been funded mainly by the EU Carcinogenomics (http://www.carcinogenomics.eu) [PL 037712] and in part by the
 * EU NuGO [NoE 503630](http://www.nugo.org/everyone) projects and in part by EMBL-EBI.
 */

import uk.ac.ebi.bioinvindex.dao.ejb3.DaoFactory;
import uk.ac.ebi.bioinvindex.model.HasReferences;
import uk.ac.ebi.bioinvindex.model.xref.Xref;

import java.sql.Timestamp;

/**
 * Persists an {@link HasReferences} object
 * TODO: Do you fancy this name in place of HasReferences?
 * TODO: All the persister hierarchy is to be reviewed and persisters who persist {@link HasReferences}
 * objects must be made to subclass this. Same for {@link AnnotatablePersister}
 * 
 * <dl><dt>date:</dt><dd>Dec 4, 2008</dd></dl>
 * @author brandizi
 *
 * @param <R>
 */
public abstract class ReferrerPersister<R extends HasReferences> extends AccessiblePersister<R>
{
	private final XrefPersister xrefPersister;
	
	protected ReferrerPersister ( DaoFactory daoFactory, Timestamp submissionTs ) {
		super ( daoFactory, submissionTs );
		xrefPersister = new XrefPersister ( daoFactory, submissionTs );
	}

	/**
	 * Works on the {@link Xref}(s). 
	 * 
	 */
	@Override
	protected void preProcess ( R object ) 
	{
		super.preProcess ( object );
		// Xrefs
		for ( Xref xref: object.getXrefs () )
			xrefPersister.persist ( xref );
	}
}
