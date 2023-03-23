package com.example.backendhvz.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HvZUser {
    @Id
    @Column(name = "hvzuser_id")
    private String id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    public HvZUser(String id, String first_name, String last_name) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
    }

    @Column(name = "complete")
    private boolean complete;

    public void setUid(String uid) {
        this.id = uid;
    }

    public void setComplete(boolean b) {
        this.complete = b;
    }

    public String getUid() {
        return this.id;
    }
}
