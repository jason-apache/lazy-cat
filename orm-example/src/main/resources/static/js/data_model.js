(function () {
    layui.use(['element', 'form', 'layer', 'table'], function (element, form, layer, table) {
        let $loadDataBtn = $('#loadDataBtn')
        let parameterManager = {
            requestParam: {},
            responseParam: {},
            modelParam: {},
            persistenceParam: {},
            loadParameter: function () {
                try {
                    this.requestParam = $.extend(true, {}, form.val('requestParameterForm'))
                    this.requestParam.body = $.extend(true, {}, JSON.parse(this.requestParam.body))
                    this.requestParam.header = $.extend(true, {}, JSON.parse(this.requestParam.header))
                    this.responseParam = $.extend(true, {}, form.val('responseParameterForm'))
                    this.modelParam = $.extend(true, {}, form.val('dataModelParameterForm'))
                    //this.modelParam.typeDefine = $.extend(true, {}, JSON.parse(this.modelParam.typeDefine))
                    this.modelParam.displayCss = $.extend(true, {}, JSON.parse(this.modelParam.displayCss))
                    this.persistenceParam = $.extend(true, {}, form.val('persistenceApiParameterForm'))
                    this.persistenceParam.header = $.extend(true, {}, JSON.parse(this.persistenceParam.header))
                } catch (e) {
                    layer.msg('数据格式异常' + e, {offset: ['4rem']})
                    throw e
                }
            }
        }
        let dataManager = {
            curSelected: -1,
            allData: [],
            loadData: function (data) {
                let objects = parameterManager.responseParam.responseRootPath.length ? eval('data.' + parameterManager.responseParam.responseRootPath) : data
                if (objects instanceof Array) {
                    this.allData = objects.concat()
                } else {
                    this.allData.push(objects)
                }
                this.curSelected = -1
                // 渲染
                output(this.allData, parameterManager.responseParam.dataType, $.extend(true, {}, parameterManager.modelParam))
            },
            discard: function () {
                this.allData = []
                $('#main').empty()
            },
            // 修改缓存中的数据
            modify: function (index, obj) {
                if (index >= 0) {
                    this.allData[index] = obj
                }
            },
            // 查看详细信息
            open: function (index, dom) {
                this.curSelected = index
                detailInfo(index, dom, this.allData[index])
            },
            close: function () {
                this.curSelected = -1
            }
        }
        // 触发请求api
        form.on('submit(requestForm)', function(data){
            parameterManager.loadParameter()
            let requestParam = parameterManager.requestParam
            dataManager.discard()
            ajax(requestParam.api, requestParam.method, requestParam.body, requestParam.headers).done((data) => {
                dataManager.loadData(data)
            })
        })
        // 触发持久化api
        form.on('submit(persistenceApiParameterForm)', (data) => {
            parameterManager.loadParameter()
            let persistenceParam = parameterManager.persistenceParam
            if (persistenceParam.dataSource && persistenceParam.dataSource.length) {
                let dataSource = eval(persistenceParam.dataSource)
                if (dataSource && dataSource.length) {
                    ajax(persistenceParam.api, persistenceParam.method, dataSource, persistenceParam.headers).done((data) => {
                        layer.msg('接口请求成功')
                        $loadDataBtn.click()
                    })
                } else {
                    layer.msg('未选择数据', {offset: ['4rem']})
                }
            } else {
                layer.msg('数据源不能为空', {offset: ['4rem']})
            }
        })

        let dataItemSubjectClass = 'data-item-subject'
        let dataItemHeadClass = 'data-item-head'
        let dataItemBodyClass = 'data-item-body'
        let dataItemInfoClass = 'data-item-info'
        let dataItemCheckboxClass = 'data-item-checkbox'
        let dataIndexAttr = 'data-index'
        // 输出数据模型至main
        function output(data, type, modelParam) {
            let $main = $('#main')
            $main.empty()
            if (!data) {
                return
            }
            let containerCss
            let interval
            switch (modelParam.iconSize) {
                case 's':
                    containerCss = 'layui-col-md2'
                    interval = 6
                    break
                case 'm':
                    containerCss = 'layui-col-md3'
                    interval = 4
                    break
                case 'l':
                    containerCss = 'layui-col-md4'
                    interval = 3
                    break
                default:
            }
            let headKey = modelParam.headKey
            let nameKey = modelParam.nameKey
            let descKey = modelParam.descKey
            let displayCss = modelParam.displayCss
            let $container
            if (type !== 'array') {
                data = [data]
            }
            for (let i = 0; i < data.length; i++) {
                let d = data[i]
                let head = headKey && headKey.length ? eval('d.' + headKey) : ''
                let name = nameKey && nameKey.length ? eval('d.' + nameKey) : 'no name object'
                let desc = descKey && descKey.length ? eval('d.' + descKey) : ''
                let $item = $(`<div class="${containerCss} ${dataItemSubjectClass}" ${dataIndexAttr}="${i}" style="${displayCss.subjectStyle}"><div class="${dataItemHeadClass}" style="${displayCss.headStyle}">${head}</div><div class="${dataItemBodyClass}" style="${displayCss.bodyStyle}">${name}</div><div class="${dataItemInfoClass}" style="${displayCss.infoStyle}">${desc}</div></div>`)
                let display = tabCurIndex === 3 ? '' : 'display:none'
                let $checkbox = $(`<input type="checkbox" class="${dataItemCheckboxClass}" ${dataIndexAttr}="${i}" style="float:left;${display};zoom:165%"/>`)
                $checkbox.click((e) => e.stopPropagation())
                $item.prepend($checkbox)
                if (i % interval === 0) {
                    $container = $(`<div class="layui-row row" style="${displayCss.rowStyle}"></div>`)
                    $main.append($container)
                }
                $item.bind('click', function () {
                    dataManager.open($item.attr(dataIndexAttr), $item)
                })
                $container.append($item)
            }
        }

        // 显示数据模型详细信息
        function detailInfo(index, domObj, obj) {
            let domId = 'content'
            layer.open({
                type: 1,
                title: null,
                area: ['90%', '90%'],
                btn: ['确定', '取消'],
                offset: ['2rem'],
                resize: false,
                content: buildDataInfoPanel(domId, obj),
                success: function () {
                },
                yes: function (_index, _layer) {
                    try {
                        dataManager.modify(index, JSON.parse($('#' + domId).val()))
                    } catch (e) {
                        layer.msg('数据格式异常' + e, {offset: ['4rem']})
                    }
                    flush(index, domObj)
                    layer.close(_index);
                },
                end: function () {
                    dataManager.close()
                }
            })
        }

        // 数据详细信息面板
        function buildDataInfoPanel(domId, obj) {
            let $container = $(`<div class="layui-tab layui-tab-brief"></div>`)
            let $title = $(`<ul class="layui-tab-title"><li class="layui-this">raw</li></ul>`)
            let $content = $(`<div class="layui-tab-content"></div>`)
            let $raw = $(`<div class="layui-tab-item layui-show"></div>`)
            let $rawContent = $(`<textarea id="${domId}" style="width: 100%;height: 84%;resize:none;border:none;">${JSON.stringify(obj, null, 4)}</textarea>`)
            return $container.append($title).append($content.append($raw.append($rawContent)))[0].outerHTML
        }

        // 刷新数据模型渲染
        function flush(index, domObj) {
            domObj = $(domObj)
            let obj = dataManager.allData[index]
            let modelParam = parameterManager.modelParam
            let head = modelParam.headKey && modelParam.headKey.length ? eval('obj.' + modelParam.headKey) : ''
            let name = modelParam.nameKey && modelParam.nameKey.length ? eval('obj.' + modelParam.nameKey) : 'no name object'
            let desc = modelParam.descKey && modelParam.descKey.length ? eval('obj.' + modelParam.descKey) : ''
            domObj.attr('dirty', true)
            domObj.css('background-color', '#838383')
            domObj.find('.' + dataItemHeadClass).text(head)
            domObj.find('.' + dataItemBodyClass).text(name)
            domObj.find('.' + dataItemInfoClass).text(desc)
        }

        let tabCurIndex = -1
        element.on('tab(mainTab)', function (data) {
            tabCurIndex = data.index
            if (tabCurIndex === 3) {
                // 点击了持久化接口页签
                $('.' + dataItemCheckboxClass).show()
            } else {
                $('.' + dataItemCheckboxClass).hide()
            }
        });
        // 加载全部api接口
        (function () {
            ajax('/apiInfo/getAll', 'get', null, null).done((data) => {
                for (let i = 0; i < data.length; i++) {
                    $('#apiSelector').append($(`<option>${data[i].apiPath}</option>`))
                    $('#persistenceApiSelector').append($(`<option>${data[i].apiPath}</option>`))
                    form.render('select')
                }
                // 初始化配置记录缓存策略
                configPersistentStrategy.init()
            })
        })();

        // 绑定点击事件
        (function () {
            $('#modified').click(() => $('.' + dataItemSubjectClass + '[dirty=true]').find('.' + dataItemCheckboxClass).prop('checked', true))
            $('#selectAll').click(() => $('.' + dataItemCheckboxClass).prop('checked', true))
            $('#unselectAll').click(() => $('.' + dataItemCheckboxClass).prop('checked', false))
            $('#addNewObject').click(() => {
                let nameKey = parameterManager.modelParam.nameKey
                let path = parameterManager.responseParam.responseRootPath
                let dataType = parameterManager.responseParam.dataType
                let data = {}
                let obj = {}
                if (nameKey && nameKey.length) {
                    obj[nameKey] = 'new Object'
                }
                if (dataType === 'array') {
                    if (path && path.length) {
                        path = path.split('.')
                        let preObj = data
                        for (let i = 0; i < path.length; i++) {
                            if (i === path.length -1) {
                                eval('preObj.' + path[i] + '= preObj = []')
                                preObj.push(obj)
                            } else {
                                eval('preObj.' + path[i] + ' = preObj = {}')
                            }
                        }
                        eval('data.' + parameterManager.responseParam.responseRootPath + ' = data.'+ parameterManager.responseParam.responseRootPath +'.concat(dataManager.allData)')
                    } else {
                        data = [obj].concat(dataManager.allData)
                    }
                } else {
                    data = obj
                }
                dataManager.discard()
                dataManager.loadData(data)
                flush(0, $('.' + dataItemSubjectClass)[0])
            })
        })();

        function ajax(api, method, body, headers) {
            body = body ? JSON.stringify(body) : null
            headers = $.extend(true, {}, headers)
            let index = layer.load(2, {shade: [0.2,'#000000']})
            return $.ajax({
                url: api,
                type: method,
                data: body,
                headers: headers
            }).fail(function (xhr, status) {
                let e = xhr.responseJSON
                if (e) {
                    layer.alert(e.message, {icon: 2, title: e.desc, shadeClose: true, closeBtn: 0, btn: [], area: '33%', offset: ['4rem']})
                } else {
                    layer.alert(xhr.responseText, {icon: 2, title: null, shadeClose: true, closeBtn: 0, btn: [], area: '33%', offset: ['4rem']})
                }
            }).always(() => {
                layer.close(index)
            })
        }


        // 绑定点击事件
        (() => {
            // 保存当前配置记录
            $('#configAddBtn').click(() => {
                let formDomId = 'configSaveForm'
                layer.open({
                    type: 1,
                    title: '为你的配置命名',
                    area: ['30%', '30%'],
                    closeBtn: false,
                    shadeClose: true,
                    offset: ['2rem'],
                    resize: false,
                    content: buildSaveCurConfigPanel(formDomId),
                    success: function () {
                        form.on('submit(configSave)', function (data) {
                            let desc = data.field.desc
                            parameterManager.loadParameter()
                            configPersistentStrategy.push(JSON.stringify(parameterManager), desc)
                            layer.closeAll()
                            layer.msg('保存当前配置成功, 请在配置记录中查看', {offset: ['4rem']})
                        })
                    },
                    yes: function (_index, _layer) {
                        layer.close(_index);
                    },
                    end: function () {
                    }
                })
            })
            // 查看所有配置记录
            $('#configRecordBtn').click(() => {
                let domId = 'configRecordContainer'
                let tableDomId = 'configRecordTable'
                let tableEvent = 'configRecordTableEvent'
                let clearBtnDomId = 'clearConfigRecord'
                layer.open({
                    type: 1,
                    title: '数据配置记录',
                    area: ['80%', '90%'],
                    btn: false,
                    closeBtn: false,
                    shadeClose: true,
                    offset: ['2rem'],
                    resize: false,
                    content: buildConfigRecordPanel(domId, tableDomId, tableEvent, clearBtnDomId),
                    success: function () {
                        renderTable(domId, tableDomId, tableEvent, clearBtnDomId)
                    },
                    yes: function (_index, _layer) {
                        layer.close(_index);
                    },
                    end: function () {
                    }
                })
            })

            // 重绘配置记录表格
            function renderTable(containerDomId, tableDomId, tableEvent, clearBtnDomId) {
                let data = configPersistentStrategy.content
                let controlBtnClass = 'rowControl'
                let controlKey = 'rowControl'
                let controlApply = 'apply'
                let controlDel = 'del'
                table.render({
                    elem: '#' + tableDomId,
                    cols: [[
                        {field: 'desc', title: '备注'},
                        {field: 'time', title: '操作时间'},
                        {field: 'locked', title: '是否锁定', templet: function (d) {
                                return `<input type="checkbox" ${dataIndexAttr}="${d.index}" name="lock" lay-skin="switch" lay-text="否|是" lay-filter="recordLocked" ${d.locked ? 'checked' : ''}>`
                        }},
                        {field: 'data', title: '配置参数'},
                        {title: '操作', templet: function (d) {
                            return `<div class="layui-btn-group"><button ${dataIndexAttr}="${d.index}" class="layui-btn ${controlBtnClass}" ${controlKey}="${controlApply}">应用</button><button ${dataIndexAttr}="${d.index}" class="layui-btn ${controlBtnClass}" ${controlKey}="${controlDel}">移除</button></div>`
                        }}
                    ]],
                    skin: 'line',
                    even: true,
                    page: false,
                    limit: data.length,
                    data: data
                })
                // 表格行双击监听
                table.on('rowDouble('+ tableEvent +')', (obj) => {
                    apply(obj.data.index)
                })
                // 锁定复选框监听
                form.on('switch(recordLocked)', (obj) => {
                    let locked = obj.elem.checked
                    let dataIndex = $(obj.elem).attr(dataIndexAttr)
                    configPersistentStrategy.lock(locked, dataIndex)
                    let msg = (locked ? '已锁定' : '已解锁') + '并重排序'
                    layer.msg(msg, {offset: ['4rem']})
                    renderTable(containerDomId, tableDomId, tableEvent, clearBtnDomId)
                })
                // 应用/移除按钮监听
                $('#' + containerDomId + ' .' + controlBtnClass).click((event) => {
                    let ele = event.currentTarget
                    let dataIndex = $(ele).attr(dataIndexAttr)
                    switch ($(ele).attr(controlKey)) {
                        case controlApply:
                            apply(dataIndex)
                            break
                        case controlDel:
                            configPersistentStrategy.remove(dataIndex)
                            renderTable(containerDomId, tableDomId, tableEvent, clearBtnDomId)
                            break
                    }
                })
                // clear按钮监听
                $('#' + clearBtnDomId).click(() => {
                    configPersistentStrategy.clear()
                    layer.msg('已清空未锁定的配置项', {offset: ['4rem']})
                    renderTable(containerDomId, tableDomId, tableEvent, clearBtnDomId)
                })
                function apply(index) {
                    let config = configPersistentStrategy.content[index]
                    configPersistentStrategy.flushLastSelect(index)
                    // 加载配置
                    useConfig(JSON.parse(config.data))
                    layer.closeAll()
                    layer.msg('已加载 "'+ config.desc +'" 配置', {offset: ['4rem']})
                }
            }
        })();

        // 配置记录面板
        function buildConfigRecordPanel(domId, tableDomId, tableEvent, clearBtnDomId) {
            let $container = $(`<div id="${domId}"></div>`)
            let $table = $(`<table id="${tableDomId}" lay-filter="${tableEvent}"></table>`)
            let $clearBtn = $(`<button type="button" class="layui-btn" id="${clearBtnDomId}">clear</button>`)
            $container.append($table).append($clearBtn)
            return $container[0].outerHTML
        }

        // 保存当前配置面板
        function buildSaveCurConfigPanel(formDomId) {
            let $form = $(`<form id="${formDomId}" class="layui-form" onsubmit="return false;"></form>`)
            $form.append(`<div class="layui-form-item">
                            <label class="layui-form-label">名称</label>
                            <div class="layui-input-block">
                                <input type="text" name="desc" lay-verify="required" autocomplete="off" placeholder="配置名称" class="layui-input">
                            </div>
                          </div>`)
            $form.append($(`<div class="layui-form-item" style="text-align: center;">
                            <button class="layui-btn" lay-submit="configSave" lay-filter="configSave">确定</button>
                        </div>`))
            return $form[0].outerHTML
        }

        // 加载配置
        function useConfig(obj) {
            delete parameterManager.requestParam
            delete parameterManager.responseParam
            delete parameterManager.modelParam
            delete parameterManager.persistenceParam
            $.extend(true, parameterManager, obj)
            form.val('requestParameterForm', escape(parameterManager.requestParam))
            form.val('responseParameterForm', escape(parameterManager.responseParam))
            form.val('dataModelParameterForm', escape(parameterManager.modelParam))
            form.val('persistenceApiParameterForm', escape(parameterManager.persistenceParam))
            function escape(obj) {
                let newObj = $.extend(true, {}, obj)
                for (const k in newObj) {
                    let val = newObj[k]
                    if (typeof val === 'object') {
                        newObj[k] = JSON.stringify(val, null, 4)
                    }
                }
                return newObj
            }
        }

        // 配置记录持久化策略
        let configPersistentStrategy = {
            key: 'lazy-cat-test',
            lastSelect: null,
            content: [],
            init: function () {
                let data = JSON.parse(localStorage.getItem(this.key))
                if (!data) {
                    return
                }
                let store = data.content
                this.content = store ? store : []
                this.lastSelect = data.lastSelect
                if (this.content.length && this.lastSelect) {
                    useConfig(JSON.parse(this.lastSelect.data))
                    layer.msg('自动加载上次选择的配置: "'+ this.lastSelect.desc +'"', {offset: ['4rem']})
                }
            },
            push: function (data, desc) {
                if (data && data !== '{}') {
                    this.content.push(this.format(data, this.content.length, desc))
                    this.persistence()
                }
            },
            lock: function (locked, index) {
                this.content[index].locked = locked
                this.sortArr(this.content)
                this.persistence()
            },
            clear: function () {
                this.content = this.content.filter(c => c.locked)
                this.sortArr(this.content)
                this.persistence()
            },
            remove: function (index) {
                if (index === 0) {
                    this.content = this.content.slice(1)
                } else {
                    let newArr = this.content.slice(0, index)
                    newArr = newArr.concat(this.content.slice(parseInt(index) + 1))
                    let v = this.content.slice(parseInt(index) + 1)
                    this.content = newArr
                }
                this.sortArr(this.content)
                this.persistence()
            },
            flushLastSelect: function (index) {
                this.lastSelect = this.content[index]
                this.persistence()
            },
            persistence: function () {
                try {
                    let data = {content: this.content, lastSelect: this.lastSelect}
                    localStorage.setItem(this.key, JSON.stringify(data))
                } catch (e) {
                    layer.msg('保存数据配置记录失败: ' + e, {'offset': ['4rem']})
                }
            },
            format: function (data, index, desc){
                return {time: getNowFormatDate(), locked: false, index: index, desc: desc, data: data}
            },
            sortArr: function (arr){
                arr.sort(this.sortFn)
                for (let i = 0; i < arr.length; i++) {
                    arr[i].index = i
                }
            },
            sortFn: function (a, b) {
                if (a.locked && b.locked) {
                    return a.time > b.time ? 1 : -1
                } else if (a.locked) {
                    return -1
                } else if (b.locked) {
                    return 1
                } else {
                    return a.time > b.time ? 1 : -1
                }
            }
        }

        function getNowFormatDate() {
            let date = new Date();

            let seperator1 = "-";
            let seperator2 = ":";

            let month = date.getMonth() + 1;
            let strDate = date.getDate();
            let hour = date.getHours();
            let minutes = date.getMinutes();
            let seconds = date.getSeconds();

            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            if (hour >= 0 && hour <= 9) {
                hour = "0" + hour;
            }
            if (minutes >= 0 && minutes <= 9) {
                minutes = "0" + minutes;
            }
            if (seconds >= 0 && seconds <= 9) {
                seconds = "0" + seconds;
            }

            return date.getFullYear() + seperator1 + month + seperator1 + strDate + " " + hour + seperator2 + minutes + seperator2 + seconds;
        }
    });
})();