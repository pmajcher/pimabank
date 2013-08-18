package tk.pimabank

import org.hibernate.Hibernate
import tk.pimabank.data.OperationDTO

import groovy.sql.Sql

class HomeController {

	def springSecurityService
	
	def dataSource 
	
    def index() {
		
		def user = User.get(springSecurityService.principal.id)
//		params.max = 10;
		
		def dtoOperations = new ArrayList<OperationDTO>()
		def operations = Operation.findAllByUser(user, params)
		
		def accountState = user.getAccountState()[0];
		def notApprovedAmount =  user.getAccountState()[1];
		
		[	principal: springSecurityService.principal,
			order: MealOrder.getCurrent(),	
			accountState: accountState,
			notApprovedAmount: notApprovedAmount]
		
	}
	
	def ajaxTest(){
		println "enter ajax test"
		def user = User.get(springSecurityService.principal.id)
		def operations = Operation.findAllByUser(user);
		def operationList = new HashMap<BigInteger>() 
		
		
		def BigDecimal amount = 0
		
		def number = Operation.findAll("from Operation as o where o.approved = true and (o.user=:user or o.recipient=:user )",
				[user: user]).size()
		
		Long [][] array = new Long[number][2];
		Long [] array2 = new Long[number];
		println "number: "+number
		
//		Operation.findAllApprovedByUser(user, [sort: 'dateCreated', order:'asc']).eachWithIndex(){		
		Operation.findAll("from Operation as o where o.approved = true and (o.user=:user or o.recipient=:user )",
			[user: user]).eachWithIndex(){
			it, i ->
			switch (it) {
				case Meal:
					if(it.agregator != null){
						amount -= (it.agregator.mealPrice *  it.partOfMeal) / 8
					}else{
						amount -= it.amount
					}
				break;
				case Loan:
				case Transfer:
					amount -= it.amount
				break;
				case Payment:
				case Refund:
					amount += it.amount
				break;
			}
			println "i: "+i + ", amount: "+amount
			array[i][0] = it.dateCreated.getTime(); 
			array[i][1] = amount;
			array2[i] = amount;
		}	
		getAccountState()
		
		render(contentType: "text/json") {
			categories = array
		}
	}
	
	def getAccountState(){
		println "enter getAccountState"
		
		def user = User.get(springSecurityService.principal.id)
		def operations = Operation.findAllByUser(user, params)
		
		
		def accountState = user.getAccountState()[0];
		def notApprovedAmount =  user.getAccountState()[1];
		
		render(template:"accountState",model:[accountState: accountState,
			 notApprovedAmount: notApprovedAmount ])
	}
	
	def bankState(){
		println "enter bankState"
		
		def bankState = 0
		def bankStateNoApproved = 0;
		 User.findAll().each(){
			 bankState += it.getAccountState()[0];
			 bankStateNoApproved += it.getAccountState()[1];
		 }
	//	params.max = 100;
		
		def dtoOperations = new ArrayList<OperationDTO>()

		
		[principal: springSecurityService.principal,
		 bankState: bankState,
		 bankStateNoApproved: bankStateNoApproved]
	}
	
	
	def getBankHistory(){

		def sql = new Sql(dataSource)
		
		StringBuilder query = new StringBuilder();
		query.append("select cast( date_created as date ) as date_created1, sum(amount) as amount from")
		query.append(" ( " )
		query.append("		select id, -(((part_of_meal)*(select meal_price from meal_agregator where id = agregator_id))/8  ) as amount, ")
		query.append(" 				date_created from operation where class = 'tk.pimabank.Meal' and approved = 1")
		query.append(" union ")
		query.append("		select id, -amount, date_created from operation where class = 'tk.pimabank.Loan' and approved = 1 ")
		query.append(" union ")
		query.append("		select id, amount, date_created from operation where class = 'tk.pimabank.Payment' and approved = 1")
		query.append(" union ")
		query.append("		select id, amount, cast( date_created as date ) as date_created from operation where class = 'tk.pimabank.Refund' and approved = 1")		
		query.append(" ) as oper")
		query.append(" group by date_created1");

		def result = sql.rows(query.toString());
		
		println "result: " + result
		
		def operations = Operation.findAll();
		def operationList = new HashMap<BigInteger>()
		
		
		def BigDecimal amount = 0
		
		int number = result.size()
		def all = number
		number = all //number > 10 ? 10 : number
		
		Long [][] array = new Long[number][2];

		def currentState = 0
		result.eachWithIndex(){
			it, i -> 
			if(i >= all - number){
				println "i: "+i + ", amount: "+it.amount + "  i - number - all: " + (i + number - all)
				currentState += it.amount
				array[i + number - all][0] = it.date_created1.getTime()
				array[i + number - all][1] = currentState	
			}
			
		}
		println "array: "+array
		render(contentType: "text/json") {
			categories = array
		}
	}
}
