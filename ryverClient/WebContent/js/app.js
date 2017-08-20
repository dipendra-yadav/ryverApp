var app = angular.module("myApp", [ 'ngRoute', 'ngCookies' ]);
app.config(function($routeProvider) {
	console.log("**********From App.js => Entering myApp");

	$routeProvider

	// loads this page first - home
	.when('/', {
		templateUrl : '/index.html'
	})
	// Home
	.when('/home', {
		templateUrl : '_user/login.html'
	})

	// Login
	.when('/login', {
		controller : 'UserController',
		templateUrl : '_user/login.html'
	})

	// for a new user to register
	.when('/register', {
		controller : 'UserController',
		templateUrl : '_user/register.html'
	})

});
app.run(function($cookieStore, $rootScope, $location, UserService) {

	console.log("run hit****");
	if ($rootScope.currentUser == undefined) {
		$rootScope.currentUser = $cookieStore.get('currentUser');

	}

	$rootScope.logout = function() {
		console.log('logout function')
		delete $rootScope.currentUser;
		$cookieStore.remove('currentUser')
		UserService.logout().then(function(response) {
			console.log("logged out successfully..");
			
			//$scope.message = "Logged out Successfully";
			$location.path('/login')
		}, function(response) {
			console.log(response.status);
		})

	}

});
