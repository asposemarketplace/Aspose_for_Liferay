package com.aspose.liferay.portlets.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;

/**
 * Aspose Export Service Utility.
 * 
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeExportServiceUtil {

	private static final String SERVICE_FIELD_NAME = "_service";
	private static final String ASPOSE_SERVLET_CONTEXT_NAME = "liferay-aspose-export-hook";
	private static final String FILE_EXT_SEP = FilenameUtils.EXTENSION_SEPARATOR_STR;
	public static final String DOC_FILE_EXT_NAME = "doc";
	public static final String DOC_FILE_EXT = FILE_EXT_SEP + DOC_FILE_EXT_NAME;

	public static final String PDF_FILE_EXT_NAME = "pdf";
	public static final String PDF_FILE_EXT = FILE_EXT_SEP + PDF_FILE_EXT_NAME;

	public static final String XLS_FILE_EXT_NAME = "xlsx";
	public static final String XLS_FILE_EXT = FILE_EXT_SEP + XLS_FILE_EXT_NAME;
	public static int ASPOSE_DOWNLOAD_CACHE_MAX_AGE = 0;

	private static final Log s_log = LogFactoryUtil
			.getLog(AsposeExportServiceUtil.class);
	private static AsposeExportService _service;

	public static void exportToDoc(File tempDocFile, byte[] bytes)
			throws PortalException, SystemException {
		getService().exportToDoc(tempDocFile, bytes);

	}

	public static void exportToPdf(File tempDocFile, byte[] bytes)
			throws PortalException, SystemException {
		getService().exportToPdf(tempDocFile, bytes);

	}

	public static void exportToXls(File tempDocFile, byte[] bytes)
			throws PortalException, SystemException {
		getService().exportToXls(tempDocFile, bytes);

	}

	public static void exportDynamicListToDoc(File tempDocFile,
			DDLRecordSet recordSet) throws Exception {
		getService().exportDynamicListToDoc(tempDocFile, recordSet);

	}

	public static void exportDynamicListToPdf(File tempPdfFile,
			DDLRecordSet recordSet) throws Exception {
		getService().exportDynamicListToPdf(tempPdfFile, recordSet);

	}

	public static void exportDynamicListToXls(File tempXlsFile,
			DDLRecordSet recordSet) throws Exception {
		getService().exportDynamicListToXls(tempXlsFile, recordSet);

	}

	public static AsposeExportService getService() {

		if (_service == null) {
			if (StringUtils.isEmpty(ASPOSE_SERVLET_CONTEXT_NAME)) {
				throw new IllegalStateException(
						"Servlet context name is undefined");
			}
			String beanName = AsposeExportService.class.getName();
			_service = (AsposeExportService) PortletBeanLocatorUtil.locate(
					ASPOSE_SERVLET_CONTEXT_NAME, beanName);
			ReferenceRegistry.registerReference(AsposeExportServiceUtil.class,
					SERVICE_FIELD_NAME);
		}

		return _service;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public void setService(AsposeExportService service) {
	}

	public static void sendFile(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse, File file, String fileName)
			throws Exception {

		FileInputStream fileInputStream = null;

		try {

			if (StringUtils.isEmpty(fileName)) {
				fileName = FilenameUtils.getBaseName(file.getName());
			}

			String fileMimeType = MimeTypesUtil.getContentType(fileName);
			resourceResponse.setContentType(fileMimeType);

			int folderDownloadCacheMaxAge = ASPOSE_DOWNLOAD_CACHE_MAX_AGE;
			if (folderDownloadCacheMaxAge > 0) {
				String cacheControlValue = "max-age="
						+ folderDownloadCacheMaxAge + ", must-revalidate";
				resourceResponse.addProperty(HttpHeaders.CACHE_CONTROL,
						cacheControlValue);
			}

			String contentDispositionValue = "attachment; filename=\""
					+ fileName + "\"";
			resourceResponse.addProperty(HttpHeaders.CONTENT_DISPOSITION,
					contentDispositionValue);

			// NOTE: java.io.File may return a length of 0 (zero) for a valid
			// file
			// @see java.io.File#length()
			long fileLength = file.length();
			if ((fileLength > 0L) && (fileLength < (long) Integer.MAX_VALUE)) {
				resourceResponse.setContentLength((int) fileLength);
			}

			fileInputStream = new FileInputStream(file);
			OutputStream responseOutputStream = resourceResponse
					.getPortletOutputStream();
			long responseByteCount = IOUtils.copy(fileInputStream,
					responseOutputStream);
			responseOutputStream.flush();
			responseOutputStream.close();
			fileInputStream.close();
			fileInputStream = null;

			if (s_log.isDebugEnabled()) {
				s_log.debug("sent " + responseByteCount + " byte(s) for file "
						+ fileName);
			}

		} catch (Exception e) {

			String name = StringPool.BLANK;
			if (file != null) {
				name = file.getName();
			}

			String msg = "Error sending file " + name + " : " + e.getMessage();
			s_log.error(msg);
			throw new PortalException(msg, e);

		} finally {

			if (fileInputStream != null) {
				try {
					fileInputStream.close();
					fileInputStream = null;
				} catch (Exception e) {
					String msg = "Error closing input stream : "
							+ e.getMessage();
					s_log.error(msg);
				}
			}

		}
	}

	public static File createTempFile(String tempFileSuffix) throws IOException {

		String tempFilePrefix = PortalUUIDUtil.generate();
		File tempFile = File.createTempFile(tempFilePrefix, tempFileSuffix);
		return tempFile;
	}

	public static void safeDeleteFile(File targetFile) {

		if (targetFile != null) {
			try {
				if (targetFile.exists()) {
					if (!targetFile.delete()) {
						targetFile.deleteOnExit();
					}
				}
			} catch (Exception e) {
				String msg = "Error safely deleting file " + targetFile + " : "
						+ e.getMessage();
				s_log.error(msg);
			} finally {
				if (targetFile.exists()) {
					targetFile.deleteOnExit();
				}
			}
		}
	}

}
