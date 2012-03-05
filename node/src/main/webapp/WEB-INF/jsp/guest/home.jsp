<%@ page import="woko.facets.builtin.WokoFacets" %>
<%@ page import="com.rvkb.rfs.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/woko/jsp/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<w:facet facetName="<%=WokoFacets.layout%>" />
<w:facet targetObject="${o}" facetName="<%=WokoFacets.renderTitle%>"/>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<fmt:message var="pageTitle" key="application.home.title"/>
<s:layout-render name="${layout.layoutPath}" layout="${layout}" pageTitle="${pageTitle}">
    <s:layout-component name="body">
        <script type="text/javascript">
            dojo.require("rfs.Transfers");
            dojo.require("rfs.Buddies");
        </script>
        <div class="row-fluid">
            <div class="span12">
                <h1 class="page-header">Admin interface</h1>
                <div class="row-fluid">
                    <div class="span3">
                        <h2>
                            Configuration
                        </h2>
                        <div class="folder">
                            <a href="file://${home.config.baseDir}">
                                <c:out value="${home.config.baseDir}"/>
                            </a>
                        </div>
                        <p>
                            <w:url facetName="edit" object="${home.config}" var="editCfgUrl"/>
                            <a href="${editCfgUrl}" class="btn">Edit configuration</a>
                        </p>

                        <h2>
                            Buddies
                        </h2>
                        <div id="buddies"></div>
                        <c:choose>
                            <c:when test="${not empty home.buddies}">
                                <div data-dojo-type="rfs.Buddies" data-dojo-props="baseUrl:'${cp}'"></div>
                            </c:when>
                            <c:otherwise>
                                <p>
                                    You haven't got no buddies.
                                </p>
                            </c:otherwise>
                        </c:choose>
                        <w:url facetName="save" targetObjectClass="<%=User.class%>" var="addBuddyUrl"/>
                        <a href="${addBuddyUrl}" class="btn">Add buddy</a>
                    </div>
                    <div class="span6">
                        <c:set var="files" value="${home.files}"/>
                        <h2>Shared folder contents <small>${fn:length(files)} file(s)</small></h2>
                        <c:choose>
                            <c:when test="${not empty files}">
                                <ul id="files">
                                    <c:forEach items="${home.files}" var="file">
                                        <li>
                                            <a href="${cp}/download/File${file.path}">${file.path}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <p>
                                    No files found in shared folder.
                                </p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="span3">
                        <h2>Network activity</h2>
                        <div data-dojo-type="rfs.Transfers" data-dojo-props="baseUrl:'${cp}'"></div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>