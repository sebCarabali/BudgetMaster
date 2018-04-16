package de.deadlocker8.budgetmaster.entities;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Payment
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ID;
	private Integer amount;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private DateTime date;

	@ManyToOne
	private Account account;

	@ManyToOne
	private Category category;

	private String name;
	private String description;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Tag> tags;
	//	private RepeatingDefinition repeatingDefinition;

	public Payment()
	{
	}

	public Integer getID()
	{
		return ID;
	}

	public void setID(Integer ID)
	{
		this.ID = ID;
	}

	public Integer getAmount()
	{
		return amount;
	}

	public void setAmount(Integer amount)
	{
		this.amount = amount;
	}

	public DateTime getDate()
	{
		return date;
	}

	public void setDate(DateTime date)
	{
		this.date = date;
	}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<Tag> getTags()
	{
		return tags;
	}

	public void setTags(List<Tag> tags)
	{
		this.tags = tags;
	}

	@Override
	public String toString()
	{
		return "Payment{" +
				"ID=" + ID +
				", amount=" + amount +
				", date=" + date +
				", account=" + account +
				", category=" + category +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", tags=" + tags +
				'}';
	}
}