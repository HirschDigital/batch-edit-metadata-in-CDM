//**************************************************************************************************
//*  CatcherSample.java version 6.0 as of 03/07/2011
//*                                                                                                *
//*  All sample code is provided by OCLC for illustrative purposes only. These examples have       *
//*  not been thoroughly tested under all conditions. OCLC therefore cannot guarantee or           *
//*  imply reliability, serviceability, or functionality of these programs.                        *
//*                                                                                                *
//*  All programs contained herein are provided to you "AS IS" without warranties of any           *
//*  kind. The implied warranties of non-infringement, merchantability and fitness for a           *
//*  particular purpose are expressly disclaimed.                                                  *
//*                                                                                                *
//**************************************************************************************************
package org.oclc.catcher;




//**************************************************************************************************
//*                                                                                                *
//*  Import classes generated from web service WSDL                                                *
//*                                                                                                *
//**************************************************************************************************
import java.util.List;
import org.oclc.cdm.catcherws.CatcherService;
import org.oclc.cdm.catcherws.Metadata;
import org.oclc.cdm.catcherws.MetadataWrapper;
import org.oclc.cdm.catcherws.MetadataWrapper.MetadataList;




/***************************************************************************************************
*
*  This is an example of creating a Java program to communicate with the Catcher web service.
*  There are many ways this can be accomplished and this is just one example. We create
*  a class to communicate with the OCLC CONTENTdm Catcher service. The first thing is to
*  generate Java classes based on the WSDL provided. To generate classes from the WSDL, use
*  wsimport (provided in the bin folder of the Java JDK) via the command prompt:
*
*    C:\>wsimport -p org.oclc.cdm.catcherws -s .
*    		https://worldcat.org/webservices/contentdm/catcher/6.0/CatcherService.wsdl
*
*  The generated code will be in the package "org.oclc.cdm.catcherws". Place all of the Java
*  source code into your Java IDE. You should then be able to compile and run this sample code
*  to communicate with the web service.
*
***************************************************************************************************/
public class Catcher
{
	/***********************************************************************************************
	*
	*  Create object to connect to web service, then make calls
	*
	***********************************************************************************************/
	public static void main(String[] args)
	{
		final String CDMURL = "http://yourcollection.yourorgname.org";     //valid CONTENTdm URL
		final String CDMUSERNAME = "johndoe";                         //CONTENTdm user name
		final String CDMPASSWORD = "password123";                     //CONTENTdm password
		final String CDMLICENSE = "123456789abcdefghijk";             //valid 20-digit CONTENTdm Server license code
		final String CDMALIAS = "/pNNNNqdc";                          //existing CONTENTdm collection alias name
        final String CDMCVFIELD = "fieldnickname";                      //CONTENTdm field's nickname in the specified collection with cv terms

		String retMsg;                                        //return message from web service call

		//------------------------------------------------------------------------------------------
		//  create object to communicate with the web service
		//------------------------------------------------------------------------------------------
		CatcherService cs = new CatcherService();

		//------------------------------------------------------------------------------------------
		//  The getWSVersion call returns the version number of the web service
		//------------------------------------------------------------------------------------------
		System.out.println ("Current web service version\n---------------------------");
		System.out.println (cs.getCatcherPort().getWSVersion());

		//------------------------------------------------------------------------------------------
		//
		//  The getCONTENTdmCatalog call will return a list of collections from a CONTENTdm Server.
		//  You will need to specify the Server URL, administrator username, password,
		//  and a valid 20-digit license code. The return result will be in the following form:
		//
		//  <?xml version="1.0" encoding="utf-8" ?>
		//  <collinfo>
		//    <num_collections>4</num_collections>           <!-- number of collections on the server -->
		//    <collection>                                            <!-- start of a collection -->
		//      <collection_alias>/pNNNNqdc</collection_alias>                      <!-- collection alias -->
		//      <collection_name>Your Collection</collection_name>         <!-- collection title -->
		//      <collection_fullres>
		//        <fullres_enabled>no</fullres_enabled>                <!-- is full res enabled? -->
		//      </collection_fullres>
		//    </collection>
		//    <collection>
		//    ...                                                          <!-- more collections -->
		//
		//  Errors: on success, you will see "<?xml version="1.0" encoding="utf-8" ?>..."
		//  returned. Only strings are returned. Anything that does not start with this text
		//  is an error and there will be a message describing the error. For example, if
		//  you mistype a URL and it is not a CONTENTdm server then you'll receive
		//  Error detail: No CONTENTdm server is responding at this URL - http://badurl...
		//
		//  ****************************************************************************************
		//   IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT  IMPORTANT
		//  ****************************************************************************************
		//  For all calls requiring credentials, if you are not able to authenticate
		//  the response will be raw-HTML, which is originally from the web server CONTENTdm
		//  is running on. For example, if you are not able to authenticate, from IIS you
		//  will receive:
		//
		//    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" ...
		//    <HTML><HEAD><TITLE>You are not authorized to view this page</TITLE>
		//    ...
		//
		//  Depending on the web server CONTENTdm is running on, you'll have a different return
		//  string if you fail to authenticate.
		//
		//------------------------------------------------------------------------------------------
		System.out.println ("\n\nList of collection\n------------------");
		System.out.println (
				cs.getCatcherPort().getCONTENTdmCatalog(CDMURL,
						CDMUSERNAME, CDMPASSWORD, CDMLICENSE));

		//------------------------------------------------------------------------------------------
		//
		//  The getCONTENTdmCollectionConfig call will return the collection fields and their attributes; for
		//	the collection parameter in this call the alias of the collection config you want returned
		//	should be used; the collection alias is listed in the getCONTENTdmCatalog result.
		//	The result of the getCONTENTdmCollectionConfig call is:
		//
		//	<?xml version="1.0" encoding="utf-8" ?>
		//	<fields>
		//	  <code>0</code>
		//	  <field>
		//	    <admin>0</admin>                                    <!-- is this an admin field? -->
		//	    <nickname>title</nickname>                                      <!-- field alias -->
		//	    <name>Title</name>                                    <!-- displayed field title -->
		//	    <type>TEXT</type>                                                <!-- field type -->
		//	    <size>0</size>
		//	    <search>1</search>
		//	    <hidden>0</hidden>
		//	    <vocab>0</vocab>
		//	    <vocdb></vocdb>
		//	    <dcmap>title</dcmap>
		//	    <req>1</req>
		//	    <readonly>0</readonly>
		//	    <tag></tag>
		//	  </field>
		//	  <field>                                                         <!-- another field -->
		//	    ...
		//
		//  Errors: on success, you will see "<?xml version="1.0" encoding="utf-8" ?>..."
		//  returned. Only strings are returned. Anything that does not start with this text
		//  is an error and there will be a message describing the error. For example, if
		//  you mistype the alias and there is no collection matching that alias then you'll receive
		//  Error detail: Error looking up collection /pNNNNqdc
		//
		//------------------------------------------------------------------------------------------
		System.out.println ("\n\nCollection fields/attributes\n----------------------------");
		System.out.println (
				cs.getCatcherPort().getCONTENTdmCollectionConfig(CDMURL,
						CDMUSERNAME, CDMPASSWORD, CDMLICENSE, CDMALIAS));

                //------------------------------------------------------------------------------------------
                //
                //  The getCONTENTdmControlledVocabTerms call will return the controlled vocabulary terms; for
                //      the collection parameter in this call the alias of the collection config you want returned
                //      should be used and the field parameter is the field with the controlled vocabulary applied.
                //      The result of the getCONTENTdmControlledVocabTerms call is:
                //
                //      <?xml version="1.0" encoding="utf-8"?>
                //      <terms>
                //        <term><![CDATA['u.ds]]]]>><![CDATA[</term>
                //        <term><![CDATA[1-inch]]]]>><![CDATA[</term>
                //        <term>...                                                    <!-- another term -->
                //
                //  Errors: on success, you will see "<?xml version="1.0" encoding="utf-8" ?>..."
                //  returned. Only strings are returned. Anything that does not start with this text
                //  is an error and there will be a message describing the error. For example, if
                //  you choose a field without controlled vocabulary activated, then you'll receive
                //  Error detail: There is no controlled vocabulary for this field.
                //
                //------------------------------------------------------------------------------------------
                System.out.println ("\n\nControlled vocabulary terms\n----------------------------");
                System.out.println (
                                    cs.getCatcherPort().getCONTENTdmControlledVocabTerms(CDMURL,
                                             CDMUSERNAME, CDMPASSWORD, CDMLICENSE, CDMALIAS, CDMCVFIELD));

		//------------------------------------------------------------------------------------------
		//
		//  The following processCONTENTdm call will add a metadata only item (null item) to a CONTENTdm collection.
		//  The first step is to form the metadata; the set of metadata for the collection
		//  can be retrieved by the getCONTENTdmCollectionConfig call. In this example we
		//  want to add an item with the following metadata:
		//
		//    title = cats
		//    subject = animals
		//
		//  Note the title field alias is "title" and subject field alias is "subjec". Title is a required field
		//  for each add call. After this call completes you should see items in the Server Admin approve queue, i.e.
		//
		//    http://CONTENTdm/cgi-bin/admin/selimage.exe?CISODB=ALIAS
		//
		//  Approve each item and index the collection, then browse the collection to see the newly
		//  added item. The result of the call is returned in a String. Remember to re-index
		//  the collection for this to take effect.
		//
		//  Errors: upon a successful call, you should get a receipt from the web service indicating the
		//  the initial status as follows:
		//
		//    Transaction ID: dcscatchws01qxdu:117:8459087439987
		//     Add initiated.
		//
		//  The transaction ID is a unique identifier that will be surfaced in future reporting of final transaction
		//  status in Server Admin. This receipt does not mean the transaction has completed; it just means the
		//  Catcher web service has accepted your submission and can proceed to send the data to CONTENTdm.
		//  When there is an error you should receive a message string that starts with "Error detail: ...".
		//  For example, suppose you mistype a field alias as "titlex" instead of "title"; you should receive the
		//  following message: "Error detail: Invalid metadata field specified - titlex."
		//
		//------------------------------------------------------------------------------------------
		MetadataWrapper.MetadataList mList = new MetadataList();
		List<Metadata> metadata = mList.getMetadata();
		Metadata m = new Metadata();
		m.setField("title");
		m.setValue("cats");
		metadata.add(m);
		m = new Metadata();
		m.setField("subjec");
		m.setValue("animals");
		metadata.add(m);
		MetadataWrapper wrapper = new MetadataWrapper();
		wrapper.setMetadataList(mList);
		System.out.println ("\n\nAdd an item to collection\n-------------------------");
		retMsg = cs.getCatcherPort().processCONTENTdm("add", CDMURL,
				CDMUSERNAME, CDMPASSWORD, CDMLICENSE, CDMALIAS, wrapper);
		System.out.println (retMsg);

		//------------------------------------------------------------------------------------------
		//
		//  The following processCONTENTdm call will change the metadata of an existing item in a collection. The first
		//  step is to locate the dmrecord number to uniquely identify the item to edit. The dmrecord number is the
		//  same as the CISOPTR number for the item. It may be located by searching your collection to find the
		//  URL of the item. The number in the URL is labeled "CISOPTR=". The CONTENTdm number also equals
		//  the dmrecord and CISOPTR number. In this example we want to change the title from "cats" to "dogs",
		//  and the CISOPTR is 0.
		//
		//  Errors: upon a successful call, you should get a receipt from the web service indicating the
		//  the initial status as follows:
		//
		//    Transaction ID: dcscatchws01qxdu:117:8461557426455
		//     Edit initiated.
		//
		//  The transaction ID is a unique identifier that will be surfaced in future reporting of final transaction
		//  status in Server Admin. This receipt does not mean the transaction has completed; it just means the
		//  Catcher web service has accepted your submission and can proceed to send the data to CONTENTdm.
		//  When there is an error you should receive a message string that starts with "Error detail: ...".
		//  For example,  if the dmrecord value cannot be found in the collection you should receive the
		//  following message: "Error detail: No permission to access this item."
		//
		//------------------------------------------------------------------------------------------
		mList = new MetadataList();
		metadata = mList.getMetadata();
		m = new Metadata();
		m.setField("dmrecord");
		m.setValue("0");
		metadata.add(m);
		m = new Metadata();
		m.setField("title");
		m.setValue("dogs");
		metadata.add(m);
		wrapper = new MetadataWrapper();
		wrapper.setMetadataList(mList);
		System.out.println ("\n\nEdit an item to collection\n--------------------------");
		retMsg = cs.getCatcherPort().processCONTENTdm("edit", CDMURL,
				CDMUSERNAME, CDMPASSWORD, CDMLICENSE, CDMALIAS, wrapper);
		System.out.println (retMsg);

		//------------------------------------------------------------------------------------------
		//
		//  The following processCONTENTdm call will delete an existing item in a collection. The first step
		//  is to locate the dmrecord number to uniquely identify the item to edit. The dmrecord number is the
		//  same as the CISOPTR number for the item. It may be located by searching your collection to find the
		//  URL of the item. The number in the URL is labeled "CISOPTR=". The CONTENTdm number also equals
		//  the dmrecord and CISOPTR number. As in the previous edit call example, the CISOPTR is 0. The only
		//  thing that needs to be set for this call is the dmrecord field.
		//
		//  Errors: upon a successful call, you should get a receipt from the web service indicating the
		//  the initial status as follows:
		//
		//    Transaction ID: dcscatchws01qxdu:117:8477593175187
		//     Delete initiated.
		//
		//  The transaction ID is a unique identifier that will be surfaced in future reporting of final transaction
		//  status in Server Admin. This receipt does not mean the transaction has completed; it just means the
		//  Catcher web service has accepted your submission and can proceed to send the data to CONTENTdm.
		//  When there is an error you should receive a message string that starts with "Error detail: ...".
		//
		//------------------------------------------------------------------------------------------
		mList = new MetadataList();
		metadata = mList.getMetadata();
		m = new Metadata();
		m.setField("dmrecord");
		m.setValue("0");
		metadata.add(m);
		wrapper = new MetadataWrapper();
		wrapper.setMetadataList(mList);
		System.out.println ("\n\nDelete an item to collection\n----------------------------");
		retMsg = cs.getCatcherPort().processCONTENTdm("delete", CDMURL,
				CDMUSERNAME, CDMPASSWORD, CDMLICENSE, CDMALIAS, wrapper);
		System.out.println (retMsg);

		//------------------------------------------------------------------------------------------
		//
		//  The getCONTENTdmHTTPTransferVersion call will return the version of the upload module
		//  in CONTENTdm; it is an optional call for Catcher applications and may be useful for troubleshooting.
		//
		//  Errors: for this call, you should get back a string that says something like
		//  "CONTENTdm HTTP Transfer Version 5.0.0.57", or if there is an error you should
		//  receive a message string that starts with "Error detail: ..."
		//
		//------------------------------------------------------------------------------------------
		System.out.println ("\n\nCONTENTdm upload module version\n-------------------------------");
		System.out.println (
				cs.getCatcherPort().getCONTENTdmHTTPTransferVersion(CDMURL,
						CDMUSERNAME, CDMPASSWORD));

		//------------------------------------------------------------------------------------------
		//  end of sample
		//------------------------------------------------------------------------------------------
		System.out.println ("\n\nDONE");
	}
}
