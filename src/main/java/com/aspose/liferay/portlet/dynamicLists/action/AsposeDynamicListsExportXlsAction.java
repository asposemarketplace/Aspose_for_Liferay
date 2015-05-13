package com.aspose.liferay.portlet.dynamicLists.action;

import java.io.File;

import javax.portlet.PortletConfig;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.aspose.liferay.portlets.service.AsposeExportServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.struts.BaseStrutsPortletAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetServiceUtil;

/**
 * Export Dynamic Lists Data To Excel Action.
 * 
 * This action exports the Dynamic Lists Data to MS-Excel xlsx file.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeDynamicListsExportXlsAction extends BaseStrutsPortletAction {

	private static final Log s_log = LogFactoryUtil
			.getLog(AsposeDynamicListsExportXlsAction.class);

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {

		long recordSetId = ParamUtil.getLong(resourceRequest, "recordSetId");

		
		File tempXlsFile = null;
		String listTitle = "DynamicList-" + recordSetId;

		try {

			DDLRecordSet recordSet = DDLRecordSetServiceUtil
					.getRecordSet(recordSetId);
			tempXlsFile = AsposeExportServiceUtil
					.createTempFile(AsposeExportServiceUtil.XLS_FILE_EXT);
			
			 AsposeExportServiceUtil.exportDynamicListToXls(tempXlsFile, recordSet);
			
			String downloadXlsFileName = listTitle
					+ AsposeExportServiceUtil.XLS_FILE_EXT;

			AsposeExportServiceUtil.sendFile(resourceRequest, resourceResponse,
					tempXlsFile, downloadXlsFileName);

		} catch (Exception e) {

			String msg = "Error exporting Dynamic Lists Data" + recordSetId
					+ " (to xls) :" + e.getMessage();

			s_log.error(msg, e);

			throw new PortalException(msg, e);

		} finally {

			AsposeExportServiceUtil.safeDeleteFile(tempXlsFile);
			tempXlsFile = null;

		}
	}

}
