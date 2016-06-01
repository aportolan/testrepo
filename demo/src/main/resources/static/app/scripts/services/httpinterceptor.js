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
			config.headers['Accept'] = 'application/json';
			config.headers['Content-Type']= 'application/json';
			config.method='POST';
     			return config;
    		}
            };
  });
$httpProvider.interceptors.push('httpInterceptor');
