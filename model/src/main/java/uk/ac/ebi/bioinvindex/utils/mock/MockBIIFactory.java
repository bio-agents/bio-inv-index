package uk.ac.ebi.bioinvindex.utils.mock;

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

import uk.ac.ebi.bioinvindex.dao.BIIDAOException;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.term.Design;
import uk.ac.ebi.bioinvindex.model.term.FreeTextTerm;
import uk.ac.ebi.bioinvindex.model.term.OntologyEntry;


/**
 * @author Nataliya Sklyar (nsklyar@ebi.ac.uk)
 * Date: Sep 14, 2007
 */
public class MockBIIFactory {

	public static Study buildStudy(String acc, String title) {
		Study study = new Study(title);
		study.setAcc(acc);
		study.setDescription("Very long mock Study description");
		study.setObjective("Very important Study objective");

		return study;
	}

	public static <T extends FreeTextTerm> T buildTerm(Class<T> termClass, String name) {
		if (termClass == null) {
			throw new IllegalArgumentException("termClass cannot be null!");
		}

		T term;

		try {
			term = termClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BIIDAOException("Cannot instantiate class " + termClass, e);
		}

		term.setValue(name);
		return term;
	}


	public static <T extends OntologyEntry> T buildOntologyEntry(Class<T> termClass, String name, String acc) {
		if (termClass == null) {
			throw new IllegalArgumentException("termClass cannot be null!");
		}

		T term;

		try {
			term = termClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BIIDAOException("Cannot instantiate class " + termClass, e);
		}

		term.setName(name);
		term.setAcc(acc);

		return term;
	}


	public static void main(String[] args) {
		Design design = MockBIIFactory.buildTerm(Design.class, "Design");
		System.out.println("design = " + design);


	}
}
