package com.example.xmlprocessing.productShop.model.dto.out.query4;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersAndProductsRootDto {

    @XmlAttribute(name = "count")
    private Integer count;
    @XmlElement(name = "user")
    private List<UserNameAndAgeDto> users;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<UserNameAndAgeDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserNameAndAgeDto> users) {
        this.users = users;
    }
}
