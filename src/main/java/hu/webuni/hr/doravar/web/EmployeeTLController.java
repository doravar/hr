package hu.webuni.hr.doravar.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.doravar.model.Employee;

@Controller
public class EmployeeTLController { // for Thymeleaf

	private List<Employee> allEmployees = new ArrayList<>();

//	{
//		allEmployees.add(new Employee(1L, "Nyúl Péter", "programmer", 500000, LocalDate.of(2020, 01, 01)));
//		allEmployees.add(new Employee(2L, "Macska Jancsi", "manager", 700000, LocalDate.of(2020, 01, 01)));
//		allEmployees.add(new Employee(3L, "Füttyös Vilkó", "programmanager", 600000, LocalDate.of(2020, 01, 01)));
//	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/employees")
	public String listEmployees(Map<String, Object> model) {			// model: adat-szállító, key a neve, ez alapján azonosítja be a template, value az adat, itt megkapja a listát, és egy üres Employee-t, ezek jelennek meg a template-ben
		model.put("employees", allEmployees);
		model.put("newEmployee", new Employee());
		return "employees";
	}

	@PostMapping("/employees")
	public String addEmployee(Employee employee) {
		allEmployees.add(employee);
		return "redirect:employees";
	}

	@GetMapping("/employees/{id}") // paraméterezhető uri, {}-ben a paraméter + pathariable annotáció
	public String editEmployee(Map<String, Object> model, @PathVariable Long id) {
		model.put("employee", allEmployees.stream().filter(e -> e.getId() == id).findFirst().get());
		return "editEmployee";
	}
	
	@PostMapping("/editEmployee") // itt nem kell id-t megadni, mert paraméterként megy át az employee
	public String editEmployee(Employee employee) {
		for (ListIterator<Employee> it = allEmployees.listIterator(); it.hasNext();) {
			Employee employeeInList = it.next();
			if (employeeInList.getId() == employee.getId()) {
				it.set(employee);
				break;
			}
		}
		return "redirect:employees";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		for (Iterator<Employee> it = allEmployees.iterator(); it.hasNext();) {
			Employee employee = it.next();
			if (employee.getId() == id) {
				it.remove();
				break;
			}
		}
		return "redirect:/employees"; // itt azért kell / az employees elé, mert ha nincs, akkor a
										// deleteEmployee/employees-re irányít ami nincs
	}

}
