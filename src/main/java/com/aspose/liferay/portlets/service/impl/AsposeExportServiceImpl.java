package com.aspose.liferay.portlets.service.impl;

import java.io.File;

import com.aspose.liferay.portlets.service.AsposeExportService;
import com.aspose.liferay.portlets.service.AsposeExportServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;

/**
 * Aspose Export Service Implementation.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 * 
 * @see AsposeExportService
 * @see AsposeExportServiceUtil
 * @see AsposExportHelper
 */
public class AsposeExportServiceImpl implements AsposeExportService {

	public void exportToDoc(File tempDocFile, byte[] bytes)
			throws PortalException, SystemException {
		AsposExportHelper.exportToDoc(tempDocFile, bytes);
	}

	public void exportToPdf(File tempDocFile, byte[] bytes)
			throws PortalException, SystemException {
		AsposExportHelper.exportToPdf(tempDocFile, bytes);
	}

	public void exportToXls(File tempDocFile, byte[] bytes)
			throws PortalException, SystemException {
		AsposExportHelper.exportToXls(tempDocFile, bytes);
	}

	public void exportDynamicListToDoc(File tempDocFile, DDLRecordSet recordSet)
			throws Exception {
		AsposExportHelper.exportDynamicListToDoc(tempDocFile, recordSet);
	}

	public void exportDynamicListToPdf(File tempPdfFile, DDLRecordSet recordSet)
			throws PortalException, SystemException, Exception {
		AsposExportHelper.exportDynamicListToPdf(tempPdfFile, recordSet);
	}

	public void exportDynamicListToXls(File tempXlsFile, DDLRecordSet recordSet)
			throws PortalException, SystemException, Exception {
		AsposExportHelper.exportDynamicListToXls(tempXlsFile, recordSet);
	}
}
