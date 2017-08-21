app.factory('UserService', function($http) {
	
	console.log('**********From userservice.js => Entering UserService');
	var userService = {};
	var BASE_URL = "http://localhost:1010/restResource/"
	
	
	userService.registerUser = function(user) {
		console.log('**********From UserService.js => registerUser() => calling REST CONTROLLER for /register')
		return $http.post(BASE_URL   +"/register", user)
	}
	
	
	
	userService.login = function(user) {
		console.log('**********From UserService.js => login() => calling REST CONTROLLER for /login')
		return $http.post(BASE_URL + "/login", user)
	}
	
	userService.logout=function(){
		console.log('**********From UserService.js => logout() => calling REST CONTROLLER for /logout')
		return $http.put(BASE_URL + "/logout")
	}
	
	
	userService.home = function() {
		console.log('**********From UserService.js => Home() => calling REST CONTROLLER for /home')
		return $http.post(BASE_URL + "/home")
	}
	
	
	userService.getAllUsers=function(){
		console.log('**********From UserService.js => getAllUsers() => calling REST CONTROLLER for /getUsers')
		return $http.get(BASE_URL +"/get/alluser")
	}
	
	
	
   return userService;
	
});