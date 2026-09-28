	package com.baitap.jwt_nimbus_demo.entity;
	
	
	import jakarta.persistence.*;
	import lombok.*;
	
	@Entity
	@Table(name = "users")
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class User {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	
	    @Column(
	            name = "username",
	            nullable = false,
	            unique = true,
	            length = 50,
	            columnDefinition = "NVARCHAR(50)"
	    )
	    private String username;
	
	    @Column(
	            name = "password",
	            nullable = false,
	            columnDefinition = "NVARCHAR(255)"
	    )
	    private String password;
	
	    @Column(
	            name = "full_name",
	            nullable = false,
	            columnDefinition = "NVARCHAR(100)"
	    )
	    private String fullName;
	
	    @Column(
	            name = "email",
	            nullable = false,
	            unique = true,
	            columnDefinition = "NVARCHAR(100)"
	    )
	    private String email;
	
	    @Column(
	            name = "role",
	            nullable = false,
	            columnDefinition = "NVARCHAR(30)"
	    )
	    private String role;
	
	    @Column(nullable = false)
	    private Boolean enabled = true;
	
	}
