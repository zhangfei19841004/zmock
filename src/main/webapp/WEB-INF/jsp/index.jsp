<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title>zmock</title>
    <link href="css/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="css/datatables/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="css/common.css" rel="stylesheet">
    <script src="js/jquery/jquery-3.2.1.min.js"></script>
    <script src="js/datatables/jquery.dataTables.min.js"></script>
    <script src="js/datatables/dataTables.bootstrap.min.js"></script>
    <script src="js/bootstrap/bootstrap.js"></script>
</head>
<body>
<div class="panel">
    <div class=" panel-heading"></div>
    <table id="caseInfo" class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>序号</th>
            <th>请求路径</th>
            <th>请求方法</th>
            <th>请求参数模板</th>
            <th>模拟返回</th>
            <th>操作</th>
        </tr>
        </thead>
    </table>
</div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content modal-popup">
            <a href="#" class="close-link"><i class="icon_close_alt2"></i></a>
            <h3 class="white">添加mock接口</h3>
            <form id="addCaseForm" class="popup-form" onsubmit="return addcase()">
                <input name="username" type="text" class="form-control form-white" placeholder="json">
                <h6 id="addCaseInfo" class="text-danger"></h6>
                <button type="submit" class="btn btn-submit" id="addcasebutton">添加</button>
            </form>
        </div>
    </div>
</div>


<script>
    function addcase() {

        $.ajax({
            type: "POST",
            url: "addmockcase",
            contentType: "application/json",
            data: $("#addCaseForm").serializeArray()[0].value,
            success: function () {
                $(".modal.fade.in").modal('hide');
                window.location.href = "";
            },
            error: function () {
                alert(arguments[1]);
            }
        });
        return false;
    }

    function delcase(id) {

        $.ajax({
            type: "POST",
            url: "delmockcase",
            data: "id=" + id,
            success: function () {
                window.location.href = "";
            },
            error: function () {
                alert(arguments[1]);
            }
        });
        return false;
    }

    $(function () {


        var operate = function (json) {
            //更改请求结果数据结构
            $.each(json, function (i, re) {
                re.responseStr = JSON.stringify(re.responseCondition);
                re.operate = "<a href='javascript:void(0)' onclick='delcase(" + re.id + ")'><li class='glyphicon glyphicon-trash' title='删除'></li></a>"


            });
            return json;
        }

        // getTasks();
        var dataTableLanguage = {
            "lengthMenu": "每页显示 _MENU_ 条记录",
            "zeroRecords": " 无数据",
            "info": "显示第 _PAGE_ 页  共 _PAGES_ 页",
            "infoEmpty": "没有获取到数据",
            "infoFiltered": "(从共 _MAX_ 条数记录中搜索)",
            "search": "搜索: ",

            "paginate": {
                "first": "第一页",
                "last": "最后一页",
                "next": "下页",
                "previous": "前页"
            },
            "loadingRecords": "载入中..."

        };

        //初始化任务列表样式
        var TasksTable = $('#caseInfo').DataTable({
            //请求
            ajax: {
                url: 'getmockcase',
                "dataSrc": function (json) {

                    return operate(json);
                }
            },
            //设置每列显示的数据
            columns: [
                {data: 'id'},
                {data: 'requestUrl'},
                {data: 'requestMethod'},
                {data: 'requestParamTemplate'},
                {data: 'responseStr'},
                {data: 'operate'}
            ],
            //样式定义
            "columnDefs": [
                // { "width": "10%", "targets": 0},
                // { "width": "10%", "targets": 2 },
                // {"width": "10%", "targets": 5 }
            ],
            "aaSorting": [[0, 'asc']]
            ,
            "scrollCollapse": true,
            "bLengthChange": false,
            "dom": 'l<"toolbar">frtip',
            //语言设置
            "language": dataTableLanguage
        });
        $(" div.toolbar").css("float", "left");
        $(" div.toolbar").html('<button type="button" class="btn btn-info btn-circle btn-lg" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-plus"></i></button>');


    })
</script>
</body>
</html>

