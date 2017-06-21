<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<style type="text/css">
    .icon-new-setting {
        background: url('${ctx}/zmock/resources/img/setting.png') no-repeat center center;
    }
</style>
<script type="text/javascript">
    function addCollections() {
        if('${mockDataDir}'==''){
            $.messager.alert('消息','请设置mock的workspace目录!');
        }else if(parseInt('${mockCurrentDataCount}')>=parseInt('${mockDataCount}')){
            $.messager.alert('消息','已达到设定的mock数据阀值，不能再添加!');
        }else{
            $("#addCollection").dialog('open').dialog('refresh','${ctx}/zmock/add');
        }
    }

    function editCollections() {
        var node = $('#collectionsTree').tree("getSelected");
        if(node){
            var collectionName;
            var mockName;
            if(node.attributes){
                collectionName = node.attributes.pnode;
                mockName = node.text;
            }else{
                collectionName = node.text;
                mockName = "";
            }
            $("#editCollection").dialog('open').dialog('refresh','${ctx}/zmock/edit?collectionName='+encodeURIComponent(encodeURIComponent(collectionName))+'&mockName='+encodeURIComponent(encodeURIComponent(mockName)));
        }else{
            $.messager.alert('消息','请选中一个节点!');
        }
    }

    function deleteCollections(){
        var node = $('#collectionsTree').tree("getSelected");
        if(node){
            var collectionName;
            var mockName;
            if(node.attributes){
                collectionName = node.attributes.pnode;
                mockName = node.text;
            }else{
                collectionName = node.text;
                mockName = "";
            }
            $.messager.confirm('删除','你确定要删除?', function(ok){
                if(ok) {
                    $.ajax({
                        url: "${ctx}/zmock/delete",
                        async: false,
                        type:'post',
                        data: {collectionName : collectionName, mockName:mockName},
                        dataType:'json',
                        success:function(data) {
                            if(data.retCode=='200') {
                                refreshCollections();
                            }else{
                                $.messager.alert('警告','删除失败!','error');
                            }
                        }
                    });
                }
            });
        }else{
            $.messager.alert('消息','请选中一个节点!');
        }
    }

    function treeInit() {
        $('#collectionsTree').tree({
            url: '${ctx}/zmock/tree',
            onClick: function(node){
                if(node.attributes){
                    var collectionName = node.attributes.pnode;
                    var mockName = node.text;
                    addTab(node.text, '${ctx}/zmock/content?collectionName='+encodeURIComponent(encodeURIComponent(collectionName))+'&mockName='+encodeURIComponent(encodeURIComponent(mockName)));
                }
            }
        });
    }

</script>
<!-- 菜单栏 -->
<div id="collections" class="easyui-panel" data-options="onOpen:function(){treeInit();}" fit="true" border="false">
    <div style="padding:3px;height:auto" class="datagrid-toolbar">
        <a href="#" class="easyui-linkbutton" onclick="addCollections()" data-options="iconCls:'icon-add',toggle:true,plain:true"></a>
        <a href="#" class="easyui-linkbutton" onclick="editCollections()" data-options="iconCls:'icon-edit',toggle:true,plain:true"></a>
        <c:if test="${mockDataRole==1}">
            <a href="#" class="easyui-linkbutton" onclick="deleteCollections()" data-options="iconCls:'icon-cut',toggle:true,plain:true"></a>
        </c:if>
    </div>
    <ul id="collectionsTree"></ul>
    <c:if test="${setting==1}">
        <div id="nav" class="easyui-accordion" data-options="animate:false,fit:true,border:false" style="margin-top: 10px">
            <div title="平台设置" data-options="animate:false,iconCls:'icon-help',border:false">
                <div style="margin-top:2px;margin-left: 10px">
                    <a href="#"  class="easyui-linkbutton" onclick="addTab('平台设置','${ctx}/zmock/setting')" data-options="iconCls:'icon-new-setting',plain:true">平台设置</a>
                </div>
            </div>
        </div>
    </c:if>
</div>
