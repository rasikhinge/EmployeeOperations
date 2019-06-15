package controllers

import javax.inject.Inject
import model.Dashboard
import play.api.mvc.{AbstractController, ControllerComponents}
import service.EmployeeService

class DashboardController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def welcome() = Action {
    Ok("Welcome to Dashboard Page..")
  }

  def getDashboardDetails() = Action {

    val createCount = EmployeeService.getCreateEmployeeCount()
    val updateCount = EmployeeService.getUpdateEmployeeCount()
    val deleteCount = EmployeeService.getDeleteEmployeeCount()

    val dashboardData = Dashboard(createCount, updateCount, deleteCount)

    Ok(views.html.dashboardHome(dashboardData))
    /*Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello Dashboard"), Some("text/plain"))
    )*/
  }

  def getOperationDetails(operation: String) = Action {
    val affectedEmployees = operation.trim.toLowerCase() match {
      case "create" => EmployeeService.getCreatedEmployees()
      case "update" => EmployeeService.getUpdatedEmployees()
      case "delete" => EmployeeService.getDeletedEmployees()
    }

    Ok(views.html.viewOperationDetails(affectedEmployees))
  }
}
