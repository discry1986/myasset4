$(function () {

    /**
     * 初始化表格，加载数据
     */
    $("#ProjectTable").bootstrapTable({
        method: "get",
        url: 'projectListJson',
        toolbar: '#ProTbar',
        showRefresh: true,//刷新按钮
        showColumns: true,
        showToggle: true,
        contentType: "application/x-www-form-urlencoded",
        striped: true,
        dataField: "content",
        sidePagination: "server",
        pageNumber: 1, //初始化加载第一页，默认第一页
        pageSize: 5,//单页记录数
        pagination: true,//是否分页
        totalField: "totalElements",
        // queryParamsType: '',
        //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort
        //设置为 ''  在这种情况下传给服务器的参数为：pageSize,pageNumber
        columns: [
            {checkbox: true, title: '全选'},
            {field: 'id', title: 'ID'},
            {field: 'projectType.id', visible:false},
            {field: 'projectType.name', title: '项目类型'},
            {field: 'number', title: '编号'},
            {field: 'name', title: '项目名'},
            {
                field: 'createdate', title: '最后更新日期',
                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
                }
            },
            {
                field: 'fstaus', title: '状态',
                formatter: operateFormatter,
                events: 'statusEvents'
            }
        ],
        pagination: true,
        queryParams: function (params) {
            return {
                pageNumber: this.pageNumber,
                pageSize: this.pageSize
                // DeptName: $("#txt_search_departmentname").val()
            };
        },
        sortable: true,
        sortName: 'projectType.id', // 设置默认排序为 name
        sortOrder: 'asc', // 设置排序为反序 desc
        search: true,
        strictSearch: true,
        searchOnEnterKey: true
    });

    $('#ProjectTable').bootstrapTable('hideColumn', 'id');
    // $('#projectTable').bootstrapTable('hideColumn','projectType.id');

    /**
     * 状态列重新显示
     */
    function operateFormatter(value, row, index) {
        var html;
        if (value == 'NORMAL')
            html = '<button class="status btn btn-success btn-xs"><span class="glyphicon glyphicon-ok">已启用</span></button>'
        if (value == 'DISABLED')
            html = '<button class="status btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove">已禁用</span></button>'
        return html;
    }

    /**
     * 绑定.status的click事件
     */
    window.statusEvents = {
        'click .status': function (e, value, row, index) {
            // alert('You click like action, row: ' + JSON.stringify(row));
            var tipText = "禁用或启用";
            var status = 1;
            if (row.fstaus == "NORMAL") {
                tipText = "禁用";
                status = 2;
            }
            if (row.fstaus == "DISABLED") {
                tipText = "启用";
                status = 1;
            }
            updateProjectStatus(row.id, tipText, status);
        }
    }

    /**
     * 刷新项目类明细表内容
     */
    function refreshProjectTable() {
        $('#ProjectTable').bootstrapTable('refresh', {url: 'projectListJson'});
    }

    /**
     * 更新Project状态
     * */
    function updateProjectStatus(id, tipText, status) {
        swal({
            title: '确认信息',
            text: "要" + tipText + "选中的项目吗?",
            type: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#DD6B55',
            confirmButtonText: '是的，' + tipText + '它',
            cancelButtonText: '取消'
        }).then(function (isConfirm) {
            if (isConfirm.value) {
                $.ajax({
                    type: "post",
                    url: "updateProStatus",
                    data: JSON.stringify({"status": status, "id": id}),
                    dataType: 'json',
                    contentType:'application/json;charset=utf-8',
                    success: function (data, status) {
                        if (status == "success") {
                            toastr.success('操作成功');
                            refreshProjectTable();
                        }
                    }, error: function () {
                        toastr.error('操作失败');
                        refreshProjectTable();
                    }, complete: function () {

                    }
                });
            }
        });
    };

    /**
     * 新增项目明细初始化界面
     */
    $('#btn_add_pro').click(function (e) {
        // $('#id').val(null);
        $("#add_edit_pro_Modal").modal('show');
    });

    $("#proTypeSelect").selectpicker({
        noneSelectedText: '请选择'
    });

    $('#proTypeSelect').on('shown.bs.select', function () {
        var select = $("#proTypeSelect");

        if (select.find("option").length > 0)
            return false;

        select.empty();
        loadProType(select,'');
    });


    function loadProType(select,val) {
        $.ajax({
            type: "get",
            url: "ptlistJson",
            dataType: 'json',
            contentType:'application/json;charset=utf-8',
            beforeSend: function () {

            },
            success: function (datas) {//返回list数据并循环获取
                for (var i = 0; i < datas.length; i++) {
                    select.append("<option value='" + datas[i].id + "'>"
                        + datas[i].name + "</option>");
                }
                $('.selectpicker').selectpicker('val', val);
                $('.selectpicker').selectpicker('refresh');
            }
        })
    }

    /**
     *  PType绑定验证器
     * */
    $('#proForm').bootstrapValidator({
        message: '输入值无效！',
        feedbackIcons: {
            /*input状态样式图片*/
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            v_pro_number: {
                validators: {
                    notEmpty: {
                        message: '项目编码不能为空'
                    }
                }
            },
            v_pro_name: {
                validators: {
                    notEmpty: {
                        message: '项目名称不能为空'
                    }
                }
            },
            v_proType: {
                validators: {
                    notEmpty: {
                        message: '项目类型不能为空'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        //提交逻辑
        $.ajax({
            type: "post",
            url: "addPro",
            data: JSON.stringify({
                "id": $('#pro_id').val(),
                "number": $('#pro_number').val(),
                "name": $('#pro_name').val(),
                "projectType": {'id': $('#proTypeSelect').val()}
            }),
            dataType: 'json',
            contentType:'application/json;charset=utf-8',
            beforeSend: function () {

            },
            success: function (data, status) {
                // console.log("---------->"+data);
                if (status == "success") {
                    toastr.success('提交数据成功');
                    refreshProjectTable();
                }
            }, error: function () {
                toastr.error('保存失败');
            }, complete: function () {
                $('#add_edit_pro_Modal').modal('hide');
            }
        });
    });

    /**
     * 在hide 方法调用时立即触发
     * 清除验证信息
     * */
    $('#add_edit_pro_Modal').on('hide.bs.modal', function () {
        $('#proForm').data("bootstrapValidator").resetForm(true);
    });

    /**
     * 编辑Modal界面初始化
     */
    $('#btn_edit_pro').click(function (e) {
        var a = $('#ProjectTable').bootstrapTable('getSelections');
        if (a.length != 1) {
            toastr.warning("请选择一条记录");
        } else {
            $('#add_edit_pro_Modal').modal('show');
            $('#pro_id').val(a[0].id);
            $('#pro_number').val(a[0].number);
            $('#pro_name').val(a[0].name);
            var select = $('#proTypeSelect');
            if (select.find("option").length == 0)
                loadProType(select,a[0].projectType.id);
            else
                select.selectpicker('val', a[0].projectType.id);
        }
    });
});