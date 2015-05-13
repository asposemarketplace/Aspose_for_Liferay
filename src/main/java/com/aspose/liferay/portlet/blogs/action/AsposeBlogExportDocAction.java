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
 * Export Blog entry To Doc Action.
 * 
 * This action exports the blogs to MS-Word doc file.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeBlogExportDocAction extends BaseStrutsPortletAction {

	private static final Log s_log = LogFactoryUtil
			.getLog(AsposeBlogExportDocAction.class);

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {

		long entryId = ParamUtil.getLong(resourceRequest, "entryId");

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntry(entryId);

		String content = entry.getContent();
		String blogTitle = "Blogs-" + entry.getEntryId();

		File tempDocFile = null;

		try {

			tempDocFile = AsposeExportServiceUtil
					.createTempFile(AsposeExportServiceUtil.DOC_FILE_EXT);
			AsposeExportServiceUtil
					.exportToDoc(tempDocFile, content.getBytes());
			String downloadDocFileName = blogTitle
					+ AsposeExportServiceUtil.DOC_FILE_EXT;
			AsposeExportServiceUtil.sendFile(resourceRequest, resourceResponse,
					tempDocFile, downloadDocFileName);

		} catch (Exception e) {

			String msg = "Error exporting blog entry " + entryId
					+ " (to doc) :" + e.getMessage();

			s_log.error(msg, e);

			throw new PortalException(msg, e);

		} finally {

			AsposeExportServiceUtil.safeDeleteFile(tempDocFile);
			tempDocFile = null;

		}
	}

}
