package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity(name = "wizard_deposits")
public class WizardDeposits extends BaseEntity{

    private String firstName;
    private String lastName;
    private String notes;
    private int age;
    private String magicWandCreator;
    private int magicWandSize;
    private String depositGroup;
    private LocalDateTime depositStartDate;
    private  float depositAmount;
    private  float depositInterest;
    private  float depositCharge;
    private  LocalDateTime depositExpirationDate;
    private Boolean isDepositExpired;

    public WizardDeposits() {
    }

    @Column(name = "first_name",length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "last_name",nullable = false, length = 60)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(columnDefinition = "TEXT")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "magic_wand_creator",length = 100)
    public String getMagicWandCreator() {
        return magicWandCreator;
    }

    public void setMagicWandCreator(String magicWandCreator) {
        this.magicWandCreator = magicWandCreator;
    }

    @Column(name = "magic_wand_size",nullable = false)
    public int getMagicWandSize() {
        return magicWandSize;
    }

    public void setMagicWandSize(int magicWandSize) {
        this.magicWandSize = magicWandSize;
    }
@Column(name = "deposit_group",length = 20)
    public String getDepositGroup() {
        return depositGroup;
    }

    public void setDepositGroup(String depositGroup) {
        this.depositGroup = depositGroup;
    }
@Column(name = "deposit_start_date")
    public LocalDateTime getDepositStartDate() {
        return depositStartDate;
    }

    public void setDepositStartDate(LocalDateTime depositStartDate) {
        this.depositStartDate = depositStartDate;
    }
@Column(name = "deposit_amount")
    public float getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(float depositAmount) {
        this.depositAmount = depositAmount;
    }
@Column(name = "deposit_interest")
    public float getDepositInterest() {
        return depositInterest;
    }

    public void setDepositInterest(float depositInterest) {
        this.depositInterest = depositInterest;
    }
@Column(name = "deposit_charge")
    public float getDepositCharge() {
        return depositCharge;
    }

    public void setDepositCharge(float depositCharge) {
        this.depositCharge = depositCharge;
    }
@Column(name = "deposit_expiration_date")
    public LocalDateTime getDepositExpirationDate() {
        return depositExpirationDate;
    }

    public void setDepositExpirationDate(LocalDateTime depositExpirationDate) {
        this.depositExpirationDate = depositExpirationDate;
    }
@Column(name = "is_deposit_expired")
    public Boolean getDepositExpired() {
        return isDepositExpired;
    }

    public void setDepositExpired(Boolean depositExpired) {
        isDepositExpired = depositExpired;
    }
}
