<%@ page import="woko.facets.builtin.WokoFacets" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/woko/jsp/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<w:facet facetName="<%=WokoFacets.layout%>" />
<w:facet targetObject="${o}" facetName="<%=WokoFacets.renderTitle%>"/>

<fmt:message var="pageTitle" key="application.home.title"/>
<s:layout-render name="${layout.layoutPath}" layout="${layout}" pageTitle="${pageTitle}">
    <s:layout-component name="body">
        <div class="row-fluid">
            <div class="span12">
                <h1 class="page-header">Admin interface</h1>
                <div class="row-fluid">
                    <div class="span3">
                        <h2>
                            Configuration
                        </h2>
                        <p>
                            Shared folder :
                        </p>
                        <ul>
                            <li>${home.config.baseDir}</li>
                        </ul>
                        <p>
                            <w:url facetName="edit" object="${home.config}" var="editCfgUrl"/>
                            <a href="${editCfgUrl}" class="btn">Edit configuration</a>
                        </p>

                        <hr/>

                        <h2>
                            Buddies
                        </h2>
                            <c:choose>
                                <c:when test="${not empty home.buddies}">
                                    <ul>
                                        <c:forEach items="${home.buddies}" var="buddy">
                                            <li>
                                                <w:url object="${buddy}" var="buddyUrl"/>
                                                <a href="${buddyUrl}">${buddy.name}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <p>
                                        You haven't got no buddies.
                                    </p>
                                </c:otherwise>
                            </c:choose>
                        <a href="#" class="btn">Manage buddies</a>
                    </div>
                    <div class="span9">
                        <h2>Shared folder contents <small>${fn:length(home.files)} file(s)</small></h2>
                        <ul id="files">
                        <c:forEach items="${home.files}" var="file">
                            <li>
                                <a href="${pageContext.request.contextPath}/download/File${file.path}">${file.path}</a>
                            </li>
                        </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>