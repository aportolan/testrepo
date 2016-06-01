'use strict';

/**
 * @ngdoc function
 * @name usermessageApp.controller:OperationscontrollerCtrl
 * @description
 * # OperationscontrollerCtrl
 * Controller of the usermessageApp
 */
angular.module('usermessageApp')
  .controller('operationsCtrl', function ($scope,$stomp,userFactory,messageFactory, notificationFactory) {

	 $scope.messages = [];	
 	 $scope.users = [];
	 var messageSubscription;
	 var pagingPositionUsers=0;
	 var pagingPositionMessages=0;
	 $scope.userSelected=false;


	// -------------------------------------------- USERS ------------------------------------------     

            $scope.addModeUsers = false;
 
            $scope.toggleAddModeUsers = function () {
                $scope.addModeUsers = !$scope.addModeUsers;
            };
     	    var getUsersSuccessCallback = function (data, status) {
                $scope.users = data.payload;
		
            };
            $scope.toggleEditModeUsers = function (user) {
                user.editMode = !user.editMode;
            };
	    var successCallbackUsers = function (data, status, headers, config) {
		notificationFactory.success();
		pagingPositionUsers = 0;
		$scope.userSelected = false;
		return userFactory.getUsers(null,pagingPositionUsers).success(getUsersSuccessCallback).error(errorCallback);
	    };
	 
	    var successPostCallbackUsers = function (data, status, headers, config) {
		successCallbackUsers(data, status, headers, config).success(function () {
		    $scope.toggleAddModeUsers();
		    $scope.user = {};
		    $scope.selectedUser = {};
		});
	    };
	    $scope.addUsers = function () {
		userFactory.saveUsers($scope.user).success(successPostCallbackUsers).error(errorCallback);
		$scope.userSelected = false;
	    };
	    $scope.updateUsers = function (user) {
		userFactory.saveUsers(user).success(successPostCallbackUsers).error(errorCallback);
	    };
	    $scope.deleteUsers = function (user) {
		userFactory.deleteUsers(user).success(successPostCallbackUsers).error(errorCallback);
		$scope.userSelected = false;
	    };
	    $scope.initLoadUsers = function () {

      		$stomp.connect('/usermessage/messageResponder', {})
			.then(function (frame) {
    			messageSubscription = $stomp.subscribe('/messageTopic/initLoad', messagingCallback, {});
		});
		userFactory.initLoadUsers().success(successPostCallbackUsers).error(errorCallback);

	
	    };
	    $scope.refreshUsers = function () {
		if($scope.searchUser!=null){
			if($scope.searchUser.uid=="")
				$scope.searchUser.uid = null;
			if($scope.searchUser.name=="")
				$scope.searchUser.name = null;
			if($scope.searchUser.tag=="")
				$scope.searchUser.tag = null;
		}
		pagingPositionUsers = 0;
		$scope.userSelected = false;
		return userFactory.getUsers($scope.searchUser,pagingPositionUsers).success(getUsersSuccessCallback).error(errorCallback);
	    };
	    $scope.getMessagesForUser = function (user) {
		var message = {mid:"",title:"",body:"",validity:"",user:{uid:user.uid}};
		$scope.selectedUser = user;
		pagingPositionMessages = 0;
		return messageFactory.getMessages(message,pagingPositionMessages).success(getMessagesSuccessCallback).error(errorCallback);
	    };
	    $scope.pagingUsersNext = function () {
		if($scope.users.length < 10 )
			return;
	   	pagingPositionUsers=pagingPositionUsers+10;
		$scope.userSelected = false;
		return userFactory.getUsers($scope.searchUser,pagingPositionUsers).success(getUsersSuccessCallback).error(errorCallback);
	    };
	    $scope.pagingUsersPrevious = function () {
		if(pagingPositionUsers<10)
			return ;
		pagingPositionUsers=pagingPositionUsers-10;
		$scope.userSelected = false;
		return userFactory.getUsers($scope.searchUser,pagingPositionUsers).success(getUsersSuccessCallback).error(errorCallback);
	    };
	// -------------------------------------------- MESSAGES ------------------------------------------   

            $scope.addModeMessages = false;
 
            $scope.toggleAddModeMessages = function () {
                $scope.addModeMessages = !$scope.addModeMessages;
            };
      	    var getMessagesSuccessCallback = function (data, status) {

                $scope.messages = data.payload;
		$scope.userSelected = true;
            };
            $scope.toggleEditModeMessages = function (message) {
                message.editMode = !message.editMode;
            }; 
	    var successCallbackMessages = function (data, status, headers, config) {
		data.payload.user.messages = null;
		var message = {mid:"",title:"",body:"",validity:"",user:data.payload.user};
		notificationFactory.success();
		pagingPositionMessages = 0;
		return messageFactory.getMessages(message,pagingPositionMessages).success(getMessagesSuccessCallback).error(errorCallback);
	    };
	 
	    var successPostCallbackMessages = function (data, status, headers, config) {
		successCallbackMessages(data, status, headers, config).success(function () {
		    $scope.toggleAddModeMessages();
		    $scope.message = {};
		});
	    };
	    $scope.addMessages = function () {
		$scope.message.user = {uid:$scope.selectedUser.uid};
		messageFactory.saveMessages($scope.message).success(successPostCallbackMessages).error(errorCallback);
	    };
	    $scope.saveAllMessages = function () {
		$scope.message.user = null;
		messageFactory.saveAllMessages($scope.message).success(successPostCallbackMessages).error(errorCallback);
	    };
	    $scope.updateMessages = function (message) {
		messageFactory.saveMessages(message).success(successPostCallbackMessages).error(errorCallback);
	    };
	    $scope.deleteMessages = function (message) {
		var messageArray =[message]; 
		messageFactory.deleteMessages(messageArray).success(successPostCallbackMessages).error(errorCallback);
	    };
	    $scope.refreshMessages = function () {
		$scope.searchMessage = {user:{uid:$scope.selectedUser.uid}};
	        if($scope.searchMessage!=null){
			if($scope.searchMessage.mid=="")
				$scope.searchMessage.mid = null;
			if($scope.searchMessage.title=="")
				$scope.searchMessage.title = null;
			if($scope.searchMessage.body=="")
				$scope.searchMessage.body = null;
			if($scope.searchMessage.validity=="")
				$scope.searchMessage.validity = null;
			
		}
		
 		pagingPositionMessages = 0;
		return messageFactory.getMessages($scope.searchMessage,pagingPositionMessages).success(getMessagesSuccessCallback).error(errorCallback);
	    };
	    $scope.pagingMessagesNext = function () {
		if($scope.messages.length < 10 )
			return;
 		pagingPositionMessages=pagingPositionMessages+10;
		return userFactory.getMessages($scope.searchMessage,pagingPositionMessages).success(getMessagesSuccessCallback).error(errorCallback);
	    };
	    $scope.pagingMessagesPrevious = function () {
		if(pagingPositionMessages<10)
			return ;
		pagingPositionMessages=pagingPositionMessages-10;
		return userFactory.getMessages($scope.searchMessage,pagingPositionMessages).success(getMessagesSuccessCallback).error(errorCallback);
	    };
	//-------------------------------------------- GENERAL ----------------------------------------------
	    $scope.confirmAlert = function(text) {
		return confirm(text);
  	    };
	    var errorCallback = function (data, status, headers, config) {
		notificationFactory.error(data.errorMessage);
	    };
	    var messagingCallback = function (payload, headers, res) {
            	notificationFactory.progress(res.body);
            };
	     
  });
