package edu.agh.zp.hibernate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "test_hibernate")

public class TestEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="name", length = 13)
    private String name;

    @Column(name="some_number", length = 13)
    private int some_number;

    @Override
    public String toString() {
        return "EmployeeEntity [id=" + id + ", Name=" + name +
                ", number=" + some_number;
    }

    public TestEntity(){};

    public TestEntity(String name,int num )
    {
        this.name = name;
        this.some_number = num;
    }

    public int getNum(){
        return this.some_number;
    }

    public String getName(){
        return this.name;
    }
}