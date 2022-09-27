package com.t95.t95backend.returnBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReturnUserInfo {
	private String name;
	private Long id;
	private String jwt;
	
	
	public ReturnUserInfo(String name, Long id) {
		super();
		this.name = name;
		this.id = id;
	}


	public ReturnUserInfo(String name, Long id, String jwt) {
		super();
		this.name = name;
		this.id = id;
		this.jwt = jwt;
	}
	
	
	
}
