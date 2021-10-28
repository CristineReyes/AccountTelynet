package com.devcristine.accounttelynet

import java.util.*


/**
 * Created by Cristine R.M. on 27/10/2021.
 * DevCristineAguirre
 */
data class AccountModel (
    var code: Int = getAutoCode(),
    var name : String="",
    var phone: String="",
    var email: String="",
    var visited: String=""


){
    companion object{
        fun getAutoCode():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}