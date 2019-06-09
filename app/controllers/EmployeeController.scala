package controllers

import javax.inject.Inject
import model.Employee
import play.api.data.Forms._
import play.api.data._
import play.api.mvc.{AbstractController, ControllerComponents}
import service.EmployeeService

class EmployeeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  //val logger = Logger.getLogger(this.getClass.getName)

  def home() = Action {
    Ok(views.html.employeeHome())
  }

  /*val employeeForm: Form[Employee] = Form {
    mapping(
      "name" -> text,
      "age" -> number
    )(Employee.apply)(Employee.unapply)
  }*/
  /*
    val employeeForm = Form(
      mapping(
        "name" -> text,
        "age" -> number,
        "accept" -> checked("accept terms and conditions")
      ),(
        (name, age, _) => Employee(name, age),
        (emp: Employee) => Some(emp.name, emp.age, false)
      ),null

    )*/
  /*  val employeeForm = Form(
      mapping(
        "name" -> text,
        "age" -> number
      )(Employee.apply)(Employee.unapply)
    )*/

  /*val employeeForm = Form(
    mapping(
      "name" -> text,
      "age" -> number(min = 1,max = 99)
    )(
      (name, age) => Employee(name, age)
    )(
      (emp: Employee) => Option(emp.name, emp.age)
    )
  )*/

  def validate(emp: Employee): Boolean = {
    emp match {
      case Employee(name, age) =>
        if (!"Admin".equals(name)) {
          println("1")
          if (age > 18) {
            println("2")
            true
          } else {
            println("3")
            false
          }
        } else {
          println("4")
          true
        }
      case _ =>
        println("5")
        false
    }
  }

  val employeeForm = Form(
    mapping(
      "name" -> text,
      "age" -> number
    )(
      (name, age) => Employee(name, age)
    )(
      (emp: Employee) => Some(emp.name, emp.age)
    ).verifying(
      "Failed from constraints = ",
      fields =>
        fields match {
          case emp => validate(emp)
        }
    )

  )

  def createEmployeeForm() = Action {
    Ok(views.html.employeeCreate())
  }

  /*def createEmployee() = Action { implicit request =>
    val employee = employeeForm.bindFromRequest().fold(
      formWithErrors =>{
        println(formWithErrors)
        BadRequest("Error due to validations")
      },
      emp =>{
        emp
      }
    )
    val empValue = employee match {
      case Employee(name,age) => Employee(name,age)
      case _ => Nil
    }

    //logger.info("create employee = " + employee)
    println("create employee = " + employee)
    println("create Product = " + empValue)
    println("create Product 1= " + empValue.productElement(0))
    println("create Product 2= " + empValue.productElement(1))
  //  EmployeeService.addEmployee()
    Ok(views.html.employees(EmployeeService.getEmployees()))
  }*/

  def createEmployee() = Action(parse.form(employeeForm)) { implicit request =>

    //logger.info("create employee = " + employee)
    println("creating employee = " + request.body)
    //  EmployeeService.addEmployee()
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
      case None => new Employee("Not Found", 0)
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
