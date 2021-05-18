(function (win) {
    function MyChart(elm) {
        this.elm = elm;
        this.chart = echarts.init(elm);
        this.tems = [];        //积压量数组（存放服务器返回的所有积压量）
        this.times = []
        this.dateTimes = []
        // this.chart.gap = 40;
    }

    MyChart.prototype.init = function () {
        const myChart = this.chart;
        const myChartData = this;        //积压量数组（存放服务器返回的所有积压量）
        const gap = this.chart.gap;
        const option = {
            title: {    //图表标题
                text: 'DPS数据库实时积压量',
                left: 'center',
            },
            tooltip: {
                trigger: 'axis', //坐标轴触发提示框，多用于柱状、折线图中
                /*
                 控制提示框内容输出格式
                formatter: '{b0}<br/><font color=#FF3333>&nbsp;●&nbsp;</font>{a0} : {c0} ℃ ' +
                               '<br/><font color=#53FF53>●&nbsp;</font>{a1} : {c1} % ' +
                               '<br/><font color=#68CFE8>&nbsp;●&nbsp;</font>{a3} : {c3} mm ' +
                               '<br/><font color=#FFDC35>&nbsp;●&nbsp;</font>{a4} : {c4} m/s ' +
                               '<br/><font color=#B15BFF>&nbsp;&nbsp;&nbsp;&nbsp;●&nbsp;</font>{a2} : {c2} hPa '
                */
                // 提示框的位置

            },
            dataZoom: [{                // 内置于坐标系中，使用户可以在坐标系上通过鼠标拖拽、鼠标滚轮、手指滑动（触屏上）来缩放或漫游坐标系
                type: 'inside',
                start: 50,
                end: 100
            }, {
                start: 50,
                end: 100,                  // handleIcon 手柄的 icon 形状，支持路径字符串
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',        //  控制手柄的尺寸，可以是像素大小，也可以是相对于 dataZoom 组件宽度的百分比，默认跟 dataZoom 宽度相同。
                handleStyle: {
                    color: 'pink',
                    shadowBlur: 3,      // shadowBlur图片阴影模糊值，shadowColor阴影的颜色
                    shadowColor: 'red',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            legend: {    //图表上方的类别显示
                show: false,
                data: ['积压量']
            },
            color: [
                '#FF3333',    //温度曲线颜色
                '#53FF53',    //湿度曲线颜色
                '#B15BFF',    //压强图颜色
                '#68CFE8',    //雨量图颜色
                '#FFDC35'    //风速曲线颜色
            ],
            // toolbox：这是ECharts中的工具栏。内置有导出图片、数据视图、动态类型切换、数据区域缩放、重置五个工具
            toolbox: {    //工具栏显示
                show: true,
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'  // y轴不缩放，Index默认为0
                    },
                    restore: {},
                    saveAsImage: {}    //显示“另存为图片”工具
                    // ,magicType: {
                    //     type: ['bar', 'line']
                    // }
                }
            },
            xAxis: {    //X轴
                type: 'category',
                boundaryGap: false,
                data: []    //先设置数据值为空，后面用Ajax获取动态数据填入
            },
            yAxis: [    //Y轴（这里我设置了两个Y轴，左右各一个）
                {
                    //第一个（左边）Y轴，yAxisIndex为0
                    type: 'value',
                    name: '积压量',
                    /* max: 120,
                    min: -40, */
                    // scale: true,
                    boundaryGap: [0, '100%'],// 分别表示数据最小值和最大值的延伸范围，可以直接设置数值或者相对的百分比，
                    axisLabel: {
                        formatter: '{value} 条'    //控制输出格式
                    }
                }
            ],
            series: [    //系列（内容）列表
                {
                    name: '积压量',
                    smooth: true,  // 开启平滑处理。true的平滑程度相当于0.5
                    sampling: 'average', //  取过滤点的平均值
                    type: 'line',    //折线图表示（生成温度曲线）
                    symbol: 'emptydiamond',    //设置折线图中表示每个坐标点的符号；emptycircle：空心圆；emptyrect：空心矩形；circle：实心圆；emptydiamond：菱形
                    data: [],       //数据值通过Ajax动态获取
                    itemStyle: {
                        normal: {
                            color: 'rgb(255, 70, 131)' //  图形的颜色。
                        }
                    },
                    areaStyle: {    // 区域填充样式。
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: 'rgb(255, 158, 68)'
                            }, {
                                offset: 1,
                                color: 'rgb(255, 70, 131)'
                            }])
                        }
                    },
                },
            ]
        };
        myChart.showLoading();
        myChart.setOption(option);
        $.ajax({    //使用JQuery内置的Ajax方法
            type: "GET",        //post请求方式
            async: true,        //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "/graph/fetchAllData",    //请求发送到ShowInfoIndexServlet处
            //data: {name: "A0001"},        //请求内包含一个key为name，value为A0001的参数；服务器接收到客户端请求时通过request.getParameter方法获取该参数值
            dataType: "json",        //返回数据形式为json
            success: function (result) {
                //请求成功时执行该函数内容，result即为服务器返回的json对象
                if (result != null && result.length > 0) {
                    for (var i = 0; i < result.length; i++) {
                        myChartData.tems.push(result[i].tableCount);        //挨个取出温度、湿度、压强等值并填入前面声明的温度、湿度、压强等数组
                        myChartData.times.push(formatTime(new Date(parseInt(result[i].createTime))));
                        myChartData.dateTimes.push(result[i].createTime)
                    }
                    myChart.hideLoading();    //隐藏加载动画

                    myChart.setOption({        //载入数据
                        xAxis: {
                            data: myChartData.times   //填入X轴数据
                        },
                        series: [    //填入系列（内容）数据
                            {
                                // 根据名字对应到相应的系列
                                name: '积压量',
                                data: myChartData.tems
                            }
                        ]
                    });

                } else {
                    //返回的数据为空时显示提示信息
                    alert("图表请求数据为空，可能服务器暂未录入近五天的观测数据，您可以稍后再试！");
                    myChart.hideLoading();
                }

            },
            error: function (errorMsg) {
                //请求失败时执行该函数
                // alert("图表请求数据失败，可能是服务器开小差了");
                myChart.hideLoading();
                console.log(errorMsg);
            }
        });
    };

    MyChart.prototype.update = function (data, timestamp) {
        const myChart = this.chart;
        const myChartData = this;
        if (data != null && data.length > 0) {
            let lastTimestamp = parseInt(timestamp);
            for (var i = 0; i < data.length; i++) {
                if (parseInt(data[i].createTime) > lastTimestamp) {
                    myChartData.tems.push(data[i].tableCount);        //挨个取出温度、湿度、压强等值并填入前面声明的温度、湿度、压强等数组
                    myChartData.times.push(formatTime(new Date(parseInt(data[i].createTime))));
                    myChartData.dateTimes.push(data[i].createTime);
                }
            }
            myChart.setOption({        //载入数据
                xAxis: {
                    data: myChartData.times    //填入X轴数据
                },
                series: [{
                    data: myChartData.tems
                }]
            });
        }
    };

    MyChart.prototype.getDataFromServer = function (timestamp) {
        const myChartData = this;
        $.ajax({
            type: "GET",
            url: "/graph/getNewestDataByStartTime",
            data: {timestamp: timestamp},
            success: function (res) {
                myChartData.update(res, timestamp);
            }
        })
    };

    function formatTime(time) {
        const hour = time.getHours();
        const min = time.getMinutes();
        const sec = time.getSeconds();
        const h = hour < 10 ? "0" + hour : hour;
        const m = min < 10 ? "0" + min : min;
        const s = sec < 10 ? "0" + sec : sec;
        return h + ":" + m + ":" + s;
    }

    win.MyChart = MyChart;
}(window));
