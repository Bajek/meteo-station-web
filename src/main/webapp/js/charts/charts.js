/**
 * Created by bajek on 2/19/15.
 */
'use strict';
/* Controllers */
var chartController = angular.module('meteoStation', []);

chartController.controller('chartController', ['$scope', '$http', '$interval', '$q',
    function ($scope, $http, $interval, $q) {

        var startTime, endTime, intervalEnabled = false;



        initPlot();
        getPlotData();


        function getPlotData() {
            var p1, p2;
            getTime();
            p1 = $http.get('/rest/get/1/' + startTime.getTime() + '/' + endTime.getTime()).then(setTemp);
            p2 = $http.get('/rest/get/2/' + startTime.getTime() + '/' + endTime.getTime()).then(setHumi);

            if (!intervalEnabled) {
                $q.all([p1, p2]).then(initInterval);
            }

        }


        function setTemp(restData) {
            var chart = $('#chartsContainer').highcharts();
            chart.series[0].setData(restData.data.data);
        }

        function setHumi(restData) {
            var chart = $('#chartsContainer').highcharts();
            chart.series[1].setData(restData.data.data);
        }

        function initPlot() {
            $('#chartsContainer').highcharts({
                global: {
                    getTimezoneOffset: 1
                },
                credits: {
                    enabled: false
                },
                plotOptions: {
                    line: {
                        marker: {
                            enabled: false
                        }
                    }
                },
                chart: {
                    zoomType: "x"
                },
                title: {
                    text: 'Temperature and humidity',
                    x: -20 //center
                },
                subtitle: {
                    text: 'Last hour',
                    x: -20
                },
                xAxis: {
                    type: 'datetime'
                },
                yAxis: {
                    title: {
                        text: 'Values'
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    tooltip: {
                        valueSuffix: '°C'
                    },
                    name: 'Temperature (°C)',
                    data: [] // arrayData[0].data.data
                },
                {
                    tooltip: {
                        valueSuffix: '%'
                    },
                    name: 'Humidity (%)',
                    data: [] // arrayData[1].data.data
                }]

            });

        }

        function getTime() {
            //last hour
            startTime = new Date();
            startTime = new Date(startTime.getTime() - (1000*60*60));
            endTime = new Date();
        }

        function initInterval() {
            $interval(getPlotData, 5000);
            intervalEnabled = true;
        }


        $scope.value = 5;

    }]);