<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fnx" uri="http://java.sun.com/jsp/jstl/functionsx"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.jspxcms.com/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Jspxcms管理平台 - Powered by Jspxcms</title>
<jsp:include page="/WEB-INF/views/commons/head.jsp"></jsp:include>
<script type="text/javascript">
$(function() {
	$("#pagedTable").tableHighlight();
	$("#sortHead").headSort();
	<shiro:hasPermission name="core:homepage:message:show">
	$("#pagedTable tbody tr").dblclick(function(eventObj) {
		var nodeName = eventObj.target.nodeName.toLowerCase();
		if(nodeName!="input"&&nodeName!="select"&&nodeName!="textarea") {
			location.href=$("#show_opt_"+$(this).attr("beanid")).attr('href');
		}
	});
	</shiro:hasPermission>
});
function confirmDelete() {
	return confirm("<s:message code='confirmDelete'/>");
}
function optSingle(opt) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(Cms.checkeds("ids")>1) {
		alert("<s:message code='pleaseSelectOne'/>");
		return false;
	}
	var id = $("input[name='ids']:checkbox:checked").val();
	location.href=$(opt+id).attr("href");
}
function optMulti(form, action, msg) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(msg && !confirm(msg)) {
		return false;
	}
	form.action=action;
	form.submit();
	return true;
}
function optDelete(form) {
	if(Cms.checkeds("ids")==0) {
		alert("<s:message code='pleaseSelectRecord'/>");
		return false;
	}
	if(!confirmDelete()) {
		return false;
	}
	form.action='message_delete_by_contact.do';
	form.submit();
	return true;
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="homepage.message"/> - <s:message code="list"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${pagedList.totalElements}"/>)</span>
</div>
<form method="post">
<tags:search_params/>
<div class="ls-bc-opt">
	<shiro:hasPermission name="core:homepage:message:send:form">
	<div class="ls-btn"><input type="button" value="<s:message code="message.send"/>" onclick="$('#sendMessageDialog').dialog('open');"/></div>
	</shiro:hasPermission>
	<div class="ls-btn"></div>
	<shiro:hasPermission name="core:homepage:message:delete">
	<div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
	</shiro:hasPermission>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead id="sortHead" pagesort="<c:out value='${page_sort[0]}' />" pagedir="${page_sort_dir[0]}" pageurl="message_list.do?page_sort={0}&page_sort_dir={1}&${searchstringnosort}">
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="70"><s:message code="operate"/></th>
    <th width="30" class="ls-th-sort"><span class="ls-sort" pagesort="id">ID</span></th>
    <th width="120"><s:message code="message.contact"/></th>
    <th><s:message code="message.title"/></th>
    <th width="130" class="ls-th-sort"><span class="ls-sort" pagesort="send_time_"><s:message code="message.sendTime"/></span></th>
    <th width="70" class="ls-th-sort"><span class="ls-sort" pagesort="number_of_messages_"><s:message code="message.numberOfMessages"/></span></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${pagedList.content}">
  <tr beanid="${bean[0].id}">
    <td><input type="checkbox" name="ids" value="${bean[0].id}"/></td>
    <td align="center">
    	<shiro:hasPermission name="core:homepage:message:show">
      <a id="show_opt_${bean[0].id}" href="message_show.do?contactId=${bean[3]}" class="ls-opt"><s:message code="show"/></a>
      </shiro:hasPermission>
    	<shiro:hasPermission name="core:homepage:message:delete">
      <a href="message_delete_by_contact.do?ids=${bean[0].id}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
      </shiro:hasPermission>
     </td>
    <td><c:out value="${bean[0].id}"/></td>
    <td>
    	<c:choose>
    		<c:when test="${bean[3] ge 0}"><c:out value="${bean[1].username}"/></c:when>
    		<c:otherwise><s:message code="message.systemMessage"/></c:otherwise>
    	</c:choose>
    	<c:if test="${bean[7] gt 0}"><span style="padding:2px 5px;background-color:red;color:#fff;">${bean[7]}</span></c:if>
    </td>
    <td><c:out value="${fnx:substringx_sis(bean[0].title,30,'...')}"/></td>
    <td align="center"><fmt:formatDate value="${bean[0].sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    <td align="right"><c:out value="${bean[6]}"/></td>
  </tr>
  </c:forEach>
  </tbody>
</table>
<c:if test="${fn:length(pagedList.content) le 0}"> 
<div class="ls-norecord margin-top5"><s:message code="recordNotFound"/></div>
</c:if>
</form>
<form action="message_list.do" method="get" class="ls-page">
	<tags:search_params excludePage="true"/>
  <tags:pagination pagedList="${pagedList}"/>
</form>
<jsp:include page="/WEB-INF/views/core/homepage/message_send.jsp"></jsp:include>
</body>
</html>