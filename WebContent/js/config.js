var config = {
//	"Server_IP" : "http://localhost:8080",
	"Server_IP":"http://52.27.53.182:8080",
	"login" : "/iControlE-ServiceProvider/rest/auth/login",
	"logout" : "/iControlE-ServiceProvider/rest/auth/logout",
	"devicelogin":"/iControlE-ServiceProvider/rest/auth/devicelogin",
		
	"createUser" : "/iControlE-ServiceProvider/rest/registration/create",
	"updateUser" : "/iControlE-ServiceProvider/rest/registration/update",
	"deleteUser":"/iControlE-ServiceProvider/rest/registration/delete",
	"getuserdetails":"/iControlE-ServiceProvider/rest/registration/getuserdetails",
	
	
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