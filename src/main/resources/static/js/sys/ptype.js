$(function () {
    /**
     * 初始化表格，加载数据
     */
    $('#PTypeTable').bootstrapTable({
        method: "get",
        url: 'ptlistJson',
        toolbar: '#PTypeTbar',
        toolbarAlign: 'left',//工具栏对齐方式
        showRefresh: true,//刷新按钮
        showColumns: true,
        showToggle: true,
        striped: true,
        columns: [
            {checkbox: true},
            {field: 'id', title: 'ID'},
            {
                field: 'number', title: '编号',
                editable: {
                    type: 'text',
                    clear: false,
                    validate: function (value) { //字段验证
                        if (!$.trim(value)) {
                            return '不能为空';
                        }
                    }
                }
            },
            {
                field: 'name', title: '项目类型',
                editable: {
                    type: 'text',
                    clear: false,
                    validate: function (value) { //字段验证
                        if (!$.trim(value)) {
                            return '不能为空';
                        }
                    }
                }
            },
            {
                field: 'createdate', title: '最后更新日期',
                formatter: function (value, row, index) {
                    return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
                }
            }
        ],
        idField: 'id',
        onEditableHidden: function (field, row, $el, reason) {
            if (reason === 'save') {
                var $td = $el.closest('tr').children();
                $td.eq(-1).html(new Date().Format("yyyy-MM-dd HH:mm:ss"));
                //TODO
            } else if (reason === 'nochange') {
                // $el.closest('tr').next().find('.editable').editable('show');
                //TODO
            }
        }, onEditableSave: function (field, row, oldValue, $el) {
            $('#PTypeTable').bootstrapTable('resetView');
            $.ajax({
                type: "post",
                url: "updatePt",
                data: JSON.stringify(row),
                dataType: 'JSON',
                contentType: 'application/json;charset=UTF-8',
                success: function (data, status) {
                    if (status == "success") {
                        toastr.success('提交数据成功');
                    }
                }, error: function () {
                    toastr.error('保存失败');
                }, complete: function () {

                }
            });
        }
    });

    $('#PTypeTable').bootstrapTable('hideColumn', 'id');
    $('#PTypeTable').on('click', 'td:has(.editable)', function (e) {
        //e.preventDefault();
        e.stopPropagation(); // 阻止事件的冒泡行为
        $(this).find('.editable').editable('show'); // 打开被点击单元格的编辑状态
    });

    /**
     * 新增项目类型初始化界面
     */
    $('#btn_add_pt').click(function (e) {
        $('#pt_id').val(null);
        $('#pt_number').val(null);
        $('#pt_name').val(null);
        $("#add_edit_ptype_Modal").modal('show');
    });

    /**
     * 刷新项目类型表内容
     */
    function refreshPTypeTable() {
        $('#PTypeTable').bootstrapTable('refresh', {url: 'ptlistJson'});
    }

    /**
     * 绑定删除按钮事件，提交删除请求
     */
    $('#btn_delete_pt').click(function (e) {
        var a = $('#PTypeTable').bootstrapTable('getSelections');
        if (a.length != 1) {
            toastr.warning("请选择一条记录");
        } else {
            swal({
                title: '确认信息',
                text: "要删除选中的项目类型吗?",
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: '是的，删除它',
                cancelButtonText: '取消'
            }).then(function (isConfirm) {
                if (isConfirm.value) {
                    $.ajax({
                        type: "post",
                        url: "removePt/" + a[0].id,
                        success: function (data, status) {
                            if (status == "success") {
                                toastr.success('删除成功');
                                refreshPTypeTable();
                            }
                        }, error: function () {
                            //alert(status);
                            toastr.error('删除失败');
                            refreshPTypeTable();
                        }, complete: function () {
                        }
                    });
                }
            });
        }
    });

    /**
     * 编辑Modal界面初始化
     */
    $('#btn_edit_pt').click(function (e) {
        var a = $('#PTypeTable').bootstrapTable('getSelections');
        if (a.length != 1) {
            toastr.warning("请选择一条记录");
        } else {
            $('#add_edit_ptype_Modal').modal('show');
            $('#pt_id').val(a[0].id);
            $('#pt_number').val(a[0].number);
            $('#pt_name').val(a[0].name);
        }
    });


    /**
     * 在hide 方法调用时立即触发
     * 清除验证信息
     * */
    $('#add_edit_ptype_Modal').on('hide.bs.modal', function () {
        $('#prdTypeForm').data("bootstrapValidator").resetForm(true);
        // alert("初始化...");
    });

    /**
     *  PType绑定验证器
     * */
    $('#prdTypeForm').bootstrapValidator({
        message: '输入值无效！',
        feedbackIcons: {
            /*input状态样式图片*/
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            v_pt_number: {
                validators: {
                    notEmpty: {
                        message: '项目类型编码不能为空'
                    }
                }
            },
            v_pt_name: {
                validators: {
                    notEmpty: {
                        message: '项目类型名称不能为空'
                    }
                }
            }
        }
    }).on('success.form.bv', function (e) {
        e.preventDefault();
        //提交逻辑
        $.ajax({
            type: "post",
            url: "addPt",
            data: JSON.stringify({
                'id': $('#pt_id').val(),
                'number': $('#pt_number').val(),
                'name': $('#pt_name').val()
            }),
            dataType: 'JSON',
            contentType: 'application/json;charset=UTF-8',
            success: function (data, status) {
                if (status == "success") {
                    // console.log("---------->"+data);
                    toastr.success('提交数据成功');
                    refreshPTypeTable();
                }
            }, error: function () {
                toastr.error('保存失败');
            }, complete: function () {
                $('#add_edit_ptype_Modal').modal('hide');
            }
        });
    });

});