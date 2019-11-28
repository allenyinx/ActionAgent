package com.airta.action.agent.utility.parser;

import com.airta.action.agent.action.raw.fields.RawActionContext;
import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.entity.feature.FormElement;
import com.airta.action.agent.entity.feature.ImageElement;
import com.airta.action.agent.entity.feature.LinkElement;
import com.airta.action.agent.entity.html.ElementType;
import com.airta.action.agent.handler.URLAction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HtmlParser {

    private static final Logger logger = LoggerFactory.getLogger(HtmlParser.class);

    private Document doc = null;

    private Elements pickupSpecifiedRawElements(String cssQueryStr, String pageContent) {

        try {
            doc = Jsoup.parse(pageContent);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return null;
        }
        Elements aLinks = doc.select(cssQueryStr);
        return aLinks;
    }

    public List<com.airta.action.agent.entity.html.Element> parseChildLinkElements(String pageContent, RawActionContext rawActionContext, com.airta.action.agent.entity.html.Element parentElement) {
        List<com.airta.action.agent.entity.html.Element> urlElementList = new ArrayList<>();

        Elements rawElements = pickupSpecifiedRawElements("a[href]", pageContent);
        if (rawElements == null) {
            return Collections.emptyList();
        } else {
            for (int index = 0; index < rawElements.size(); index++) {
                Element rawElement = rawElements.get(index);
                String elementUrlStr = rawElement.attr("href");
                if (!elementUrlStr.contains("http://") && !elementUrlStr.contains("https://")) {
                    if (elementUrlStr.startsWith("/")) {
                        elementUrlStr = elementUrlStr.substring(1);
                    }
                    elementUrlStr = DriverConfig.ENTRY_PAGE + elementUrlStr;
                }

                LinkElement featureElement = new LinkElement();
                featureElement.setUrl(elementUrlStr);
                if (rawElement.hasAttr("id")) {
                    featureElement.setId(rawElement.attr("id"));
                } else {
                    featureElement.setId(rawActionContext.getPagePath() + "_" + ElementType.link.toString() + "_" + index);
                }
                featureElement.setClassName(rawElement.attr("class"));
                if (rawElement.hasAttr("onclick")) {
                    featureElement.setOnClick(rawElement.attr("onclick"));
                }

                attributeCommonElement(featureElement, ElementType.link, rawActionContext, parentElement, index);

                urlElementList.add(featureElement);
            }
        }

        return urlElementList;
    }

    /**
     * @param pageContent target URL
     */
    public List<String> parseChildLinks(String pageContent) {
        List<String> urlList = new ArrayList<>();

        try {
            doc = Jsoup.parse(pageContent);
        } catch (Exception e) {
            // Received failure 404
            logger.error(e.getLocalizedMessage());
            return urlList;
        }
        Elements aLinks = doc.select("a[href]");
        for (Element element : aLinks) {
            String url = element.attr("href");
            if (!URLAction.actionBuilder().isAccessibleURLAddress(url)) {
                continue;
            }
            if (!url.contains("http://") && !url.contains("https://")) {
                if (url.startsWith("/")) {
                    url = url.substring(1);
                }
                url = DriverConfig.ENTRY_PAGE + url;
            }

            if (!URLAction.actionBuilder().isURLSchemaValid(url)) {
                System.out.println("## Invalid URL: " + url);
                continue;
            }

            System.out.println(" >>: " + url);
            urlList.add(url);
        }

        return urlList;
    }

    public List<com.airta.action.agent.entity.html.Element> parseChildImageElements(String pageContent, RawActionContext rawActionContext,
                                                                                    com.airta.action.agent.entity.html.Element parentElement) {
        List<com.airta.action.agent.entity.html.Element> urlElementList = new ArrayList<>();

        Elements rawElements = pickupSpecifiedRawElements("img[src]", pageContent);
        if (rawElements == null) {
            return Collections.emptyList();
        } else {
            for (int index = 0; index < rawElements.size(); index++) {
                Element rawElement = rawElements.get(index);
                String elementUrlStr = rawElement.attr("src");
                if (!elementUrlStr.contains("http://") && !elementUrlStr.contains("https://")) {
                    if (elementUrlStr.startsWith("/")) {
                        elementUrlStr = elementUrlStr.substring(1);
                    }
                    elementUrlStr = parentElement.getUrl() + "/" + elementUrlStr;
                }

                ImageElement featureElement = new ImageElement();
                featureElement.setUrl(elementUrlStr);
                if (rawElement.hasAttr("id")) {
                    featureElement.setId(rawElement.attr("id"));
                } else {
                    featureElement.setId(rawActionContext.getPagePath() + "_" + ElementType.image.toString() + "_" + index);
                }
                featureElement.setClassName(rawElement.attr("class"));
                attributeCommonElement(featureElement, ElementType.image, rawActionContext, parentElement, index);

                urlElementList.add(featureElement);
            }
        }

        return urlElementList;
    }

    private com.airta.action.agent.entity.html.Element attributeCommonElement(com.airta.action.agent.entity.html.Element featureElement, ElementType elementType,
                                                                              RawActionContext rawActionContext, com.airta.action.agent.entity.html.Element parentElement, int index) {

        featureElement.setType(ElementType.image);
        featureElement.setParentId(parentElement.getElementId());
        featureElement.setElementId(parentElement.getParentId() + "_" + featureElement.getId() + "_" + index);
        featureElement.setWorkingOn(false);
        if (rawActionContext != null) {
            featureElement.setPathPath(rawActionContext.getPagePath() + "_" + ElementType.image.toString() + "_" + index);
        }
        return featureElement;
    }

    public List<String> getImageLinks(String pageContent) {
        List<String> imageUrlList = new ArrayList<>();

        try {
            doc = Jsoup.parse(pageContent);
        } catch (Exception e) {
            // Received failure 404
            logger.error(e.getLocalizedMessage());
            return imageUrlList;
        }
        Elements imgLinks = doc.select("img[src]");
        for (Element element : imgLinks) {
            String srcStr = element.attr("src");

            if (!srcStr.contains("http://") && !srcStr.contains("https://")) {//没有这两个头
                srcStr = DriverConfig.ENTRY_PAGE + srcStr;
            }
            System.out.println("## Image link: " + srcStr);
            imageUrlList.add(srcStr);
        }

        return imageUrlList;
    }

    public List<com.airta.action.agent.entity.html.Element> parseChildFormElements(String pageContent, RawActionContext rawActionContext,
                                                                                    com.airta.action.agent.entity.html.Element parentElement) {
        List<com.airta.action.agent.entity.html.Element> formElementList = new ArrayList<>();

        Elements rawElements = pickupSpecifiedRawElements("form[id]", pageContent);
        if (rawElements == null) {
            return Collections.emptyList();
        } else {
            for (int index = 0; index < rawElements.size(); index++) {
                Element rawElement = rawElements.get(index);

                FormElement featureElement = new FormElement();
                if (rawElement.hasAttr("id")) {
                    featureElement.setId(rawElement.attr("id"));
                } else {
                    featureElement.setId(rawActionContext.getPagePath() + "_" + ElementType.form.toString() + "_" + index);
                }
                featureElement.setClassName(rawElement.attr("class"));
                if (rawElement.hasAttr("action")) {
                    featureElement.setAction(rawElement.attr("action"));
                }
                if (rawElement.hasAttr("onsubmit")) {
                    featureElement.setOnSubmit(rawElement.attr("onsubmit"));
                }
                attributeCommonElement(featureElement, ElementType.form, rawActionContext, parentElement, index);

                formElementList.add(featureElement);
            }
        }

        return formElementList;
    }

    public List<String> getForms(String pageContent) {
        List<String> formList = new ArrayList<>();

        try {
            doc = Jsoup.parse(pageContent);
        } catch (Exception e) {
            // Received failure 404
            logger.error(e.getLocalizedMessage());
            return formList;
        }
        Elements imgLinks = doc.select("form[id]");
        for (Element element : imgLinks) {
            String formAction = element.attr("action");
            String formId = element.attr("id");

//            if (!StringUtils.isEmpty(formAction)) {
//                System.out.println("## formAction link: " + formAction);
//                formList.add(formId + "_" + formAction);
//            }
        }

        return formList;
    }

//    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//
//        String response =" <Resultant> " +
//                " <exceptionDetails> " +
//                " <exceptionList> " +
//                " <code> ABC</code> " +
//                " <message> Invalid Value</message> " +
//                " </exceptionList> " +
//                " <exceptionList> " +
//                " <code> ABZ</code> " +
//                " <message> Invalid Structure</message> " +
//                " </exceptionList> " +
//                " </exceptionDetails> " +
//                " <Result> " +
//                " <code> 1234</code> " +
//                " <Details> " +
//                " <Detail> " +
//                " <System type=\"A\"> Admin</System> " +
//                " <Type> full</Type> " +
//                " <Date> 2010-02-08</Date> " +
//                " </Detail> " +
//                " <Detail> " +
//                " <System type=\"B\"> Beneficiary</System> " +
//                " <Type> full</Type> " +
//                " <Date>2015-10-05</Date> " +
//                " </Detail> " +
//                " <Detail> " +
//                " <System type=\"C\"> Customer</System> " +
//                " <Type> Partial</Type> " +
//                " <Date>2010-11-01</Date> " +
//                " </Detail> " +
//                " </Details> " +
//                " </Result> " +
//                "</Resultant> " ;
//
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//        org.w3c.dom.Document doc = dBuilder.parse(new InputSource(new StringReader(response)));
//        ((org.w3c.dom.Document) doc).getDocumentElement().normalize();
//
//        NodeList nList;
//        nList = doc.getElementsByTagName("exceptionList");
//        System.out.println("nList " + nList.getLength());
//        nList = doc.getElementsByTagName("Details");
//        System.out.println("nList " + nList.getLength());
//
//
//    }

}