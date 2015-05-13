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
 * Export Blog entry To PDF Action.
 * 
 * This action exports the blogs to pdf file.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeBlogExportPdfAction extends BaseStrutsPortletAction {

	private static final Log s_log = LogFactoryUtil
			.getLog(AsposeBlogExportPdfAction.class);

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {

		long entryId = ParamUtil.getLong(resourceRequest, "entryId");

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntry(entryId);

		String content = entry.getContent();
		String blogTitle = "Blogs-" + entry.getEntryId();

		File tempPdfFile = null;

		try {

			tempPdfFile = AsposeExportServiceUtil
					.createTempFile(AsposeExportServiceUtil.PDF_FILE_EXT);
			AsposeExportServiceUtil
					.exportToPdf(tempPdfFile, content.getBytes());
			String downloadPdfFileName = blogTitle
					+ AsposeExportServiceUtil.PDF_FILE_EXT;
			AsposeExportServiceUtil.sendFile(resourceRequest, resourceResponse,
					tempPdfFile, downloadPdfFileName);

		} catch (Exception e) {

			String msg = "Error exporting blog entry " + entryId
					+ " (to Pdf) :" + e.getMessage();

			s_log.error(msg, e);

			throw new PortalException(msg, e);

		} finally {

			AsposeExportServiceUtil.safeDeleteFile(tempPdfFile);
			tempPdfFile = null;

		}
	}

}
