package com.aspose.liferay.portlets.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * Aspose Export Property Values.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 * 
 */
public class AsposeExportPropsValues {

	public static String DEFAULT_ASPOSE_EXPORT_DOC_ACTIONS_MENU_EXT = "aspose_export_doc";
	
	public static int DEFAULT_ASPOSE_EXPORT_CACHE_MAX_AGE = 0;
	
	public static String DEFAULT_ASPOSE_EXPORT_SERVLET_CONTEXT_NAME = "liferay-aspose-export-hook";
		
	public static String[] ASPOSE_EXPORT_DOC_ACTIONS_MENU_EXT 
		= StringUtil.split(
				GetterUtil.get(PropsUtil.get(AsposeExportPropsKeys.ASPOSE_EXPORT_DOC_ACTIONS_MENU_EXT),DEFAULT_ASPOSE_EXPORT_DOC_ACTIONS_MENU_EXT));

    public static final int ASPOSE_EXPORT_CACHE_MAX_AGE 
		= GetterUtil.getInteger(PropsUtil.get(AsposeExportPropsKeys.ASPOSE_EXPORT_CACHE_MAX_AGE),DEFAULT_ASPOSE_EXPORT_CACHE_MAX_AGE);
        
    public static final String ASPOSE_EXPORT_SERVLET_CONTEXT_NAME 
		= GetterUtil.getString(PropsUtil.get(AsposeExportPropsKeys.ASPOSE_EXPORT_SERVLET_CONTEXT_NAME),DEFAULT_ASPOSE_EXPORT_SERVLET_CONTEXT_NAME);    
    
}
