package com.aspose.liferay.portlet.journal.action;

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
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;


/**
 * Export Journal To Excel Action.
 * 
 * This action exports the web-content / journal to MS-Excel xlsx file.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeJournalExportXlsAction extends BaseStrutsPortletAction {

    private static final Log s_log = LogFactoryUtil.getLog(AsposeJournalExportXlsAction.class);
    public static final double DEFAULT_VERSION = 1.0;
   
    @Override
    public void serveResource(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig,
            ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
       
        long groupId = ParamUtil.getLong(resourceRequest, "groupId");
        String articleId = ParamUtil.getString(resourceRequest, "articleId");
        double version = ParamUtil.getDouble(resourceRequest, "version", DEFAULT_VERSION);
        JournalArticle selectedArticleObject = JournalArticleLocalServiceUtil.getArticle((long)groupId, articleId, version);
        String content = selectedArticleObject.getContent();
        
    	content=content.substring(content.indexOf("<![CDATA[")+9,content.lastIndexOf("]]>"));
    	
    	
        String articleTitle = "Article-"+selectedArticleObject.getArticleId();
     
        File tempXlsFile = null;

        try {
        	
        	tempXlsFile = AsposeExportServiceUtil.createTempFile(AsposeExportServiceUtil.XLS_FILE_EXT);
            AsposeExportServiceUtil.exportToXls(tempXlsFile,content.getBytes());
            String downloadXlsFileName = articleTitle+ AsposeExportServiceUtil.XLS_FILE_EXT;
            AsposeExportServiceUtil.sendFile(resourceRequest, resourceResponse, tempXlsFile, downloadXlsFileName);

        } catch (Exception e) {
        	
        	String msg = "Error exporting article " + articleId 
        				+ " (to xls), groupId " + groupId
        				+ " : " + e.getMessage();
        	
        	s_log.error( msg ,e );
        	
        	throw new PortalException( msg, e ); 
        	
        } finally {
        	
        	AsposeExportServiceUtil.safeDeleteFile(tempXlsFile);
        	tempXlsFile = null;
            
        }
    }
  
    
    
}
