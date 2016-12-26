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
	form.action='delete.do';
	form.submit();
	return true;
}
</script>
</head>
<body class="c-body">
<jsp:include page="/WEB-INF/views/commons/show_message.jsp"/>
<div class="c-bar margin-top5">
  <span class="c-position"><s:message code="visitLog.osAnalysis"/></span>
	<span class="c-total">(<s:message code="totalElements" arguments="${fn:length(list)}"/>)</span>
</div>
<fieldset class="c-fieldset">
  <legend><s:message code="search"/></legend>
  <span id="radio">
    <input type="radio" id="radioToday" onclick="location.href='os_analysis.do?period=today';"<c:if test="${period eq 'today'}"> checked="checked"</c:if>/><label for="radioToday"><s:message code="visitLog.trafficAnalysis.today"/></label>
		<input type="radio" id="radioYesterday" onclick="location.href='os_analysis.do?period=yesterday';"<c:if test="${period eq 'yesterday'}"> checked="checked"</c:if>/><label for="radioYesterday"><s:message code="visitLog.trafficAnalysis.yesterday"/></label>
		<input type="radio" id="radioLast7Day" onclick="location.href='os_analysis.do?period=last7Day';"<c:if test="${period eq 'last7Day'}"> checked="checked"</c:if>/><label for="radioLast7Day"><s:message code="visitLog.trafficAnalysis.last7Day"/></label>
		<input type="radio" id="radioLast30Day" onclick="location.href='os_analysis.do?period=last30Day';"<c:if test="${period eq 'last30Day'}"> checked="checked"</c:if>/><label for="radioLast30Day"><s:message code="visitLog.trafficAnalysis.last30Day"/></label>
	</span>
	<script type="text/javascript">
	$("#radio").buttonset();
	</script>
	<form action="os_analysis.do" method="get" style="display:inline;">
	  <label class="c-lab"><s:message code="beginDate"/>: <input type="text" name="begin" value="<fmt:formatDate value='${begin}' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:80px;"/></label>
	  <label class="c-lab"><s:message code="endDate"/>: <input type="text" name="end" value="<fmt:formatDate value='${end}' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" style="width:80px;"/></label>
	  <label class="c-lab"><input type="submit" value="<s:message code="search"/>"/></label>
	</form>
</fieldset>

<div id="chart" style="padding-right:15px;height:300px;"></div>
<script type="text/javascript">
  var chart = echarts.init(document.getElementById('chart'));
  var option = {
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: [
	            <c:forEach var="bean" varStatus="status" items="${list}">
		            <c:choose>
		    					<c:when test="${'UNKNOWN' eq bean[0]}">'<s:message code="visitLog.UNKNOWN"/>'</c:when>
		    					<c:otherwise>'<c:out value="${bean[0]}"/>'</c:otherwise>
		    				</c:choose><c:if test="${!status.last}">,</c:if>
	            </c:forEach>
	        ]
	    },
	    series : [
	        {
	            name: '<s:message code="visitLog.source"/>',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
									<c:forEach var="bean" varStatus="status" items="${list}">
										{value:${bean[1]}, name:'<c:choose><c:when test="${'UNKNOWN' eq bean[0]}"><s:message code="visitLog.UNKNOWN"/></c:when><c:otherwise><c:out value="${bean[0]}"/></c:otherwise></c:choose>'}<c:if test="${!status.last}">,</c:if>
									</c:forEach>
	            ],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
  chart.setOption(option);
</script>
<form action="url_analysis.do" method="post">
<table id="pagedTable" border="0" cellpadding="0" cellspacing="0" class="ls-tb margin-top5">
  <thead>
  <tr class="ls_table_th">
    <th width="30">#</th>
    <th><s:message code="visitLog.os"/></th>
    <th><s:message code="visitLog.pv"/></th>
    <th><s:message code="visitLog.uv"/></th>
    <th><s:message code="visitLog.ip"/></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bean" varStatus="status" items="${list}">
  <tr>
    <td>${status.count}</td>
    <td>
    	<c:choose>
    		<c:when test="${'UNKNOWN' eq bean[0]}"><s:message code="visitLog.UNKNOWN"/></c:when>
    		<c:otherwise><c:out value="${bean[0]}"/></c:otherwise>
    	</c:choose>
    </td>
    <td align="right"><c:out value="${bean[1]}"/></td>
    <td align="right"><c:out value="${bean[2]}"/></td>
    <td align="right"><c:out value="${bean[3]}"/></td>
  </tr>
  </c:forEach>
  </tbody>
</table>
<c:if test="${fn:length(list) le 0}"> 
<div class="ls-norecord margin-top5"><s:message code="recordNotFound"/></div>
</c:if>
</form>
<form action="url_analysis.do" method="get" class="ls-page">
	<f:hidden name="begin" value="${begin}"/>
	<f:hidden name="end" value="${end}"/>
  <%-- <tags:pagination pagedList="${pagedList}"/> --%>
</form>
</body>
</html>