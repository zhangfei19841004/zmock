<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/jsp/common/meta.jsp" %>
    <%@ include file="/WEB-INF/jsp/common/resource.jspf" %>
    <title>zmock平台</title>
    <style type="text/css">
        .icon-new-add {
            background: url('${ctx}/zmock/resources/img/new-add.png') no-repeat center center;
        }

        .icon-new-delete {
            background: url('${ctx}/zmock/resources/img/new-delete.png') no-repeat center center;
        }

        .icon-new-up {
            background: url('${ctx}/zmock/resources/img/new-up.png') no-repeat center center;
        }

        .icon-new-down {
            background: url('${ctx}/zmock/resources/img/new-down.png') no-repeat center center;
        }

        .icon-new-save {
            background: url('${ctx}/zmock/resources/img/new-save.png') no-repeat center center;
        }

        .scripts {
            position: relative;
            border: 1px solid #D4D4D4;
            display: inline-block;
            vertical-align: middle;
            white-space: pre-wrap;
            margin: 0;
            padding: 0;
            -moz-border-radius: 5px 5px 5px 5px;
            -webkit-border-radius: 5px 5px 5px 5px;
            border-radius: 5px 5px 5px 5px;
            font-size: 13px;
            outline: none;
        }
    </style>
    <script type="text/javascript">

        function escapeHtml(string) {
            var entityMap = {
                "&": "&amp;",
                "<": "&lt;",
                ">": "&gt;",
                '"': '&quot;',
                "'": '&#39;',
                "/": '&#x2F;'
            };
            return String(string).replace(/[&<>"'\/]/g, function (s) {
                return entityMap[s];
            });
        }

        function formatJSON() {
            var v = JSON.stringify(JSON.parse($("#requestParamTemplate").textbox('getValue')), null, 4);
            $("#requestParamTemplate").textbox('setValue', v);
        }

        var action = {
            mockRule: function (value, row, index) {
                return "<input class='inputcls' id='mockRule_" + index + "' name='resCondition' style='margin: 3px;width: 100%' value='" + escapeHtml(value) + "'>";
            },
            mockRes: function (value, row, index) {
                return "<input class='inputcls1' id='mockRes_" + index + "' name='resValue' style='margin: 3px;width: 100%' value='" + escapeHtml(value) + "'>";
            }
        }

        $(function () {
            $('#mockRuleList').datagrid({
                onLoadSuccess: function (data) {
                    initialInput();
                },
                onClickRow: function (rowIndex, rowData) {

                }
            })

            $("#requestParamTemplate").textbox('setValue', '${rules.requestParamTemplate}');

            var methods = '${rules.requestMethod}';
            if (methods) {
                $("#requestMethod").combobox('setValues', methods.split(','));
            }

        });

        function initialInput() {
            $('.inputcls').textbox({multiline: true, height: 108, required: true});
            $('.inputcls1').textbox({
                multiline: true, height: 108, iconWidth: 22,
                icons: [{
                    iconCls: 'icon-search',
                    handler: function (e) {
                        var v = JSON.stringify(JSON.parse($(e.data.target).textbox('getValue')), null, 4);
                        $(e.data.target).textbox('setValue', v);
                    }
                }]
            });
        }

        function addRow(gridname) {
            $("#" + gridname).datagrid("insertRow", {
                row: {
                    mockRule: "",
                    mockRes: ""
                }
            });
            initialInput();
        }

        function deleteRow(gridname) {
            var row = $("#" + gridname).datagrid('getSelected');
            if (row) {
                var index = $("#" + gridname).datagrid('getRowIndex', row);
                $("#" + gridname).datagrid('deleteRow', index);
            }
        }

        function moveUp(gridname) {
            var row = $("#" + gridname).datagrid('getSelected');
            if (row) {
                var index = $("#" + gridname).datagrid('getRowIndex', row);
                mysort(index, 'up', gridname);
            }
        }
        //下移
        function moveDown(gridname) {
            var row = $("#" + gridname).datagrid('getSelected');
            if (row) {
                var index = $("#" + gridname).datagrid('getRowIndex', row);
                mysort(index, 'down', gridname);
            }
        }

        function mysort(index, type, gridname) {
            if ("up" == type) {
                if (index != 0) {
                    var toup = getRowInfo(gridname, index);
                    var todown = getRowInfo(gridname, index - 1);
                    $('#' + gridname).datagrid('getRows')[index] = todown;
                    $('#' + gridname).datagrid('getRows')[index - 1] = toup;
                    $('#' + gridname).datagrid('refreshRow', index);
                    $('#' + gridname).datagrid('refreshRow', index - 1);
                    $('#' + gridname).datagrid('selectRow', index - 1);
                    initialInput();
                }
            } else if ("down" == type) {
                var rows = $('#' + gridname).datagrid('getRows').length;
                if (index != rows - 1) {
                    var todown = getRowInfo(gridname, index);
                    ;
                    var toup = getRowInfo(gridname, index + 1);
                    $('#' + gridname).datagrid('getRows')[index + 1] = todown;
                    $('#' + gridname).datagrid('getRows')[index] = toup;
                    $('#' + gridname).datagrid('refreshRow', index);
                    $('#' + gridname).datagrid('refreshRow', index + 1);
                    $('#' + gridname).datagrid('selectRow', index + 1);
                    initialInput();
                }
            }
        }

        function getRowInfo(gridname, index) {
            var fields = $('#' + gridname).datagrid('getColumnFields');
            var fieldsInfo = {};
            for (var i = 0; i < fields.length; i++) {
                fieldsInfo[fields[i]] = $("#" + fields[i] + "_" + index).textbox("getValue");
            }
            return fieldsInfo
        }

        function submitMockForm() {
            submitForm({
                'formId': 'mockForm',
                'url': '${ctx}/zmock/save',
                'fn': '$.messager.alert("保存", json.retMsg, "info")'
            });
        }

    </script>
