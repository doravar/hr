package hu.webuni.hr.doravar.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import hu.webuni.hr.doravar.model.Employee;

public class HrUser extends User {		// azért hozunk létre custom usert, hogy a teljes employee-t is beletehessük (csekkolni szeretnénk az id-ját, managert stb)
	private Employee employee;

	public HrUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Employee employee) {
		super(username, password, authorities);
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
