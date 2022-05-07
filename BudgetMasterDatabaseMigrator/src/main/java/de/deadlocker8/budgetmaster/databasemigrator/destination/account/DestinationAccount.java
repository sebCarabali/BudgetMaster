package de.deadlocker8.budgetmaster.databasemigrator.destination.account;


import de.deadlocker8.budgetmaster.databasemigrator.destination.ProvidesID;
import de.deadlocker8.budgetmaster.databasemigrator.destination.TableNames;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = TableNames.ACCOUNT)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DestinationAccount implements ProvidesID
{
	@Id
	private Integer ID;

	@Column(unique = true)
	private String name;

	@Column(name = "is_selected")
	private boolean isSelected;

	@Column(name = "is_default")
	private boolean isDefault;

	@Column(name = "account_state")
	private Integer accountState;

	@Column(name = "icon_reference_id")
	private Integer iconReferenceID;

	private Integer type;
}
