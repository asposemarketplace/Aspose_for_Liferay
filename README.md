# Aspose Java for Liferay

This is Liferay CMS / Portal plugin released by [Aspose pty ltd](http://www.aspose.com).

Aspose.Total Java for Liferay (hook plugin app) provides options for exporting web-contents and blogs created in html to MS-WORD, MS-EXCEL and PDF file formats using [Aspose.Total Java APIs](http://www.aspose.com/java/total-component.aspx). (Aspose.Words, Aspose.Cells and Aspose.PDF) 

The Plugin also provides very useful functionality / options for exporting the Dynamic Data Lists to MS-WORD, MS-EXCEL and PDF file formats using [Aspose.Total Java APIs](http://www.aspose.com/java/total-component.aspx). (Aspose.Words, Aspose.Cells and Aspose.PDF) 

## Supported Products

### GitHub Project Master 

* Liferay Portal 6.2 CE : 6.2 CE GA1 (6.2.0+)
* Liferay Portal 6.2 EE : 6.2 EE GA1 (6.2.10+)

## Downloads

The latest release is available from [Codeplex - Aspose Java for Liferay](https://asposeliferay.codeplex.com/releases/view/615042 "Aspose Java for Liferay").

You can also download or install the hook from [Liferay Marketplace - Aspose Java for Liferay](https://www.liferay.com/marketplace/-/mp/category/11232561 "Aspose Java for Liferay")


## Usage

Step 1. Navigate to Liferay Site Administration - Web Content / Blogs / Dynamic Data Lists page.

![Web-Content Portlet](http://i.imgur.com/FNpUJyM.jpg "Web-Content Portlet")

Step 2. Click on the "Action" button for a web-content and select "Export to MS-Word" / "Export to PDF" / "Export to MS-Excel" menu items.

![Web-Content Action Menu](http://i.imgur.com/WnsFibG.jpg "Web-Content Action Menu")

Step 3. Open .DOC/.XLSX/.PDF file or save to local download folder in your web browser.

![Firefox Download File Dialog](http://i.imgur.com/VVuVVFN.jpg "Firefox Download File Dialog")


## Building

Step 1. Checkout source from GitHub project

Step 1.1. Checkout master from GitHub project

    $ md work
    $ cd work
    $ md master
    $ git clone https://github.com/asposemarketplace/Aspose_for_Liferay
	Cloning into 'Aspose_for_Liferay'...
	remote: Counting objects: 97, done.
	remote: Compressing objects: 100% (68/68), done.
	remote: Total 97 (delta 16), reused 73 (delta 10), pack-reused 0
	Unpacking objects: 100% (97/97), done.
	Checking connectivity... done.
	
Step 2. Build and package

    % mvn -U clean package

This will build "liferay-aspose-export-hook-N.N.N.N.war" in the targets tolder.

NOTE: You will require JDK 1.6+ and Maven 3.


## Installation

### Liferay Portal + Apache Tomcat Bundle

eg.

Copy / Deploy "liferay-aspose-export-hook-N.N.N.N.war" to "LIFERAY_HOME/deploy" folder.


## Project Team

* Adeel Ilyas - adeel.ilyas@aspose.com

**Recommended Links**

*   [Aspose Blog on "Aspose Java for Liferay"] (http://youtube.com)

*   [Video tutorial on "Aspose Java for Liferay"](http://youtube.com)

*   [Aspose Confluence Wiki of the plugin](http://support.aspose.dynabic.com/wiki/display/marketplace/6.+Aspose+Java+for+Liferay)

*   [Aspose Java Components](http://www.aspose.com/java/total-component.aspx)

![](http://i.imgur.com/IB3pzFP.jpg)
