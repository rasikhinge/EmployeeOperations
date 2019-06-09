package controllers

import akka.util.ByteString
import javax.inject.Inject
import play.api.http.HttpEntity
import play.api.mvc.{AbstractController, ControllerComponents, ResponseHeader, Result}

class DashboardController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def welcome() = Action {
    Ok("Welcome to Dashboard Page..")
  }

  def helloDashboard() = Action {
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello Dashboard"), Some("text/plain"))
    )
  }
}
