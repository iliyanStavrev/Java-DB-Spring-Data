package hiberspring.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeSeedRootDto {

    private List<EmployeeSeedDto> employee;

    public List<EmployeeSeedDto> getEmployee() {
        return employee;
    }

    public void setEmployee(List<EmployeeSeedDto> employee) {
        this.employee = employee;
    }
}
