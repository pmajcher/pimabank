package com.cloudfoundry.pimabank

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
   	List operations
    static hasMany = [operations: Operation]


/*	def addOperation(Operation operation){
		if(operations == null){
		operations = new ArrayList<Operation>();			
		}
		operations.put(operation);
	}*/

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<UserRole> getAuthorities() {
		UserUserRole.findAllByUser(this).collect { it.userRole } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	static User findByOperation(Operation operation){
		def criteria = User.createCriteria()
		def result = criteria.get {
			operations {
				idEq(operation.id)
			}
		}
		return result
	}
}
