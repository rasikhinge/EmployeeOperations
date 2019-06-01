package service

import model.Employee

import scala.collection.mutable.ListBuffer

class EmployeeService {


}

object EmployeeService {

  private val employees: ListBuffer[Employee] = new ListBuffer()

  def getEmployees(): ListBuffer[Employee] = employees

  def addEmployee(emp: Employee) = {
    employees += emp
    println("addEmployee " + employees)
  }

  def getEmployee(name: String): Option[Employee] = {
    employees.find(e => e.name.equals(name))
  }

}
