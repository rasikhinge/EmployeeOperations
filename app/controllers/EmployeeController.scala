package controllers

import javax.inject.Inject
import model.Employee
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AbstractController, ControllerComponents}
import service.EmployeeService

class EmployeeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  //val logger = Logger.getLogger(this.getClass.getName)

  def home() = Action {
    Ok(views.html.employeeHome())
  }

  val employeeForm: Form[Employee] = Form {
    mapping(
      "name" -> text
    )(Employee.apply)(Employee.unapply)
  }

  def createEmployeeForm() = Action {
    Ok(views.html.employeeCreate())
  }

  def createEmployee() = Action { implicit request =>
    val employee = employeeForm.bindFromRequest().get
    //logger.info("create employee = " + employee)
    println("create employee = " + employee)
    EmployeeService.addEmployee(employee)
    Ok(views.html.employees(EmployeeService.getEmployees()))
  }

  def viewEmployees() = Action {
    val employees = EmployeeService.getEmployees()
    Ok(views.html.employees(employees));
  }

  def editEmployee(name: String) = Action {
    val emp = EmployeeService.getEmployee(name)
    val emp1 = EmployeeService.getEmployee(name) match {
      case Some(x) => x
      case None => new Employee("Not Found")
    }

    println(emp1)
    Ok(views.html.employeeEdit(emp1))
  }

  def updateEmployee() = Action { implicit request =>
    val employee = employeeForm.bindFromRequest().get
    println("employee" + employee)
    Ok(views.html.employees(EmployeeService.getEmployees()))
  }

}
