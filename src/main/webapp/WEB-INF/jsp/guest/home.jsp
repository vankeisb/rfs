<%@ page import="woko.facets.builtin.WokoFacets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/woko/jsp/taglibs.jsp"%>

<w:facet facetName="<%=WokoFacets.layout%>" />
<w:facet targetObject="${o}" facetName="<%=WokoFacets.renderTitle%>"/>

<fmt:message bundle="${wokoBundle}" var="pageTitle" key="application.home.title"/>
<s:layout-render name="${layout.layoutPath}" layout="${layout}" pageTitle="${pageTitle}">
    <s:layout-component name="body">
        <div class="row-fluid">
            <div class="span12">
                <h1 class="page-header">Admin interface</h1>
                <p>
                    Blah blah
                </p>
                <div class="row-fluid">
                    <div class="span8">
                        <h2>Shared folder contents</h2>
                        <ul id="files">
                        <c:forEach items="${home.files}" var="file">
                            <li>
                                <w:url facetName="download" object="${file}" var="dlUrl"/>
                                <a href="${dlUrl}">${file.path}</a>
                            </li>
                        </c:forEach>
                        </ul>
                    </div>
                    <div class="span4">
                        <h2>
                            Configuration
                        </h2>
                        <p>
                            Shared folder : ${home.config.baseDir}
                        </p>
                        <p>
                            <w:url facetName="edit" object="${home.config}" var="editCfgUrl"/>
                            <a href="${editCfgUrl}">Edit config</a>
                        </p>
                        <h2>
                            Buddies
                        </h2>

                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>