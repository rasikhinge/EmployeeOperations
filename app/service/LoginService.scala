package service

import model.{LoginBean, RegisterBean}

import scala.collection.mutable.ListBuffer

object LoginService {

  var users: ListBuffer[RegisterBean] = new ListBuffer();

  def authenticate(loginBean: LoginBean) = {
    users.find(u => u.name.equals(loginBean.userName))
      .map(u => u.password.equals(loginBean.password))
  }

  def registerUser(newUser: RegisterBean): Unit = {
    users += newUser
  }

}