</head>
<body>
<div id="mockLayout" class="easyui-layout" fit="true" style="margin: 5px;background:#f5f9fc">
    <form id="mockForm" name="updateForm" method="post">
        <div data-options="region:'west'" border="false" style="width:50%;background:#f5f9fc">
            <div class="easyui-layout" fit="true" style="margin: 0px;background:#f5f9fc">
                <div data-options="region:'north',split:true,collapsible:false" title="mock请求信息"
                     style="width:100%;height:50%;background:#f5f9fc;padding: 2px">
                    <table cellpadding="0" cellspacing="0" class="tab_info">
                        <tr>
                            <td class="inquire_item">mock请求URL:</td>
                            <td class="inquire_form">
                                <input class="easyui-textbox"
                                       data-options="prompt:'Enter something here...',required:true" style="width: 50%"
                                       name="requestUrl" value="${rules.requestUrl}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="inquire_item">mock请求方法:</td>
                            <td class="inquire_form">
                                <select class="easyui-combobox" data-options="multiple:true" name="requestMethod"
                                        style="width: 50%" id="requestMethod">
                                    <option value="POST">POST</option>
                                    <option value="GET">GET</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="inquire_item">mock请求参数模板:</td>
                            <td class="inquire_form">
                                <input class="easyui-textbox" style="width: 50%" data-options="multiline:true,height:108,required:true,iconWidth: 22,
										icons:[{
											iconCls:'icon-search',
											handler: function(e){
												formatJSON();
											}
										}]"
                                       name="requestParamTemplate" id="requestParamTemplate"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <div data-options="region:'south',collapsible:false" title="mock脚本及说明"
                     style="width:100%;height:50%;background:#f5f9fc;padding: 2px">
                    <div class="easyui-tabs" id="responseTab"
                         data-options="tabWidth:112,selected:0,plain:true,border:false"
                         style="width: 100%;height: 100%">
                        <div title="mock说明" style="padding:5px">
                            <div style="color:#F00;vertical-align:top;margin-left: 10px;width: 95%">
                                <div>mock请求参数模板说明:</div>
                                <div>1. .*表示请求参数为任意字符串，用于POST BODY</div>
                                <div>2. 如username=&password=表示POST表单提交或GET请求</div>
                                <div>3. 如{"username":"","password":""}表示POST BODY为JSON串</div>
                                <p/>
                                <div>mock脚本说明:</div>
                                <div>1. 脚本采用jexl表达示，具体请参考:<a target="_blank" href="http://commons.apache.org/proper/commons-jexl/">jexl官网</a></div>
                                <div>2. 内置对象有:headers,params,response</div>
                            </div>
                        </div>
                        <div title="请求脚本" style="padding:5px">
                            <textarea class="scripts" id="requestScript" name="requestScript" spellcheck="false"
                                      style="height:100%;width:100%;">${rules.requestScript}</textarea>
                        </div>

                        <div title="响应脚本" style="padding:5px">
                            <textarea class="scripts" id="responseScript" name="responseScript" spellcheck="false"
                                      style="height:100%;width:100%;">${rules.responseScript}</textarea>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <div data-options="region:'east',split:true,collapsible:false" title="mock规则定义"
             style="width:50%;background:#f5f9fc;padding: 2px">
            <table id="mockRuleList" class="easyui-datagrid" toolbar="#mockRuleListTool" fit="true" fitColumns="true"
                   data-options="singleSelect:true,nowrap:false,
							   url:'${ctx}/zmock/rules',
							   queryParams:{collectionName:'${collectionName}',mockName:'${mockName}'},
							   method:'post'">
                <thead>
                <tr>
                    <th data-options="field:'mockRule', formatter:action.mockRule" width="50%">规则</th>
                    <th data-options="field:'mockRes', formatter:action.mockRes" width="50%">响应</th>
                </tr>
                </thead>
            </table>
            <div id="mockRuleListTool" class="datagrid-toolbar" style="padding:2px">
                <a class="easyui-linkbutton" iconCls="icon-new-add" onclick="addRow('mockRuleList')">新增</a>
                <a class="easyui-linkbutton" iconCls="icon-new-delete" onclick="deleteRow('mockRuleList')">删除</a>
                <a class="easyui-linkbutton" iconCls="icon-new-up" onclick="moveUp('mockRuleList')">上移</a>
                <a class="easyui-linkbutton" iconCls="icon-new-down" onclick="moveDown('mockRuleList')">下移</a>
            </div>
        </div>
        <input type="hidden" name="collectionName" value="${collectionName}">
        <input type="hidden" name="mockName" value="${mockName}">
        <div data-options="region:'south',split:true" style="height:40px;">
            <a class="easyui-linkbutton" plain="true" iconCls="icon-new-save" onclick="submitMockForm()">保存</a>
        </div>
    </form>
</div>
</body>
</html>