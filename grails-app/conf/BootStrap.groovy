import tk.pimabank.Meal
import tk.pimabank.User
import tk.pimabank.UserRole
import tk.pimabank.UserUserRole
import tk.pimabank.Requestmap

class BootStrap {

    def springSecurityService

    def init = { servletContext ->

        def userRole = UserRole.findByAuthority('ROLE_USER') ?: new UserRole(authority: 'ROLE_USER').save(failOnError: false)
        def adminRole = UserRole.findByAuthority('ROLE_ADMIN') ?: new UserRole(authority: 'ROLE_ADMIN').save(failOnError: false)


	    new Requestmap(url: '/user/**', configAttribute: 'ROLE_ADMIN').save(failOnError: false)
		new Requestmap(url: '/admin/**', configAttribute: 'ROLE_ADMIN').save(failOnError: false)
  //      new Requestmap(url: '/account/**', configAttribute: 'ROLE_USER, ROLE_ADMIN').save(failOnError: false)
 //       new Requestmap(url: '/orderPeperone/**', configAttribute: 'ROLE_USER, ROLE_ADMIN').save(failOnError: false)
        new Requestmap(url: '/history/**', configAttribute: 'ROLE_USER, ROLE_ADMIN').save(failOnError: false)
		new Requestmap(url: '/', configAttribute: 'ROLE_USER, ROLE_ADMIN').save(failOnError: false)
		 


        def adminUser = User.findByUsername('admin') ?: new User(
                username: 'admin',
                password: 'admin',
                enabled: true).save(failOnError: false)

        if (!adminUser.save()){
            log.error "${adminUser.errors.allErrors}"
        }

        if (!adminUser.authorities.contains(adminRole)) {
            UserUserRole.create adminUser, adminRole
        }
		
		def user = User.findByUsername('marz') ?: new User(
			username: 'marz',
			password: 'marz',
			enabled: true).save(failOnError: false)
			
		if (!user.save()){
			log.error "${user.errors.allErrors}"
		}

		if (!user.authorities.contains(userRole)) {
			UserUserRole.create user, userRole
		}
		
    }
    def destroy = {
    }
}
