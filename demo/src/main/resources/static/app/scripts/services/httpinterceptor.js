'use strict';

/**
 * @ngdoc service
 * @name usermessageApp.UserFactory
 * @description
 * # UserFactory
 * Factory in the usermessageApp.
 */
$provide.module('usermessageApp')
  .factory('httpInterceptor', function () {

     return {
		'request': function(config) {
			config.headers['Accept'] = 'application/json;charset=UTF-8';
			config.headers['Content-Type']= 'application/json;charset=UTF-8';
			config.method='POST';
     			return config;
    		}
            };
  });
$httpProvider.interceptors.push('httpInterceptor');
