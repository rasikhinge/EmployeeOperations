package controllers

import javax.inject.Inject
import model.{LoginBean, RegisterBean}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AbstractController, ControllerComponents, Session}
import service.LoginService

class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def loginPage() = Action {
    Ok(views.html.login())
  }


  val loginForm = Form(
    mapping(
      "username" -> text,
      "password" -> text
    )(
      (username, password) => new LoginBean(username, password)
    )(
      (loginBean: LoginBean) => Some(loginBean.userName, loginBean.password)
    )
  )

  def login() = Action { implicit request =>
    val bean = loginForm.bindFromRequest().get


    val maybeBoolean = LoginService.authenticate(bean)
    val isAuthenticated = maybeBoolean.fold(false)(b => b)

    if (isAuthenticated)
      Redirect(routes.EmployeeController.home())
        .withSession(new Session(Map("user" -> bean.userName)))
    else
      Unauthorized("Invalid username or password")
  }

  def welcome() = Action {
    Ok(views.html.welcome())
  }

  val registerForm = Form(
    mapping(
      "name" -> text,
      "age" -> number,
      "email" -> email,
      "password" -> text
    )(
      (name, age, email, password) => new RegisterBean(name, age, email, password)
    )(
      (registerBean: RegisterBean) => Some(registerBean.name, registerBean.age, registerBean.email, "")
    )
  )

  def registerUser() = Action { implicit request =>
    val user = registerForm.bindFromRequest().get
    LoginService.registerUser(user)
    Ok(views.html.login())
  }

  def register() = Action {
    Ok(views.html.register())
  }

  def logOut() = Action {
    Ok(views.html.welcome())
  }
}
