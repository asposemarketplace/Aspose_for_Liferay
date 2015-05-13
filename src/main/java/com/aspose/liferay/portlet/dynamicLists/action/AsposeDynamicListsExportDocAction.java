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
 * Export Dynamic Lists Data To Doc Action.
 * 
 * This action exports the Dynamic Lists Data to MS-Word doc file.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeDynamicListsExportDocAction extends BaseStrutsPortletAction {

	private static final Log s_log = LogFactoryUtil
			.getLog(AsposeDynamicListsExportDocAction.class);

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {

		long recordSetId = ParamUtil.getLong(resourceRequest, "recordSetId");
		String listTitle = "DynamicList-" + recordSetId;
		File tempDocFile = null;
		try {
			
			DDLRecordSet recordSet = DDLRecordSetServiceUtil
					.getRecordSet(recordSetId);
			tempDocFile = AsposeExportServiceUtil
					
					.createTempFile(AsposeExportServiceUtil.DOC_FILE_EXT);

			AsposeExportServiceUtil.exportDynamicListToDoc(tempDocFile,
					recordSet);

			String downloadDocFileName = listTitle
					+ AsposeExportServiceUtil.DOC_FILE_EXT;

			AsposeExportServiceUtil.sendFile(resourceRequest, resourceResponse,
					tempDocFile, downloadDocFileName);

		} catch (Exception e) {

			String msg = "Error exporting Dynamic Lists Data" + recordSetId
					+ " (to MS-Word format) :" + e.getMessage();

			s_log.error(msg, e);

			throw new PortalException(msg, e);

		} finally {

			AsposeExportServiceUtil.safeDeleteFile(tempDocFile);
			tempDocFile = null;

		}
	}
}
