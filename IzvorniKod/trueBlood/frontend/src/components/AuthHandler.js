import axios from 'axios'
import { SPRING_URL } from './Constants'

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'username'
export const USER_PASSWORD_SESSION_ATTRIBUTE_NAME = 'password'
export const USER_ROLE_SESSION_ATTRIBUTE_NAME = 'user'

class AuthHandler {

    executeBasicAuthenticationService(username, password) {
        return axios.get(SPRING_URL.concat('/user/login'),
            { headers: { authorization: this.createBasicAuthToken(username, password) } })
    }

    createBasicAuthToken(username, password) {
        return 'Basic ' +  window.btoa(username + ":" + password)
    }

    registerSuccessfulLogin(username, password, role) {
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME, username)
        sessionStorage.setItem(USER_PASSWORD_SESSION_ATTRIBUTE_NAME, password)
        sessionStorage.setItem(USER_ROLE_SESSION_ATTRIBUTE_NAME, role)
        this.setupAxiosInterceptors(this.createBasicAuthToken(username, password))
    }

    logout() {
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
        sessionStorage.removeItem(USER_PASSWORD_SESSION_ATTRIBUTE_NAME);
        sessionStorage.removeItem(USER_ROLE_SESSION_ATTRIBUTE_NAME);
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (user === null) return false
        return true
    }

    getLoggedInUserName() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)
        if (user === null) return ''
        return user
    }

    getLoggedInPassword() {
        return sessionStorage.getItem(USER_PASSWORD_SESSION_ATTRIBUTE_NAME);
    }

    getLoggedInRole(){
        return sessionStorage.getItem(USER_ROLE_SESSION_ATTRIBUTE_NAME);
    }

    setupAxiosInterceptors(token) {
        axios.interceptors.request.use(
            (config) => {
                if (this.isUserLoggedIn()) {
                    config.headers.authorization = token
                }
                return config
            }
        )
    }
}

export default new AuthHandler()