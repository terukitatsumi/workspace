package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

// クラス名 ≒ テーブル名
// テーブルに関連するアノテーションの設定
@Entity
@Table(name = "EMPLOYEES")
public class Employee {

    // フィールド ≒ カラム
    // カラムに関連するアノテーションの設定
    @Id
    @SequenceGenerator(name = "EMPLOYEE_ID_GENERATOR", sequenceName = "EMPLOYEE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_ID_GENERATOR")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DEPARTMENT")
    private String department;

    // 以下はすべてアクセサメソッド
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}