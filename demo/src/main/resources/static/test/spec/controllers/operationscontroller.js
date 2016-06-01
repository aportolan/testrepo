'use strict';

describe('Controller: OperationscontrollerCtrl', function () {

  // load the controller's module
  beforeEach(module('usermessageApp'));

  var OperationscontrollerCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    OperationscontrollerCtrl = $controller('OperationscontrollerCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(OperationscontrollerCtrl.awesomeThings.length).toBe(3);
  });
});
