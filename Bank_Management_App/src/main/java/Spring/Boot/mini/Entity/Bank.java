package Spring.Boot.mini.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Bank {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Integer bankId;
private String bankName;
@Column(unique=true,length=11,nullable=false)
private String ifsc;
@Column(unique=true)
private String branchName;
@Column(unique=true,length=10)
private Long contactNumber;
public Integer getBankId() {
	return bankId;
}
public void setBankId(Integer bankId) {
	this.bankId = bankId;
}
public String getBankName() {
	return bankName;
}
public void setBankName(String bankName) {
	this.bankName = bankName;
}
public String getIfsc() {
	return ifsc;
}
public void setIfsc(String ifsc) {
	this.ifsc = ifsc;
}
public String getBranchName() {
	return branchName;
}
public void setBranchName(String branchName) {
	this.branchName = branchName;
}
public Long getContactNumber() {
	return contactNumber;
}
public void setContactNumber(Long contactNumber) {
	this.contactNumber = contactNumber;
}
@Override
public String toString() {
	return "Bank [bankId=" + bankId + ", bankName=" + bankName + ", ifsc=" + ifsc + ", branchName=" + branchName
			+ ", contactNumber=" + contactNumber + "]";
}

//1-* relation between bank and account
@JsonIgnore
@OneToMany(cascade = CascadeType.ALL, mappedBy = "bank")
private List<Account> account;

public List<Account> getAccount() {
	return account;
}
public void setAccount(List<Account> account) {
	this.account = account;
}


//1-1 relation between bank and address
@JoinColumn
@OneToOne(cascade=CascadeType.ALL)
private Address address;
public Address getAddress() {
	return address;
}
public void setAddress(Address address) {
	this.address = address;
}






}
