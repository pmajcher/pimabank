package tk.pimabank

import org.apache.commons.lang.StringUtils;
import org.apache.ivy.core.module.descriptor.ExtendsDescriptor;



class Transfer extends Operation{

	static hasOne = [recipient: User]
	
	def beforeValidate() {
		println "enter transfer validator"
		if(amount < 0){
			errors.rejectValue("amount", "transfer.amount.validate.negative")
		}
		if(StringUtils.isEmpty(description)){
			errors.rejectValue("amount", "transfer.description.validate.empty")
		}
		if(recipient.equals(user)){
			errors.rejectValue("recipient", "transfer.recipient.validate.invalid")
		}
	}
}
