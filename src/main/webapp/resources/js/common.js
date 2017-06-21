/**
 * Created by zhangfei on 2017/6/5/005.
 */

function submitForm(param){
    $("#"+param.formId).form('submit', {
        url : param.url,
        dataType : 'text',
        onSubmit: function(){
            $.messager.progress();
            var isValid = $(this).form('validate');
            if (!isValid){
                $.messager.progress('close');	// hide progress bar while the form is invalid
            }
            return isValid;
        },
        success : function(result) {
            var json = $.parseJSON(result);
            if(json.retCode=='200'){
                //$.messager.alert('提交成功', json.retMsg, 'info');
                if(param.dialogId){
                    $('#'+param.formId).form('clear');
                    $("#"+param.dialogId).dialog('close');
                }
                if(param.fn){
                    eval(param.fn);
                }
            }else{
                $.messager.alert('提交失败', json.retMsg, 'error');
            }
            $.messager.progress('close');
        }
    });
}

function addTab(title, url){
    if ($('#content_tab').tabs('exists', title)){
        $('#content_tab').tabs('select', title);
    } else {
        if(url){
            var content = '<iframe scrolling="no" frameborder="0"  src="' + url + '" style="width:100%;height:100%;margin:0;padding:0;"></iframe>';
        }else{
            var content = '该功能暂未开放';
        }
        $('#content_tab').tabs('add',{
            title:title,
            content: content,
            closable:true
        });
    }
}