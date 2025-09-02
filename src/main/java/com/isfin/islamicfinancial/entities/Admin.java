// Admin.java
package com.isfin.islamicfinancial.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User {
    public Admin() {
        super();
    }
}
