angular.module('usermessageApp')
	.directive('ngConfirmClick', [
        function(){
	    
            return {
		priority: -1,
      		restrict: 'A',
                link: function (scope, element, attr) {
                    var clickAction = attr.confirmedClick;
                    element.bind('click',function (event) {
			var msg = attr.ngConfirmClick || "Are you sure?";
			if(msg && !scope.confirmAlert(msg)){
				event.stopImmediatePropagation();
				event.preventDefault();
			  }

			});

                }
            };
    }])
