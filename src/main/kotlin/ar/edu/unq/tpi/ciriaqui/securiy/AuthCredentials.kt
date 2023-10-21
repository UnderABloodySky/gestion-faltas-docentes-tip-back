package ar.edu.unq.tpi.ciriaqui.securiy
class AuthCredentials {
    var email: String = ""
    var password: String = ""

    constructor() {}

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
}
