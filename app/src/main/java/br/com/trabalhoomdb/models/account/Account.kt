package br.com.trabalhoomdb.models.account

import java.io.Serializable

class Account : Serializable {
    lateinit var id: String
    lateinit var email: String
    lateinit var password: String
    lateinit var name: String
}