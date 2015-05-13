package com.aspose.liferay.portlets.service;

import java.io.File;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;


/**
 * Aspose Export Service.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public interface AsposeExportService {
	
    public void exportToDoc(File tempDocFile, byte[] bytes)
    	throws PortalException, SystemException;
    
    public void exportToPdf(File tempDocFile, byte[] bytes)
        	throws PortalException, SystemException;
    
    public void exportToXls(File tempDocFile, byte[] bytes)
        	throws PortalException, SystemException;
    
    public void exportDynamicListToDoc(File tempDocFile, DDLRecordSet recordSet)
        	throws PortalException, SystemException, Exception;
    
    public void exportDynamicListToPdf(File tempPdfFile, DDLRecordSet recordSet)
        	throws PortalException, SystemException, Exception;
    
    public void exportDynamicListToXls(File tempXlsFile, DDLRecordSet recordSet)
        	throws PortalException, SystemException, Exception;
    
}
