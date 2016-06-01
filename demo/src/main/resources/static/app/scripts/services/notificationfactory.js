'use strict';

/**
 * @ngdoc service
 * @name usermessageApp.UserFactory
 * @description
 * # UserFactory
 * Factory in the usermessageApp.
 */
angular.module('usermessageApp')
  .factory('notificationFactory', function () {
	toastr.options = {
	  "closeButton": false,
	  "debug": false,
	  "newestOnTop": false,
	  "progressBar": false,
	  "positionClass": "toast-top-full-width",
	  "preventDuplicates": false,
	  "onclick": null,
	  "showDuration": "300",
	  "hideDuration": "1000",
	  "timeOut": "5000",
	  "extendedTimeOut": "4000",
	  "showEasing": "swing",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}
     return {
                success: function () {
                    toastr.success("Success");
                },
                error: function (text) {
                    toastr.error(text, "Error!");
                },
                progress: function (text) {
                    toastr.info(text, "Progress:");
                }
            };
  });
