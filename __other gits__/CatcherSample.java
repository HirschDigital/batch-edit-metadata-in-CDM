
package org.oclc.catcher;


import java.util.List;
import org.oclc.cdm.catcherws.CatcherService;
import org.oclc.cdm.catcherws.Metadata;
import org.oclc.cdm.catcherws.MetadataWrapper;
import org.oclc.cdm.catcherws.MetadataWrapper.MetadataList;


public class Catcher
{

	public static void main(String[] args)
	{
		final String CDMURL = "http://server17480.contentdm.oclc.org:8888";   
		final String CDMUSERNAME = "SarahLong";                        
		final String CDMPASSWORD = "ep9riseSkinwalker";                   
		final String CDMLICENSE = "R7WWM-LE5RB-HPYRT-RZJ53";      
		final String CDMALIAS = "/leirner";                        
        final String CDMCVFIELD = "rights";                      

		String retMsg;                                      


		CatcherService cs = new CatcherService();

		System.out.println ("Current web service version\n---------------------------");
		System.out.println (cs.getCatcherPort().getWSVersion());

		System.out.println ("\n\nList of collection\n------------------");
		System.out.println (
				cs.getCatcherPort().getCONTENTdmCatalog(CDMURL,
						CDMUSERNAME, CDMPASSWORD, CDMLICENSE));

		System.out.println ("\n\nCollection fields/attributes\n----------------------------");
		System.out.println (
				cs.getCatcherPort().getCONTENTdmCollectionConfig(CDMURL,
						CDMUSERNAME, CDMPASSWORD, CDMLICENSE, CDMALIAS));

                System.out.println ("\n\nControlled vocabulary terms\n----------------------------");
                System.out.println (
                                    cs.getCatcherPort().getCONTENTdmControlledVocabTerms(CDMURL,
                                             CDMUSERNAME, CDMPASSWORD, CDMLICENSE, CDMALIAS, CDMCVFIELD));


		System.out.println ("\n\nCONTENTdm upload module version\n-------------------------------");
		System.out.println (
				cs.getCatcherPort().getCONTENTdmHTTPTransferVersion(CDMURL,
						CDMUSERNAME, CDMPASSWORD));

		System.out.println ("\n\nDONE");
	}
}
