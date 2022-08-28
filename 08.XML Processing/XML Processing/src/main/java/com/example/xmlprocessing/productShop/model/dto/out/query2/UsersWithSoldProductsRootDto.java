package com.example.xmlprocessing.productShop.model.dto.out.query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithSoldProductsRootDto {

    private List<UsersWithSoldProductsDto> users;

    public List<UsersWithSoldProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UsersWithSoldProductsDto> users) {
        this.users = users;
    }
}
