package tk.pimabank

class User {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
   	List operations
    static hasMany = [operations: Operation, incommingCharges: Transfer]

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}
	static mappedBy = [operations: "user", incommingCharges: "recipient"]
	

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
	
	def getAccountState(){
		
		def accountState = 0;
		def notApprovedAmount = 0;
		
		Operation.findAll("from Operation as o where o.user=:user or o.recipient=:user ",
			[user: this]).each(){
//		operations.each(){
				switch (it) {
					case Meal:
						if(it.approved && it.agregator != null){
							accountState -= (it.agregator.mealPrice *  it.partOfMeal) / 8
							
						}else if (it.approved && it.agregator == null){
							accountState -= it.amount
						}
						break;
					case Loan:
					case Transfer:
						if(it.approved){
							accountState -= it.amount
						}else{
							notApprovedAmount  += it.amount
						}
					break;
					case Payment:
					case Refund:
						if(it.approved){
							accountState += it.amount
						}else{
							notApprovedAmount  += it.amount
						}
					break;
				}
		}
		[accountState, notApprovedAmount]
	}
	
}
