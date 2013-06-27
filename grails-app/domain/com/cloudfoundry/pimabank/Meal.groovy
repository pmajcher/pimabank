package com.cloudfoundry.pimabank

class Meal extends Operation{

	static belongsTo = [order: MealOrder]

    static constraints = {
    }
}
