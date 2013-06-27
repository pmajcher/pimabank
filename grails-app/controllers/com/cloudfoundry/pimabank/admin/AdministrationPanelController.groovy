package com.cloudfoundry.pimabank.admin

class AdministrationPanelController {
	
	def springSecurityService
	
    def index() { 
		
		def userId = springSecurityService.principal.id
		def user = User.get(userId)
		[user: user]
	}
}
