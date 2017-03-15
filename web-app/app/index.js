var angular = require('angular');
var $ = require('jquery');
var crypt = require('crypto');
//hide and show container.
$(document).ready(function () {
    $('#responseMsg_Container').hide();
    $('#sale_Container').hide();

    $('#login-form-link').click(function (e) {
        $('#responseMsg_Container').hide();
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

    $('#register-form-link').click(function (e) {
        $('#responseMsg_Container').hide();
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

    $('#header_Container').click(function (e) {
        $('#login-form-link').click();
    });
});

var baseURL = "http://localhost:8085/auction";

// Angular app module.
var app = angular.module('app', []);

app.run(function ($rootScope, $http) {
    $rootScope.isSessionEstablished = false;
    //$rootScope.sales = [];
});


//Login Controller.
app.controller("loginController", function ($scope, $http, sessionService) {
    var authToken = { "basic": '' };
    $scope.login = function () {
        // construct Json object.
        var dataObj = {
            "email": $scope.loginName,
            "password": $scope.password
        }

        //Request Headers.
        var config = {
            headers: {
                'Content-Type': 'application/json;charset=utf-8;'
            }
        }

        //TODO: externalize base url.
        var res = $http.post(baseURL + '/user/login', dataObj, config);
        res.success(function (data, status, headers, config) {
            $('#responseMsg_Container').hide();
            $('#sale_Container').show();
            $('#loginSignUp_Container').hide();
            $scope.isSessionEstablished = true;
            $scope.currentUser = dataObj.email;
            authToken.basic = new Buffer(dataObj.email + ":" + crypt.createHash('md5')
                .update(dataObj.password).digest('hex')).toString('base64');

            sessionService.prepSession(dataObj.email);

            //load sales.
            sales();

            $('#saleList_Container').show();
        });

        res.error(function (data, status, headers, config) {
            $('#responseMsg_Container').addClass('panel panel-danger');
            $('#responseMsg_Container').show();
            $scope.respMessage = data.errorVOs[0].message;
        });
        // Making the fields empty
        $scope.loginName = '';
        $scope.password = '';
    };

    $scope.register = function () {
        // use $.param jQuery function to serialize data from JSON
        var dataObj = {
            "email": $scope.regEmail,
            "password": $scope.regPassword,
            "firstName": $scope.regFirstName,
            "lastName": $scope.regLastName,
            "contact": $scope.regContact,
            "address": $scope.regAddress
        }
        //Request Headers.
        var config = {
            headers: {
                'Content-Type': 'application/json;charset=utf-8;'
            }
        }

        var res = $http.post(baseURL + '/user', dataObj, config);
        res.success(function (data, status, headers, config) {
            $('#responseMsg_Container').addClass('panel panel-success');
            $('#responseMsg_Container').show();
            $('#login-form-link').click();
            $scope.respMessage = data.message;
        });
        res.error(function (data, status, headers, config) {
            $('#responseMsg_Container').addClass('panel panel-danger');
            $('#responseMsg_Container').show();
            $scope.respMessage = data.errorVOs[0].message;
        });
        // Making the fields empty 
        $scope.regLoginName = '';
        $scope.regPassword = '';
        $scope.regEmail = '';
        $scope.regContact = '';
        $scope.regFirstName = '';
        $scope.regLastName = '';
        $scope.regAddress = '';
    };

    $scope.detail = function (saleId) {

        var res = $http.get(baseURL + '/sale/' + saleId, {
            headers: {
                'Content-Type': 'application/json;charset=utf-8;',
                'Authorization': 'Basic ' + authToken.basic
            }
        });
        res.success(function (data, status, headers, config) {
            $('#saleDetail_Container').show();
            $('#saleList_Container').hide();
             $('#responseMsg_Container').hide();
            if (data.saleVOs.length > 0) {
                $scope.saleDetail = data.saleVOs[0];
            }
        });
        res.error(function (data, status, headers, config) {
            $('#responseMsg_Container').addClass('panel panel-danger');
            $('#responseMsg_Container').show();
            $scope.respMessage = data.errorVOs[0].message;
        });
    };

    $scope.bid = function (saleId) {
        var dataObj = {
            "price": $scope.bidPrice
        }
        var config = {
            headers: {
                'Content-Type': 'application/json;charset=utf-8;',
                'Authorization': 'Basic ' + authToken.basic
            }
        }
        var res = $http.post(baseURL + '/sale/' + saleId + "/bid", dataObj, config);
        res.success(function (data, status, headers, config) {
            $scope.respMessage = data.message;
            $('#responseMsg_Container').addClass('panel panel-success');
            $('#responseMsg_Container').show();
            //load sales.
            sales();
            $('#saleDetail_Container').hide();
            $('#sale_Container').show();
            $('#saleList_Container').show();
        });
        res.error(function (data, status, headers, config) {
            $('#responseMsg_Container').addClass('panel panel-danger');
            $('#responseMsg_Container').show();
            $scope.respMessage = data.errorVOs[0].message;
        });
    };

    $scope.backToList = function () {
        sales();
        $('#saleDetail_Container').hide();
        $('#sale_Container').show();
        $('#saleList_Container').show();
    }

    $scope.getSaleCreateForm = function () {
        $('#saleCreate_Container').show(); 
        $('#saleList_Container').hide();
        $('#saleDetail_Container').hide();
        $('#responseMsg_Container').hide();
    }

    $scope.postSale = function () {
        var dataObj = {
            "productId": $scope.productID,
            "productName": $scope.productName,
            "productType": $scope.productType,
            "productDesc": $scope.productDesc,
            "productImageUrl": $scope.productImageURL,
            "price": $scope.bidPrice,
            "endTime": $scope.endDate
        }
        //Request Headers.
        var config = {
            headers: {
                'Content-Type': 'application/json;charset=utf-8;',
                'Authorization': 'Basic ' + authToken.basic
            }
        }

        var res = $http.post(baseURL + '/sale', dataObj, config);
        res.success(function (data, status, headers, config) {
            $('#saleCreate_Container').hide();
            $('#responseMsg_Container').addClass('panel panel-success');
            $('#responseMsg_Container').show();
            $scope.respMessage = data.message;
            $scope.backToList();
        });
        res.error(function (data, status, headers, config) {
            $('#responseMsg_Container').addClass('panel panel-danger');
            $('#responseMsg_Container').show();
            $scope.respMessage = data.errorVOs[0].message;
        });
    }

    $scope.logout = function () {
        authToken.basic = '';
        $scope.currentUser = '';
        $scope.isSessionEstablished = false;
        $('#responseMsg_Container').addClass('panel panel-success');
        $('#responseMsg_Container').show();
        $scope.respMessage = "Thank you, visit again..!"; 
        $('#sale_Container').hide();
        $('#loginSignUp_Container').show();
        $('#login-form-link').click();
    }

    function sales() {

        var getRes = $http.get(baseURL + '/sale/list', {
            headers: {
                'Content-Type': 'application/json;charset=utf-8;',
                'Authorization': 'Basic ' + authToken.basic
            }
        });
        getRes.success(function (data, status, headers, config) {
            $scope.sales = data.saleVOs;
        });

        getRes.error(function (data, status, headers, config) {
            $scope.sales = [];
        });
    }
});

//Sale Controller.
app.controller("saleController", function ($scope, $http) {

});

//custom directives.
app.directive('registerForm', function () {
    return {
        restrict: 'E',
        templateUrl: './template/registerform-template.html'
    };
});

app.directive('loginForm', function () {
    return {
        restrict: 'E',
        templateUrl: './template/login-form-template.html'
    };
});

app.directive('saleListView', function () {
    return {
        restrict: 'E',
        templateUrl: './template/sale-list-template.html'
    };
});

app.directive('saleDetailView', function () {
    return {
        restrict: 'E',
        templateUrl: './template/sale-detail-template.html'
    };
});


app.directive('saleCreateForm', function () {
    return {
        restrict: 'E',
        templateUrl: './template/sale-create-template.html'
    };
});

//services.
app.factory('sessionService', function ($rootScope) {
    var session = {};
    session.user = '';
    session.prepSession = function (user) {
        this.user = user;
        this.broadcastSession();
    }

    session.broadcastSession = function () {
        $rootScope.$broadcast('handleSession');
    }

    return session;
});
