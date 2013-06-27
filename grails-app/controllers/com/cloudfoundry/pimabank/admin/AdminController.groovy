package com.cloudfoundry.pimabank.admin

import com.cloudfoundry.pimabank.User;
import com.cloudfoundry.pimabank.MealOrder;

class AdminController {

	def springSecurityService
	
    def index() { 
		def userId = springSecurityService.principal.id
		def user = User.get(userId)
		[user: user,
		 order: MealOrder.getCurrent()]
	}
	
	def trigOrder(){
		println "enter trigOrder"
		def currentOrder = MealOrder.getCurrent()
		currentOrder.open = !currentOrder.open
		currentOrder.save(flush: true)
		
		render currentOrder.open
		
	}
	
}
