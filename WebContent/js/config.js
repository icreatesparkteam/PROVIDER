var config = {
	"Server_IP" : "http://localhost:8080",
	"splogin" : "/iControlE-ServiceProvider/rest/auth/login",
	"splogout" : "/iControlE-ServiceProvider/rest/auth/logout",
	"devicelogin":"/iControlE-ServiceProvider/rest/auth/devicelogin",
		
	"createUser" : "/iControlE-ServiceProvider/rest/registration/create",
	"updateUser" : "/iControlE-ServiceProvider/rest/registration/update",
	
	"changepassword" : "/iControlE-ServiceProvider/rest/registration/changepassword",	
	"passwordRecovery" : "/iControlE-ServiceProvider/rest/registration/passwordRecovery",
	"setquestion":"/iControlE-ServiceProvider/rest/registration/setquestion",
	
	"userlist" : "/iControlE-ServiceProvider/rest/registration/userlist",
	"getrole":"/iControlE-ServiceProvider/rest/registration/getrole",
	
	
	"clusterlist":"/iControlE-ServiceProvider/rest/cluster/clusterlist",
	"clusterlistbyid":"/iControlE-ServiceProvider/rest/cluster/clusterlistbyid",
	"clustercommandlist":"/iControlE-ServiceProvider/rest/cluster/clustercommandlist",
	
	"creategateway" : "/iControlE-ServiceProvider/rest/gateway/create",
	"updategateway" : "/iControlE-ServiceProvider/rest/gateway/update",
	"getgatewaybyuser":"/iControlE-ServiceProvider/rest/gateway/getgatewaybyuser",
	"gatewaylist":"/iControlE-ServiceProvider/rest/gateway/gatewaylist",
	
	"addsmartdevice":"/iControlE-ServiceProvider/rest/gateway/addsmartdevice",
	"devicelist":"/iControlE-ServiceProvider/rest/gateway/devicelist",
	
	"getmanufacturerbyname":"/iControlE-ServiceProvider/rest/manufacturer/getmanufacturerbyname",
	"manufacturerlist":"/iControlE-ServiceProvider/rest/manufacturer/manufacturerlist"	
	
}