package com.cloudfoundry.pimabank

import groovyx.net.http.*
import groovy.util.slurpersupport.NodeChild

class OrderController {

    def index() {



    }

    List<Meal> getAllMeals(){

     //   def list = [new MealOrder(name:  "Kurczak", price:  "13.6")]
     //   return list
        Meal.getAll()
    }


}



