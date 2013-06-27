package com.cloudfoundry.pimabank

class Cart {

	List positions
    static hasMany = [positions: CartPosition]

    static constraints = {
    }
}
