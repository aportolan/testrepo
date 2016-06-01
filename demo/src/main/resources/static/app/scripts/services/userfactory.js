'use strict';

/**
 * @ngdoc service
 * @name usermessageApp.UserFactory
 * @description
 * # UserFactory
 * Factory in the usermessageApp.
 */
angular.module('usermessageApp')
  .factory('userFactory', function ($http) {

   var linkRoot = "users";
   var requestObjectWrapper = {"payload":{},"offset":0,"limit":10};
    return {
 	getUsers: function (requestObject,position) {
	    requestObjectWrapper.payload=requestObject;
 	    requestObjectWrapper.offset=position;
            return $http.post(linkRoot+"/get",
        	requestObjectWrapper);
        },
        saveUsers: function (requestObject) {
	    requestObjectWrapper.payload=requestObject;
            return $http.post(linkRoot+"/save",
        	requestObjectWrapper);
        },
        deleteUsers: function (requestObject) {
	    requestObjectWrapper.payload=requestObject;
            return $http.post(linkRoot+"/delete",
        	requestObjectWrapper);
        },
        initLoadUsers: function () {
            return $http.post(linkRoot+"/initLoad");
        },
	// create for all users via (uid/tag/name/)
        saveAllUsers: function (requestObject) {
	    requestObjectWrapper.payload=requestObject;
            return $http.post(linkRoot+"/saveAll",
        	requestObjectWrapper);
        }
    };
  });
