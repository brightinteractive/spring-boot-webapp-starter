package com.bright.appstarter.user;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "user_account")
// 'user' is a reserved word in most DBs
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String emailAddress;
	private String passwordHash;

	@ElementCollection
	@CollectionTable(name = "user_has_role")
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	Collection<Role> roles = Collections.emptyList();

	public User()
	{
	}

	public User(Long id)
	{
		this.id = id;
	}

	public User(String emailAddress,
		String passwordHash)
	{
		this.passwordHash = passwordHash;
		this.emailAddress = emailAddress;
	}

	public User(Long id,
		String emailAddress,
		String passwordHash)
	{
		this.id = id;
		this.passwordHash = passwordHash;
		this.emailAddress = emailAddress;
	}

	public User(Long id,
		String emailAddress,
		String passwordHash,
		Collection<Role> roles)
	{
		this.id = id;
		this.passwordHash = passwordHash;
		this.emailAddress = emailAddress;
		this.roles = roles;
	}

	public User(String emailAddress,
		String passwordHash,
		Collection<Role> roles)
	{
		this.passwordHash = passwordHash;
		this.emailAddress = emailAddress;
		this.roles = roles;
	}

	public Long getId()
	{
		return id;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getPasswordHash()
	{
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash)
	{
		this.passwordHash = passwordHash;
	}

	public Collection<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Collection<Role> roles)
	{
		this.roles = roles;
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
			.append(emailAddress)
			.toHashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof User))
		{
			return false;
		}
		User that = (User) obj;
		return new EqualsBuilder()
			.append(this.emailAddress, that.emailAddress)
			.isEquals();
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this)
			.append(id)
			.append(emailAddress)
			.append(roles)
			.toString();
	}
}
