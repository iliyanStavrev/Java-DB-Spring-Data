package com.example.xmlprocessing.carDealer.model.dto.query4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartsRootDto {

    private List<PartsDto> part;

    public List<PartsDto> getPart() {
        return part;
    }

    public void setPart(List<PartsDto> part) {
        this.part = part;
    }
}
