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
	$("#sendMessageForm").validate();
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
	form.action='message_delete.do';
	form.submit();
	return true;
}
</script>
<style type="text/css">
.form-group{padding:3px;}
.form-label{}
.form-input{}
</style>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="homepage.message"/> - <s:message code="message.contact"/>(<c:out value="${contact.username}"></c:out>)</span>
	<span class="c-total">(<s:message code="totalElements" arguments="${pagedList.totalElements}"/>)</span>
</div>
<form id="sendMessageForm" action="message_send.do" method="post">
	<fieldset class="c-fieldset" style="padding:5px 10px;">
    <%-- <legend><s:message code="message.send"/></legend> --%>
    <input type="hidden" name="receiverUsername" value="<c:out value='${contact.username}'/>"/>
    <input type="hidden" name="contactId" value="<c:out value='${contactId}'/>"/>
    <div class="form-group">
    	<label class="form-label"><s:message code="message.subject"/>:</label>
    	<div class="form-input"><input type="text" name="subject" maxlength="150" style="width:500px;"/></div>
    </div>
    <div class="form-group">
    	<label class="form-label"><em class="required">*</em><s:message code="message.text"/>:</label>
    	<div class="form-input"><textarea name="text" class="required" maxlength="65535" style="width:500px;height:100px;" onkeydown="if(event.ctrlKey&&event.keyCode==13){$('#sendMessageForm').submit();}"></textarea></div>
    </div>
    <div class="form-group">
    	<input type="submit" value="<s:message code='message.send'/>"/>
    </div>
  </fieldset>
</form>
<form method="post">
<input type="hidden" name="contactId" value="${contactId}"/>
<tags:search_params/>
<div class="ls-bc-opt">
	<shiro:hasPermission name="core:homepage:message:delete">
	<div class="ls-btn"><input type="button" value="<s:message code="delete"/>" onclick="return optDelete(this.form);"/></div>
	</shiro:hasPermission>
	<div class="in-btn"></div>
	<div class="in-btn"><input type="button" value="<s:message code="refresh"/>" onclick="location.reload();"/></div>
	<div class="in-btn"></div>
	<div class="in-btn"><input type="button" value="<s:message code="return"/>" onclick="location.href='message_list.do?${searchstring}';"/></div>
	<div style="clear:both"></div>
</div>
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead>
  <tr class="ls_table_th">
    <th width="25"><input type="checkbox" onclick="Cms.check('ids',this.checked);"/></th>
    <th width="50"><s:message code="operate"/></th>
    <th width="30">ID</th>
    <th width="130"><s:message code="message.sender"/></th>
    <th><s:message code="message.content"/></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${pagedList.content}">
  <tr beanid="${bean.id}">
    <td><input type="checkbox" name="ids" value="${bean.id}"/></td>
    <td align="center">
    	<shiro:hasPermission name="core:homepage:message:delete">
      <a href="message_delete.do?ids=${bean.id}&contactId=${contactId}&${searchstring}" onclick="return confirmDelete();" class="ls-opt"><s:message code="delete"/></a>
      </shiro:hasPermission>
     </td>
    <td><c:out value="${bean.id}"/></td>
    <td>
    	<div><c:out value="${bean.sender.username}"/></div>
    	<div><fmt:formatDate value="${bean.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
    </td>
    <td>
    	<div style="font-weight:bold;"><c:out value="${bean.subject}"/></div>
    	<div>${fnx:bbcode(bean.text)}</div>
    </td>
  </tr>
  </c:forEach>
  </tbody>
</table>
<c:if test="${fn:length(pagedList.content) le 0}"> 
<div class="ls-norecord margin-top5"><s:message code="recordNotFound"/></div>
</c:if>
</form>
<form action="message_show.do" method="get" class="ls-page">
	<input type="hidden" name="contactId" value="${contactId}"/>
	<tags:search_params excludePage="true"/>
  <tags:pagination pagedList="${pagedList}"/>
</form>
</body>
</html>