'use strict';

/**
 * @ngdoc service
 * @name usermessageApp.MessageFactory
 * @description
 * # MessageFactory
 * Factory in the usermessageApp.
 */
angular.module('usermessageApp')
  .factory('messageFactory', function ($http) {


   var linkRoot = "messages";
   var requestObjectWrapper = {"payload":{},"offset":0,"limit":10};

    return {
 	getMessages: function (requestObject,position) {
	    requestObjectWrapper.payload=requestObject;
	    requestObjectWrapper.offset=position;
            return $http.post(linkRoot+"/get",
        	requestObjectWrapper);
        },
        saveMessages: function (requestObject) {
	    requestObjectWrapper.payload=requestObject;
            return $http.post(linkRoot+"/save",
        	requestObjectWrapper);
        },
        deleteMessages: function (requestObject) {
	    requestObjectWrapper.payload=requestObject;
            return $http.post(linkRoot+"/delete",
        	requestObjectWrapper);
        },
	// create for all users via (uid/tag/name/)
        saveAllMessages: function (requestObject) {
	    requestObjectWrapper.payload=requestObject;
            return $http.post(linkRoot+"/saveAll",
        	requestObjectWrapper);
        }
    };
  });
