package com.jhmarryme.security;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.io.Serializable;

@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1448841352566743056L;
    @Id
    private Long id;

    private String name;

}