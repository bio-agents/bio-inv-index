package uk.ac.ebi.bioinvindex.utils;

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

import java.io.InputStream;
import java.util.List;


import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import uk.ac.ebi.bioinvindex.model.term.AnnotationTypes;
import uk.ac.ebi.bioinvindex.model.xref.AssayTypeDataLocation;
import uk.ac.ebi.bioinvindex.model.xref.ReferenceSource;
import uk.ac.ebi.bioinvindex.dao.IdentifiableDAO;
import uk.ac.ebi.bioinvindex.dao.ReferenceSourceDAO;
import uk.ac.ebi.bioinvindex.dao.ejb3.DaoFactory;
import uk.ac.ebi.bioinvindex.utils.datasourceload.DataSourceLoader;

/**
 * @author: Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: May 1, 2009
 */
public class DataSourceLoaderTest {

	private DataSourceLoader loader = new DataSourceLoader();

	@Test
	public void testPersistAll() throws Exception {

		InputStream resource = this.getClass().getClassLoader().getResourceAsStream("testdata/dataconfig.xml");

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BIIEntityManager");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		loader.setEntityManager(entityManager);
		loader.loadAll(resource);

		IdentifiableDAO<AssayTypeDataLocation> dao = DaoFactory.getInstance(entityManager).getIdentifiableDAO(AssayTypeDataLocation.class);
		List<AssayTypeDataLocation> dataLocations = dao.getAll();
		assertEquals(3, dataLocations.size());

		ReferenceSourceDAO refSourseDao = DaoFactory.getInstance(entityManager).getReferenceSourceDAO();
		ReferenceSource referenceSource = refSourseDao.getReferenceSourceByName(ReferenceSource.ISATAB_METADATA);
		assertNotNull( "source for meta-data not found!", referenceSource );
	
		ReferenceSource nmrSrc = refSourseDao.getReferenceSourceByName ( "bii:datasources:nmr" );
		assertNotNull( "NMR source for meta-data not found!", nmrSrc );
		assertEquals ( "Wrong processed path retrieved for NMR!", 
			"processed2/${study-acc}", nmrSrc.getSingleAnnotationValue ( AnnotationTypes.PROCESSED_DATA_FILE_PATH.getName () ) );
		assertEquals ( "Wrong generic link retrieved for NMR!", 
			"ftp://generic2/${study-acc}", nmrSrc.getSingleAnnotationValue ( AnnotationTypes.GENERIC_DATA_FILE_LINK.getName () ) );
		
	}

}
