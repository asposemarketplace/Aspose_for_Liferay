package com.aspose.icon.action;

import java.io.InputStream;
import java.io.OutputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.io.IOUtils;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.struts.StrutsPortletAction;

/**
 * Display Aspose Icons
 * 
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeIconAction implements StrutsPortletAction {

	private static final Log s_log = LogFactoryUtil
			.getLog(AsposeIconAction.class);


	@Override
	public void processAction(PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String render(PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String render(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serveResource(PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {
		resourceResponse.setContentType("image/png");

		resourceResponse.addProperty(HttpHeaders.CACHE_CONTROL,
				"max-age=5, must-revalidate");

		OutputStream out = resourceResponse.getPortletOutputStream();
		InputStream is=getClass().getClassLoader().getResourceAsStream("asposeicon/aspose.png");
		byte[] bytes = IOUtils.toByteArray(is);
		out.write(bytes, 0, bytes.length);

		out.close();
		
	}


	@Override
	public void processAction(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		// TODO Auto-generated method stub
		
	}
}