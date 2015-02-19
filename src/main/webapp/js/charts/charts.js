/**
 * Created by bajek on 2/19/15.
 */
'use strict';
/* Controllers */
var chartController = angular.module('meteoStation', []);

chartController.controller('chartController', ['$scope', '$http',
    function ($scope, $http) {

        var startTime = new Date(),
            endTime = new Date(),
            counter = 0,
            arrayData = [];

        startTime.setDate(startTime.getDate() - 1);


        getPlotData();

        function getPlotData() {
            $http.get('/rest/get/1/' + startTime.getTime() + '/' + endTime.getTime()).then(increaseCounter);
            $http.get('/rest/get/2/' + startTime.getTime() + '/' + endTime.getTime()).then(increaseCounter);
        }

        function increaseCounter(restData) {
            counter++;
            arrayData[restData.data.sensorId] = restData.data.data;
            if (counter == 2) {
                plotData(arrayData);
            }

        }

        function plotData(arrayData) {
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
                    text: 'Last week',
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
                    data: arrayData[1]
                },
                {
                    tooltip: {
                        valueSuffix: '%'
                    },
                    name: 'Humidity (%)',
                    data: arrayData[2]
                }]
            });

        }


        $scope.value = 5;

    }]);