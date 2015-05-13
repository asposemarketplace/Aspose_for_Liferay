package com.aspose.liferay.portlet.blogs.action;

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
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

/**
 * Export Blog entry To Excel Action.
 * 
 * This action exports the blogs to MS-Excel xlsx file.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeBlogExportXlsAction extends BaseStrutsPortletAction {

	private static final Log s_log = LogFactoryUtil
			.getLog(AsposeBlogExportXlsAction.class);

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {

		long entryId = ParamUtil.getLong(resourceRequest, "entryId");

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntry(entryId);

		String content = entry.getContent();
		String blogTitle = "Blogs-" + entry.getEntryId();

		File tempXlsFile = null;

		try {

			tempXlsFile = AsposeExportServiceUtil
					.createTempFile(AsposeExportServiceUtil.XLS_FILE_EXT);
			AsposeExportServiceUtil
					.exportToXls(tempXlsFile, content.getBytes());
			String downloadXlsFileName = blogTitle
					+ AsposeExportServiceUtil.XLS_FILE_EXT;
			AsposeExportServiceUtil.sendFile(resourceRequest, resourceResponse,
					tempXlsFile, downloadXlsFileName);

		} catch (Exception e) {

			String msg = "Error exporting blog entry " + entryId
					+ " (to xls) :" + e.getMessage();

			s_log.error(msg, e);

			throw new PortalException(msg, e);

		} finally {

			AsposeExportServiceUtil.safeDeleteFile(tempXlsFile);
			tempXlsFile = null;

		}
	}

}
