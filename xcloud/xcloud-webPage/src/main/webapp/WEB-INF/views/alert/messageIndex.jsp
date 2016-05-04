<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12">
            <div class="page-header">
                <h1>消息管理</h1>
            </div>
        </div>
    </div>

    <div class="row vm-table">
        <div class="col-lg-12 col-md-12 col-sm-12">
            <table id="unMandateVMTableId" class="easyui-datagrid"></table>
            <div id="unMandateVMtb">
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-12">
                       <!--  <label>状态：未发送 &nbsp; 已发送 &nbsp; 发送失败</label> -->
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12">
                        <div class="filter clearfix">
                            <div class="filter-tool">
                                <a role="button" href="#" class="btn btn-default"><i class="fa fa-external-link"></i></a>
                                <a role="button" href="#" class="btn btn-default"><i class="fa fa-cog"></i></a>
                            </div>
                            <div class="filter-search">
                                <input id="mailObjectQuery" class="sugon-searchbox"  style="width:100%; height:32px;"></input>
                                <div id="tableSearch">
                                    <div data-options="name:'name'">消息主题</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="unMandateVMDialogDivId" class="sugon-dialog"></div>
        </div>
    </div>
</div>
<script src="${ctx}/resources/js/alert/message.js"></script>
<script type="text/javascript">
    $(function() {
        cloudmanager.messagePush.init();
    });
</script>