package service

import model.Employee

import scala.collection.mutable.ListBuffer

class EmployeeService {


}

object EmployeeService {

  private val employees: ListBuffer[Employee] = new ListBuffer()
  private val createEmployees: ListBuffer[Employee] = new ListBuffer()
  private val deleteEmployees: ListBuffer[Employee] = new ListBuffer()
  private val updateEmployees: ListBuffer[Employee] = new ListBuffer()

  def getEmployees(): ListBuffer[Employee] = employees

  def addEmployee(emp: Employee) = {
    employees += emp
    createEmployees += emp
    println("addEmployee " + employees)
  }

  def getEmployee(name: String): Option[Employee] = {
    employees.find(e => e.name.equals(name))
  }

  def deleteEmployee(emp: Employee): Unit = {
    employees -= emp
    deleteEmployees += emp
  }

  def updateEmployee(emp: Employee) = {
    getEmployee(emp.name) match {
      case Some(x) => {
        employees -= x
        employees += emp
        updateEmployees += emp
      }
      case None => new Employee("Not Found", 0)
    }
  }

  def getCreateEmployeeCount(): Int = {
    createEmployees.length
  }

  def getUpdateEmployeeCount(): Int = {
    updateEmployees.length
  }

  def getDeleteEmployeeCount(): Int = {
    deleteEmployees.length
  }

  def getCreatedEmployees() = {
    createEmployees
  }

  def getUpdatedEmployees() = {
    updateEmployees
  }

  def getDeletedEmployees() = {
    deleteEmployees
  }

}
