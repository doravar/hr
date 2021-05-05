package hu.webuni.hr.doravar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import hu.webuni.hr.doravar.model.CompanyType;
import hu.webuni.hr.doravar.service.InitDbService;
import hu.webuni.hr.doravar.service.SalaryService;


@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	SalaryService salaryService;
	
	@Autowired
	InitDbService initDbService;

	

	public static void main(String[] args) {

		ApplicationContext c = SpringApplication.run(HrApplication.class, args);
		
// beanek kilistázása:
//		ApplicationContext ac = SpringApplication.run(HrApplication.class, args);
//		String [] beans = ac.getBeanDefinitionNames();
//		Arrays.sort(beans);
//		for (String string : beans) {
//			System.out.println(string);
//		}
		
	}

	@Override
	public void run(String... args) throws Exception {

		initDbService.clearDB();
		initDbService.insertTestData();
		
		
		
//		System.out.println("minimum 10 éve dolgozó: "
//				+ salaryService.getNewSalary(new Employee(100_000, LocalDate.of(2010, 9, 20))));
//		System.out.println("minimum 5 éve dolgozó: "
//				+ salaryService.getNewSalary(new Employee(100_000, LocalDate.of(2015, 9, 20))));
//		System.out.println("minimum 2,5 éve dolgozó: "
//				+ salaryService.getNewSalary(new Employee(100_000, LocalDate.of(2017, 3, 20))));
//		System.out.println("2,5 éve még nem dolgozó: "
//				+ salaryService.getNewSalary(new Employee(100_000, LocalDate.of(2021, 3, 30))));
	}

}
