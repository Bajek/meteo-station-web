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
            var p1, p2, p3, callback;
            getTime();
            callback = callbackCreator(0);
            p1 = $http.get('/rest/get/1/' + startTime.getTime() + '/' + endTime.getTime()).then(callback);
            callback = callbackCreator(1);
            p2 = $http.get('/rest/get/2/' + startTime.getTime() + '/' + endTime.getTime()).then(callback);
            callback = callbackCreator(2);
            p3 = $http.get('/rest/get/3/' + startTime.getTime() + '/' + endTime.getTime()).then(callback);

            if (!intervalEnabled) {
                $q.all([p1, p2, p3]).then(initInterval);
            }

        }

        function callbackCreator(seriesId) {
            return function(restData) {
                var chart = $('#chartsContainer').highcharts();
                chart.series[seriesId].setData(restData.data.data);
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

            Highcharts.setOptions({
                global: {
                    //useUTC: false,
                    timezoneOffset: -60
                }
            });

            $('#chartsContainer').highcharts({
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
                    name: 'DHT11 temperature (°C)',
                    data: []
                },
                {
                    tooltip: {
                        valueSuffix: '%'
                    },
                    name: 'DHT11 humidity (%)',
                    data: []
                },
                {
                    tooltip: {
                        valueSuffix: '°C'
                    },
                    name: 'Dallas temperature (°C)',
                    data: []
                }
                ]

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