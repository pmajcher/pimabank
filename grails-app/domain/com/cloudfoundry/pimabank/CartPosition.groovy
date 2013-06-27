package com.cloudfoundry.pimabank

class CartPosition implements Comparable{

    Meal meal

    static belongsTo = Cart

    int compareTo(obj) {
        meal.name.compareTo(obj.meal.name)
    }

    static constraints = {
    }
}
